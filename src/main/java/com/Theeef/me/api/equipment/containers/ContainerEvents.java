package com.Theeef.me.api.equipment.containers;

import com.Theeef.me.Echovale;
import com.Theeef.me.api.equipment.Equipment;
import com.Theeef.me.util.NBTHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ContainerEvents implements Listener {

    @EventHandler
    public void containerRip(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.DROPPED_ITEM && Container.isContainer(((Item) event.getEntity()).getItemStack())) {
            Bukkit.getScheduler().runTaskLater(Echovale.getPlugin(Echovale.class), () -> {
                Container container = new Container(((Item) event.getEntity()).getItemStack());

                if (event.getEntity().isDead()) {
                    for (ContainerEquipment equipment : container.getContents())
                        if (equipment != null)
                            event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), equipment.getItemStack());
                } else {
                    ContainerEquipment drop = null;

                    while (drop == null && !container.isEmpty())
                        drop = container.getContents().get((int) (Math.random() * container.getContents().size()));

                    if (drop != null)
                        event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), drop.getItemStack());
                }
            }, 1L);
        }
    }

    // TODO: SAFER, MORE LOGICAL WAY OF KEEPING CONTAINER AND INVENTORY CONTENTS CONSISTANT

    @EventHandler
    public void changeContents(InventoryClickEvent event) {
        Container container = getInventoryContainer(event.getInventory());

        if (event.getClickedInventory() != null && container != null) {
            double weight = weighInventory(event.getInventory());
            double maxWeight = container.getCapacity();

            if (event.isShiftClick() && event.getClickedInventory().getType() == InventoryType.PLAYER && event.getCurrentItem() != null) {
                double itemWeight = Equipment.weighItem(event.getCurrentItem());
                int amountCanMove = (int) ((maxWeight - weight) / itemWeight);

                if (event.getCurrentItem().getAmount() > amountCanMove) {
                    event.setCancelled(true);
                    event.getCurrentItem().setAmount(event.getCurrentItem().getAmount() - amountCanMove);

                    ItemStack item = event.getCurrentItem().clone();
                    item.setAmount(amountCanMove);
                    event.getInventory().addItem(item);

                    if (amountCanMove == 0)
                        containerFull((Player) event.getWhoClicked());
                }
            } else if (!event.isShiftClick() && event.getClickedInventory().getType() != InventoryType.PLAYER && event.getCursor().hasItemMeta() && (event.getCurrentItem() == null || event.getCurrentItem().isSimilar(event.getCursor()))) {
                double itemWeight = Equipment.weighItem(event.getCursor());
                int amountCanMove = (int) ((maxWeight - weight) / itemWeight);
                int amountMoving = Math.min(64 - (event.getCurrentItem() == null ? 0 : event.getCurrentItem().getAmount()), event.getCursor().getAmount());

                if (amountCanMove <= 0) {
                    event.setCancelled(true);
                    containerFull((Player) event.getWhoClicked());
                } else if (!event.isRightClick() && amountMoving > amountCanMove) {
                    ItemStack item = event.getCursor().clone();
                    item.setAmount(amountCanMove);
                    event.getCursor().setAmount(event.getCursor().getAmount() - amountCanMove);
                    event.getClickedInventory().setItem(event.getSlot(), item);
                    event.setCancelled(true);
                }
            } else if (!event.isShiftClick() && event.getClickedInventory().getType() != InventoryType.PLAYER && event.getCursor().hasItemMeta() && event.getCurrentItem() != null) {
                double currentWeight = Equipment.weighItem(event.getCurrentItem());
                double cursorWeight = Equipment.weighItem(event.getCursor());

                if (weight - currentWeight + cursorWeight > maxWeight) {
                    event.setCancelled(true);
                    containerFull((Player) event.getWhoClicked());
                }
            }
        }
    }

    private double weighInventory(Inventory inventory) {
        double weight = 0;

        for (ItemStack item : inventory.getContents())
            if (item != null) {
                Equipment equipment = Equipment.fromItem(item);

                if (equipment instanceof CommonEquipment)
                    weight += ((CommonEquipment) equipment).getWeight() * item.getAmount();
            }

        return weight;
    }

    private int countItems(Inventory inventory) {
        int count = 0;

        for (ItemStack item : inventory.getContents())
            if (item != null)
                count++;

        return count;
    }

    @EventHandler
    public void changeContents(InventoryDragEvent event) {
        if (event.getInventory().getHolder() != null)
            return;

        Container container = getInventoryContainer(event.getInventory());

        if (container != null) {
            double weight = 0;

            for (int slot : event.getNewItems().keySet())
                if (slot < event.getInventory().getSize())
                    weight += Equipment.weighItem(event.getNewItems().get(slot)) * event.getNewItems().get(slot).getAmount();

            if (weighInventory(event.getInventory()) + weight > container.getCapacity()) {
                event.setCancelled(true);
                containerFull((Player) event.getWhoClicked());
            }
        }
    }

    private void containerFull(Player player) {
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 1.0f, 0.0f);
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1.0f, 0.0f);
        player.sendMessage(ChatColor.RED + "That container is full");
    }

    @EventHandler
    public void saveContainerContents(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() == null) {
            Container container = getInventoryContainer(event.getInventory());

            if (container != null) {
                // Update Item NBT with contents
                for (ItemStack item : container.getContainerItems()) {
                    container.setContents(event.getInventory().getContents());
                    item.setItemMeta(container.getItemStack().getItemMeta());
                }
            }
        }
    }

    private static Container getInventoryContainer(Inventory inventory) {
        for (Container container : Container.openInventories.keySet())
            if (Container.openInventories.get(container).equals(inventory))
                return container;

        return null;
    }

    @EventHandler
    public void openContainer(PlayerInteractEvent event) {
        ItemStack item = event.getItem();


        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (item != null && item.hasItemMeta()) {
                if (Container.isContainer(item)) {
                    Container container = new Container(item);
                    container.open(event.getPlayer(), item);
                    event.setCancelled(true);
                }
            }
        }
    }
}

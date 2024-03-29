package com.Theeef.me.api.equipment.containers;

import com.Theeef.me.Echovale;
import com.Theeef.me.api.equipment.Equipment;
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
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ContainerEvents implements Listener {

    @EventHandler
    public void containerRip(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.DROPPED_ITEM && Container.isContainer(((Item) event.getEntity()).getItemStack())) {
            Bukkit.getScheduler().runTaskLater(Echovale.getPlugin(Echovale.class), () -> {
                Container container = Container.getContainerFromItem(((Item) event.getEntity()).getItemStack());

                if (event.getEntity().isDead()) {
                    for (ItemQuantity equipment : container.getContents())
                        if (equipment != null)
                            event.getEntity().getWorld().dropItemNaturally(event.getEntity().getLocation(), equipment.getItemStack());
                } else {
                    ItemQuantity drop = null;

                    while (drop == null && container.hasContents())
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

        if (event.getClickedInventory() == null || container == null)
            return;

        if (Container.isContainer(event.getCurrentItem()) && container.getUUID().equals(Container.getContainerUUID(event.getCurrentItem()))) {
            event.setCancelled(true);
            return;
        } else if (event.getAction() == InventoryAction.HOTBAR_SWAP) {
            ItemStack item = event.getView().getBottomInventory().getItem(event.getHotbarButton());

            if (Container.isContainer(item) && container.getUUID().equals(Container.getContainerUUID(item)))
                event.setCancelled(true);
        }

        double weight = weighInventory(event.getInventory());
        double maxWeight = container.getCapacity();

        if (event.getAction() == InventoryAction.HOTBAR_SWAP && event.getClickedInventory().getType() != InventoryType.PLAYER && event.getCurrentItem() == null) {
            ItemStack moving = event.getView().getBottomInventory().getItem(event.getHotbarButton());
            int amountCanMove = Math.max(0, (int) ((maxWeight - weight) / Equipment.weighItem(moving, false)));

            if (moving.getAmount() > amountCanMove && Equipment.weighItem(moving, false) > 0) {
                event.setCancelled(true);

                moving.setAmount(moving.getAmount() - amountCanMove);

                ItemStack item = moving.clone();
                item.setAmount(amountCanMove);
                event.getInventory().setItem(event.getSlot(), item);

                if (amountCanMove == 0)
                    containerFull((Player) event.getWhoClicked());
            }
        } else if (event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY && event.getClickedInventory().getType() == InventoryType.PLAYER) {
            double itemWeight = Equipment.weighItem(event.getCurrentItem(), false);
            int amountCanMove = Math.max(0, (int) ((maxWeight - weight) / itemWeight));

            if (event.getCurrentItem().getAmount() > amountCanMove && itemWeight > 0) {
                event.setCancelled(true);
                event.getCurrentItem().setAmount(event.getCurrentItem().getAmount() - amountCanMove);

                ItemStack item = event.getCurrentItem().clone();
                item.setAmount(amountCanMove);
                event.getInventory().addItem(item);

                if (amountCanMove == 0)
                    containerFull((Player) event.getWhoClicked());
            }
        } else if (event.getAction().name().startsWith("PLACE") && event.getClickedInventory().getType() != InventoryType.PLAYER) {
            double itemWeight = Equipment.weighItem(event.getCursor(), false);
            int amountCanMove = Math.max(0, (int) ((maxWeight - weight) / itemWeight));
            int amountMoving = Math.min(64 - (event.getCurrentItem() == null ? 0 : event.getCurrentItem().getAmount()), event.getCursor().getAmount());

            if (itemWeight <= 0)
                return;

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
        } else if (event.getAction() == InventoryAction.SWAP_WITH_CURSOR && event.getClickedInventory().getType() != InventoryType.PLAYER) {
            double currentWeight = Equipment.weighItem(event.getCurrentItem(), true);
            double cursorWeight = Equipment.weighItem(event.getCursor(), true);

            if (weight - currentWeight + cursorWeight > maxWeight) {
                event.setCancelled(true);
                containerFull((Player) event.getWhoClicked());
            }
        } else if (event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD && event.getClickedInventory().getType() != InventoryType.PLAYER) {
            double currentWeight = Equipment.weighItem(event.getCurrentItem(), false) * event.getCurrentItem().getAmount();
            ItemStack switchItem = event.getView().getBottomInventory().getItem(event.getHotbarButton());
            double switchWeight = Equipment.weighItem(switchItem, true);

            if (weight - currentWeight + switchWeight > maxWeight) {
                event.setCancelled(true);
                containerFull((Player) event.getWhoClicked());
            }
        }
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
                    if (Container.isContainer(event.getNewItems().get(slot)) && container.getUUID().equals(Container.getContainerUUID(event.getNewItems().get(slot)))) {
                        event.setCancelled(true);
                        return;
                    } else
                        weight += Equipment.weighItem(event.getNewItems().get(slot), true);

            if (weighInventory(event.getInventory()) + weight > container.getCapacity()) {
                event.setCancelled(true);
                containerFull((Player) event.getWhoClicked());
            }
        }
    }

    @EventHandler
    public void saveContainerContents(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() == null) {
            Container container = getInventoryContainer(event.getInventory());

            if (container != null)
                for (ItemStack item : container.getContainerItems())
                    container.setContents(event.getInventory().getContents());
        }
    }

    @EventHandler
    public void openContainer(PlayerInteractEvent event) {
        ItemStack item = event.getItem();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (item != null && item.hasItemMeta()) {
                if (Container.isContainer(item)) {
                    Container container = Container.getContainerFromItem(item);
                    container.open(event.getPlayer(), item);
                    event.setCancelled(true);
                }
            }
        }
    }

    // Helper methods
    private double weighInventory(Inventory inventory) {
        double weight = 0;

        for (ItemStack item : inventory.getContents())
            if (item != null)
                weight += Equipment.weighItem(item, true);

        return weight;
    }

    private int countItems(Inventory inventory) {
        int count = 0;

        for (ItemStack item : inventory.getContents())
            if (item != null)
                count++;

        return count;
    }

    private void containerFull(Player player) {
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 1.0f, 0.0f);
        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_GENERIC, 1.0f, 0.0f);
        player.sendMessage(ChatColor.RED + "That container is full");
    }

    // Static methods
    private static Container getInventoryContainer(Inventory inventory) {
        for (Container container : Container.openInventories.keySet())
            if (Container.openInventories.get(container).equals(inventory))
                return container;

        return null;
    }
}

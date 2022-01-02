package com.Theeef.me.interaction.character;

import com.Theeef.me.Echovale;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.choice.ChoiceResult;
import com.Theeef.me.api.common.choice.MultipleOption;
import com.Theeef.me.api.common.choice.Option;
import com.Theeef.me.util.NBTHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;

public class ChoiceMenu {

    public static HashMap<Integer, ChoiceMenu> menuMap = new HashMap<>();
    public static HashMap<ChoiceMenu, Inventory> inventoryMap = new HashMap<>();

    private final String name;
    private final String previousPage;
    private final ChoiceMenu previousMenu;
    private Inventory previousInventory;
    private final ChoiceResult result;
    private final APIReference specificReference;

    public ChoiceMenu(String name, String previousPage, ChoiceMenu previousMenu, ChoiceResult result, APIReference specificReference) {
        this.name = "Choice: " + name;
        this.previousPage = previousPage;
        this.result = result;
        this.specificReference = specificReference;
        this.previousMenu = previousMenu;
        this.previousInventory = null;
    }

    public ChoiceMenu(String name, Inventory previousInventory, ChoiceResult result) {
        this(name, null, null, result, null);

        this.previousInventory = previousInventory;
        ChoiceMenu.menuMap.put(hashCode(), this);
    }

    public ChoiceMenu(String name, ChoiceMenu previousMenu, ChoiceResult result) {
        this(name, null, previousMenu, result, null);
    }

    public ChoiceMenu(String name, String previousPage, ChoiceResult result) {
        this(name, previousPage, null, result, null);
    }

    public void open(Player player) {
        for (Option option : this.result.getNonexcludedOptions())
            if (option.getOptionType() == Option.OptionType.CHOICE || (option.getOptionType() == Option.OptionType.MULTIPLE && this.result.getMultiOptionChoices((MultipleOption) option) != null)) {
                open(player, true);
                return;
            }

        open(player, false);
    }

    public void open(Player player, boolean startLoop) {
        CharacterCreator creator = CharacterCreator.getWIPCharacter(player);
        Inventory inventory = Bukkit.createInventory(null, 9 * ((this.result.getNonexcludedOptions().size() - 1) / 9 + 3), this.name);
        List<Option> options = this.result.getNonexcludedOptions();

        if (getChoiceResult().getChoice().getType().equals("expertise"))
            options.retainAll(creator.toOptionsList(creator.getCurrentProficiencies()));

        for (Option option : options)
            inventory.addItem(option.getOptionItem(this.result, creator));

        if (this.specificReference != null)
            for (ItemStack item : inventory.getContents())
                if (item != null)
                    NBTHandler.addString(item, "specificReference", this.specificReference.getUrl());

        for (ItemStack item : inventory.getContents())
            if (item != null)
                attachToItem(item);


        if (this.previousPage != null)
            inventory.setItem(inventory.getSize() - 9, attachToItem(CharacterCreatorItems.previousPage(this.previousPage)));
        else if (this.previousMenu != null)
            inventory.setItem(inventory.getSize() - 9, this.previousMenu.attachToItem(CharacterCreatorItems.previousPage("previous")));
        else if (this.previousInventory != null)
            inventory.setItem(inventory.getSize() - 9, ChoiceMenu.attachInventoryToItem(CharacterCreatorItems.previousPage("previous"), this));

        player.openInventory(inventory);

        ChoiceMenu.inventoryMap.put(this, inventory);

        ChoiceMenu menu = this;

        if (startLoop) {
            new BukkitRunnable() {
                public void run() {
                    if (ChoiceMenu.inventoryMap.containsKey(menu) && ChoiceMenu.inventoryMap.get(menu).equals(player.getOpenInventory().getTopInventory()))
                        open(player);
                }
            }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);
        }
    }

    public static ItemStack attachInventoryToItem(ItemStack item, ChoiceMenu owningMenu) {
        NBTHandler.addString(item, "attachedInventory", Integer.toString(owningMenu.hashCode()));

        return item;
    }

    public static Inventory getAttachedInventory(ItemStack item) {
        return NBTHandler.hasString(item, "attachedInventory") && ChoiceMenu.menuMap.containsKey(Integer.parseInt(NBTHandler.getString(item, "attachedInventory"))) && ChoiceMenu.menuMap.get(Integer.parseInt(NBTHandler.getString(item, "attachedInventory"))).getInventory() != null ? ChoiceMenu.menuMap.get(Integer.parseInt(NBTHandler.getString(item, "attachedInventory"))).getInventory() : null;
    }

    // Helper methods
    public ItemStack attachToItem(ItemStack item) {
        NBTHandler.addString(item, "choiceMenuCode", Integer.toString(hashCode()));

        if (!ChoiceMenu.menuMap.containsKey(hashCode()))
            ChoiceMenu.menuMap.put(hashCode(), this);

        return item;
    }

    // Getter methods
    public String getName() {
        return this.name;
    }

    public String getPreviousPage() {
        return this.previousPage;
    }

    public ChoiceResult getChoiceResult() {
        return this.result;
    }

    public APIReference getSpecificReference() {
        return this.specificReference;
    }

    public Inventory getInventory() {
        return this.previousInventory;
    }

    // Static methods

    public static ChoiceMenu getMenuFromItem(ItemStack item) {
        return NBTHandler.hasString(item, "choiceMenuCode") ? ChoiceMenu.menuMap.get(Integer.parseInt(NBTHandler.getString(item, "choiceMenuCode"))) : null;
    }
}

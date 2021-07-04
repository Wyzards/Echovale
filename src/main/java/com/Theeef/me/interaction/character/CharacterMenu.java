package com.Theeef.me.interaction.character;

import com.Theeef.me.api.chardata.AbilityScore;
import com.Theeef.me.api.classes.DNDClass;
import com.Theeef.me.util.NBTHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CharacterMenu {

    public static void characterMenu(Player player, Character character) {
        Inventory inventory = Bukkit.createInventory(null, 9, "Character: " + character.getName());

        inventory.setItem(0, abilityScoresItem(character));

        player.openInventory(inventory);
    }

    public static ItemStack abilityScoresItem(Character character) {
        ItemStack item = new ItemStack(Material.TARGET);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.RESET + "Ability Scores");
        item.setItemMeta(meta);

        abilityScoresLore(item, character);

        return NBTHandler.addString(item, "characterUUID", character.getUUID().toString());
    }

    private static void abilityScoresLore(ItemStack item, Character character) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        assert meta != null;

        for (AbilityScore score : AbilityScore.values())
            lore.add(ChatColor.GOLD + score.getFullName() + ": " + ChatColor.WHITE + character.getAbilityScore(score));

        lore.add("");
        lore.add(ChatColor.WHITE + "Click for more info");
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public static void characters(Player player) {
        Inventory inventory = Bukkit.getServer().createInventory(null, 9, "Characters");
        List<Character> characters = Character.getUsersCharacters(player.getUniqueId());

        for (Character character : characters)
            inventory.addItem(characterItem(character));

        player.openInventory(inventory);
    }

    private static ItemStack characterItem(Character character) {
        ItemStack item = new ItemStack(Material.GLASS);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.RESET + character.getName());
        item.setItemMeta(meta);

        characterItemLore(item, character);

        return NBTHandler.addString(item, "characterUUID", character.getUUID().toString());
    }

    private static void characterItemLore(ItemStack item, Character character) {
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Race: " + ChatColor.WHITE + character.getRace().getName());
        lore.add(ChatColor.GRAY + "Classes:");

        for (DNDClass dndclass : character.getClasses().keySet())
            lore.add(ChatColor.WHITE + "- " + dndclass.getName() + (character.getSubclasses().containsKey(dndclass) ? " (" + character.getSubclasses().get(dndclass).getName() + ")" : "") + " ~ Level " + character.getClasses().get(dndclass));

        lore.add(ChatColor.GRAY + "Health: " + ChatColor.RED + character.getCurrentHitPoints() + "/" + character.getMaxHitPoints() + ChatColor.YELLOW + " (" + character.getTempHitPoints() + ")");
        lore.add("");
        lore.add(ChatColor.WHITE + "Click for more info");

        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

}

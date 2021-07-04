package com.Theeef.me.interaction.character;

import com.Theeef.me.api.races.Race;
import com.Theeef.me.util.NBTHandler;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class CharacterMenuEvents implements Listener {

    @EventHandler
    public void clickRacialTraitsPage(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getInventory().getHolder() == null && event.getView().getTitle().equals("Racial Traits")) {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();

            if (item == null)
                return;

            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());

            if (item.isSimilar(creator.previousPage(ChatColor.GRAY + "Return to the race menu")))
                creator.raceMenu();
        }
    }

    @EventHandler
    public void clickCharacterListMenu(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getInventory().getHolder() == null && event.getView().getTitle().equals("Characters")) {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();

            if (item == null)
                return;

            if (NBTHandler.hasString(item, "characterUUID")) {
                Character character = new Character(event.getWhoClicked().getUniqueId(), UUID.fromString(NBTHandler.getString(item, "characterUUID")));

                CharacterMenu.characterMenu((Player) event.getWhoClicked(), character);
            }
        }
    }

    @EventHandler
    public void clickRaceMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().equals("Race") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            event.setCancelled(true);

            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());

            assert creator != null;
            if (event.getCurrentItem() != null)
                if (event.getCurrentItem().isSimilar(creator.selectRaceItem()))
                    creator.menuPickRace((Player) event.getWhoClicked());
                else if (event.getCurrentItem().isSimilar(CharacterCreator.nextPage(ChatColor.GRAY + "Continue to class selection")))
                    creator.classMenu();
                else if (event.getCurrentItem().isSimilar(creator.raceTraitItem()))
                    creator.racialTraitMenu();
        }
    }

    @EventHandler
    public void clickSelectRaceMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().equals("Select your race") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            event.setCancelled(true);

            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());

            assert creator != null;

            if (event.getCurrentItem() != null)
                if (event.getCurrentItem().isSimilar(CharacterCreator.previousPage(ChatColor.GRAY + "Return to the race menu")))
                    creator.raceMenu();
                else if (NBTHandler.hasString(event.getCurrentItem(), "race")) {
                    creator.setRace(new Race(NBTHandler.getString(event.getCurrentItem(), "race")));
                    creator.raceMenu();
                }
        }
    }

    @EventHandler
    public void clickClassMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().equals("Class") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            event.setCancelled(true);

            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());

            assert creator != null;

            if (event.getCurrentItem() != null)
                if (event.getCurrentItem().isSimilar(CharacterCreator.previousPage(ChatColor.GRAY + "Return to the race page")))
                    creator.raceMenu();
        }
    }
}

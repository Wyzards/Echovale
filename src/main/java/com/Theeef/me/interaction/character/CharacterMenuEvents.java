package com.Theeef.me.interaction.character;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.chardata.Language;
import com.Theeef.me.api.chardata.Proficiency;
import com.Theeef.me.api.classes.DNDClass;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.choice.ChoiceResult;
import com.Theeef.me.api.races.Race;
import com.Theeef.me.api.races.Subrace;
import com.Theeef.me.api.races.Trait;
import com.Theeef.me.api.spells.Spell;
import com.Theeef.me.util.NBTHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class CharacterMenuEvents implements Listener {

    @EventHandler
    public void clickSelectClassMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().equals("Select A Class") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            event.setCancelled(true);

            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());
            ItemStack item = event.getCurrentItem();

            if (item == null)
                return;

            if (NBTHandler.hasString(item, "class")) {
                creator.setClass(new DNDClass(NBTHandler.getString(item, "class")));
                creator.classMenu();
            }
        }
    }

    @EventHandler
    public void clickChoiceMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().startsWith("Choice: ") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());
            ItemStack item = event.getCurrentItem();
            String previousPage = event.getInventory().getItem(event.getInventory().getSize() - 9) == null ? null : NBTHandler.getString(event.getInventory().getItem(event.getInventory().getSize() - 9), "goesTo");
            String nextPage = event.getInventory().getItem(event.getInventory().getSize() - 1) == null ? null : NBTHandler.getString(event.getInventory().getItem(event.getInventory().getSize() - 9), "goesTo");

            event.setCancelled(true);

            if (item == null)
                return;

            ChoiceResult result = null;
            Object option = null;
            Class<?> type = null;
            APIReference specificReference = NBTHandler.hasString(item, "specificReference") ? new APIReference(APIRequest.request(NBTHandler.getString(item, "specificReference"))) : null;

            // Check for various types, get choiceResults from creator then reconstruct inventory
            if (NBTHandler.hasString(item, "trait")) {
                type = Trait.class;
                option = new Trait(NBTHandler.getString(item, "trait"));
                Trait parent = ((Trait) option).getParent();

                if (parent != null)
                    result = creator.getSubtraitChoices().get(parent);
            } else if (NBTHandler.hasString(item, "language")) {
                type = Language.class;
                option = new Language(NBTHandler.getString(item, "language"));
                assert previousPage != null;
                result = previousPage.equals("subrace") ? creator.getSubraceLanguageChoiceResult() : creator.getRaceLanguageChoiceResult();
            } else if (NBTHandler.hasString(item, "proficiency")) {
                type = Proficiency.class;
                option = new Proficiency(NBTHandler.getString(item, "proficiency"));
                result = creator.getRaceProfChoiceResult();
            } else if (NBTHandler.hasString(item, "spell")) {
                type = Spell.class;
                option = new Spell(NBTHandler.getString(item, "spell"));
                result = creator.getTraitSpellChoices().get(new Trait(specificReference));
            } else if (NBTHandler.hasString(item, "race"))
                creator.setRace(new Race(NBTHandler.getString(item, "race")));
            else if (NBTHandler.hasString(item, "subrace"))
                creator.setSubrace(new Subrace(NBTHandler.getString(item, "subrace")));
            else if (NBTHandler.hasString(item, "class"))
                creator.setClass(new DNDClass(NBTHandler.getString(item, "class")));

            if (result != null) {
                if (result.alreadyChosen(option)) {
                    result.unchoose(option);
                } else
                    result.choose(option);

                ChoiceMenu menu = new ChoiceMenu(event.getView().getTitle().substring(8), previousPage, nextPage, result.getChoice(), result, type, specificReference);
                menu.open(creator.getPlayer());
            }
        }
    }

    @EventHandler
    public void clickSubraceTraitsMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().equals("Subrace Traits") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();
            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());

            if (item == null)
                return;

            if (NBTHandler.hasString(item, "trait")) {
                Trait trait = new Trait(NBTHandler.getString(item, "trait"));

                if (trait.getTraitSpecific() != null && trait.getTraitSpecific().getSpellOptions() != null) {
                    ChoiceMenu<Spell> menu = new ChoiceMenu<Spell>("Select " + trait.getTraitSpecific().getSpellOptions().getChoiceAmount() + (trait.getTraitSpecific().getSpellOptions().getChoiceAmount() > 1 ? " Spells " : " Spell"), "subrace traits", null, trait.getTraitSpecific().getSpellOptions(), creator.getTraitSpellChoices().get(trait), Spell.class, new APIReference(APIRequest.request(trait.getUrl())));
                    menu.open(creator.getPlayer());
                }
            }
        }
    }

    @EventHandler
    public void clickRacialTraitsPage(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getInventory().getHolder() == null && event.getView().getTitle().equals("Racial Traits")) {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();

            if (item == null)
                return;

            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());

            if (NBTHandler.hasString(item, "trait")) {
                Trait trait = new Trait(NBTHandler.getString(item, "trait"));

                if (trait.getTraitSpecific() != null && trait.getTraitSpecific().getSubtraitOptions() != null) {
                    creator.subtraitsMenu(trait, "racial traits");
                }
            }
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

    @EventHandler(priority = EventPriority.HIGH)
    public void clickCharacterCreationMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            event.setCancelled(true);

            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());
            ItemStack item = event.getCurrentItem();

            if (item == null)
                return;

            if (NBTHandler.hasString(item, "goesTo"))
                creator.goToPage(NBTHandler.getString(item, "goesTo"));
        }
    }
}

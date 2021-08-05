package com.Theeef.me.interaction.character;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.choice.*;
import com.Theeef.me.api.equipment.Equipment;
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
    public void clickMultiOptionsInfoPage(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().equals("Items") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            event.setCancelled(true);

            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());
            ItemStack item = event.getCurrentItem();

            if (item == null)
                return;

            ChoiceMenu menu = ChoiceMenu.getMenuFromItem(item);
            ChoiceResult result = menu.getChoiceResult(); // This result's options contains this multiOption
            MultipleOption multiOption = (MultipleOption) result.getChoice().getOptions().get(Integer.parseInt(NBTHandler.getString(item, "optionIndex")));

            if (event.getSlot() < multiOption.getItems().size() && multiOption.getItems().get(event.getSlot()).getOptionType() == Option.OptionType.CHOICE) {
                ChoiceMenu newMenu = new ChoiceMenu("Starting Equipment", event.getInventory(), result.getChoiceOptionResult(multiOption).getChoiceOptionResult(multiOption.getItems().get(event.getSlot())), Equipment.class);
                newMenu.open((Player) event.getWhoClicked());
            }
        }
    }

    @EventHandler
    public void clickStartingEquipmentMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().equals("Starting Equipment") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            event.setCancelled(true);

            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());
            ItemStack item = event.getCurrentItem();

            if (item == null)
                return;

            if (event.getSlot() >= creator.getDNDClass().getStartingEquipment().size() && event.getSlot() <= creator.getDNDClass().getStartingEquipment().size() + creator.getDNDClass().getStartingEquipmentOptions().size()) {
                ChoiceMenu menu = new ChoiceMenu("Starting Equipment", "starting equipment", creator.getStartingEquipmentChoiceResult().get(event.getSlot() - creator.getDNDClass().getStartingEquipment().size()), Equipment.class);
                menu.open(creator.getPlayer());
            }
        }
    }

    @EventHandler
    public void clickChoiceMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().startsWith("Choice: ") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());
            ItemStack item = event.getCurrentItem();
            event.setCancelled(true);

            if (item == null)
                return;

            ChoiceMenu menu = ChoiceMenu.getMenuFromItem(item);

            if (event.getSlot() < menu.getChoiceResult().getChoice().getOptions().size()) {
                Option option = menu.getChoiceResult().getChoice().getOptions().get(event.getSlot());

                switch (option.getOptionType()) {
                    case CHOICE:
                        ChoiceResult optionResult = menu.getChoiceResult().getChoiceOptionResult((ChoiceOption) option);

                        if (optionResult == null)
                            optionResult = new ChoiceResult(((ChoiceOption) option).getChoice());

                        if (menu.getChoiceResult().alreadyChosen(option) && event.getClick().isRightClick()) {
                            ChoiceMenu choiceMenu = new ChoiceMenu("Starting Equipment", menu, optionResult, Equipment.class);
                            choiceMenu.open((Player) event.getWhoClicked());
                        } else
                            chooseClick(menu, option, creator);

                        break;
                    case MULTIPLE:
                        if (event.getClick().isRightClick())
                            menu.multiOptionInfo(menu.getChoiceResult(), event.getSlot(), creator);
                        else
                            chooseClick(menu, option, creator);
                        break;
                    case SINGLE:
                        if (NBTHandler.hasString(item, "race"))
                            creator.setRace((SingleOption) option);
                        else if (NBTHandler.hasString(item, "subrace"))
                            creator.setSubrace((SingleOption) option);
                        else if (NBTHandler.hasString(item, "class"))
                            creator.setClass((SingleOption) option);
                        else
                            chooseClick(menu, option, creator);
                        break;
                }
            }
        }
    }


    private void chooseClick(ChoiceMenu menu, Option option, CharacterCreator creator) {
        if (menu.getChoiceResult().alreadyChosen(option))
            menu.getChoiceResult().unchoose(option);
        else if (!menu.getChoiceResult().isComplete())
            menu.getChoiceResult().choose(option);

        menu.open(creator.getPlayer());
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
                    ChoiceMenu<Spell> menu = new ChoiceMenu<Spell>("Select " + trait.getTraitSpecific().getSpellOptions().getChoiceAmount() + (trait.getTraitSpecific().getSpellOptions().getChoiceAmount() > 1 ? " Spells " : " Spell"), "subrace traits", creator.getTraitSpellChoices().get(trait), Spell.class, new APIReference(APIRequest.request(trait.getUrl())));
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

                if (trait.getTraitSpecific() != null && trait.getTraitSpecific().getSubtraitOptions() != null)
                    creator.subtraitsMenu(trait, "racial traits");
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
                if (NBTHandler.hasString(item, "parentInventory")) {
                    ChoiceMenu menu = ChoiceMenu.getMenuFromItem(item);
                    event.getWhoClicked().openInventory(menu.getParentInventory());
                } else if (NBTHandler.getString(item, "goesTo").equals("previous choice menu")) {
                    ChoiceMenu.getMenuFromItem(item).open((Player) event.getWhoClicked());
                    System.out.println("THING");
                } else
                    creator.goToPage(NBTHandler.getString(item, "goesTo"));
        }
    }
}

package com.Theeef.me.interaction.character;

import com.Theeef.me.APIRequest;
import com.Theeef.me.api.chardata.AbilityScore;
import com.Theeef.me.api.classes.Feature;
import com.Theeef.me.api.classes.subclasses.Subclass;
import com.Theeef.me.api.common.APIReference;
import com.Theeef.me.api.common.choice.*;
import com.Theeef.me.api.races.Trait;
import com.Theeef.me.util.NBTHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class CharacterMenuEvents implements Listener {

    @EventHandler
    public void clickAbilityScoresMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().startsWith("Ability Scores: ") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());
            ItemStack item = event.getCurrentItem();
            event.setCancelled(true);

            if (item == null || !Objects.equals(event.getClickedInventory(), event.getInventory()))
                return;

            if (NBTHandler.hasString(item, "decreaseAbility")) {
                creator.decreaseScore(new AbilityScore(NBTHandler.getString(item, "decreaseAbility")));
                creator.abilityScoresMenu();
            } else if (NBTHandler.hasString(item, "increaseAbility")) {
                creator.increaseScore(new AbilityScore(NBTHandler.getString(item, "increaseAbility")));
                creator.abilityScoresMenu();
            }
        }
    }

    @EventHandler
    public void clickSubclassFeatureMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().equals("Subclass") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());
            ItemStack item = event.getCurrentItem();
            event.setCancelled(true);

            if (item == null || !Objects.equals(event.getClickedInventory(), event.getInventory()))
                return;

            if (NBTHandler.hasString(item, "subclass"))
                creator.subclassFeatureMenu(Subclass.fromIndex(NBTHandler.getString(item, "subclass")));
        }
    }

    @EventHandler
    public void clickPerkMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().equals("Leveling Perks") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());
            ItemStack item = event.getCurrentItem();
            event.setCancelled(true);

            if (item == null || !Objects.equals(event.getClickedInventory(), event.getInventory()))
                return;

            int currentRow = Integer.parseInt(NBTHandler.getString(event.getClickedInventory().getItem(49), "row"));

            if (item.isSimilar(CharacterCreatorItems.perkMenuPrevPage()))
                creator.getMenus().perkInfoMenu(currentRow - 1);
            else if (item.isSimilar(CharacterCreatorItems.perkMenuNextPage()))
                creator.getMenus().perkInfoMenu(currentRow + 1);
        }
    }

    @EventHandler
    public void clickFeatureTableMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().equals("Level Features") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());
            ItemStack item = event.getCurrentItem();
            event.setCancelled(true);

            if (item == null || !Objects.equals(event.getClickedInventory(), event.getInventory()))
                return;

            int currentRow = Integer.parseInt(NBTHandler.getString(event.getClickedInventory().getItem(49), "row"));

            if (item.isSimilar(CharacterCreatorItems.previousFeatureRow()))
                creator.featureTable(currentRow - 1);
            else if (item.isSimilar(CharacterCreatorItems.nextFeatureRow()))
                creator.featureTable(currentRow + 1);
        }
    }

    @EventHandler
    public void clickSpellTableMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().equals("-Spell Slots per Spell Level-") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());
            ItemStack item = event.getCurrentItem();
            event.setCancelled(true);

            if (item == null || !Objects.equals(event.getClickedInventory(), event.getInventory()))
                return;

            int currentRow = Integer.parseInt(NBTHandler.getString(event.getClickedInventory().getItem(49), "row"));

            if (item.isSimilar(CharacterCreatorItems.spellTablePrevRow()))
                creator.spellcastingTable(currentRow - 1);
            else if (item.isSimilar(CharacterCreatorItems.spellTableNextRow()))
                creator.spellcastingTable(currentRow + 1);
        }
    }


    @EventHandler
    public void clickMultiInfo(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().equals("More Option Info") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());
            ItemStack item = event.getCurrentItem();
            event.setCancelled(true);

            if (item == null || !Objects.equals(event.getClickedInventory(), event.getInventory()))
                return;

            ChoiceMenu previousMenu = ChoiceMenu.getMenuFromItem(event.getClickedInventory().getItem(event.getInventory().getSize() - 9));
            assert previousMenu != null;
            MultipleOption multioption = (MultipleOption) previousMenu.getChoiceResult().getNonexcludedOptions().get(Integer.parseInt(NBTHandler.getString(event.getInventory().getItem(event.getInventory().getSize() - 9), "optionIndex")));

            if (multioption.getChoices().size() > 0 && event.getSlot() + 1 > multioption.getItems().size() && event.getSlot() < multioption.getItems().size() + multioption.getChoices().size()) {
                ChoiceOption option = multioption.getChoices().get(event.getSlot() - multioption.getItems().size());

                if (previousMenu.getChoiceResult().alreadyChosen(multioption))
                    option.choiceMenu(event.getInventory(), previousMenu.getChoiceResult().getMultiOptionChoices(multioption).get(option), creator);
            }
        }
    }

    @EventHandler
    public void clickStartingEquipmentMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && !event.getView().getTitle().equals("Background Starting Equipment") && event.getView().getTitle().endsWith("Starting Equipment") && !event.getView().getTitle().startsWith("Choice:") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            event.setCancelled(true);

            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());
            ItemStack item = event.getCurrentItem();

            if (item == null || !Objects.equals(event.getClickedInventory(), event.getInventory()))
                return;

            if (event.getSlot() >= creator.getDNDClass().getStartingEquipment().size() && event.getSlot() <= creator.getDNDClass().getStartingEquipment().size() + creator.getDNDClass().getStartingEquipmentOptions().size())
                new ChoiceMenu("Starting Equipment", "starting equipment", creator.getStartingEquipmentChoiceResult().get(event.getSlot() - creator.getDNDClass().getStartingEquipment().size())).open(creator.getPlayer());
        }
    }

    @EventHandler
    public void clickClassProficiencyChoicesMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().startsWith("Class Proficiency Choices") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            event.setCancelled(true);

            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());
            ItemStack item = event.getCurrentItem();

            if (item == null || !Objects.equals(event.getClickedInventory(), event.getInventory()))
                return;

            if (event.getSlot() <= creator.getDNDClass().getProficiencyChoices().size())
                new ChoiceMenu("Choose " + creator.getClassProficiencyChoiceResults().get(event.getSlot()).getChoice().getChoiceAmount() + " Proficiencies", "class proficiency choices", creator.getClassProficiencyChoiceResults().get(event.getSlot())).open(creator.getPlayer());
        }
    }

    @EventHandler
    public void clickBackgroundStartingEquipmentMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().equals("Background Equipment Choice") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            event.setCancelled(true);

            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());
            ItemStack item = event.getCurrentItem();

            if (item == null || !Objects.equals(event.getClickedInventory(), event.getInventory()))
                return;

            if (event.getSlot() >= creator.getBackground().getStartingEquipment().size() && event.getSlot() <= creator.getBackground().getStartingEquipment().size() + creator.getBackground().getStartingEquipmentOptions().size()) {
                ChoiceMenu menu = new ChoiceMenu("Background Equipment", "background equipment", creator.getBackgroundStartingEquipmentChoiceResult().get(event.getSlot() - creator.getBackground().getStartingEquipment().size()));
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

            if (item == null || !Objects.equals(event.getClickedInventory(), event.getInventory()))
                return;

            ChoiceMenu menu = ChoiceMenu.getMenuFromItem(item);

            if (menu == null)
                return;

            if (event.getSlot() < menu.getChoiceResult().getNonexcludedOptions().size()) {
                Option option = menu.getChoiceResult().getNonexcludedOptions().get(event.getSlot());

                switch (option.getOptionType()) {
                    case CHOICE:
                        if (event.getClick().isLeftClick())
                            chooseClick(menu, option, creator);
                        else if (event.getClick().isRightClick() && menu.getChoiceResult().alreadyChosen(option))
                            ((ChoiceOption) option).choiceMenu(menu, creator);
                        break;
                    case MULTIPLE:
                        if (event.getClick().isLeftClick())
                            chooseClick(menu, option, creator);
                        else if (event.getClick().isRightClick()) {
                            ((MultipleOption) option).optionInfo(menu, creator);
                        }
                        break;
                    case SINGLE:
                        if (NBTHandler.hasString(item, "race"))
                            creator.setRace((SingleOption) option);
                        else if (NBTHandler.hasString(item, "subrace"))
                            creator.setSubrace((SingleOption) option);
                        else if (NBTHandler.hasString(item, "class"))
                            creator.setClass((SingleOption) option);
                        else if (NBTHandler.hasString(item, "background"))
                            creator.setBackground((SingleOption) option);
                        else if (NBTHandler.hasString(item, "subclass"))
                            creator.setSubclass((SingleOption) option);
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
        else if (!menu.getChoiceResult().isComplete(false))
            menu.getChoiceResult().choose(option);

        menu.open(creator.getPlayer(), false);
    }

    @EventHandler
    public void clickSubraceTraitsMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && event.getView().getTitle().equals("Subrace Traits") && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();
            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());

            if (item == null || !Objects.equals(event.getClickedInventory(), event.getInventory()))
                return;

            if (NBTHandler.hasString(item, "trait")) {
                Trait trait = new Trait(NBTHandler.getString(item, "trait"));

                if (trait.getTraitSpecific() != null && trait.getTraitSpecific().getSpellOptions() != null)
                    new ChoiceMenu("Select " + trait.getTraitSpecific().getSpellOptions().getChoiceAmount() + (trait.getTraitSpecific().getSpellOptions().getChoiceAmount() > 1 ? " Spells " : " Spell"), "subrace traits", null, creator.getTraitSpellChoices().get(trait), new APIReference(APIRequest.request(trait.getUrl()))).open(creator.getPlayer());
            }
        }
    }

    @EventHandler
    public void clickRacialTraitsPage(InventoryClickEvent event) {
        if (event.getClickedInventory() != null && event.getInventory().getHolder() == null && event.getView().getTitle().equals("Racial Traits")) {
            event.setCancelled(true);

            ItemStack item = event.getCurrentItem();

            if (item == null || !event.getClickedInventory().equals(event.getInventory()))
                return;

            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());

            if (NBTHandler.hasString(item, "trait")) {
                Trait trait = new Trait(NBTHandler.getString(item, "trait"));

                if (trait.getTraitSpecific() != null && trait.getTraitSpecific().getSubtraitOptions() != null)
                    creator.getMenus().subtraitsMenu(trait, "racial traits");
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void clickCharacterCreationMenu(InventoryClickEvent event) {
        if (event.getInventory().getHolder() == null && CharacterCreator.hasWIPCharacter((Player) event.getWhoClicked())) {
            event.setCancelled(true);

            CharacterCreator creator = CharacterCreator.getWIPCharacter((Player) event.getWhoClicked());
            ItemStack item = event.getCurrentItem();

            if (item == null || !Objects.equals(event.getClickedInventory(), event.getInventory()))
                return;

            if (NBTHandler.hasString(item, "goesTo"))
                if (NBTHandler.getString(item, "goesTo").equals("previous")) {
                    if (ChoiceMenu.getMenuFromItem(item) != null)
                        Objects.requireNonNull(ChoiceMenu.getMenuFromItem(item)).open(creator.getPlayer());
                    else if (ChoiceMenu.getAttachedInventory(item) != null) {
                        creator.getPlayer().openInventory(Objects.requireNonNull(ChoiceMenu.getAttachedInventory(item)));
                        if (creator.getPlayer().getOpenInventory().getTitle().equals("More Option Info")) {
                            ChoiceResult result = Objects.requireNonNull(ChoiceMenu.getMenuFromItem(creator.getPlayer().getOpenInventory().getTopInventory().getItem(creator.getPlayer().getOpenInventory().getTopInventory().getSize() - 9))).getChoiceResult();
                            ((MultipleOption) result.getNonexcludedOptions().get(Integer.parseInt(NBTHandler.getString(creator.getPlayer().getOpenInventory().getTopInventory().getItem(creator.getPlayer().getOpenInventory().getTopInventory().getSize() - 9), "optionIndex")))).optionInfo(ChoiceMenu.getMenuFromItem(creator.getPlayer().getOpenInventory().getTopInventory().getItem(creator.getPlayer().getOpenInventory().getTopInventory().getSize() - 9)), creator);
                        }
                    }
                } else
                    creator.goToPage(NBTHandler.getString(item, "goesTo"));

            if (NBTHandler.hasString(item, "feature")) {
                Feature feature = Feature.fromIndex(NBTHandler.getString(item, "feature"));

                if (feature.getFeatureSpecific() != null)
                    if (feature.getFeatureSpecific().getSubfeatureOptions() != null)
                        creator.getMenus().subfeatureMenu(feature);
                    else if (feature.getFeatureSpecific().getExpertiseOptions() != null)
                        creator.getMenus().featureExpertiseMenu(feature);
            }
        }
    }
}

package com.Theeef.me.interaction.character;

import com.Theeef.me.Echovale;
import com.Theeef.me.api.classes.Feature;
import com.Theeef.me.api.classes.Level;
import com.Theeef.me.api.common.Info;
import com.Theeef.me.api.common.choice.ChoiceResult;
import com.Theeef.me.api.common.choice.CountedReference;
import com.Theeef.me.api.races.Trait;
import com.Theeef.me.util.Util;
import com.google.common.collect.Lists;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.simple.JSONObject;

public class CharacterCreatorMenus {

    private final CharacterCreator creator;
    private final CharacterCreatorItems items;

    public CharacterCreatorMenus(CharacterCreator creator) {
        this.creator = creator;
        this.items = creator.getItemCreator();
    }

    public void classProficiencyChoicesMenu() {
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, "Class Proficiency Choices");

        if (creator.getClassProficiencyChoiceResults().size() == 1) {
            new ChoiceMenu("Choose " + creator.getClassProficiencyChoiceResults().get(0).getChoice().getChoiceAmount() + " Proficiencies", "class", creator.getClassProficiencyChoiceResults().get(0)).open(creator.getPlayer());
            return;
        }

        for (ChoiceResult result : creator.getClassProficiencyChoiceResults())
            inventory.addItem(result.getItem(creator));

        inventory.setItem(2 * 9, CharacterCreatorItems.previousPage("class"));
        creator.getPlayer().openInventory(inventory);
    }

    public void backgroundMenu() {
        Inventory inventory;
        final String invName;

        if (!creator.getBackgroundChoiceResult().isComplete()) {
            invName = "Background";
            inventory = Bukkit.createInventory(null, 3 * 9, invName);
            inventory.setItem(9 + 4, this.items.selectBackgroundItem());
            inventory.setItem(3 * 9 - 1, CharacterCreatorItems.nextPage("ability scores"));
        } else {
            invName = "Background: " + creator.getBackground().getName();
            inventory = Bukkit.createInventory(null, 5 * 9, invName);
            inventory.setItem(9 + 4, this.items.backgroundItem());
            inventory.setItem(9 * 3 + 2, this.items.backgroundLanguageOptionsItem());
            inventory.setItem(9 * 3 + 3, this.items.backgroundStartingEquipmentItem());
            inventory.setItem(9 * 3 + 5, this.items.backgroundFeatureItem());
            inventory.setItem(9 * 3 + 6, this.items.backgroundRoleplayItem());
            inventory.setItem(5 * 9 - 1, CharacterCreatorItems.nextPage("ability scores"));
        }

        inventory.setItem(inventory.getSize() - 9, CharacterCreatorItems.previousPage("class"));
        creator.getPlayer().openInventory(inventory);

        new BukkitRunnable() {
            public void run() {
                if (creator.getPlayer().getOpenInventory().getTitle().equals(invName) && !creator.backgroundComplete())
                    backgroundMenu();
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);
    }

    public void backgroundStartingEquipmentMenu() {
        Inventory inventory = Bukkit.createInventory(null, 27, "Background Equipment Choice");

        for (ItemStack item : creator.getBackground().getStartingEquipment())
            inventory.addItem(item);

        for (ChoiceResult equipmentChoiceResult : creator.getBackgroundStartingEquipmentChoiceResult())
            inventory.addItem(equipmentChoiceResult.getItem(creator));

        inventory.setItem(inventory.getSize() - 9, CharacterCreatorItems.previousPage("backgrounds"));
        creator.getPlayer().openInventory(inventory);

        new BukkitRunnable() {
            public void run() {
                if (creator.getPlayer().getOpenInventory().getTitle().equals("Background Equipment Choice"))
                    for (ChoiceResult result : creator.getBackgroundStartingEquipmentChoiceResult())
                        if (!result.isComplete()) {
                            backgroundStartingEquipmentMenu();
                            break;
                        }
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);
    }

    public void backgroundRoleplayMenu() {
        String invName = "Background Traits";
        Inventory inventory = Bukkit.createInventory(null, 3 * 9, invName);
        inventory.setItem(9 + 1, this.items.backgroundPersonalityTraitsItem());
        inventory.setItem(9 + 3, this.items.backgroundFlawsItem());
        inventory.setItem(9 + 5, this.items.backgroundBondsItem());
        inventory.setItem(9 + 7, this.items.backgroundIdealsItem());
        inventory.setItem(inventory.getSize() - 9, CharacterCreatorItems.previousPage("backgrounds"));

        creator.getPlayer().openInventory(inventory);

        new BukkitRunnable() {
            public void run() {
                if (creator.getPlayer().getOpenInventory().getTitle().equals(invName))
                    if (!creator.getBackgroundBonds().isComplete() || !creator.getBackgroundPersonalityTraits().isComplete() || !creator.getBackgroundFlaws().isComplete() || !creator.getBackgroundIdeals().isComplete())
                        backgroundRoleplayMenu();
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);
    }


    public void startingEquipmentMenu() {
        Inventory inventory = Bukkit.createInventory(null, 27, creator.getDNDClass().getName() + " Starting Equipment");

        for (CountedReference equipment : creator.getDNDClass().getStartingEquipment())
            inventory.addItem(equipment.getEquipment());

        for (ChoiceResult equipmentChoiceResult : creator.getStartingEquipmentChoiceResult())
            inventory.addItem(equipmentChoiceResult.getItem(creator));

        inventory.setItem(inventory.getSize() - 9, CharacterCreatorItems.previousPage("class"));
        creator.getPlayer().openInventory(inventory);

        new BukkitRunnable() {
            public void run() {
                if (creator.getPlayer().getOpenInventory().getTitle().equals(creator.getDNDClass().getName() + " Starting Equipment"))
                    for (ChoiceResult result : creator.getStartingEquipmentChoiceResult())
                        if (!result.isComplete()) {
                            startingEquipmentMenu();
                            break;
                        }
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);
    }

    public void spellcastingMenu() {
        Inventory inventory = Bukkit.createInventory(null, 27, creator.getDNDClass().getName() + " Spellcasting");
        ItemStack infoItem = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta meta = infoItem.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.AQUA + "Spellcasting");
        meta.setLore(Lists.newArrayList(ChatColor.GRAY + creator.getDNDClass().getName() + " unlocks spellcasting at level " + creator.getDNDClass().getSpellcasting().getLevel(), "", ChatColor.GRAY + "Spellcasting Ability: " + ChatColor.WHITE + creator.getDNDClass().getSpellcasting().getSpellcastingAbility().getFullName()));
        infoItem.setItemMeta(meta);

        inventory.addItem(infoItem);

        for (Info info : creator.getDNDClass().getSpellcasting().getInfo())
            inventory.addItem(CharacterCreatorItems.infoItem(Material.MAP, info));

        inventory.setItem(inventory.getSize() - 9, CharacterCreatorItems.previousPage("class"));

        creator.getPlayer().openInventory(inventory);
    }

    public void classLevelsMenu() {
        classLevelsMenu(true);
    }

    public Inventory classLevelsMenu(boolean openAgain) {
        // Item for spellcasting
        // Item for proficiency & features (click for more info)
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "Leveling");
        inventory.setItem(0, CharacterCreatorItems.previousPage("class"));

        inventory.setItem(2, CharacterCreatorItems.levelInfoItem());
        inventory.setItem(3, this.items.levelingFeaturesItem());

        if (creator.getDNDClass().getSpellcasting() != null)
            inventory.setItem(4, CharacterCreatorItems.levelingSpellcastingItem());

        if (openAgain)
            creator.getPlayer().openInventory(inventory);

        new BukkitRunnable() {
            public void run() {
                if (creator.getPlayer().getOpenInventory().getTitle().equals("Leveling") && !creator.classFeaturesComplete())
                    matchInventory(creator.getPlayer().getOpenInventory(), classLevelsMenu(false));
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);

        return inventory;
    }

    public void subfeatureMenu(Feature feature) {
        new ChoiceMenu("Choose A " + feature.getName(), creator.getPlayer().getOpenInventory().getTopInventory(), creator.getSubfeatureChoices().get(feature)).open(creator.getPlayer());
    }

    public void featureExpertiseMenu(Feature feature) {
        ChoiceMenu menu = new ChoiceMenu(feature.getName(), creator.getPlayer().getOpenInventory().getTopInventory(), creator.getExpertiseChoices().get(feature));
        menu.open(creator.getPlayer());
    }


    public void matchInventory(InventoryView view, Inventory toMatch) {
        view.getTopInventory().clear();

        for (int i = 0; i < toMatch.getContents().length; i++)
            view.getTopInventory().setItem(i, toMatch.getContents()[i]);
    }

    public void perkInfoMenu(int level) {
        Inventory inventory = Bukkit.createInventory(null, 6 * 9, "Leveling Perks");
        inventory.setItem(1, CharacterCreatorItems.proficiencyColumn());

        int mainColStart = 2;
        Level level20 = creator.getDNDClass().getClassLevel(20);

        if (level20.getSpellcasting() != null && level20.getSpellcasting().getCantripsKnown() != 0) {
            inventory.setItem(mainColStart, CharacterCreatorItems.cantripsKnownColumn());
            mainColStart++;
        }

        if (level20.getSpellcasting() != null && level20.getSpellcasting().getSpellsKnown() != 0) {
            inventory.setItem(mainColStart, CharacterCreatorItems.spellsKnownColumn());
            mainColStart++;
        }

        for (Object object : level20.getClassSpecific().keySet()) {
            inventory.setItem(mainColStart, CharacterCreatorItems.classSpecificColumn(object.toString()));
            mainColStart++;
        }

        for (int i = 0; i < 4; i++) {
            int colStart = 2;
            Level levelObj = creator.getDNDClass().getClassLevel(level + i);
            inventory.setItem((i + 1) * 9, CharacterCreatorItems.perkMenuLevelItem(level + i));
            inventory.setItem((i + 1) * 9 + 1, CharacterCreatorItems.perkInfoTableValue("Proficiency Bonus", (int) levelObj.getProficiencyBonus()));

            if (levelObj.getSpellcasting() != null && creator.getDNDClass().getClassLevel(20).getSpellcasting().getCantripsKnown() != 0) {
                inventory.setItem((i + 1) * 9 + colStart, CharacterCreatorItems.perkInfoTableValue("Cantrips Known", (int) levelObj.getSpellcasting().getCantripsKnown()));
                colStart++;
            }

            if (levelObj.getSpellcasting() != null && creator.getDNDClass().getClassLevel(20).getSpellcasting().getSpellsKnown() != 0) {
                inventory.setItem((i + 1) * 9 + colStart, CharacterCreatorItems.perkInfoTableValue("Spells Known", (int) levelObj.getSpellcasting().getSpellsKnown()));
                colStart++;
            }

            for (Object object : levelObj.getClassSpecific().keySet()) {
                if (levelObj.getClassSpecific().get(object) instanceof Long) {
                    inventory.setItem((i + 1) * 9 + colStart, CharacterCreatorItems.perkInfoTableValue(Util.cleanEnumName(object.toString()), (int) (long) levelObj.getClassSpecific().get(object)));
                    colStart++;
                } else if (levelObj.getClassSpecific().get(object) instanceof JSONObject && ((JSONObject) levelObj.getClassSpecific().get(object)).containsKey("dice_count")) {
                    inventory.setItem((i + 1) * 9 + colStart, CharacterCreatorItems.perkInfoTableValueJSONObject(Util.cleanEnumName(object.toString()), (JSONObject) levelObj.getClassSpecific().get(object)));
                    colStart++;
                }
            }

        }

        for (int i = 5 * 9; i < 5 * 9 + 9; i++)
            inventory.setItem(i, CharacterCreatorItems.borderItem());

        if (level > 1)
            inventory.setItem(5 * 9 + 3, CharacterCreatorItems.perkMenuPrevPage());
        if (level <= 16)
            inventory.setItem(5 * 9 + 5, CharacterCreatorItems.perkMenuNextPage());

        inventory.setItem(5 * 9 + 4, CharacterCreatorItems.perkMenuSignItem(level));
        inventory.setItem(5 * 9, CharacterCreatorItems.previousPage("class levels"));

        creator.getPlayer().openInventory(inventory);
    }

    public void subtraitsMenu(Trait parentTrait, String returnTo) {
        ChoiceMenu menu = new ChoiceMenu(parentTrait.getName(), returnTo, creator.getSubtraitChoices().get(parentTrait));
        menu.open(creator.getPlayer());
    }

    public void subraceRacialTraitsMenu() {
        Inventory inventory = Bukkit.createInventory(null, 9 * ((creator.getSubrace().getRacialTraits().size() - 1) / 9 + 3), "Subrace Traits");

        for (Trait trait : creator.getSubrace().getRacialTraits())
            inventory.addItem(this.items.traitItem(trait));

        inventory.setItem(inventory.getSize() - 9, CharacterCreatorItems.previousPage("subrace"));

        creator.getPlayer().openInventory(inventory);

        new BukkitRunnable() {
            public void run() {
                if (creator.getPlayer().getOpenInventory().getTitle().equals("Subrace Traits")) {
                    for (ChoiceResult result : creator.getSubtraitChoices().values())
                        if (!result.isComplete()) {
                            subraceRacialTraitsMenu();
                            return;
                        }

                    for (ChoiceResult result : creator.getTraitSpellChoices().values())
                        if (!result.isComplete()) {
                            subraceRacialTraitsMenu();
                            return;
                        }
                }
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);
    }

    public void subraceMenu() {
        Inventory inventory;

        if (creator.getSubrace() == null)
            inventory = Bukkit.createInventory(null, 27, "Subrace");
        else
            inventory = Bukkit.createInventory(null, 5 * 9, "Subrace");

        inventory.setItem(13, this.items.selectSubraceItem());
        inventory.setItem(inventory.getSize() - 9, CharacterCreatorItems.previousPage("race"));

        if (creator.getSubrace() != null) {
            int align = (creator.getSubrace().getRacialTraits().size() > 0 ? 1 : 0) + (creator.getSubrace().getLanguageOptions() == null ? 0 : 1) + (creator.getSubrace().getLanguages().size() == 0 ? 0 : 1) + (creator.getSubrace().getStartingProficiencies().size() > 0 ? 1 : 0);
            int count = 0;

            if (creator.getSubrace().getRacialTraits().size() > 0) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, this.items.subraceTraitItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), this.items.subraceTraitItem());
                count++;
            }

            if (creator.getSubrace().getStartingProficiencies().size() > 0) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, CharacterCreatorItems.startingProficienciesItem(creator.getSubrace().getStartingProficiencies()));
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), CharacterCreatorItems.startingProficienciesItem(creator.getSubrace().getStartingProficiencies()));
                count++;
            }

            if (creator.getSubrace().getLanguages().size() > 0) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, this.items.languagesItem(creator.getSubrace().getLanguages()));
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), this.items.languagesItem(creator.getSubrace().getLanguages()));
                count++;
            }

            if (creator.getSubrace().getLanguageOptions() != null)
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, this.items.subraceLanguageChoiceItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), this.items.subraceLanguageChoiceItem());
        }

        creator.getPlayer().openInventory(inventory);

        new BukkitRunnable() {
            public void run() {
                if (creator.getPlayer().getOpenInventory().getTitle().equals("Subrace") && !creator.subraceComplete())
                    subraceMenu();
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);
    }

    public void racialTraitMenu() {
        Inventory inventory = Bukkit.createInventory(null, 27, "Racial Traits");

        for (Trait trait : creator.getRace().getTraits())
            inventory.addItem(this.items.traitItem(trait));

        inventory.setItem(inventory.getSize() - 9, CharacterCreatorItems.previousPage("race"));

        creator.getPlayer().openInventory(inventory);

        new BukkitRunnable() {
            public void run() {
                if (creator.getPlayer().getOpenInventory().getTitle().equals("Racial Traits"))
                    for (ChoiceResult result : creator.getSubtraitChoices().values())
                        if (!result.isComplete()) {
                            racialTraitMenu();
                            break;
                        }
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);
    }

    public void classMenu() {
        Inventory inventory;

        if (creator.getDNDClass() == null)
            inventory = Bukkit.createInventory(null, 27, "Class");
        else {
            inventory = Bukkit.createInventory(null, 9 * 5, "Class: " + creator.getDNDClass().getName());

            int align = (((creator.getDNDClass().getStartingEquipment().size() > 0 || creator.getDNDClass().getStartingEquipmentOptions() != null) ? 1 : 0) + 1 + (creator.getDNDClass().getSpellcasting() != null ? 1 : 0)) + 1 + (creator.getDNDClass().getProficiencyChoices().size() > 0 ? 1 : 0);
            int count = 0;

            if (creator.getDNDClass().getStartingEquipment().size() > 0 || creator.getDNDClass().getStartingEquipment() != null) {
                inventory.setItem(27 + (9 - align) / 2, this.items.startingEquipmentItem());
                count++;
            }

            if (creator.getDNDClass().getProficiencyChoices().size() > 0) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, this.items.classProficiencyChoicesItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), this.items.classProficiencyChoicesItem());
                count++;
            }

            if (creator.getDNDClass().getSpellcasting() != null) {
                if (align % 2 != 0)
                    inventory.setItem(27 + (9 - align) / 2 + count, this.items.spellcastingItem());
                else
                    inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), this.items.spellcastingItem());
                count++;
            }

            // Leveling
            if (align % 2 != 0)
                inventory.setItem(27 + (9 - align) / 2 + count, this.items.levelingItem());
            else
                inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), this.items.levelingItem());
            count++;

            if (align % 2 != 0)
                inventory.setItem(27 + (9 - align) / 2 + count, this.items.selectSubclassItem());
            else
                inventory.setItem(27 + (9 - align) / 2 + count + (count + 1 > align / 2 ? 1 : 0), this.items.selectSubclassItem());

        }

        inventory.setItem(13, this.items.selectClassItem());
        inventory.setItem(inventory.getSize() - 9, CharacterCreatorItems.previousPage("race"));
        inventory.setItem(inventory.getSize() - 1, CharacterCreatorItems.nextPage("backgrounds"));

        creator.getPlayer().openInventory(inventory);

        new BukkitRunnable() {
            public void run() {
                if (creator.getPlayer().getOpenInventory().getTitle().equals("Class") || creator.getDNDClass() != null && creator.getPlayer().getOpenInventory().getTitle().equals("Class: " + creator.getDNDClass().getName()))
                    classMenu();
            }
        }.runTaskLater(Echovale.getPlugin(Echovale.class), 20L);
    }

}

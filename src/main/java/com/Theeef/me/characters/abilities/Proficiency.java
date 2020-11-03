package com.Theeef.me.characters.abilities;

public class Proficiency {

    public enum ProficiencyType {
        WEAPON, ARTISONS_TOOLS, GAMING_SETS, INSTRUMENTS, KIT, MISC;
    }

    public static Proficiency ALCHEMISTS_SUPPLIES = new Proficiency("Alchemist's supplies", ProficiencyType.ARTISONS_TOOLS);
    public static Proficiency BREWERS_SUPPLIES = new Proficiency("Brewer's supplies", ProficiencyType.ARTISONS_TOOLS);
    public static Proficiency CALLIGRAPHERS_SUPPLIES = new Proficiency("Calligrapher's supplies", ProficiencyType.ARTISONS_TOOLS);
    public static Proficiency CARPENTERS_TOOLS = new Proficiency("Carpenter's tools", ProficiencyType.ARTISONS_TOOLS);
    public static Proficiency CARTOGRAPHERS_TOOLS = new Proficiency("Cartographer's tools", ProficiencyType.ARTISONS_TOOLS);
    public static Proficiency COBBLERS_TOOLS = new Proficiency("Cobbler's tools", ProficiencyType.ARTISONS_TOOLS);
    public static Proficiency COOKS_UTENSILS = new Proficiency("Cook's utensils", ProficiencyType.ARTISONS_TOOLS);
    public static Proficiency GLASSBLOWERS_TOOLS = new Proficiency("Glassblower's tools", ProficiencyType.ARTISONS_TOOLS);
    public static Proficiency JEWELERS_TOOLS = new Proficiency("Jeweler's tools", ProficiencyType.ARTISONS_TOOLS);
    public static Proficiency LEATHERWORKERS_TOOLS = new Proficiency("Leatherworker's tools", ProficiencyType.ARTISONS_TOOLS);
    public static Proficiency MASONS_TOOLS = new Proficiency("Mason's tools", ProficiencyType.ARTISONS_TOOLS);
    public static Proficiency PAINTERS_SUPPLIES = new Proficiency("Painter's supplies", ProficiencyType.ARTISONS_TOOLS);
    public static Proficiency POTTERS_TOOLS = new Proficiency("Potter's tools", ProficiencyType.ARTISONS_TOOLS);
    public static Proficiency SMITHS_TOOLS = new Proficiency("Smith's tools", ProficiencyType.ARTISONS_TOOLS);
    public static Proficiency TINKERS_TOOLS = new Proficiency("Tinker's tools", ProficiencyType.ARTISONS_TOOLS);
    public static Proficiency WEAVERS_TOOLS = new Proficiency("Weaver's tools", ProficiencyType.ARTISONS_TOOLS);
    public static Proficiency WOODCARVERS_TOOLS = new Proficiency("Woodcarver's tools", ProficiencyType.ARTISONS_TOOLS);
    public static Proficiency DICE_SET = new Proficiency("Dice set", ProficiencyType.GAMING_SETS);
    public static Proficiency PLAYING_CARD_SET = new Proficiency("Playing card set", ProficiencyType.GAMING_SETS);
    public static Proficiency BAGPIPES = new Proficiency("Bagpipes", ProficiencyType.INSTRUMENTS);
    public static Proficiency DRUM = new Proficiency("Drum", ProficiencyType.INSTRUMENTS);
    public static Proficiency DULCIMER = new Proficiency("Dulcimer", ProficiencyType.INSTRUMENTS);
    public static Proficiency FLUTE = new Proficiency("Flute", ProficiencyType.INSTRUMENTS);
    public static Proficiency LUTE = new Proficiency("Lute", ProficiencyType.INSTRUMENTS);
    public static Proficiency LYRE = new Proficiency("Lyre", ProficiencyType.INSTRUMENTS);
    public static Proficiency HORN = new Proficiency("Horn", ProficiencyType.INSTRUMENTS);
    public static Proficiency PAN_FLUTE = new Proficiency("Pan flute", ProficiencyType.INSTRUMENTS);
    public static Proficiency SHAWM = new Proficiency("Shawm", ProficiencyType.INSTRUMENTS);
    public static Proficiency VIOL = new Proficiency("Viol", ProficiencyType.INSTRUMENTS);
    public static Proficiency NAVIGATORS_TOOLS = new Proficiency("Navigator's tools", ProficiencyType.MISC);
    public static Proficiency THIEVES_TOOLS = new Proficiency("Thieves' tools", ProficiencyType.MISC);
    public static Proficiency VEHICLES = new Proficiency("Vehicles", ProficiencyType.MISC);
    public static Proficiency DISGUISE_KIT = new Proficiency("Disguise Kit", ProficiencyType.KIT);
    public static Proficiency FORGERY_KIT = new Proficiency("Forgery Kit", ProficiencyType.KIT);
    public static Proficiency HERBALISM_KIT = new Proficiency("Herbalism Kit", ProficiencyType.KIT);
    public static Proficiency POISONERS_KIT = new Proficiency("Poisoner's Kit", ProficiencyType.KIT);

    private ProficiencyType type;
    private String name;

    public Proficiency(String name, ProficiencyType type) {
        this.name = name;
        this.type = type;
    }

}

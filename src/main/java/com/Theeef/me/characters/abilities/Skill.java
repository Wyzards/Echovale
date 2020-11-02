package com.Theeef.me.characters.abilities;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;

public class Skill {

    public static Skill ATHLETICS = new Skill("Athletics", Lists.newArrayList("Your Strength (Athletics) check covers difficult situations you encounter while climbing, Jumping, or swimming. Examples include the following activities:", "- You attempt to climb a sheer or slippery cliff, avoid Hazards while scaling a wall, or cling to a surface while something is trying to knock you off.", "- You try to jump an unusually long distance or pull off a stunt midjump.", "- You struggle to swim or stay afloat in treacherous Currents, storm-tossed waves, or areas of thick seaweed. Or another creature tries to push or pull you Underwater or otherwise interfere with your swimming."), Ability.STR);
    public static Skill ACROBATICS = new Skill("Acrobatics", Lists.newArrayList("Y)our Dexterity (Acrobatics) check covers your attempt to stay on your feet in a tricky situation, such as when you’re trying to run across a sheet of ice, balance on a tightrope, or stay upright on a rocking ship’s deck. The GM might also call for a Dexterity (Acrobatics) check to see if you can perform acrobatic stunts, including dives, rolls, somersaults, and flips."), Ability.DEX);
    public static Skill SLEIGHT_OF_HAND = new Skill("Sleight of Hand", Lists.newArrayList("Whenever you attempt an act of legerdemain or manual trickery, such as planting something on someone else or concealing an object on your person, make a Dexterity (Sleight of Hand) check. The GM might also call for a Dexterity (Sleight of Hand) check to determine whether you can lift a coin purse off another person or slip something out of another person’s pocket."), Ability.DEX);
    public static Skill STEALTH = new Skill("Stealth", Lists.newArrayList("Make a Dexterity (Stealth) check when you attempt to conceal yourself from enemies, slink past guards, slip away without Being Noticed, or sneak up on someone without being seen or heard."), Ability.DEX);
    public static Skill ARCANA = new Skill("Arcana", Lists.newArrayList("Your Intelligence (Arcana) check measures your ability to recall lore about Spells, Magic Items, eldritch symbols, magical traditions, The Planes of Existence, and the inhabitants of those planes."), Ability.INTEL);
    public static Skill HISTORY = new Skill("History", Lists.newArrayList("Your Intelligence (History) check measures your ability to recall lore about historical events, legendary people, ancient kingdoms, past disputes, recent wars, and lost civilizations."), Ability.INTEL);
    public static Skill INVESTIGATION = new Skill("Investigation", Lists.newArrayList("When you look around for clues and make deductions based on those clues, you make an Intelligence (Investigation) check. You might deduce the location of a hidden object, discern from the appearance of a wound what kind of weapon dealt it, or determine the weakest point in a tunnel that could cause it to collapse. Poring through ancient scrolls in Search of a hidden fragment of knowledge might also call for an Intelligence (Investigation) check."), Ability.INTEL);
    public static Skill NATURE = new Skill("Nature", Lists.newArrayList("Your Intelligence (Nature) check measures your ability to recall lore about terrain, Plants and animals, the weather, and natural cycles."), Ability.INTEL);
    public static Skill RELIGION = new Skill("Religion", Lists.newArrayList("Your Intelligence (Religion) check measures your ability to recall lore about deities, rites and prayers, religious hierarchies, holy symbols, and the practices of Secret cults."), Ability.INTEL);
    public static Skill ANIMAL_HANDLING = new Skill("Animal Handling", Lists.newArrayList("When there is any question whether you can calm down a domesticated animal, keep a mount from getting spooked, or intuit an animal’s intentions, the GM might call for a Wisdom (Animal Handling) check. You also make a Wisdom (Animal Handling) check to control your mount when you attempt a risky maneuver."), Ability.WIS);
    public static Skill INSIGHT = new Skill("Insight", Lists.newArrayList("Your Wisdom (Insight) check decides whether you can determine the true intentions of a creature, such as when searching out a lie or predicting someone’s next move. Doing so involves gleaning clues from body language, Speech habits, and changes in mannerisms."), Ability.WIS);
    public static Skill MEDICINE = new Skill("Medicine", Lists.newArrayList("A Wisdom (Medicine) check lets you try to stabilize a dying companion or diagnose an illness."), Ability.WIS);
    public static Skill PERCEPTION = new Skill("Perception", Lists.newArrayList("Your Wisdom (Perception) check lets you spot, hear, or otherwise detect the presence of something. It measures your general awareness of your surroundings and the keenness of your Senses. For example, you might try to hear a conversation through a closed door, eavesdrop under an open window, or hear Monsters moving stealthily in the Forest. Or you might try to spot things that are obscured or easy to miss, whether they are orcs lying in Ambush on a road, thugs Hiding in the shadows of an alley, or candlelight under a closed Secret door."), Ability.WIS);
    public static Skill SURVIVAL = new Skill("Survival", Lists.newArrayList("The GM might ask you to make a Wisdom (Survival) check to follow tracks, hunt wild game, guide your group through frozen wastelands, Identify signs that owlbears live nearby, predict the weather, or avoid quicksand and other natural Hazards."), Ability.WIS);
    public static Skill DECEPTION = new Skill("Deception", Lists.newArrayList("Your Charisma (Deception) check determines whether you can convincingly hide the truth, either verbally or through your Actions. This Deception can encompass everything from misleading others through ambiguity to telling outright lies. Typical situations include trying to fast- talk a guard, con a merchant, earn money through Gambling, pass yourself off in a disguise, dull someone’s suspicions with false assurances, or maintain a straight face while telling a blatant lie."), Ability.CHA);
    public static Skill INTIMIDATION = new Skill("Intimidation", Lists.newArrayList("When you attempt to influence someone through overt threats, Hostile Actions, and physical violence, the GM might ask you to make a Charisma (Intimidation) check. Examples include trying to pry information out of a prisoner, convincing street thugs to back down from a confrontation, or using the edge of a broken bottle to convince a sneering vizier to reconsider a decision."), Ability.CHA);
    public static Skill PERFORMANCE = new Skill("Performance", Lists.newArrayList("Your Charisma (Performance) check determines how well you can delight an audience with music, dance, acting, storytelling, or some other form of entertainment."), Ability.CHA);
    public static Skill PERSUASION = new Skill("Persuasion", Lists.newArrayList("When you attempt to influence someone or a group of people with tact, social graces, or good Nature, the GM might ask you to make a Charisma (Persuasion) check. Typically, you use Persuasion when acting in good faith, to foster friendships, make cordial requests, or exhibit proper etiquette. Examples of persuading others include convincing a chamberlain to let your party see the king, negotiating peace between warring tribes, or inspiring a crowd of townsfolk."), Ability.CHA);

    private String name;
    private List<String> description;
    private Ability ability;

    public Skill(String name, List<String> description, Ability ability) {
        this.name = name;
        this.description = description;
        this.ability = ability;
    }

    public static Set<Skill> values() {
        return Sets.newHashSet(ATHLETICS, ACROBATICS, SLEIGHT_OF_HAND, STEALTH, ARCANA, HISTORY, INVESTIGATION, NATURE, RELIGION, ANIMAL_HANDLING, INSIGHT, MEDICINE, PERCEPTION, SURVIVAL, DECEPTION, INTIMIDATION, PERFORMANCE, PERSUASION);
    }
}

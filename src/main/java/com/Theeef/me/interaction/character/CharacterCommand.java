package com.Theeef.me.interaction.character;

import com.Theeef.me.api.backgrounds.Background;
import com.Theeef.me.api.classes.DNDClass;
import com.Theeef.me.api.races.Race;
import com.Theeef.me.api.races.Subrace;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CharacterCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            CharacterCreator creator = new CharacterCreator(player);
        }

        if (sender == null) {
            Player player = (Player) sender;
            Race race;
            Subrace subrace;
            DNDClass dndclass;
            int cha;
            int con;
            int dex;
            int intel;
            int str;
            int wis;
            Background background;

            if (args.length == 10) {
                race = Race.fromIndex(args[0]);
                subrace = Subrace.fromIndex(args[1]);
                dndclass = DNDClass.fromIndex(args[2]);
                cha = Integer.parseInt(args[3]);
                con = Integer.parseInt(args[4]);
                dex = Integer.parseInt(args[5]);
                intel = Integer.parseInt(args[6]);
                str = Integer.parseInt(args[7]);
                wis = Integer.parseInt(args[8]);
                background = Background.fromIndex(args[9]);
            } else if (args.length == 9) {
                race = Race.fromIndex(args[0]);
                subrace = null;
                dndclass = DNDClass.fromIndex(args[1]);
                cha = Integer.parseInt(args[2]);
                con = Integer.parseInt(args[3]);
                dex = Integer.parseInt(args[4]);
                intel = Integer.parseInt(args[5]);
                str = Integer.parseInt(args[6]);
                wis = Integer.parseInt(args[7]);
                background = Background.fromIndex(args[8]);
            } else {
                player.sendMessage(ChatColor.RED + "Usage: /character <race> <subrace> <class> <CHA> <CON> <DEX> <INT> <STR> <WIS> <background>");
                return true;
            }

            Character character = new Character(player.getUniqueId(), player.getName(), race, subrace, dndclass, cha, con, dex, intel, str, wis, background);
        }

        return true;
    }
}

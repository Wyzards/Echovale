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
        if (sender instanceof Player)
            CharacterCreator.charCommand((Player) sender);

        return true;
    }
}

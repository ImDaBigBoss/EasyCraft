package com.github.imdabigboss.easycraft.commands;

import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;

import com.github.imdabigboss.easycraft.easyCraft;

import net.md_5.bungee.api.ChatColor;

public class CommandInfo implements CommandExecutor {
	private Plugin plugin = easyCraft.getPlugin();

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (!plugin.getConfig().contains("infoLines"))
    		return true;
    	
    	int Infos = plugin.getConfig().getInt("infoLines");
    	
    	for (int i = 1; i <= Infos; i++) {
    		if (!plugin.getConfig().contains("info" + i))
    			plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "You have misconfigured the config.yml of RavelCraft, info" + i + "!");
    		else
    			sender.sendMessage(plugin.getConfig().getString("info" + i));
    	}
    	return true;
    }
}
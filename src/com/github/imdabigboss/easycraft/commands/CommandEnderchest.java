package com.github.imdabigboss.easycraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.imdabigboss.easycraft.easyCraft;
import com.github.imdabigboss.easycraft.ymlUtils;

public class CommandEnderchest implements CommandExecutor {
	private Plugin plugin = easyCraft.getPlugin();

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (sender instanceof Player) { //Get if executing origin is a player
    	} else {
    		sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
    		return true;
    	}
    	Player player = (Player) sender;
    	
    	if (!plugin.getConfig().contains(player.getName() + ".enderchest") || !plugin.getConfig().getBoolean(player.getName() + ".enderchest")) {
    		player.sendMessage(ChatColor.RED + "You did not purchase this command!");
    		return true;
    	}
    	
    	player.sendMessage(ChatColor.AQUA + "Opening your enderchest");
    	player.openInventory(player.getEnderChest());
    	return true;
    }
}
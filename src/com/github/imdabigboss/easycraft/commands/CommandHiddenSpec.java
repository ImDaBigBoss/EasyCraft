package com.github.imdabigboss.easycraft.commands;

import org.bukkit.GameMode;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.imdabigboss.easycraft.easyCraft;

import net.md_5.bungee.api.ChatColor;

public class CommandHiddenSpec implements CommandExecutor {
	private Plugin plugin = easyCraft.getPlugin();

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (sender instanceof Player) { //Get if executing origin is a player
    	} else {
    		sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
    		return true;
    	}
    	
    	if (args.length != 1) {
    		sendHelp(sender);
    		return true;
    	}
    	
    	Player player = (Player) sender;
    	
    	if (args[0].equalsIgnoreCase("on")) {
    		player.setGameMode(GameMode.SPECTATOR);
    		player.setPlayerListName(ChatColor.RESET + player.getDisplayName());
    	} else if (args[0].equalsIgnoreCase("off")) {
    		player.setGameMode(GameMode.SURVIVAL);
    		player.setPlayerListName(ChatColor.RESET + player.getDisplayName());
    	} else {
    		sendHelp(sender);
    	}
    		
    	return true;
    }
    
    private void sendHelp(CommandSender sender) {
    	sender.sendMessage("The correct usage is:");
    	sender.sendMessage("- /hiddenSpec on");
    	sender.sendMessage("- /hiddenSpec off");
    }
}
package com.github.imdabigboss.easycraft.commands;

import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.github.imdabigboss.easycraft.easyCraft;

import net.md_5.bungee.api.ChatColor;

public class CommandSetMaxPlayers implements CommandExecutor {
	private Plugin plugin = easyCraft.getPlugin();
	
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (sender instanceof ConsoleCommandSender) {
    	    
    	} else {
    		sender.sendMessage("You may only set the max player count as console!!!"); 
    		return true;
    	}
    	
    	if (args.length != 1) {
    		sender.sendMessage("Choose the amount of players that can join!");
    		return false;
    	}
    	int playerCount = 0;
    	try {
    		playerCount = Integer.parseInt(args[0]);
    		changeSlots(playerCount);
    	} catch (Exception e) {
    		sender.sendMessage("An error occured!");
    		return false;
    	}
    	
    	if (plugin.getServer().getMaxPlayers() != playerCount) {
    		sender.sendMessage("An error occured!");
    	} else if (plugin.getServer().getMaxPlayers() == 0) {
    		sender.sendMessage(ChatColor.RED + "WARNING! YOU SET THE MAX PLAYER COUNT TO 0!");
    	} else {
    		sender.sendMessage("Successfully set the max player count to: " + playerCount + "!");
    	}
    	return true;
    }
    
    public void changeSlots(int slots) throws ReflectiveOperationException {
        Method serverGetHandle = plugin.getServer().getClass().getDeclaredMethod("getHandle");
        Object playerList = serverGetHandle.invoke(plugin.getServer());
        Field maxPlayersField = playerList.getClass().getSuperclass().getDeclaredField("maxPlayers");
        
        maxPlayersField.setAccessible(true);
        maxPlayersField.set(playerList, slots);

    }
}
package com.github.imdabigboss.easycraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.imdabigboss.easycraft.ChatRoom;
import com.github.imdabigboss.easycraft.easyCraft;

public class CommandShout implements CommandExecutor, TabExecutor {
	private final Plugin plugin = easyCraft.getPlugin();
	
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (sender instanceof Player) { //Get if executing origin is a player
    		ChatRoom chatRoom = easyCraft.getChatRoom();
    		if (chatRoom.isInRoom(sender.getName())) {
    			if (args.length == 0) {
    				sender.sendMessage("Correct usage is /shout <message>");
    				return true;
    			}
    			
    			String message = String.join(" ", args);
    			plugin.getServer().broadcastMessage(((Player) sender).getDisplayName() + ChatColor.RESET + ": " + message);
    		} else {
    			sender.sendMessage("You must be in a room to use this command!");
    		}
    	} else {
    		sender.sendMessage("You need to be a player to use this command!");
    		return true;
    	}
    	return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
    	if (command.getName().equalsIgnoreCase("setmaxplayers")) {
    		ArrayList<String> cmds = new ArrayList<String>();
    		cmds.add("<message>");
    		return cmds;
    	}
    	return new ArrayList<>();
    }
}
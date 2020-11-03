package com.github.imdabigboss.easycraft.commands;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.imdabigboss.easycraft.ChatRoom;
import com.github.imdabigboss.easycraft.easyCraft;

import net.md_5.bungee.api.ChatColor;

public class CommandChatRoom implements CommandExecutor {
	private ChatRoom chatRoom = easyCraft.getChatRoom();
	private Plugin plugin = easyCraft.getPlugin();
	
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (sender instanceof Player) { //Get if executing origin is a player
    	} else {
    		sender.sendMessage("You must be a player to use this command!");
    		return true;
    	}
    	
    	if (args.length != 1 && args.length != 2) {
    		SendHelp(sender);
    		return true;
    	}
    	
    	if (args[0].equalsIgnoreCase("create")) {
    		if (args.length != 2) {
    			SendHelp(sender);
    			return true;
    		}
    		if (chatRoom.isInRoom(sender.getName())) {
    			sender.sendMessage(ChatColor.RED + "You are already in a room!");
    			return true;
    		}
    		
    		int out = chatRoom.createRoom(args[1], (Player) sender);
    		if (out == 1)
    			sender.sendMessage(ChatColor.RED + "There is already a room with that name!");
    		else 
    			sender.sendMessage(ChatColor.AQUA + "You created " + args[1] + "!");
    	} else if (args[0].equalsIgnoreCase("join")) {
    		if (args.length != 2) {
    			SendHelp(sender);
    			return true;
    		}
    		if (chatRoom.isInRoom(sender.getName())) {
    			sender.sendMessage(ChatColor.RED + "You are already in a room!");
    			return true;
    		}
    		
    		int out = chatRoom.joinRoom(args[1], (Player) sender);
    		if (out == 1)
    			sender.sendMessage(ChatColor.RED + "No existing room has that name!");
    		else
    			sender.sendMessage(ChatColor.AQUA + "You joined " + args[1] + "!");
    	} else if (args[0].equalsIgnoreCase("leave")) {
    		if (args.length != 1) {
    			SendHelp(sender);
    			return true;
    		}
    		if (!chatRoom.isInRoom(sender.getName())) {
    			sender.sendMessage(ChatColor.RED + "You are not in a room!");
    			return true;
    		}
    		
    		int out = chatRoom.leaveRoom((Player) sender);
    		if (out == 1)
    			sender.sendMessage(ChatColor.RED + "An error occured");
    		else
    			sender.sendMessage(ChatColor.AQUA + "You left the room!");
    	} else if (args[0].equalsIgnoreCase("invite")) {
    		if (args.length != 2) {
    			SendHelp(sender);
    			return true;
    		}
    		if (!chatRoom.isInRoom(sender.getName())) {
    			sender.sendMessage(ChatColor.RED + "You are not in a room!");
    			return true;
    		}
    		
    		Player target = null;
    		try {
    			target = plugin.getServer().getPlayer(args[0]);
    			if (!target.isOnline()) {
    				sender.sendMessage(ChatColor.RED + args[0] + " is not online!");
        			return true;
    			}
    		} catch (Exception e) {
    			sender.sendMessage(ChatColor.RED + args[0] + " is not online!");
    			return true;
    		}
    		
    		String name = chatRoom.playerRooms.get(sender.getName());
    		target.sendMessage(sender.getName() + " has invited you to their chatroom named " + name + " to join, enter " + ChatColor.AQUA + "/chatroom join " + name);
    		
    	} else {
    		SendHelp(sender);
    	}
    	
    	return true;
    }
    
    public void SendHelp(CommandSender sender) {
    	sender.sendMessage("The correct usage is :");
    	sender.sendMessage(" - /chatroom create <RoomName>");
    	sender.sendMessage(" - /chatroom join <RoomName>");
    	sender.sendMessage(" - /chatroom leave");
    	sender.sendMessage(" - /chatroom invite <player>");
    }
}
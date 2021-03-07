package com.github.imdabigboss.easycraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.imdabigboss.easycraft.*;

public class CommandChatRoom implements CommandExecutor, TabExecutor {
	private Plugin plugin = easyCraft.getPlugin();
	private ChatRoom chatRoom = easyCraft.getChatRoom();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) { //Get if executing origin is a player
    		sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
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
    		
    		int out = chatRoom.createRoom(args[1], sender.getName());
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
    		
    		int out = chatRoom.joinRoom(args[1], sender.getName());
    		if (out == 1)
    			sender.sendMessage(ChatColor.RED + "No existing room has that name!");
    		else {
    			for (Player player : stringsToPlayers(chatRoom.getRoomPlayers(sender.getName()))) {
    				player.sendMessage(sender.getName() + " joined your chat room!");
    			}
    			sender.sendMessage(ChatColor.AQUA + "You joined " + args[1] + "!");
    		}
    	} else if (args[0].equalsIgnoreCase("leave")) {
    		if (args.length != 1) {
    			SendHelp(sender);
    			return true;
    		}
    		if (!chatRoom.isInRoom(sender.getName())) {
    			sender.sendMessage(ChatColor.RED + "You are not in a room!");
    			return true;
    		}
    		
    		int out = chatRoom.leaveRoom(sender.getName());
    		if (out == 1)
    			sender.sendMessage(ChatColor.RED + "An error occured");
    		else if (out == 2) { //No players in room
    			sender.sendMessage(ChatColor.AQUA + "You left the room!");
    		}
    		else {
    			for (Player player : stringsToPlayers(chatRoom.getRoomPlayers(sender.getName()))) {
    				player.sendMessage(sender.getName() + " left your chat room!");
    			}
    			sender.sendMessage(ChatColor.AQUA + "You left the room!");
    		}
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
    			target = plugin.getServer().getPlayer(args[1]);
    			if (!target.isOnline()) {
    				sender.sendMessage(ChatColor.RED + args[1] + " is not online!");
        			return true;
    			}
    		} catch (Exception e) {
    			sender.sendMessage(ChatColor.RED + args[1] + " is not online!");
    			return true;
    		}
    		
    		String name = chatRoom.playerRooms.get(sender.getName());
    		target.sendMessage(sender.getName() + " has invited you to their chatroom named " + name + " to join, enter " + ChatColor.AQUA + "/chatroom join " + name);
    		
    	} else {
    		SendHelp(sender);
    	}
    	
    	return true;
    }
    
    public List<Player> stringsToPlayers(List<String> playerNames) {
    	List<Player> players = new ArrayList<Player>();
		for (String player : playerNames) {
			Player out = plugin.getServer().getPlayer(player);
			if (out != null)
				players.add(out);
		}
		return players;
    }
    
    public void SendHelp(CommandSender sender) {
    	sender.sendMessage("The correct usage is :");
    	sender.sendMessage(" - /chatroom create <RoomName>");
    	sender.sendMessage(" - /chatroom join <RoomName>");
    	sender.sendMessage(" - /chatroom leave");
    	sender.sendMessage(" - /chatroom invite <player>");
    }
	
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("chatroom")) {
        	if (args.length == 1) {
        		ArrayList<String> cmds = new ArrayList<String>();
        		cmds.add("create");
        		cmds.add("join");
        		cmds.add("leave");
        		cmds.add("invite");
        		return cmds;
        	}
        	if (args.length == 2 && args[0].equalsIgnoreCase("invite")) {
        		ArrayList<String> cmds = new ArrayList<String>();
        		for (Player player : plugin.getServer().getOnlinePlayers()) {
        			cmds.add(player.getName());
        		}
        		return cmds;
        	}
        	
        	return new ArrayList<>();
        }
        return new ArrayList<>();
    }
}

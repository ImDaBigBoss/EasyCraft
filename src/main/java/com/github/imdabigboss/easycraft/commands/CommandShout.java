package com.github.imdabigboss.easycraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.imdabigboss.easycraft.ChatRoom;
import com.github.imdabigboss.easycraft.easyCraft;

public class CommandShout implements CommandExecutor {
	private Plugin plugin = easyCraft.getPlugin();
	private ChatRoom chatRoom = easyCraft.getChatRoom();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (!(sender instanceof Player)) { //Get if executing origin is a player
    		ChatRoom chatRoom = easyCraft.getChatRoom();
    		if (chatRoom.isInRoom(sender.getName())) {
    			if (args.length == 0) {
    				sender.sendMessage("Correct usage is /shout <message>");
    				return true;
    			}
    			
    			String message = String.join(" ", args);
    			Player p = (Player) sender;
    			plugin.getServer().broadcastMessage("<" + p.getDisplayName() + ChatColor.RESET + "> " + message);
    		} else {
    			sender.sendMessage("You must be in a room to use this command!");
    		}
    	} else {
    		sender.sendMessage("You need to be a player to use this command!");
    		return true;
    	}
    	return true;
    }
}
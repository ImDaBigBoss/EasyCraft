package com.github.imdabigboss.easycraft.commands;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.imdabigboss.easycraft.*;

import net.md_5.bungee.api.ChatColor;

public class CommandTpa implements CommandExecutor {
	private Plugin plugin = easyCraft.getPlugin();
	private TPAutils tpautil = easyCraft.getTpa();

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (sender instanceof Player) { //Get if executing origin is a player
    	} else {
    		sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
    		return true;
    	}
    	
    	if (command.getName().equalsIgnoreCase("tpa")) {
    		if (args.length != 1) {
    			sendHelp(sender, "tpa");
    			return true;
    		}
    		if (args[0].equals(sender.getName())) {
    			sender.sendMessage(ChatColor.RED + "You can not teleport to yourself!");
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
    		
    		int out = tpautil.createRequest((Player) sender, target, "there");
    		if (out == 0)
    			sender.sendMessage("You sent a request to " + args[0] + " so that you can teleport to them!");
    		else if (out == 1)
    			sender.sendMessage(ChatColor.RED + "Unsupported tpa request type, please contact the server Admin!");
    		else if (out == 2)
    			sender.sendMessage(ChatColor.RED + "This player already has a tpa request pending... Please wait!");
    	} else if (command.getName().equalsIgnoreCase("tpahere")) {
    		if (args.length != 1) {
    			sendHelp(sender, "tpahere");
    			return true;
    		}
    		if (args[0].equals(sender.getName())) {
    			sender.sendMessage(ChatColor.RED + "You can not teleport to yourself!");
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
    		
    		int out = tpautil.createRequest((Player) sender, target, "here");
    		if (out == 0)
    			sender.sendMessage("You sent a request to " + args[0] + " to teleport them to you!");
    		else if (out == 1)
    			sender.sendMessage(ChatColor.RED + "Unsupported tpa request type, please contact the server Admin!");
    		else if (out == 2)
    			sender.sendMessage(ChatColor.RED + "This player already has a tpa request pending... Please wait!");
    	} else if (command.getName().equalsIgnoreCase("tpaccept")) {
    		if (args.length != 0) {
    			sendHelp(sender, "tpaccept");
    			return true;
    		}
    		
    		int out = tpautil.tpaAccept((Player) sender);
    		if (out == 0)
    			sender.sendMessage("Tpa request accepted!");
    		else if (out == 1)
    			sender.sendMessage(ChatColor.RED + "You do not have any pending tpa requests!");
    		else if (out == 2)
    			sender.sendMessage("That player is no longer online... sorry!");
    	} else if (command.getName().equalsIgnoreCase("tpdeny")) {
    		if (args.length != 0) {
    			sendHelp(sender, "tpdeny");
    			return true;
    		}
    		
    		int out = tpautil.tpaDeny((Player) sender);
    		if (out == 0)
    			sender.sendMessage("You denyed the tpa request!");
    		else if (out == 1)
    			sender.sendMessage(ChatColor.RED + "You do not have any pending tpa requests!");
    		else if (out == 2)
    			sender.sendMessage("You denyed the tpa request, but the player was no longer online anyway!");
    	}
    	return true;
    }
    
    public void sendHelp(CommandSender sender, String cmd) {
    	sender.sendMessage("The correct usage is:");
    	if (cmd == "tpa") {
    		sender.sendMessage("/tpa <player>");
    	} else if (cmd == "tpahere") {
    		sender.sendMessage("/tpahere <player>");
    	} else if (cmd == "tpaccept") {
    		sender.sendMessage("/tpaccept");
    	} else if (cmd == "tpdeny") {
    		sender.sendMessage("/tpdeny");
    	}
    }
}
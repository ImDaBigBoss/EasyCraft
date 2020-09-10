package com.github.imdabigboss.easycraft.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.imdabigboss.easycraft.*;

public class CommandRank implements CommandExecutor {
	private final Ranks ranks = easyCraft.getRanks();
	private final Plugin plugin = easyCraft.getPlugin();
	
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (args.length == 0) {
    		sender.sendMessage("Choose 'get', 'set', 'add', 'remove', 'relaod' or 'list' for ranks");
    		return false;
    	}
    	
    	if (args[0].equalsIgnoreCase("get")) { //See if we are using get or set
    		if (args.length != 2) {  //Check if the command has the right amount of args
    			sender.sendMessage("Please set only a player name");
    			return false;
    		}
    		
    		String rank = ranks.getRank(args[1]);
    		if (rank == "") {
    			sender.sendMessage(args[1] + "'s rank could not be found...");
    		} else {
    		    sender.sendMessage(args[1] + "'s rank is " + rank);
    		}
    	} else if (args[0].equalsIgnoreCase("set")) {
    		if (args.length != 3) {  //Check if the command has the right amount of args
    			sender.sendMessage("Please set a player name and a rank");
    			return false;
    		}
    		
    		try {
    		    Player play = plugin.getServer().getPlayer(args[1]); //Get the player from the name
    		    System.out.println(play.getName());
    		    int ret = ranks.setRank(play, args[2]);
    		    if (ret == 1) { //Error code for rank that doesn't exist
    			    sender.sendMessage("That rank does not exist");
    			    return true;
    		    }
    		} catch(NullPointerException npe) { //Player is not online
    			sender.sendMessage(ChatColor.RED + "\"" + args[1] + "\" is not online!");
    			return true;
    		}
    		
    		sender.sendMessage(args[1] + "'s rank has successfully been set to " + args[2] + "!");
    		try {
    			plugin.getServer().getPlayer(args[1]).sendMessage(ChatColor.GOLD + sender.getName() + " set your rank to " + args[2] + "!");
    		} catch (Exception e) {
    			
    		}
    	} else if (args[0].equalsIgnoreCase("add")) {
    		if (args.length != 3) {  //Check if the command has the right amount of args
    			sender.sendMessage("Please enter a new rank name and color");
    			return false;
    		}
    		
    		boolean out = ranks.addRank(args[1], args[2]);
    		if (out) {
    			sender.sendMessage("Successfully added the rank " + args[1] + "!");
    		} else {
    			sender.sendMessage(ChatColor.RED + "An error occured while adding your rank!");
    		}
    	} else if (args[0].equalsIgnoreCase("remove")) {
    		if (args.length != 2) {  //Check if the command has the right amount of args
    			sender.sendMessage("Please enter rank name to remove");
    			return false;
    		}
    		
    		boolean out = ranks.removeRank(args[1]);
    		if (out) {
    			sender.sendMessage("Successfully removed the rank " + args[1] + "!");
    		} else {
    			sender.sendMessage(ChatColor.RED + "An error occured while removing your rank!");
    		}
    	} else if (args[0].equalsIgnoreCase("reload")) {
    		ranks.reloadRanks();
    		ranks.reloadPlayerRanks();
    		sender.sendMessage(ChatColor.GOLD + "Reloaded the ranks!");
    	} else if (args[0].equalsIgnoreCase("list")) {
    		sender.sendMessage("Ranks :");
    		for (int i = 0; i < ranks.AllRanks.size(); i++) {
    			sender.sendMessage("- " + ranks.rankColors.get(ranks.AllRanks.get(i)) + ranks.AllRanks.get(i));
    		}
    	} else {
    		sender.sendMessage("Choose 'get', 'set', 'add', 'remove', 'relaod' or 'list' for ranks");
    		return false;
    	}
    	return true;
    }
}
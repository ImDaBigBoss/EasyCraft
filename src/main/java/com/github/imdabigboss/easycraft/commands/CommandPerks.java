package com.github.imdabigboss.easycraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.imdabigboss.easycraft.*;

import net.md_5.bungee.api.ChatColor;

public class CommandPerks implements CommandExecutor, TabExecutor {
	private final Perks perks = easyCraft.getPerks();
	private final Plugin plugin = easyCraft.getPlugin();
	
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (args.length == 0) {
    		sendHelp(sender);
    		return true;
    	}
    	
    	if (args[0].equalsIgnoreCase("list")) {
    		sender.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Perks:");
    		sender.sendMessage(ChatColor.AQUA + "DonatorIII:");
    		sender.sendMessage("- superdog <DogName> (invincible dog with custom name)"); 
    		sender.sendMessage("- supercat <CatName> (invincible cat with custom name)");
    		sender.sendMessage(ChatColor.AQUA + "DonatorII and +:");
    		sender.sendMessage("- enderchest (portable enderchest, /enderchest)");
    		sender.sendMessage(ChatColor.AQUA + "DonatorI and +:");
    		sender.sendMessage("- magicstick (knockback 10 + fire aspect stick)"); 
    	} else if (args[0].equalsIgnoreCase("get")) {
    		if (sender instanceof Player) { //Get if executing origin is a player
        	} else {
        		sender.sendMessage("You must be a player to execute this command!");
        		return true;
        	}
    		
    		String out = perks.getPerk(sender, args);
    		if (out == "")
    			sender.sendMessage("You have baught the perk!");
    		else
    			sender.sendMessage(out);
    	} else if  (args[0].equalsIgnoreCase("money")) {
    		if (plugin.getConfig().contains(sender.getName() + ".money")) {
    			sender.sendMessage("You have " + plugin.getConfig().getString(sender.getName() + ".money") + " Ravel coins.");
    		} else {
    			sender.sendMessage("You have 0 Ravel coins.");
    		}
    	} else if  (args[0].equalsIgnoreCase("admin")) {
    		perksAdmin(args, sender); //Admin perks
    	} else {
    		sendHelp(sender);
    	}
    	return true;
    }
    
    public void sendHelp(CommandSender p) {
    	p.sendMessage("Here is the correct uage:");
    	p.sendMessage("- /perks list");
    	p.sendMessage("- /perks get <perk name>");
    	p.sendMessage("- /perks money");
    }
    
    public void sendAdminHelp(CommandSender p) {
    	p.sendMessage("Here is the correct uage:");
    	p.sendMessage("- /perks admin set <player> <money>");
    	p.sendMessage("- /perks admin get <player>");
    }
    
    public void perksAdmin(String[] args, CommandSender sender) {
    	if (!sender.hasPermission("ravelcraft.perks.admin")) {
    		sender.sendMessage("You are not an admin!");
    		return;
    	}
    	
    	if (args.length < 2) {
    		sendAdminHelp(sender);
    		return;
    	}
    	
    	if (args[1].equalsIgnoreCase("set")) {
    		if (args.length != 4) {
        		sendAdminHelp(sender);
        		return;
    		}
    		
    		try {
    			perks.setMoney(args[2], Integer.parseInt(args[3]));
    			sender.sendMessage("Set " + args[2] + "'s balance to " + args[3] + "!");
    		} catch (Exception e) {
    			sender.sendMessage("An error occured!");
    		}
    	} else if (args[1].equalsIgnoreCase("get")) {
    		if (args.length != 3) {
        		sendAdminHelp(sender);
        		return;
    		}
    		
    		sender.sendMessage(args[2] + " has " + perks.getMoney(args[2]));
    	} else {
    		sendAdminHelp(sender);
    	}
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("perks")) {
        	if (args.length == 1) {
        		ArrayList<String> cmds = new ArrayList<String>();
        		cmds.add("list");
        		cmds.add("get");
        		cmds.add("money");
        		cmds.add("admin");
        		return cmds;
        	} else if (args.length == 2) {
        		if (args[0].equalsIgnoreCase("get")) {
        			ArrayList<String> cmds = new ArrayList<String>();
        			cmds.add("superdog");
        			cmds.add("supercat");
        			cmds.add("enderchest");
        			cmds.add("magicstick");
        			return cmds;
        		} else if (args[0].equalsIgnoreCase("admin")) {
        			ArrayList<String> cmds = new ArrayList<String>();
        			cmds.add("get");
        			cmds.add("set");
        			return cmds;
        		}
        		return new ArrayList<>();
        	} else if (args.length == 3) {
        		if (args[1].equalsIgnoreCase("get")) {
        			ArrayList<String> cmds = new ArrayList<String>();
            		for (Player player : plugin.getServer().getOnlinePlayers()) {
            			cmds.add(player.getName());
            		}
            		return cmds;
        		} else if (args[1].equalsIgnoreCase("set")) {
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
        return new ArrayList<>();
    }
}
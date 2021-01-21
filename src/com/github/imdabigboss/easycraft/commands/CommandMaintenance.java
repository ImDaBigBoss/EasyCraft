package com.github.imdabigboss.easycraft.commands;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

import com.github.imdabigboss.easycraft.*;

public class CommandMaintenance implements CommandExecutor, TabExecutor {
	private Plugin plugin = easyCraft.getPlugin();
	
	// This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (args.length != 1) {
    		sendHelp(sender);
    		return true;
    	}
    	
    	if (args[0].equalsIgnoreCase("on")) {
    		plugin.getConfig().set("Maintenance", "on");
    		sender.sendMessage(ChatColor.AQUA + "Turned on maintenance mode!");
    		plugin.saveConfig();
    		kickPlayers(sender.getName());
    	} else if (args[0].equalsIgnoreCase("off")) {
    		plugin.getConfig().set("Maintenance", "off");
    		sender.sendMessage(ChatColor.AQUA + "Turned off maintenance mode!");
    		plugin.saveConfig();
    		for (Player p:  plugin.getServer().getOnlinePlayers()) {
        		p.sendMessage(ChatColor.AQUA + sender.getName() + " has disabled maintenance mode!");
        	}
    	} else {
    		sendHelp(sender);
    		return true;
    	}
    	return true;
    }
    
    private void kickPlayers(String player) {
    	for (Player p:  plugin.getServer().getOnlinePlayers()) {
    		if (!p.isOp())
    			p.kickPlayer("We very sorry " + ChatColor.YELLOW + p.getName() + ChatColor.RESET + " but the server has been put under " + ChatColor.RED + "maintenance mode");
    	}
    	for (Player p:  plugin.getServer().getOnlinePlayers()) {
    		p.sendMessage(ChatColor.AQUA + player + " has enabled maintenance mode!");
    	}
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("maintenance")) {
        	if (args.length == 1) {
        		ArrayList<String> cmds = new ArrayList<String>();
        		cmds.add("on");
        		cmds.add("off");
        		return cmds;
        	}
        	return new ArrayList<>();
        }
        return new ArrayList<>();
    }
    
    public void sendHelp(CommandSender sender) {
    	sender.sendMessage("Maintenance mode is " + plugin.getConfig().get("Maintenance"));
		sender.sendMessage("You may set maintenance mode using 'on' or 'off'!");
    }
}

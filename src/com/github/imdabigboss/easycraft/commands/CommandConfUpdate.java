package com.github.imdabigboss.easycraft.commands;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.imdabigboss.easycraft.easyCraft;

import net.md_5.bungee.api.ChatColor;

public class CommandConfUpdate implements CommandExecutor {
	private Plugin plugin = easyCraft.getPlugin();
	
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (sender instanceof ConsoleCommandSender) {
    	} else {
    		sender.sendMessage("You may only warn players of an update as console!!!"); 
    		return true;
    	}
    	
    	long stopTime = 30L;
    	if (plugin.getConfig().contains("confupdate-stop"))
    		stopTime = plugin.getConfig().getLong("confupdate-stop");
    	
    	plugin.getServer().broadcastMessage("This server will have to reboot because of some configuration or plugin updates!");
    	plugin.getServer().broadcastMessage("Please finish what you are doing and come back online in about 5 minutes.");
    	
    	plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "save-all");
    	
    	sender.sendMessage(ChatColor.RED + "-----------------------------------------------------");
    	sender.sendMessage(ChatColor.RED + "YOU WILL HAVE TO START THE SERVER ONCE IT IS STOPPED!");
    	sender.sendMessage(ChatColor.RED + "-----------------------------------------------------");
    	plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
        {
            public void run()
            {
            	for (Player player:  plugin.getServer().getOnlinePlayers()) {
        			player.kickPlayer("We are sorry " + ChatColor.GOLD + player.getName() + ChatColor.RESET + " but this server is rebooting because of some configuration or plugin updates!" + ChatColor.GREEN + " Please check back in about 5 minutes.");
            	}
            	plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "stop");
            }
        }, stopTime * 20L);
    	
		return true;
    }
}
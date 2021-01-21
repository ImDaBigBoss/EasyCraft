package com.github.imdabigboss.easycraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.imdabigboss.easycraft.easyCraft;

public class CommandSpawn implements CommandExecutor, TabExecutor {
	private final Plugin plugin = easyCraft.getPlugin();
	
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (sender instanceof Player) { //Get if executing origin is a player
    		Location spawnpoint = plugin.getServer().getWorld("world").getSpawnLocation();
    		((Player) sender).teleport(spawnpoint);
    		sender.sendMessage("You have been teleported to spawn!");
    	} else {
    		sender.sendMessage("You need to be a player to use this command!");
    		return true;
    	}
    	return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}
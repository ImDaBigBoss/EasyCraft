package com.github.imdabigboss.easycraft.commands;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.imdabigboss.easycraft.*;

import net.md_5.bungee.api.ChatColor;

public class CommandHome implements CommandExecutor {
	private Plugin plugin = easyCraft.getPlugin();
	private final ymlUtils yml = easyCraft.getYml();

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (sender instanceof Player) { //Get if executing origin is a player
    	} else {
    		sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
    		return true;
    	}
    	
    	int maxHomes = 0;
    	if (yml.getConfig("homes.yml").contains("maxHomes"))
    			maxHomes = yml.getConfig("homes.yml").getInt("maxHomes");
    	
    	if (args.length != 1) {
    		sender.sendMessage(ChatColor.AQUA + "You must enter a home number! Your max home count is: " + maxHomes);
    		return true;
    	}
    	
    	if (command.getName().equals("home")) {
    		home((Player)sender, args[0]);
    	} else if (command.getName().equals("sethome")) {
    		setHome((Player)sender, args[0], maxHomes);
    	} else {
    		
    	}
    	return true;
    }
    
    public void home(Player player, String number) {
    	String info = player.getUniqueId() + "." + number;
    	if (!yml.getConfig("homes.yml").contains(info  + ".World")) {
    		player.sendMessage("You have no home with that number!\nYou must go /home <homeNumber>");
    		return;
    	}
    	World world = plugin.getServer().getWorld(yml.getConfig("homes.yml").getString(info + ".World"));
    	if (world == null) {
    		player.sendMessage(ChatColor.RED + "An error occured during teleportation!");
    		return;
    	}
    	
    	Location loc = player.getLocation();
    	loc.setWorld(world);
    	
    	loc.setYaw((float)yml.getConfig("homes.yml").getDouble(info  + ".Yaw", 0));
    	loc.setPitch((float)yml.getConfig("homes.yml").getDouble(info  + ".Pitch", 0));
    	
    	loc.setX(yml.getConfig("homes.yml").getDouble(info  + ".X"));
    	loc.setY(yml.getConfig("homes.yml").getDouble(info  + ".Y"));
    	loc.setZ(yml.getConfig("homes.yml").getDouble(info  + ".Z"));
    	
    	player.teleport(loc);
    	player.sendMessage(ChatColor.AQUA + "You have been teleported home!");
    }
    
    public void setHome(Player player, String number, int maxHomes) {
    	try {
    		if (Integer.parseInt(number) > maxHomes) {
    			player.sendMessage("Your max home count is " + maxHomes + "!");
    			return;
    		}
    	} catch (Exception e) {
    		player.sendMessage("That is not a number!\nYou must go: /sethome <homeNumber>");
			return;
    	}
    	
    	String info = player.getUniqueId() + "." + number;
    	yml.getConfig("homes.yml").set(info + ".World", player.getWorld().getName());
    	
    	Location loc = player.getLocation();
    	yml.getConfig("homes.yml").set(info + ".X", loc.getX());
    	yml.getConfig("homes.yml").set(info + ".Y", loc.getY());
    	yml.getConfig("homes.yml").set(info + ".Z", loc.getZ());
    	
    	yml.getConfig("homes.yml").set(info + ".Pitch", loc.getPitch());
    	yml.getConfig("homes.yml").set(info + ".Yaw", loc.getYaw());
    	player.sendMessage(ChatColor.AQUA + "You set your home here!");
    }
}
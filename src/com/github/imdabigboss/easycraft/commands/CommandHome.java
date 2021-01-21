package com.github.imdabigboss.easycraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.imdabigboss.easycraft.*;

import net.md_5.bungee.api.ChatColor;

public class CommandHome implements CommandExecutor, TabExecutor {
	private Plugin plugin = easyCraft.getPlugin();
	private final ymlUtils yml = easyCraft.getYml();

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	int maxHomes = 0;
    	if (yml.getConfig("homes.yml").contains("maxHomes"))
    			maxHomes = yml.getConfig("homes.yml").getInt("maxHomes");
    	
    	if (command.getName().equals("home")) {
    		if (args.length != 1) {
        		sender.sendMessage(ChatColor.AQUA + "You must enter a home number! Your max home count is: " + maxHomes);
        		return true;
        	}
    		if (sender instanceof Player) { //Get if executing origin is a player
        	} else {
        		sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
        		return true;
        	}
    		home((Player)sender, args[0]);
    	} else if (command.getName().equals("sethome")) {
    		if (args.length != 1) {
        		sender.sendMessage(ChatColor.AQUA + "You must enter a home number! Your max home count is: " + maxHomes);
        		return true;
        	}
    		if (sender instanceof Player) { //Get if executing origin is a player
        	} else {
        		sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
        		return true;
        	}
    		setHome((Player)sender, args[0], maxHomes);
    	} else if (command.getName().equals("gethome")) {
    		if (args.length != 2) {
        		sender.sendMessage(ChatColor.AQUA + "You must enter a home number and a player name!");
        		return true;
        	}
    		getHome(sender, args[0], args[1]);
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
	    
	yml.saveConfig("homes.yml");
    	player.sendMessage(ChatColor.AQUA + "You set your home here!");
    }
    
    public void getHome(CommandSender sender, String number, String targetP) {
    	String target = easyCraft.getUUID(targetP);
    	if (target == "") {
    		sender.sendMessage("The player must be online!");
    		return;
    	}
    	
    	String info = target + "." + number;
    	if (!yml.getConfig("homes.yml").contains(info + ".World")) {
    		sender.sendMessage("This player does not have a home with the number: " + number);
    		return;
    	}
    	
    	String xyz = yml.getConfig("homes.yml").getDouble(info  + ".X") + " " + yml.getConfig("homes.yml").getDouble(info  + ".Y") + " " + yml.getConfig("homes.yml").getDouble(info  + ".Z");
    	String world = yml.getConfig("homes.yml").getString(info + ".World");
    	sender.sendMessage(targetP + "'s home " + number + " is located at " + xyz + " in the world named " + world + "!");
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
    	if (sender instanceof Player) { //Get if executing origin is a player
    	} else {
    		return null;
    	}
    	
    	Player player = (Player)sender;
    	String uuid = player.getUniqueId().toString();
    	
        if (command.getName().equalsIgnoreCase("home")) {
        	if (args.length == 1) {
        		ArrayList<String> cmds = new ArrayList<String>();
        		
        		int maxHomes = 0;
            	if (yml.getConfig("homes.yml").contains("maxHomes"))
            			maxHomes = yml.getConfig("homes.yml").getInt("maxHomes");
            	
            	for (int i = 1; i <= maxHomes; i++) {
            		if (yml.getConfig("homes.yml").contains(uuid + "." + i + ".World"))
            			cmds.add(Integer.toString(i));
            	}
        		
        		return cmds;
        	}
        	
        	return new ArrayList<>();
        } else if (command.getName().equalsIgnoreCase("sethome") || command.getName().equalsIgnoreCase("gethome")) {
        	if (args.length == 1) {
        		ArrayList<String> cmds = new ArrayList<String>();
        		
        		int maxHomes = 0;
            	if (yml.getConfig("homes.yml").contains("maxHomes"))
            			maxHomes = yml.getConfig("homes.yml").getInt("maxHomes");
            	
            	for (int i = 1; i <= maxHomes; i++) {
            		cmds.add(Integer.toString(i));
            	}
        		
        		return cmds;
        	} else if (args.length == 2) {
        		ArrayList<String> cmds = new ArrayList<String>();
        		for (Player p : plugin.getServer().getOnlinePlayers()) {
        			cmds.add(p.getName());
        		}
        		return cmds;
        	}
        	
        	return new ArrayList<>();
        }
        return new ArrayList<>();
    }
}

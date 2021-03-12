package com.github.imdabigboss.easycraft;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;

public class MyListener implements Listener {
	private Plugin plugin = easyCraft.getPlugin();

	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) //when a player joins
    {
		Player player = event.getPlayer();
		
		if (plugin.getConfig().get("Maintenance").equals("on")) {
			if (!player.hasPermission("easycraft.maintenance.bypass"))
				player.kickPlayer("We very sorry " + ChatColor.YELLOW + player.getName() + ChatColor.RESET + " but the server has been put under " + ChatColor.RED + "maintenance mode");
		}
		
		player.sendMessage(ChatColor.GREEN + "Welcome to the server " + ChatColor.RED + event.getPlayer().getName() + ChatColor.GREEN + "!");
		if (plugin.getConfig().contains("joinText")) {
			player.sendMessage(ChatColor.GREEN + (String)plugin.getConfig().get("joinText"));
		}
		
		if (plugin.getServer().getOnlinePlayers().size() >= plugin.getServer().getMaxPlayers()) {
			plugin.getServer().broadcastMessage("There is now " + ChatColor.RED + plugin.getServer().getOnlinePlayers().size() + ChatColor.RESET + " players connected, maybe a few players could leave to free up space for other players wanting to join!");
		}
		
		easyCraft.connectionLog(player.getName(), 1);
    }
	
	@EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) //when a player quits
    {
		Player player = event.getPlayer();
		easyCraft.connectionLog(player.getName(), 2);
    }
	
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
    	Player player = event.getEntity();
    	Entity killer = player.getKiller();
    	if (killer instanceof Player){
    		Player killerp = (Player) killer;
    		event.setDeathMessage(killerp.getName() + " skillfully killed " + player.getName() + " :D");
    		player.getWorld().dropItem(player.getLocation(), easyCraft.getHead(player));
    	} else if (killer instanceof org.bukkit.entity.Creeper) {
    		event.setDeathMessage("Hello mine turtle! HELLO " + player.getName() + "! BOOM!");
    	}
  	}
	
	@EventHandler
	public void chatFormat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		
		event.setFormat(p.getDisplayName() + ChatColor.RESET + ": " + event.getMessage());
	}
	
	@EventHandler
	public void onPlayerCommandMonitor(PlayerCommandPreprocessEvent event) {
	    String message = event.getMessage();
	    String arguments = "";
	    String command = "";
	    if (!message.contains(" ")) {
	        command = message.replace("/", "");
	    } else {
	        command = message.substring(0, message.indexOf(" ")).replace("/", "");
	        arguments = message.substring(message.indexOf(" ") + 1, message.length());
	    }
	    
	    if (command.equalsIgnoreCase("tpa") || command.equalsIgnoreCase("tpahere") || command.equalsIgnoreCase("tpaccept") || command.equalsIgnoreCase("tpdeny") || command.equalsIgnoreCase("home") || command.equalsIgnoreCase("sethome") || command.equalsIgnoreCase("spawn") || command.equalsIgnoreCase("msg") || command.equalsIgnoreCase("suicide") || command.equalsIgnoreCase("chatroom") || command.equalsIgnoreCase("enderchest") || command.equalsIgnoreCase("shout") || command.equalsIgnoreCase("headitem") || command.equalsIgnoreCase("info") || command.equalsIgnoreCase("perks")) {
	    	
	    } else {
	    	String out = "------ COMMAND: " + event.getPlayer().getName() + " ran: " + message + " ------";
	        plugin.getServer().getConsoleSender().sendMessage(out);
	        easyCraft.commandLog(event.getPlayer().getName(), message);
	    }
	}
}

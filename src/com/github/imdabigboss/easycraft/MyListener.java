package com.github.imdabigboss.easycraft;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;

public class MyListener implements Listener {
	private final Ranks ranks = easyCraft.getRanks();
	private Plugin plugin = easyCraft.getPlugin();
	private ChatRoom chatRoom = easyCraft.getChatRoom();
	
	@EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) //when a player joins
    {
		Player player = event.getPlayer();
		
		if (plugin.getConfig().get("Maintenance").equals("on")) {
			if (!player.isOp())
				player.kickPlayer("We very sorry " + ChatColor.YELLOW + player.getName() + ChatColor.RESET + " but the server has been put under " + ChatColor.RED + "maintenance mode");
		}
		
		player.sendMessage(ChatColor.GREEN + "Welcome to the server " + ChatColor.RED + event.getPlayer().getName() + ChatColor.GREEN + "!");
		if (plugin.getConfig().contains("joinText")) {
			player.sendMessage(ChatColor.GREEN + (String)plugin.getConfig().get("joinText"));
		}
		
		String playerRank = ranks.getRank(player.getName());
		ranks.displayRank(player, playerRank);
		
		if (plugin.getServer().getOnlinePlayers().size() >= plugin.getServer().getMaxPlayers()) {
			plugin.getServer().broadcastMessage("There is now " + ChatColor.RED + plugin.getServer().getOnlinePlayers().size() + ChatColor.RESET + " players connected, maybe a few players could leave to free up space for other players wanting to join!");
		}
    }
	
	@EventHandler
	public void chatFormat(AsyncPlayerChatEvent event){
		Player p = event.getPlayer();
		if (chatRoom.isInRoom(p.getName())) {
			event.setFormat(ChatColor.BLUE + "Room> " + ChatColor.RESET + p.getDisplayName() + ChatColor.RESET + ": " + event.getMessage());
			
			event.getRecipients().clear();
			event.getRecipients().addAll(chatRoom.getRoomPlayers(p.getName()));
		} else {
			event.setFormat(p.getDisplayName() + ChatColor.RESET + ": " + event.getMessage());
		}
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
	    
	    if (command.equalsIgnoreCase("tpa") || command.equalsIgnoreCase("tpaccept") || command.equalsIgnoreCase("tpdeny") || command.equalsIgnoreCase("home") || command.equalsIgnoreCase("spawn") || command.equalsIgnoreCase("msg")) {
	    	
	    } else {
	    	String out = "------ COMMAND: " + event.getPlayer().getName() + " ran: " + message + " ------";
	        plugin.getServer().getConsoleSender().sendMessage(out);
	    }
	}
}

package com.github.imdabigboss.easycraft;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.md_5.bungee.api.ChatColor;

public class TPAutils {
	private Plugin plugin = easyCraft.getPlugin();
	
	public Map<Player, Player> tpa = new HashMap<Player, Player>();
	public Map<Player, String> tpatype = new HashMap<Player, String>();
	
	
	public int createRequest(Player sender, Player target, String type) {
		if (tpa.containsKey(target))
			return 2;
		
		tpa.put(target, sender);
		tpatype.put(target, type);
		
		String typetext;
		if (type == "here") {
			typetext = "you to them";
		} else if (type == "there") {
			typetext = "to you";
		} else {
			return 1;
		}
		
		long keepAlive = 30L;
		if (plugin.getConfig().contains("tpa-keep-alive")) {
			keepAlive = plugin.getConfig().getLong("tpa-keep-alive");
		} else {
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.RED + "You have not specified a tpa keep alive time. Set it with tpa-keep-alive in config.yml!");
		}
		
		target.sendMessage(sender.getDisplayName() + " would like to teleport " + typetext + "! You may accept using " + ChatColor.GOLD + "/tpaccept" + ChatColor.RESET + " or deny the request with " + ChatColor.GOLD + "/tpdeny" + ChatColor.RESET + ".");
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable()
        {
            public void run()
            {
            	timedOut(sender, target);
            }
        }, keepAlive * 20L);
		return 0;
	}
	
	public int tpaAccept(Player sender) {
		if (!tpa.containsKey(sender))
			return 1;
		
		Player tpaTo = tpa.get(sender);
		if (!tpaTo.isOnline())
			return 2;
		
		tpaTo.sendMessage(sender.getDisplayName() + " has accepted your tpa request!");
		if (tpatype.get(sender) == "here")
			sender.teleport(tpaTo.getLocation());
		else if (tpatype.get(sender) == "there")
			tpaTo.teleport(sender.getLocation());
		else
			return 3;
		
		tpa.remove(sender);
		tpatype.remove(sender);
		return 0;
	}
	
	public int tpaDeny(Player sender) {
		if (!tpa.containsKey(sender))
			return 1;
		
		Player tpaTo = tpa.get(sender);
		if (!tpaTo.isOnline())
			return 2;
		
		tpaTo.sendMessage(ChatColor.RED + sender.getDisplayName() + ChatColor.RED + " has denyed your tpa request...");
		tpa.remove(sender);
		tpatype.remove(sender);
		return 0;
	}
	
	public void timedOut(Player sender, Player target) {
		if (tpa.containsKey(target)) {
			if (sender.isOnline())
				sender.sendMessage(ChatColor.RED + "You teleport request for " + target.getDisplayName() + ChatColor.RED + " has timed out!");
			tpa.remove(target);
			tpatype.remove(target);
		}
	}
}

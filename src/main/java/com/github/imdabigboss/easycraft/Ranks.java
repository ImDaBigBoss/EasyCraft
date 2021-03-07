package com.github.imdabigboss.easycraft;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Ranks {
	private Plugin plugin = easyCraft.getPlugin();
	
	public ArrayList<String> AllRanks = new ArrayList<String>();
	public Map<String, ChatColor> rankColors = new HashMap<String, ChatColor>();
	
	public Ranks() {
		loadRanks();
	}
	
	public void loadRanks() {
		int rankNum = plugin.getConfig().getInt("ranks.rankNumber");
		for (int i = 1; i <= rankNum; i++) {
			String rankName = plugin.getConfig().getString("ranks.rank" + i + ".name");
			ChatColor chatColor = easyCraft.stingToChatColor(plugin.getConfig().getString("ranks.rank" + i + ".color"));
			
			AllRanks.add(rankName);
			rankColors.put(rankName, chatColor);
		}
	}
	
	public void reloadRanks() {
		AllRanks.clear();
		rankColors.clear();
		loadRanks();
	}
	
	public void reloadPlayerRanks() {
		for (Player player:  plugin.getServer().getOnlinePlayers()) {
			String playerRank = getRank(player.getName());
			displayRank(player, playerRank);
    	}
	}
	
	public int setRank(Player player, String rank) {
		boolean contains = false;
		for (int i = 0; i < AllRanks.size(); i++) {
			if (AllRanks.get(i).equalsIgnoreCase(rank)) {
				contains = true;
				displayRank(player, rank);
			}
		}
		
		if (contains == false) {
			if (rank.equalsIgnoreCase("None")) {
				plugin.getConfig().set(player.getName() + ".rank", null); //Save the rank to config
				plugin.saveConfig();
				return 0;
			}
			return 1; //If the rank requested does not exist
		}
		
		plugin.getConfig().set(player.getName() + ".rank", rank); //Save the rank to config
		plugin.saveConfig();
		return 0;
	}
	
	public void displayRank(Player player, String rank) {
		boolean contains = false;
		for (int i = 0; i < AllRanks.size(); i++) {
			if (AllRanks.get(i).equalsIgnoreCase(rank)) {
				contains = true;
				player.setDisplayName("[" + rankColors.get(AllRanks.get(i)) + AllRanks.get(i) + ChatColor.RESET + "]" + player.getName());
			}
		}
		
		if (contains == false) { //Check if it is no rank we are giving
			player.setDisplayName(player.getName());
		}
	}
	
	public String getRank(String player) { //Get a player's rank
		if (plugin.getConfig().contains(player + ".rank")) { //Get if the player's rank has been set
		    return (String) plugin.getConfig().get(player + ".rank");
		} else {
			return ""; //Retrun nothing if the rank has not been saved
		}
	}
	
	public boolean addRank(String rank, String color) {
		int rankNum = plugin.getConfig().getInt("ranks.rankNumber") + 1; //Add a rank
		plugin.getConfig().set("ranks.rankNumber", rankNum);
		
		plugin.getConfig().set("ranks.rank" + rankNum + ".name", rank); //Add the rank's name
		plugin.getConfig().set("ranks.rank" + rankNum + ".color", color); //Add the rank's color
		plugin.saveConfig();
		
		if (plugin.getConfig().getString("ranks.rank" + rankNum + ".name") == "") {
			return false;
		} else {
			reloadRanks();
			reloadPlayerRanks();
			return true;
		}
	}
	
	public boolean removeRank(String rank) {
		int rankNum = plugin.getConfig().getInt("ranks.rankNumber"); //get the current amout of rank
		int removeRank = 0;
		plugin.getConfig().set("ranks.rankNumber", rankNum - 1); //Set the new amount of ranks
		
		for (int i = 1; i <= rankNum; i++) {
			String rankName = plugin.getConfig().getString("ranks.rank" + i + ".name");
			
			if (rankName.equalsIgnoreCase(rank)) {
				plugin.getConfig().set("ranks.rank" + i + ".name", null);
				plugin.getConfig().set("ranks.rank" + i + ".color", null);
				removeRank = i;
			}
		}
		if (removeRank == 0)
			return false;
		if (removeRank == rankNum) {
			plugin.getConfig().set("ranks.rank" + rankNum + ".name", null);
			plugin.getConfig().set("ranks.color" + rankNum + ".name", null);
			plugin.saveConfig();
			return true;
		}
		
		for (int i = removeRank; i <= rankNum; i++) {
			plugin.getConfig().set("ranks.rank" + i + ".name", plugin.getConfig().getString("ranks.rank" + (i+1) + ".name"));
			plugin.getConfig().set("ranks.rank" + i + ".color", plugin.getConfig().getString("ranks.rank" + (i+1) + ".color"));
		}
		plugin.getConfig().set("ranks.rank" + rankNum + ".name", null);
		plugin.getConfig().set("ranks.color" + rankNum + ".name", null);
		plugin.saveConfig();
		
		reloadRanks();
		reloadPlayerRanks();
		return true;
	}
}

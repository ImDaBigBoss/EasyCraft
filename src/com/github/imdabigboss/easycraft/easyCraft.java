package com.github.imdabigboss.easycraft;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.*;

import org.bukkit.ChatColor;
import org.bukkit.configuration.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.github.imdabigboss.easycraft.commands.*;


public class easyCraft extends JavaPlugin {
    private static Plugin plugin;
    private static Ranks ranks;
    private static Perks perks;
    private static ChatRoom chatroom;
    private static TPAutils tpa;
    private static ymlUtils yml;
    
    public static String serverName = "My Server!";
    
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	if (this.getConfig().contains("serverName"))
    		serverName = this.getConfig().getString("serverName");
    	
        plugin = this;
        ranks = new Ranks();
        perks = new Perks();
        chatroom = new ChatRoom();
        tpa = new TPAutils();
        yml = new ymlUtils();
        
        getServer().getPluginManager().registerEvents(new MyListener(), this); //Enable the listener
        
        //Enable the commands
        this.getCommand("rank").setExecutor(new CommandRank());
        this.getCommand("spawn").setExecutor(new CommandSpawn());
        this.getCommand("maintenance").setExecutor(new CommandMaintenance());
        this.getCommand("setmaxplayers").setExecutor(new CommandSetMaxPlayers());
        this.getCommand("confupdate").setExecutor(new CommandConfUpdate());
        this.getCommand("perks").setExecutor(new CommandPerks());
        this.getCommand("chatroom").setExecutor(new CommandChatRoom());
        this.getCommand("shout").setExecutor(new CommandShout());
        this.getCommand("info").setExecutor(new CommandInfo());
        this.getCommand("tempban").setExecutor(new CommandTempBan());
        this.getCommand("hiddenSpec").setExecutor(new CommandHiddenSpec());
        
        this.getCommand("tpa").setExecutor(new CommandTpa());
        this.getCommand("tpahere").setExecutor(new CommandTpa());
        this.getCommand("tpaccept").setExecutor(new CommandTpa());
        this.getCommand("tpdeny").setExecutor(new CommandTpa());
        
        this.saveDefaultConfig();
        yml.createConfig("bans.yml");
        if (!yml.getConfig("bans.yml").contains("ban"))
            yml.getConfig("bans.yml").set("ban", 0);
        
        for (Player player:  plugin.getServer().getOnlinePlayers()) {
        	customList(player);
    	}
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
        
    }
    
    public static Plugin getPlugin() {
        return plugin;
    }
    
    public static Ranks getRanks() {
        return ranks;
    }
    
    public static Perks getPerks() {
        return perks;
    }
    
    public static ChatRoom getChatRoom() {
        return chatroom;
    }
    
    public static TPAutils getTpa() {
        return tpa;
    }
    
    public static ymlUtils getYml() {
        return yml;
    }
    
    public static void customList(Player player) {
    	String dashes = "";
        for (int i = 1; i <= serverName.length(); i++) {
        	dashes = dashes + "-";
        }
    	
    	String playerName = ChatColor.RESET + player.getDisplayName();
    	
    	player.setPlayerListName(playerName);
		player.setPlayerListHeaderFooter(ChatColor.YELLOW + " --- " + serverName + " ---", ChatColor.YELLOW + dashes + "--------");
    }
    
    public static ChatColor stingToChatColor(String str) {
        switch(str) {
            case "AQUA": return ChatColor.AQUA;
            case "BLACK": return ChatColor.BLACK;
            case "BLUE": return ChatColor.BLUE;
            case "DARK_AQUA": return ChatColor.DARK_AQUA;
            case "DARK_BLUE": return ChatColor.DARK_BLUE;
            case "DARK_GRAY": return ChatColor.DARK_GRAY;
            case "DARK_GREEN": return ChatColor.DARK_GREEN;
            case "DARK_PURPLE": return ChatColor.DARK_PURPLE;
            case "DARK_RED": return ChatColor.DARK_RED;
            case "GOLD": return ChatColor.GOLD;
            case "GRAY": return ChatColor.GRAY;
            case "GREEN": return ChatColor.GREEN;
            case "LIGHT_PURPLE": return ChatColor.LIGHT_PURPLE;
            case "RED": return ChatColor.RED;
            case "WHITE": return ChatColor.WHITE;
            case "YELLOW": return ChatColor.YELLOW;
            default: return ChatColor.RESET;
        }
    }
}
   
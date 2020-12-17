package com.github.imdabigboss.easycraft;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Date;
import java.util.logging.Level;

import org.bukkit.ChatColor;
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
        this.getCommand("suicide").setExecutor(new CommandSuicide());
        this.getCommand("headitem").setExecutor(new CommandHeadItem());
        this.getCommand("enderchest").setExecutor(new CommandEnderchest());
        
        this.getCommand("tpa").setExecutor(new CommandTpa());
        this.getCommand("tpahere").setExecutor(new CommandTpa());
        this.getCommand("tpaccept").setExecutor(new CommandTpa());
        this.getCommand("tpdeny").setExecutor(new CommandTpa());
        
        this.getCommand("home").setExecutor(new CommandHome());
        this.getCommand("sethome").setExecutor(new CommandHome());
        this.getCommand("gethome").setExecutor(new CommandHome());
        
        this.saveDefaultConfig();
        yml.createConfig("homes.yml");
        yml.createConfig("bans.yml");
        yml.createConfig("connections.yml");
        yml.createConfig("commands.yml");
        if (!yml.getConfig("bans.yml").contains("bans"))
            yml.getConfig("bans.yml").set("bans", 0);
        
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
    	
    	player.setCustomName(playerName);
    	player.setCustomNameVisible(true);
    	player.setDisplayName(playerName);
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
    
    public static void connectionLog(String player, int action) {
    	int num = 0;
    	if (yml.getConfig("connections.yml").contains("connections"))
    		num = yml.getConfig("connections.yml").getInt("connections");
    	
    	num = num + 1;
    	String acstr = "ERROR";
    	if (action == 1) {
    		acstr = "connected";
    	} else if (action == 2) {
    		acstr = "disconnected";
    	}
    	yml.getConfig("connections.yml").set("con" + num, new Date().toString() + ": " + player + " -> " + acstr);
    	yml.getConfig("connections.yml").set("connections", num);
    	yml.saveConfig("connections.yml");
    }
    
    public static void commandLog(String player, String cmd) {
    	int num = 0;
    	if (yml.getConfig("commands.yml").contains("commands"))
    		num = yml.getConfig("commands.yml").getInt("commands");
    	
    	num = num + 1;
    	yml.getConfig("commands.yml").set("com" + num, new Date().toString() + ": " + player + " -> " + cmd);
    	yml.getConfig("commands.yml").set("commands", num);
    	yml.saveConfig("commands.yml");
    }
    
    public static String getUUID(String playerName) {
    	String out = "";
    	try {
    		out = plugin.getServer().getPlayer(playerName).getUniqueId().toString();
    	} catch (Exception e) {
    		
    	}
    	
    	return out;
    }
}
   
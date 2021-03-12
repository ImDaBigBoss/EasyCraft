package com.github.imdabigboss.easycraft;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;

import com.github.imdabigboss.easycraft.commands.*;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class easyCraft extends JavaPlugin implements PluginMessageListener {
    private static Plugin plugin;
    private static Perks perks = null;
    private static TPAutils tpa = null;
    private static ymlUtils yml;
    
    public static String serverName = "My Server!";
    
    // Fired when plugin is first enabled
    @Override
    public void onEnable() {
    	plugin = this;
        perks = new Perks();
        tpa = new TPAutils();
        yml = new ymlUtils();
        
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    	getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
        
        getServer().getPluginManager().registerEvents(new MyListener(), this); //Enable the listener
        
        //Enable the commands
        this.getCommand("spawn").setExecutor(new CommandSpawn());
        this.getCommand("maintenance").setExecutor(new CommandMaintenance());
        this.getCommand("setmaxplayers").setExecutor(new CommandSetMaxPlayers());
        this.getCommand("confupdate").setExecutor(new CommandConfUpdate());
        this.getCommand("perks").setExecutor(new CommandPerks());
        this.getCommand("enderchest").setExecutor(new CommandEnderchest());
        this.getCommand("serverinfo").setExecutor(new CommandServerInfo());
        this.getCommand("tempban").setExecutor(new CommandTempBan());
        this.getCommand("suicide").setExecutor(new CommandSuicide());
        this.getCommand("headitem").setExecutor(new CommandHeadItem());
        
        this.getCommand("tpa").setExecutor(new CommandTpa());
        this.getCommand("tpahere").setExecutor(new CommandTpa());
        this.getCommand("tpaccept").setExecutor(new CommandTpa());
        this.getCommand("tpdeny").setExecutor(new CommandTpa());
        

        this.getCommand("home").setExecutor(new CommandHome());
        this.getCommand("sethome").setExecutor(new CommandHome());
        this.getCommand("gethome").setExecutor(new CommandHome());
        this.getCommand("delhome").setExecutor(new CommandHome());
        
        this.saveDefaultConfig();
        yml.createConfig("homes.yml");
        yml.createConfig("bans.yml");
        yml.createConfig("connections.yml");
        yml.createConfig("commands.yml");
        
        if (!yml.getConfig("homes.yml").contains("maxHomes"))
        	yml.getConfig("homes.yml").set("maxHomes", 2);
        
        if (!yml.getConfig("bans.yml").contains("bans"))
            yml.getConfig("bans.yml").set("bans", 0);
        
        if (!yml.getConfig("connections.yml").contains("connections"))
            yml.getConfig("connections.yml").set("connections", 0);
        
        if (!yml.getConfig("commands.yml").contains("commands"))
            yml.getConfig("commands.yml").set("commands", 0);
    }
    
    // Fired when plugin is disabled
    @Override
    public void onDisable() {
        
    }
    
    public static Plugin getPlugin() {
        return plugin;
    }
    
    public static Perks getPerks() {
        return perks;
    }
    
    public static TPAutils getTpa() {
        return tpa;
    }
    
    public static ymlUtils getYml() {
        return yml;
    }
    
    public static ChatColor stingToChatColor(String str) {
    	str = str.toUpperCase();
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
    
    public static List<Player> stringsToPlayers(List<String> playerNames) {
    	if (playerNames.isEmpty())
    		return null;
    	
    	List<Player> players = new ArrayList<Player>();
		for (String player : playerNames) {
			Player out = getPlugin().getServer().getPlayer(player);
			if (out != null)
				players.add(out);
		}
		return players;
    }
    
    public static ItemStack getHead(Player player) {
    	ItemStack skull = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta sm = (SkullMeta) skull.getItemMeta();
        OfflinePlayer p = plugin.getServer().getOfflinePlayer(player.getUniqueId());
        sm.setOwningPlayer(p);
        skull.setItemMeta(sm);
        return skull;
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
    
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!channel.equalsIgnoreCase("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(bytes);
        String subChannel = in.readUTF();
        if (subChannel.equalsIgnoreCase("EasyCMD")) {
            String cmd = in.readUTF();
            String data = in.readUTF();
            runBungeeCmd(player, cmd, data);
        }
    }
    
    private void runBungeeCmd(Player player, String cmd, String data) {
    	if (cmd.equalsIgnoreCase("setdisplayname")) {
    		String name = data.split(";")[0];
    		Player p = this.getServer().getPlayer(data.split(";")[1]);
    		p.setDisplayName(name);
    		p.setCustomName(name);
    		p.setCustomNameVisible(true);
    	}
    }
}
   
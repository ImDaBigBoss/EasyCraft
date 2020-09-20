package com.github.imdabigboss.easycraft.commands;

import java.util.Calendar;
import java.util.Date;

import org.bukkit.BanList.Type;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.plugin.Plugin;

import com.github.imdabigboss.easycraft.*;

public class CommandTempBan implements CommandExecutor {
	private final Plugin plugin = easyCraft.getPlugin();
	private final ymlUtils yml = easyCraft.getYml();
	
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (args.length == 1) {
    		if (args[0].equalsIgnoreCase("list")) {
    			String bans = "Here are the bans:";
    			if (yml.getConfig("bans.yml").getInt("bans") == 0) {
    				bans = "Nobody has been banned!";
    			} else {
    				for (int i = 1; i <= yml.getConfig("bans.yml").getInt("bans"); i++) {
    					bans = bans + "\n - " + yml.getConfig("bans.yml").getString("ban" + i) + "";
    				}
    			}
    			sender.sendMessage(bans);
    			return true;
    		}
    	}
    	
    	if (args.length < 3) {
    		sendHelp(sender);
    		return true;
    	}
    	
    	String reason = "None mentioned";
    	if (args.length >= 4) {
    		reason = "";
    		for (int i = 3; i <= args.length-1; i++) {
    			reason = reason + args[i] + " ";
    		}
    	}
    	
    	try {
    		Long.parseLong(args[2]);
    	} catch (Exception e) {
    		sendHelp(sender);
    		return true;
    	}
    	
    	Date date = new Date();
    	
    	if (args[1].equalsIgnoreCase("hours")) {
    		date = addHours(date, Integer.parseInt(args[2]));
    	} else if (args[1].equalsIgnoreCase("days")) {
    		date = addDays(date, Integer.parseInt(args[2]));
    	} else {
    		sendHelp(sender);
    		return true;
    	}
    	
    	plugin.getServer().getBanList(Type.NAME).addBan(args[0], reason, date, sender.getName());
    	try {
    		plugin.getServer().getPlayer(args[0]).kickPlayer("You are banned from this server.\nReason: " + reason + "\nYour ban will be removed on " + date.toString() + " " + ChatColor.RED + "UTC!");
    	} catch (Exception e) {
    		
    	}
    	sender.sendMessage("You banned " + args[0] + " for " + args[2] + " " + args[1]);
    	int banNum = yml.getConfig("bans.yml").getInt("bans") + 1;
    	yml.getConfig("bans.yml").set("bans", banNum);
    	yml.getConfig("bans.yml").set("ban" + banNum, args[0] + " for " + args[2] + " " + args[1] + ". Reason: " + reason + ". Date: " + new Date().toString());
    	yml.saveConfig("bans.yml");
    	return true;
    }
    
    public Date addHours(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
    
    public Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }
    
    public void sendHelp(CommandSender sender) {
    	sender.sendMessage("The correct usage is:");
    	sender.sendMessage("/tempban <player> <hours/days> <length> [reason]");
    	sender.sendMessage("/tempban list");
    }
}
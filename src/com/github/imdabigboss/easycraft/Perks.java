package com.github.imdabigboss.easycraft;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class Perks {
	private Plugin plugin = easyCraft.getPlugin();
	
	public int getMoney(String player) {
		if (plugin.getConfig().contains(player + ".money")) {
			return plugin.getConfig().getInt(player + ".money");
		}
		return 0;
	}
	
	public void setMoney(String player, int money) {
		plugin.getConfig().set(player + ".money", money);
		plugin.saveConfig();
	}
	
	public void spendMoney(String player, int money) {
		setMoney(player, getMoney(player) - money);
	}
	
	public String getPerk(CommandSender sender, String[] args) {
		if (args[1].equalsIgnoreCase("shield")) {
			int MONEY = 5;
			
			if (getMoney(sender.getName()) < MONEY)
				return "You do not have enough money to buy that!";
			if (args.length != 3)
				return "Choose a pattern: \"eyeofender\", \"fire\", \"sunset\", \"mouth\"";
			
			String command = "give \"" + sender.getName() + "\" minecraft:shield";
			if (args[2].equalsIgnoreCase("eyeofender")) 
				command = command + "{BlockEntityTag:{Base:10,Patterns:[{Color:15,Pattern:\"bt\"},{Color:15,Pattern:\"tt\"},{Color:15,Pattern:\"gra\"},{Color:9,Pattern:\"flo\"},{Color:5,Pattern:\"mc\"},{Color:15,Pattern:\"cr\"}]}}";
			else if (args[2].equalsIgnoreCase("fire")) 
				command = command + "{BlockEntityTag:{Base:15,Patterns:[{Color:14,Pattern:\"moj\"},{Color:15,Pattern:\"bri\"},{Color:1,Pattern:\"bt\"},{Color:15,Pattern:\"mr\"},{Color:1,Pattern:\"gru\"},{Color:4,Pattern:\"bts\"}]}}";
			else if (args[2].equalsIgnoreCase("sunset")) 
				command = command + "{BlockEntityTag:{Base:1,Patterns:[{Color:8,Pattern:\"moj\"},{Color:4,Pattern:\"gru\"},{Color:14,Pattern:\"gra\"},{Color:15,Pattern:\"bts\"},{Color:4,Pattern:\"mc\"},{Color:15,Pattern:\"bl\"}]}}";
			else if (args[2].equalsIgnoreCase("mouth")) 
				command = command + "{BlockEntityTag:{Base:14,Patterns:[{Color:15,Pattern:\"mc\"},{Color:6,Pattern:\"bt\"},{Color:0,Pattern:\"tts\"},{Color:0,Pattern:\"bts\"},{Color:0,Pattern:\"bts\"},{Color:15,Pattern:\"mc\"}]}}";
			else
				return "Choose one of these patterns: \"eyeofender\", \"fire\", \"sunset\", \"mouth\"";
			spendMoney(sender.getName(), MONEY);
			plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
			return "";
		} else if (args[1].equalsIgnoreCase("magicstick")) {
			int MONEY = 10;
			
			if (getMoney(sender.getName()) < MONEY)
				return "You do not have enough money to buy that!";
			
			String command = "give \"" + sender.getName() + "\" stick{display:{Name:'[{\"text\":\"Magic Stick\",\"italic\":false}]'},Enchantments:[{id:\"minecraft:knockback\",lvl:10},{id:\"minecraft:fire_aspect\",lvl:1}]}";
			spendMoney(sender.getName(), MONEY);
			plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
			return "";
		} else if (args[1].equalsIgnoreCase("superdog")) {
			int MONEY = 20;
			
			if (getMoney(sender.getName()) < MONEY)
				return "You do not have enough money to buy that!";
			if (args.length != 3)
				return "You must enter a name for your pet!";
			
			String command = "give \"" + sender.getName() + "\" wolf_spawn_egg{EntityTag:{id:\"minecraft:wolf\",CustomName:\"\\\"" + args[2] + "\\\"\",CustomNameVisible:1,Invulnerable:1,Age:0,Owner:\"" + sender.getName() + "\"}}";
			spendMoney(sender.getName(), MONEY);
			plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
			return "";
		} else {
			return "That perk does not exist!";
		}
	}
	
}

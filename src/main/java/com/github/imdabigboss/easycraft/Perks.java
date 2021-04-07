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
		if (args[1].equalsIgnoreCase("magicstick")) {
			int MONEY = 5;
			
			if (getMoney(sender.getName()) < MONEY)
				return "You do not have enough money to buy that!";
			
			String command = "give \"" + sender.getName() + "\" stick{display:{Name:'[{\"text\":\"Magic Stick\",\"italic\":false}]'},Enchantments:[{id:\"minecraft:knockback\",lvl:10},{id:\"minecraft:fire_aspect\",lvl:1}]}";
			spendMoney(sender.getName(), MONEY);
			plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
			return "";
		} else if (args[1].equalsIgnoreCase("enderchest")) {
			int MONEY = 10;
			
			if (getMoney(sender.getName()) < MONEY)
				return "You do not have enough money to buy that!";
			
			plugin.getConfig().set(sender.getName() + ".enderchest", true);
			spendMoney(sender.getName(), MONEY);
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
		} else if (args[1].equalsIgnoreCase("supercat")) {
			int MONEY = 20;
			
			if (getMoney(sender.getName()) < MONEY)
				return "You do not have enough money to buy that!";
			if (args.length != 3)
				return "You must enter a name for your pet!";
			
			String command = "give \"" + sender.getName() + "\" cat_spawn_egg{EntityTag:{id:\"minecraft:wolf\",CustomName:\"\\\"" + args[2] + "\\\"\",CustomNameVisible:1,Invulnerable:1,Age:0,Owner:\"" + sender.getName() + "\"}}";
			spendMoney(sender.getName(), MONEY);
			plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), command);
			return "";
		} else {
			return "That perk does not exist!";
		}
	}
	
}

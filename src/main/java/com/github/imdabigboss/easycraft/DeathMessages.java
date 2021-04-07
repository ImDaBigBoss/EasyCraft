package com.github.imdabigboss.easycraft;

import java.util.Random;

public class DeathMessages {
	private static String[] playerMessages = { "%player% was slaughtered by %killer%", "%killer% skilfully killed %player%", "%player% died to the hand of %killer%" };
	private static String[] arrowMessages = { "%player% was impaled by %killer%", "%killer% shot %player% to death" };
	private static String[] creeperMessages = { "Hello mine turtle! HELLO %player%! BOOM!" };
	private static String[] zombieMessages = { "%player% decided to join the realm of the zombies", "%player% got eaten alive by a zombie" };
	private static String[] voidMessages = { "%player% wanted to know what the bottom of the world looks like", "%player% lost their balance and fell into the void" };
	private static String[] voidByPlayerMessages = { "%killer% ruthlessly pushed %player% into the void" };
	private static String[] lavaMessages = { "%player% wanted to know whay lava felt like", "No, %player% lava isn't baked beans!" };
	private static String[] drownMessages = { "%player% ran out of breath whilst swiming" };
	private static String[] fallMessages = { "%player%'s body got crushed whilst falling", "%player% jumped from a little too high" };
	private static String[] fireMessages = { "%player% was burned alive", "%player% burned to death", "%player% was torched and sadly died" };
	private static String[] poisonMessages = { "%player% was sadly poisoned", "Some evil person decided to poison %player% :'(" };
	private static String[] starvationMessages = { "%player% was too poor to buy food and died", "%player% starved to  death", "%player% didn't eat enogh" };
	
	public static String getPlayerKill(String player, String killer) {
		return replaceVars(random(playerMessages), player, killer);
	}
	
	public static String getKillArrow(String player, String killer) {
		return replaceVars(random(arrowMessages), player, killer);
	}
	
	public static String getKillVoidByPlayer(String player, String killer) {
		return replaceVars(random(voidByPlayerMessages), player, killer);
	}
	
	public static String getKillCreeper(String player) {
		return replaceVars(random(creeperMessages), player);
	}
	
	public static String getKillZombie(String player) {
		return replaceVars(random(zombieMessages), player);
	}
	
	public static String getKillVoid(String player) {
		return replaceVars(random(voidMessages), player);
	}
	
	public static String getKillLava(String player) {
		return replaceVars(random(drownMessages), player);
	}
	
	public static String getKillDrown(String player) {
		return replaceVars(random(lavaMessages), player);
	}
	
	public static String getKillFall(String player) {
		return replaceVars(random(fallMessages), player);
	}
	
	public static String getKillFire(String player) {
		return replaceVars(random(fireMessages), player);
	}
	
	public static String getKillPoison(String player) {
		return replaceVars(random(poisonMessages), player);
	}
	
	public static String getKillStarvation(String player) {
		return replaceVars(random(starvationMessages), player);
	}
	
	
	
	private static String replaceVars(String message, String player, String killer) {
		message = replaceVars(message, player);
		if (message.contains("%killer%"))
			message = message.replace("%killer%", killer);
			
		return message;
	}
	
	private static String replaceVars(String message, String player) {
		if (message.contains("%player%"))
			message = message.replace("%player%", player);
			
		return message;
	}
	
	private static String random(String[] array) {
		int rnd = new Random().nextInt(array.length);
	    return array[rnd];
	}
}

package com.github.imdabigboss.easycraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.*;

public class CommandHeadItem implements CommandExecutor, TabExecutor {
    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    	if (sender instanceof Player) { //Get if executing origin is a player
    	} else {
    		sender.sendMessage(ChatColor.RED + "You must be a player to run this command!");
    		return true;
    	}
    	
    	Player player = (Player) sender;
    	PlayerInventory pinv = player.getInventory();
    	
    	ItemStack handitem = pinv.getItemInMainHand();
    	ItemStack headitem = pinv.getHelmet();
    	
    	pinv.setHelmet(handitem);
    	pinv.setItemInMainHand(headitem);
    	return true;
    }
    
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}
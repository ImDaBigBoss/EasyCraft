package com.github.imdabigboss.easycraft;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.*;
import org.bukkit.plugin.Plugin;

public class ymlUtils {
	private Plugin plugin = easyCraft.getPlugin();
	
	private Map<String, File> customConfigFile = new HashMap<String, File>();
    private Map<String, FileConfiguration> customConfig = new HashMap<String, FileConfiguration>();
	
	public FileConfiguration getConfig(String name) {
        return customConfig.get(name);
    }
    
    public void createConfig(String name) {
        customConfigFile.put(name, new File(plugin.getDataFolder(), name));
        
        if (!customConfigFile.get(name).exists()) {
           customConfigFile.get(name).getParentFile().mkdirs();
           plugin.saveResource(name, false);
        }

        customConfig.put(name, new YamlConfiguration());
        
        try {
            customConfig.get(name).load(customConfigFile.get(name));
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}

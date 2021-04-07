package com.github.imdabigboss.easycraft;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.Plugin;

public class MyListener implements Listener {
    private Plugin plugin = easyCraft.getPlugin();

    private Map < Player, Entity > lastDamager = new HashMap < > ();


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) //when a player joins
    {
        Player player = event.getPlayer();

        if (plugin.getConfig().get("Maintenance").equals("on")) {
            if (!player.hasPermission("easycraft.maintenance.bypass"))
                player.kickPlayer("We very sorry " + ChatColor.YELLOW + player.getName() + ChatColor.RESET + " but the server has been put under " + ChatColor.RED + "maintenance mode");
        }

        player.sendMessage(ChatColor.GREEN + "Welcome to the server " + ChatColor.RED + event.getPlayer().getName() + ChatColor.GREEN + "!");
        if (plugin.getConfig().contains("joinText")) {
            player.sendMessage(ChatColor.GREEN + (String) plugin.getConfig().get("joinText"));
        }

        if (plugin.getServer().getOnlinePlayers().size() >= plugin.getServer().getMaxPlayers()) {
            plugin.getServer().broadcastMessage("There is now " + ChatColor.RED + plugin.getServer().getOnlinePlayers().size() + ChatColor.RESET + " players connected, maybe a few players could leave to free up space for other players wanting to join!");
        }

        easyCraft.connectionLog(player.getName(), 1);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) //when a player quits
    {
        Player player = event.getPlayer();
        easyCraft.connectionLog(player.getName(), 2);
    }

    @EventHandler
    public void onEntityDamageByBlockEvent(EntityDamageByBlockEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                if (e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent cause = (EntityDamageByEntityEvent) e.getEntity().getLastDamageCause();
                    lastDamager.put((Player) e.getEntity(), cause.getDamager());
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Entity killer = player.getKiller();
        
        EntityDamageEvent lastDamageEvent = player.getLastDamageCause();
        if (lastDamageEvent instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent damageEvent = (EntityDamageByEntityEvent) lastDamageEvent;

            if (damageEvent.getDamager() instanceof org.bukkit.entity.Creeper) {
                event.setDeathMessage(DeathMessages.getKillCreeper(player.getDisplayName()));
                
            } else if (damageEvent.getDamager() instanceof org.bukkit.entity.Arrow) {
                org.bukkit.entity.Arrow arrow = (org.bukkit.entity.Arrow) damageEvent.getDamager();
                if (arrow.getShooter() instanceof LivingEntity) {
                    LivingEntity livingEntity = (LivingEntity) arrow.getShooter();

                    if (livingEntity.getType() == EntityType.PLAYER) {
                        Player killerp = (Player) livingEntity;
                        event.setDeathMessage(DeathMessages.getKillArrow(player.getDisplayName(), killerp.getDisplayName()));
                    } else {
                        event.setDeathMessage(DeathMessages.getKillArrow(player.getDisplayName(), livingEntity.getType().toString().toLowerCase()));
                    }
                }
                
            } else if (damageEvent.getDamager() instanceof Player) {
                Player killerp = (Player) damageEvent.getDamager();
                event.setDeathMessage(DeathMessages.getPlayerKill(player.getDisplayName(), killerp.getDisplayName()));
                player.getWorld().dropItem(player.getLocation(), easyCraft.getHead(player));
                
            } else if (damageEvent.getDamager() instanceof org.bukkit.entity.Zombie) {
                event.setDeathMessage(DeathMessages.getKillZombie(player.getDisplayName()));
                
            }
        } else if (lastDamageEvent instanceof EntityDamageByBlockEvent) {
            EntityDamageByBlockEvent cause = (EntityDamageByBlockEvent) event.getEntity().getLastDamageCause();
            if (cause.getCause() == EntityDamageEvent.DamageCause.VOID) {
                Entity entity = lastDamager.get(player);
                if (entity != null) {
                    if (entity instanceof Player)
                        event.setDeathMessage(DeathMessages.getKillVoidByPlayer(player.getDisplayName(), ((Player) entity).getDisplayName()));
                    else
                        event.setDeathMessage(DeathMessages.getKillVoid(player.getDisplayName()));
                } else {
                    event.setDeathMessage(DeathMessages.getKillVoid(player.getDisplayName()));
                }
            } else if (cause.getCause() == EntityDamageEvent.DamageCause.LAVA) {
            	event.setDeathMessage(DeathMessages.getKillLava(player.getDisplayName()));
            } else if (cause.getCause() == EntityDamageEvent.DamageCause.DROWNING) {
            	event.setDeathMessage(DeathMessages.getKillDrown(player.getDisplayName()));
            } else if (cause.getCause() == EntityDamageEvent.DamageCause.FALL) {
            	event.setDeathMessage(DeathMessages.getKillFall(player.getDisplayName()));
            } else if (cause.getCause() == EntityDamageEvent.DamageCause.FIRE) {
            	event.setDeathMessage(DeathMessages.getKillFire(player.getDisplayName()));
            } else if (cause.getCause() == EntityDamageEvent.DamageCause.POISON) {
            	event.setDeathMessage(DeathMessages.getKillPoison(player.getDisplayName()));
            } else if (cause.getCause() == EntityDamageEvent.DamageCause.STARVATION) {
            	event.setDeathMessage(DeathMessages.getKillStarvation(player.getDisplayName()));
            }
        }
    }

    @EventHandler
    public void chatFormat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();

        event.setFormat(p.getDisplayName() + ChatColor.RESET + ": " + event.getMessage());
    }

    @EventHandler
    public void onPlayerCommandMonitor(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        String arguments = "";
        String command = "";
        if (!message.contains(" ")) {
            command = message.replace("/", "");
        } else {
            command = message.substring(0, message.indexOf(" ")).replace("/", "");
            arguments = message.substring(message.indexOf(" ") + 1, message.length());
        }

        if (command.equalsIgnoreCase("tpa") || command.equalsIgnoreCase("tpahere") || command.equalsIgnoreCase("tpaccept") || command.equalsIgnoreCase("tpdeny") || command.equalsIgnoreCase("home") || command.equalsIgnoreCase("sethome") || command.equalsIgnoreCase("spawn") || command.equalsIgnoreCase("msg") || command.equalsIgnoreCase("suicide") || command.equalsIgnoreCase("chatroom") || command.equalsIgnoreCase("enderchest") || command.equalsIgnoreCase("shout") || command.equalsIgnoreCase("headitem") || command.equalsIgnoreCase("info") || command.equalsIgnoreCase("perks")) {

        } else {
            String out = "------ COMMAND: " + event.getPlayer().getName() + " ran: " + message + " ------";
            plugin.getServer().getConsoleSender().sendMessage(out);
            easyCraft.commandLog(event.getPlayer().getName(), message);
        }
    }
}
package com.andrei1058.ageofempire.runnables;

import com.andrei1058.ageofempire.Main;
import com.andrei1058.ageofempire.configuration.Settings;
import com.andrei1058.ageofempire.game.Scoreboard;
import com.andrei1058.ageofempire.game.Status;
import com.andrei1058.ageofempire.game.Hologram;
import com.andrei1058.ageofempire.locations.Locations;
import com.andrei1058.ageofempire.nms.v1_8_R3.VillagerNMS;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import static com.andrei1058.ageofempire.Main.*;
import static com.andrei1058.ageofempire.Misc.forumPaper;
import static com.andrei1058.ageofempire.Misc.leatherArmor;
import static com.andrei1058.ageofempire.Misc.slotlocked;
import static com.andrei1058.ageofempire.configuration.Messages.getMsg;

public class PreGame extends BukkitRunnable {
    @Override
    public void run() {
        if (pregame_time != 0){
            pregame_time--;
        }
        if (Bukkit.getOnlinePlayers().size() < 2){
            this.cancel();
            new Restart().runTaskTimer(plugin, 0, 20);
            STATUS = Status.RESTARTING;
        }
        if (pregame_time == 2) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (bluePlayers.contains(p) || yellowPlayers.contains(p) || redPlayers.contains(p) || greenPlayers.contains(p)){
                    continue;
                }
                if (Bukkit.getOnlinePlayers().size() > max_in_team * 3) {
                    if (bluePlayers.isEmpty()){
                        bluePlayers.add(p);
                    } else if (greenPlayers.isEmpty()){
                        greenPlayers.add(p);
                    } else if (yellowPlayers.isEmpty()){
                        yellowPlayers.add(p);
                    } else if (redPlayers.isEmpty()){
                        redPlayers.add(p);
                    } else if (bluePlayers.size() <= max_in_team && bluePlayers.size() <= greenPlayers.size() && bluePlayers.size() <= yellowPlayers.size() && bluePlayers.size() <= redPlayers.size()) {
                        bluePlayers.add(p);
                    } else if (greenPlayers.size() <= max_in_team && greenPlayers.size() < bluePlayers.size() && greenPlayers.size() < yellowPlayers.size() && greenPlayers.size() < redPlayers.size()) {
                        greenPlayers.add(p);
                    } else if (yellowPlayers.size() <= max_in_team && yellowPlayers.size() <= bluePlayers.size() && yellowPlayers.size() <= greenPlayers.size() && yellowPlayers.size() <= redPlayers.size()) {
                        yellowPlayers.add(p);
                    } else if (redPlayers.size() <= max_in_team && redPlayers.size() <= bluePlayers.size() && redPlayers.size() <= greenPlayers.size() && redPlayers.size() <= yellowPlayers.size()) {
                        redPlayers.add(p);
                    } else {
                        p.kickPlayer("Teams are full");
                    }
                } else if (Bukkit.getOnlinePlayers().size() > max_in_team * 2) {
                    if (bluePlayers.isEmpty()){
                        bluePlayers.add(p);
                    } else if (greenPlayers.isEmpty()){
                        greenPlayers.add(p);
                    } else if (yellowPlayers.isEmpty()){
                        yellowPlayers.add(p);
                    } else if (bluePlayers.size() <= max_in_team && bluePlayers.size() <= greenPlayers.size() && bluePlayers.size() <= yellowPlayers.size()) {
                        bluePlayers.add(p);
                    } else if (greenPlayers.size() <= max_in_team && greenPlayers.size() <= bluePlayers.size() && greenPlayers.size() <= yellowPlayers.size()) {
                        greenPlayers.add(p);
                    } else if (yellowPlayers.size() <= max_in_team && yellowPlayers.size() <= bluePlayers.size() && yellowPlayers.size() <= greenPlayers.size()) {
                        yellowPlayers.add(p);
                    } else {
                        p.kickPlayer("Teams are full");
                    }
                } else {
                    if (bluePlayers.isEmpty()){
                        bluePlayers.add(p);
                    } else if (greenPlayers.isEmpty()){
                        greenPlayers.add(p);
                    } else if (bluePlayers.size() <= max_in_team && bluePlayers.size() <= greenPlayers.size()) {
                        bluePlayers.add(p);
                    } else if (greenPlayers.size() <= max_in_team && greenPlayers.size() <= bluePlayers.size()) {
                        greenPlayers.add(p);
                    } else {
                        p.kickPlayer("Teams are full");
                    }

                }

            }
        }
        if (pregame_time > 0){
            for (Player p : Bukkit.getOnlinePlayers()){
                nms.actionMsg(p, getMsg("game-start").replace("{time}", String.valueOf(pregame_time)));
                p.getWorld().playSound(p.getLocation(), nms.click(), 1, 1);
            }
        }
        if (pregame_time == 0){
            cancel();
            STATUS = Status.PLAYING;
            for (Player p : Bukkit.getOnlinePlayers()) {
                players.add(p);
                p.getInventory().clear();
                p.setHealth(20);
                p.setFoodLevel(20);
                p.setGameMode(GameMode.SURVIVAL);
                if (bluePlayers.contains(p)){
                    p.teleport(Locations.getLoc("Spawns."+choosenMap+".Blue"));
                    p.getInventory().setHelmet(leatherArmor(Material.LEATHER_HELMET, Color.BLUE));
                    p.getInventory().setChestplate(leatherArmor(Material.LEATHER_CHESTPLATE, Color.BLUE));
                    p.getInventory().setBoots(leatherArmor(Material.LEATHER_BOOTS, Color.BLUE));
                    p.getInventory().setLeggings(leatherArmor(Material.LEATHER_LEGGINGS, Color.BLUE));
                    p.setDisplayName("§9"+p.getName());
                } else if (greenPlayers.contains(p)){
                    p.teleport(Locations.getLoc("Spawns."+choosenMap+".Green"));
                    p.getInventory().setHelmet(leatherArmor(Material.LEATHER_HELMET, Color.GREEN));
                    p.getInventory().setChestplate(leatherArmor(Material.LEATHER_CHESTPLATE, Color.GREEN));
                    p.getInventory().setBoots(leatherArmor(Material.LEATHER_BOOTS, Color.GREEN));
                    p.getInventory().setLeggings(leatherArmor(Material.LEATHER_LEGGINGS, Color.GREEN));
                    p.setDisplayName("§a"+p.getDisplayName());
                } else if (yellowPlayers.contains(p)){
                    p.teleport(Locations.getLoc("Spawns."+choosenMap+".Yellow"));
                    p.getInventory().setHelmet(leatherArmor(Material.LEATHER_HELMET, Color.YELLOW));
                    p.getInventory().setChestplate(leatherArmor(Material.LEATHER_CHESTPLATE, Color.YELLOW));
                    p.getInventory().setBoots(leatherArmor(Material.LEATHER_BOOTS, Color.YELLOW));
                    p.getInventory().setLeggings(leatherArmor(Material.LEATHER_LEGGINGS, Color.YELLOW));
                    p.setDisplayName("§e"+p.getName());
                } else if (redPlayers.contains(p)){
                    p.teleport(Locations.getLoc("Spawns."+choosenMap+".Red"));
                    p.getInventory().setHelmet(leatherArmor(Material.LEATHER_HELMET, Color.RED));
                    p.getInventory().setChestplate(leatherArmor(Material.LEATHER_CHESTPLATE, Color.RED));
                    p.getInventory().setBoots(leatherArmor(Material.LEATHER_BOOTS, Color.RED));
                    p.getInventory().setLeggings(leatherArmor(Material.LEATHER_LEGGINGS, Color.RED));
                    p.setDisplayName("§c"+p.getName());
                }
                ItemStack pick = new ItemStack(Material.STONE_PICKAXE);
                ItemMeta pickMeta = pick.getItemMeta();
                pickMeta.spigot().setUnbreakable(true);
                pick.setItemMeta(pickMeta);

                ItemStack axe = new ItemStack(Material.STONE_AXE);
                ItemMeta axeMeta = axe.getItemMeta();
                axeMeta.spigot().setUnbreakable(true);
                axe.setItemMeta(axeMeta);

                p.getInventory().addItem(pick);
                p.getInventory().addItem(axe);
                p.getInventory().setItem(8, slotlocked());
                p.getInventory().setItem(7, slotlocked());
                p.getInventory().setItem(6, forumPaper());
                gold.put(p, 100);
            }
            Scoreboard.register();
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                try {
                    if (!bluePlayers.isEmpty()){
                        blue_villager = nms.spawnVillager(Locations.getLoc("Forums."+choosenMap+".Blue"), forum_health);
                        new Hologram(Locations.getLoc("Forums."+choosenMap+".Blue").clone(),
                                getMsg("villagers.forum"), getMsg("villagers.buy-buildings"), blue_villager);
                    }
                    if (!greenPlayers.isEmpty()){
                        green_villager = nms.spawnVillager(Locations.getLoc("Forums."+choosenMap+".Green"), forum_health);
                        new Hologram(Locations.getLoc("Forums."+choosenMap+".Green").clone(),
                                getMsg("villagers.forum"), getMsg("villagers.buy-buildings"), green_villager);
                    }
                    if (!yellowPlayers.isEmpty()){
                        yellow_villager = nms.spawnVillager(Locations.getLoc("Forums."+choosenMap+".Yellow"), forum_health);
                        new Hologram(Locations.getLoc("Forums."+choosenMap+".Yellow").clone(),
                                getMsg("villagers.forum"), getMsg("villagers.buy-buildings"), yellow_villager);
                    }
                    if (!redPlayers.isEmpty()){
                        red_villager = nms.spawnVillager(Locations.getLoc("Forums."+choosenMap+".Red"), forum_health);
                        new Hologram(Locations.getLoc("Forums."+choosenMap+".Red").clone(),
                                getMsg("villagers.forum"), getMsg("villagers.buy-buildings"), red_villager);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }, 30L);
            Main.pvp_assault = 60000*Settings.load().getInt("countdowns.pvp");
            new Game().runTaskTimer(plugin, 0, 20);
        }
    }
}

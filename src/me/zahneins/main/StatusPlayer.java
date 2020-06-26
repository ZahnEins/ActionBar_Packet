package me.zahneins.main;

import net.minecraft.server.v1_16_R1.ChatMessageType;
import net.minecraft.server.v1_16_R1.IChatBaseComponent;
import net.minecraft.server.v1_16_R1.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.DecimalFormat;
import java.util.Objects;

public class StatusPlayer extends JavaPlugin {
    public String ValueH ;
    public String ValueD ;
    public String Health ;
    public String Defense ;
    @Override
    public void onEnable() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "Plugin : Enable");
        new ActionBar().runTaskTimerAsynchronously(this,0,25);

        if(getConfig() == null){
            getConfig().getDefaults();
            getConfig().set("ValueH","&c");
            getConfig().set("Health","&c● พลังชีวิต");
            getConfig().set("ValueD","&a");
            getConfig().set("Defense","&a❈ พลังป้องกัน");
            saveConfig();
        }
        this.ValueH = Objects.requireNonNull(getConfig().get("ValueH")).toString();
        this.ValueD = Objects.requireNonNull(getConfig().get("ValueD")).toString();
        this.Health = Objects.requireNonNull(getConfig().get("Health")).toString();
        this.Defense = Objects.requireNonNull(getConfig().get("Defense")).toString();


    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "Plugin : Disable");
    }

    public class ActionBar extends BukkitRunnable {
        @Override
        public void run() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player != null) {
                    int armorpoint = 0;
                    if(player.getInventory().getHelmet() != null){
                        switch (player.getInventory().getHelmet().getType()){
                            case LEATHER_HELMET:
                                armorpoint += 1;
                                break;
                            case CHAINMAIL_HELMET:
                            case IRON_HELMET:
                            case GOLDEN_HELMET:
                                armorpoint += 2;
                                break;
                            case DIAMOND_HELMET:
                            case NETHERITE_HELMET:
                                armorpoint += 3;
                                break;
                        }

                    }
                    if(player.getInventory().getChestplate() != null) {
                        switch (player.getInventory().getChestplate().getType()){
                            case LEATHER_CHESTPLATE:
                                armorpoint += 3;
                                break;
                            case CHAINMAIL_CHESTPLATE:
                            case GOLDEN_CHESTPLATE:
                                armorpoint += 5;
                                break;
                            case IRON_CHESTPLATE:
                                armorpoint += 6;
                                break;
                            case DIAMOND_CHESTPLATE:
                            case NETHERITE_CHESTPLATE:
                                armorpoint += 8;
                                break;
                        }

                    }
                    if(player.getInventory().getLeggings() != null) {
                        switch (player.getInventory().getLeggings().getType()){
                            case LEATHER_LEGGINGS:
                                armorpoint += 2;
                                break;
                            case CHAINMAIL_LEGGINGS:
                                armorpoint += 4;
                                break;
                            case IRON_LEGGINGS:
                                armorpoint += 5;
                                break;
                            case GOLDEN_LEGGINGS:
                                armorpoint += 3;
                                break;
                            case DIAMOND_LEGGINGS:
                            case NETHERITE_LEGGINGS:
                                armorpoint += 6;
                                break;
                        }

                    }
                    if(player.getInventory().getBoots() != null) {
                        switch (player.getInventory().getBoots().getType()){
                            case LEATHER_BOOTS:
                            case GOLDEN_BOOTS:
                            case CHAINMAIL_BOOTS:
                                armorpoint += 1;
                                break;
                            case IRON_BOOTS:
                                armorpoint += 2;
                                break;
                            case DIAMOND_BOOTS:
                            case NETHERITE_BOOTS:
                                armorpoint += 3;
                                break;

                        }
                    }

                    IChatBaseComponent infoP = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&',ValueH) + ""
                            + new DecimalFormat("#").format(player.getHealth()) + "/" + new DecimalFormat("#").format(player.getMaxHealth())
                            + ChatColor.translateAlternateColorCodes('&',Health) + ChatColor.WHITE + " | " + ChatColor.translateAlternateColorCodes('&',ValueD) + armorpoint + ChatColor.translateAlternateColorCodes('&',Defense) + "\"}");

                    PacketPlayOutChat bar = new PacketPlayOutChat(infoP, ChatMessageType.GAME_INFO,player.getUniqueId());

                    ((CraftPlayer) player).getHandle().playerConnection.sendPacket(bar);


                }
            }

        }
    }

}

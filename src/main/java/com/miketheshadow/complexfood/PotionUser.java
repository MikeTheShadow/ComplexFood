package com.miketheshadow.complexfood;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class PotionUser extends BukkitRunnable {

    public String UID;
    public String name;
    public int timeLeft;

    public Thread thread;

    public PotionUser(UUID UID,String name) {
        this.UID = UID.toString();
        this.name = name;
        this.timeLeft = 60 * 30 * 1000;
        //this.timeLeft = 60 * 1000;
        start();
    }

    @Override
    public void run() {
        try {
            Thread.sleep(timeLeft);
        } catch (InterruptedException ignored){}
        Player player = Bukkit.getPlayer(name);
        if(player != null) {
            player.setFoodLevel(17);
            player.sendMessage(ChatColor.YELLOW + "You feel hungry...");
        }
        OnPlayerConsumeEvent.potionUsers.remove(this);
    }

    public void start() {

        if (this.thread == null) {
            this.thread = new Thread(this, UID.toString());
            this.thread.start();
        }
    }
}

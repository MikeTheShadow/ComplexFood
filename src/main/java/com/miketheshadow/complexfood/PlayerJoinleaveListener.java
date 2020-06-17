package com.miketheshadow.complexfood;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

public class PlayerJoinleaveListener implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        for(PotionUser user : OnPlayerConsumeEvent.potionUsers) {
            if(user.UID.equals(event.getPlayer().getUniqueId().toString())) {
                return;
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        for(PotionUser user : OnPlayerConsumeEvent.potionUsers) {
            if(user.UID.equals(player.getUniqueId().toString())) {
                return;
            }
        }
        for(PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
        player.setFoodLevel(17);
    }

}

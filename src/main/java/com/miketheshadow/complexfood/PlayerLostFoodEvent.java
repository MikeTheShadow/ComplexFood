package com.miketheshadow.complexfood;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerLostFoodEvent implements Listener {

    @EventHandler
    public void playerHungerEvent(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }
}

package com.miketheshadow.complexfood;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class OnPlayerConsumeEvent implements Listener {

    public static List<PotionUser> potionUsers = new ArrayList<>();

    @EventHandler
    public void playerConsumeEvent(PlayerItemConsumeEvent event) {
        for (PotionUser potionUser : potionUsers){
            if(potionUser.UID.equals(event.getPlayer().getUniqueId().toString())) {
                event.getPlayer().sendMessage(ChatColor.RED + "You have recently eaten and cannot eat again!");
                return;
            }
        }
        event.getPlayer().setFoodLevel(20);
        ItemMeta item = event.getItem().getItemMeta();
        PotionUser user = new PotionUser(event.getPlayer().getUniqueId(),event.getPlayer().getDisplayName());
        potionUsers.add(user);
        event.getPlayer().sendMessage(ChatColor.YELLOW + "You feel satisfied.");
        if(item == null) return;
        if(item.getLore() == null)return;
        if(item.getLore().size() < 1)return;
        List<String> lore = item.getLore();
        for(String loreItem : lore) {
            String potionItem = fixPotionName(loreItem.split(" ")[0].replace("-",""));
            PotionEffectType type = PotionEffectType.getByName(potionItem);
            if(type == null) {
                event.getPlayer().sendMessage("Effect doesn't exist: " + potionItem);
            } else {
                int duration = Integer.parseInt(loreItem.replaceAll("[^\\d.]", ""));
                duration = duration * 60 * 20;
                int amplifier = getAmplifier(loreItem);
                event.getPlayer().addPotionEffect(new PotionEffect(type,duration,amplifier,true,false));
                Bukkit.broadcastMessage(type.getName());
            }
        }

    }

    //TODO make this pretty
    public int getAmplifier(String parseString) {
        if(parseString.contains(" II")) {
            return 1;
        } else if(parseString.contains(" III")) {
            return 2;
        } else if(parseString.contains(" IV")) {
            return 3;
        } else if(parseString.contains(" V")) {
            return 4;
        }
        return 0;
    }

    public String fixPotionName(String name) {
        if(name.equalsIgnoreCase("strength")) {
            return "increase_damage";
        }
        else if(name.equalsIgnoreCase("resistance")) {
            return "damage_resistance";
        }
        return name;
    }
}

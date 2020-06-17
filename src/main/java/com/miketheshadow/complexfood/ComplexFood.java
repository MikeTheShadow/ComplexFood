package com.miketheshadow.complexfood;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public final class ComplexFood extends JavaPlugin
{

    @Override
    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Loaded ComplexFood!");
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new OnPlayerConsumeEvent(),this);
        manager.registerEvents(new PlayerJoinleaveListener(),this);
        manager.registerEvents(new PlayerLostFoodEvent(), this);
        Bukkit.getPluginCommand("foodexample").setExecutor(this::onCommand);
        Bukkit.getPluginCommand("potionlist").setExecutor(this::onCommand);
        Bukkit.getPluginCommand("foodreset").setExecutor(this::onCommand);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(command.getName().equalsIgnoreCase("foodexample")) {
            if(!(sender instanceof Player)) return false;
            Player player = (Player)sender;
            ItemStack itemStack = new ItemStack(Material.COOKED_BEEF);
            ItemMeta itemMeta = itemStack.getItemMeta();
            List<String> lore = new ArrayList();
            lore.add("-strength 5m");
            lore.add("-speed II 3m");
            itemMeta.setLore(lore);
            itemStack.setItemMeta(itemMeta);
            player.getInventory().addItem(itemStack);
            return true;
        } else if(command.getName().equalsIgnoreCase("potionlist")) {
            if(!(sender instanceof Player)) return false;
            Player player = (Player)sender;
            for (PotionEffectType type: PotionEffectType.values()) {
                player.sendMessage(type.getName());
            }
            return true;
        }
        if(command.getName().equalsIgnoreCase("foodreset")) {
            OnPlayerConsumeEvent.potionUsers.removeIf(p -> p.UID.equals(((Player) sender).getUniqueId().toString()));
            ((Player)sender).setFoodLevel(17);
            return true;
        }
        return false;
    }
}

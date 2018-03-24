package com.tjplaysnow.chiselertool.main;

import com.tjplaysnow.chiselertool.api.config.Config;
import com.tjplaysnow.chiselertool.api.config.ItemManager;
import com.tjplaysnow.chiselertool.api.events.Events;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class PluginMain extends JavaPlugin {
    
    Plugin plugin;
    
    Config config;
    boolean configCreated = true;
    
    Events<PlayerInteractEvent> playerInteractEvents;
    
    @Override
    public void onEnable() {
        plugin = this;
        
        config = new Config("plugins/" + getName(), "Config.yml", () -> {
            configCreated = false;
        }, plugin);
        if (!configCreated) {
            List<String> materials = new ArrayList<>();
            for (Material mat : Material.values()) {
                if (mat.toString().contains("STAIR")) {
                    materials.add(mat.toString());
                }
                if (mat.toString().contains("TERRACOTTA")) {
                    materials.add(mat.toString());
                }
                if (mat.toString().contains("HOPPER")) {
                    materials.add(mat.toString());
                }
                if (mat.toString().contains("RAIL")) {
                    materials.add(mat.toString());
                }
                if (mat.toString().contains("PISTON")) {
                    materials.add(mat.toString());
                }
                if (mat.toString().contains("LOG")) {
                    materials.add(mat.toString());
                }
                if (mat.toString().contains("SLAB") || mat.toString().contains("STEP")) {
                    materials.add(mat.toString());
                }
                if (mat.toString().contains("DOOR") && !mat.equals(Material.IRON_DOOR)&& !mat.equals(Material.IRON_DOOR_BLOCK)) {
                    materials.add(mat.toString());
                }
                if (mat.toString().contains("SHULKER_BOX")) {
                    materials.add(mat.toString());
                }
            }
            materials.add(Material.REDSTONE_COMPARATOR.toString());
            materials.add(Material.REDSTONE_COMPARATOR_OFF.toString());
            materials.add(Material.REDSTONE_COMPARATOR_ON.toString());
            materials.add(Material.OBSERVER.toString());
            materials.add(Material.DROPPER.toString());
            materials.add(Material.DISPENSER.toString());
            materials.add(Material.DIODE.toString());
            materials.add(Material.DIODE_BLOCK_OFF.toString());
            materials.add(Material.DIODE_BLOCK_ON.toString());
            materials.add(Material.HAY_BLOCK.toString());
            materials.add(Material.QUARTZ_BLOCK.toString());
            materials.add(Material.BONE_BLOCK.toString());
            materials.add(Material.ANVIL.toString());
            materials.add(Material.SIGN_POST.toString());
            materials.add(Material.ENDER_CHEST.toString());
            materials.add(Material.PURPUR_PILLAR.toString());
            config.getConfig().set("Rotatable", materials);
            config.getConfig().set("Chiseler.ItemData", 2);
            config.getConfig().set("Chiseler.Durability", 1024);
            config.saveConfig();
        }
        
        playerInteractEvents = new Events<>(this, PlayerInteractEvent.class);
        
        ItemStack item = ItemManager.setNameAndLore(ItemManager.setUnbreakable(new ItemStack(Material.WOOD_HOE, 1, (short) 2)), "§fChiseler", "§7Uses: " + config.getConfig().get("Chiseler.Durability") + "/" + config.getConfig().get("Chiseler.Durability"));
    
        ShapedRecipe recipe1 = new ShapedRecipe(new NamespacedKey(plugin, "chisel-1"), item);
        recipe1.shape("   ", "D  ", "S  ");
        recipe1.setIngredient('D', Material.DIAMOND);
        recipe1.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(recipe1);
    
        ShapedRecipe recipe2 = new ShapedRecipe(new NamespacedKey(plugin, "chisel-2"), item);
        recipe2.shape("   ", " D ", " S ");
        recipe2.setIngredient('D', Material.DIAMOND);
        recipe2.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(recipe2);
    
        ShapedRecipe recipe3 = new ShapedRecipe(new NamespacedKey(plugin, "chisel-3"), item);
        recipe3.shape("   ", "  D", "  S");
        recipe3.setIngredient('D', Material.DIAMOND);
        recipe3.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(recipe3);
    
        ShapedRecipe recipe4 = new ShapedRecipe(new NamespacedKey(plugin, "chisel-4"), item);
        recipe4.shape("D  ", "S  ", "   ");
        recipe4.setIngredient('D', Material.DIAMOND);
        recipe4.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(recipe4);
    
        ShapedRecipe recipe5 = new ShapedRecipe(new NamespacedKey(plugin, "chisel-5"), item);
        recipe5.shape(" D ", " S ", "   ");
        recipe5.setIngredient('D', Material.DIAMOND);
        recipe5.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(recipe5);
    
        ShapedRecipe recipe6 = new ShapedRecipe(new NamespacedKey(plugin, "chisel-6"), item);
        recipe6.shape("  D", "  S", "   ");
        recipe6.setIngredient('D', Material.DIAMOND);
        recipe6.setIngredient('S', Material.STICK);
        Bukkit.addRecipe(recipe6);
        
        playerInteractEvents.onEvent((event) -> {
            if (event.getItem() == null) {
                return;
            }
            if (event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                return;
            }
            if (ItemManager.isEqual(event.getItem(), item)) {
                event.setCancelled(true);
                List<String> lore = event.getItem().getItemMeta().getLore();
                String stringDamage = lore.get(0).replace("§7Uses: ", "");
                stringDamage = stringDamage.replace("/" + config.getConfig().get("Chiseler.Durability"), "");
                int damage = Integer.valueOf(stringDamage);
                if (event.getClickedBlock() == null) {
                    return;
                }
                boolean worked = false;
                for (String materialName : config.getConfig().getStringList("Rotatable")) {
                    if (event.getClickedBlock().getType().equals(Material.valueOf(materialName))) {
                        worked = true;
                        break;
                    }
                }
                if (worked) {
                    Block block = event.getClickedBlock();
                    byte blockData = block.getData();
                    
                    if (block.getType().toString().contains("RAIL") && !block.getType().equals(Material.RAILS)) {
                        if (blockData == 1) {
                            blockData = 0;
                        } else {
                            blockData++;
                        }
                    } else if (block.getType().equals(Material.RAILS)) {
                        if (blockData == 1) {
                            blockData = 6;
                        } else
                        if (blockData == 9) {
                            blockData = 0;
                        } else {
                            blockData++;
                        }
                    } else if (block.getType().equals(Material.REDSTONE_COMPARATOR) || block.getType().equals(Material.REDSTONE_COMPARATOR_OFF) || block.getType().equals(Material.REDSTONE_COMPARATOR_ON)) {
                        if (blockData == 7) {
                            blockData = 0;
                        } else {
                            blockData++;
                        }
                    } else if (block.getType().toString().contains("SLAB") || block.getType().toString().contains("STEP")) {
                        if (blockData < 8) {
                            blockData += 8;
                        } else if (blockData >= 8) {
                            blockData -= 8;
                        }
                    } else if (block.getType().equals(Material.ANVIL)) {
                        if (blockData >= 0 && blockData <= 3) {
                            if (blockData == 3) {
                                blockData = 0;
                            } else {
                                blockData++;
                            }
                        } else if (blockData >= 4 && blockData <= 7) {
                            if (blockData == 7) {
                                blockData = 4;
                            } else {
                                blockData++;
                            }
                        } else if (blockData >= 8 && blockData <= 11) {
                            if (blockData == 11) {
                                blockData = 8;
                            } else {
                                blockData++;
                            }
                        }
                    } else if (block.getType().toString().contains("PISTON")) {
                        if (blockData == 5) {
                            blockData = 0;
                        } else {
                            blockData++;
                        }
                    } else if (block.getType().toString().contains("LOG") || block.getType().equals(Material.PURPUR_PILLAR)) {
                        blockData += 4;
                    } else if (block.getType().equals(Material.QUARTZ_BLOCK)) {
                        if (blockData >= 2 && blockData <= 4) {
                            if (blockData == 4) {
                                blockData = 2;
                            } else {
                                blockData++;
                            }
                        }
                    } else if (block.getType().equals(Material.HOPPER)) {
                        if (blockData == 0) {
                            blockData = 2;
                        } else {
                            blockData++;
                        }
                    } else {
                        if (blockData == 15) {
                            blockData = 0;
                        } else {
                            blockData++;
                        }
                    }
                    
                    block.setData(blockData);
                    damage--;
                    if (damage < 0) {
                        event.getPlayer().getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                        event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
                    } else {
                        event.getPlayer().getInventory().setItemInMainHand(ItemManager.setLore(event.getPlayer().getInventory().getItemInMainHand(), "§7Uses: " + damage + "/" + config.getConfig().getInt("Chiseler.Durability")));
                    }
                }
            }
        });
    }
}

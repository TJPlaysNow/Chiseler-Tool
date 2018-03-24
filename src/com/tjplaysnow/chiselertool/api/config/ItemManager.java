package com.tjplaysnow.chiselertool.api.config;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
	
	public static ItemStack getSkull(SkullType type) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) type.ordinal());
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack getSkull(String playerName) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta im = (SkullMeta) item.getItemMeta();
		im.setOwner(playerName);
		item.setItemMeta(im);
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static ItemStack setNameAndLoreandSkull(String name, String playerName, String... lore) {
		ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta im = (SkullMeta) item.getItemMeta();
		im.setDisplayName(name);
		List<String> il = new ArrayList<String>();
		for (String l : lore) {
			il.add(l);
		}
		im.setLore(il);
		im.setOwner(playerName);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack setNameAndLore(ItemStack item, String name, String... lore) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		List<String> il = new ArrayList<String>();
		for (String l : lore) {
			il.add(l);
		}
		im.setLore(il);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack setNameAndLore(ItemStack item, String name, List<String> il) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		im.setLore(il);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack setNameAndLoreandEnchants(ItemStack item, String name, List<String> il, List<String> enchants) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		im.setLore(il);
		for (String enchant : enchants) {
			String[] part = enchant.split(":");
			im.addEnchant(Enchantment.getByName(part[0]), Integer.getInteger(part[1]), true);
		}
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack setName(ItemStack item, String name) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack setLore(ItemStack item, String... lore) {
		ItemMeta im = item.getItemMeta();
		List<String> il = new ArrayList<String>();
		for (String l : lore) {
			il.add(l);
		}
		im.setLore(il);
		item.setItemMeta(im);
		return item;
	}
	
	public static ItemStack setLore(ItemStack item, List<String> lore) {
		ItemMeta im = item.getItemMeta();
		List<String> il = new ArrayList<String>();
		for (String l : lore) {
			il.add(l);
		}
		im.setLore(il);
		item.setItemMeta(im);
		return item;
	}
	
//	public static ItemStack setUnbreakable(ItemStack item) {
//		ItemMeta meta = item.getItemMeta();
//		meta.setUnbreakable(true);
//		item.setItemMeta(meta);
//		return item;
//	}
	
	public static ItemStack stripLore(ItemStack item) {
		if (item.getType().equals(Material.SKULL) || item.getType().equals(Material.SKULL_ITEM)) {
			SkullMeta meta = (SkullMeta) item.getItemMeta();
			List<String> lore = new ArrayList<String>();
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;
		} else {
			ItemMeta meta = item.getItemMeta();
			List<String> lore = new ArrayList<String>();
			meta.setLore(lore);
			item.setItemMeta(meta);
			return item;
		}
	}
	
	public static boolean isEqual(ItemStack item1, ItemStack item2) {
		if (stripLore(item1.clone()).equals(stripLore(item2.clone()))) {
			return true;
		} else {
			return false;
		}
	}

	public static ItemStack setUnbreakable(ItemStack itemStack) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.setUnbreakable(true);
		itemStack.setItemMeta(meta);
		return itemStack;
	}
}
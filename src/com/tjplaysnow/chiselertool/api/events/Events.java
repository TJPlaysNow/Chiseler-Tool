package com.tjplaysnow.chiselertool.api.events;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Events<T extends Event> implements Listener {
	
	private final List<Consumer<T>> listeners = new ArrayList<Consumer<T>>();
	
	public Events(Plugin plugin, Class<T> E) {
		this(plugin, E, EventPriority.NORMAL);
	}
	
	public Events(Plugin plugin, Class<T> E, EventPriority prio) {
		EventExecutor eventExecutor = (listener, e) -> {
			eventListener((T) e);
		};
		plugin.getServer().getPluginManager().registerEvent(E, this, prio, eventExecutor, plugin);
	}
	
	public boolean onEvent(Consumer<T> listener) {
		return listeners.add(listener);
	}
	
	public List<Consumer<T>> getListeners() {
		return listeners;
	}
	
	public void eventListener(T event) {
		listeners.forEach(consumer -> consumer.accept(event));
	}
}
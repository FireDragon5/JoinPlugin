package me.firedragon5.joinplugin.events;

import me.firedragon5.joinplugin.JoinPlugin;
import me.firedragon5.joinplugin.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener {

	private final JoinPlugin plugin;

	public LeaveEvent(JoinPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();


		event.setQuitMessage(Utils.chat(plugin.getConfig().getString("Leave_Message")
				.replace("%player%", player.getName())));

	}

}

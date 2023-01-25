package me.firedragon5.joinplugin;

import org.bukkit.*;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;



public class Utils implements Listener {


	private final JoinPlugin plugin;

	public Utils(JoinPlugin plugin) {
		this.plugin = plugin;
	}


	public static String chat(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}


//	Spawn in Fireworks with a delay

	public static void spawnFireworks(Location loc) {
		Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
		FireworkMeta fwm = fw.getFireworkMeta();

		Random r = new Random();

		int rt = r.nextInt(5) + 1;

		FireworkEffect.Type type = FireworkEffect.Type.BALL;

		if (rt == 1) type = FireworkEffect.Type.BALL;
		if (rt == 2) type = FireworkEffect.Type.BALL_LARGE;
		if (rt == 3) type = FireworkEffect.Type.BURST;
		if (rt == 4) type = FireworkEffect.Type.CREEPER;
		if (rt == 5) type = FireworkEffect.Type.STAR;

		int r1i = r.nextInt(17) + 1;
		int r2i = r.nextInt(17) + 1;


		FireworkEffect effect = FireworkEffect.builder().flicker(
				r.nextBoolean()).withColor(Color.RED).withFade(Color.BLUE).with(type).trail(r.nextBoolean()).build();

		fwm.addEffect(effect);


	}



//		Add sound on join with config

	public static void playSound(Player player) {

//		Play sound on join
		player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);


	}






}


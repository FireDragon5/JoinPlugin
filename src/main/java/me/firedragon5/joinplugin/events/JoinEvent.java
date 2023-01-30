package me.firedragon5.joinplugin.events;

import me.clip.placeholderapi.PlaceholderAPI;
import me.firedragon5.joinplugin.JoinPlugin;
import me.firedragon5.joinplugin.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinEvent implements Listener {

	private final JoinPlugin plugin;

	public JoinEvent(JoinPlugin plugin) {
		this.plugin = plugin;
	}


	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		if (!player.hasPlayedBefore()) {

//			Increase the total by 1 and also store the UUID in the config

//			File file = new File(plugin.getDataFolder(), "playerData.yml");
//			YamlConfiguration playerData = YamlConfiguration.loadConfiguration(file);
//
//			int total = playerData.getInt("Total_Players");
//			total++;
//			playerData.set("Total_Players", total);
//
//			playerData.set("Players." + player.getName(),player.getUniqueId().toString());
//
//			try {
//				playerData.save(file);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}


			event.setJoinMessage(Utils.chat(PlaceholderAPI.setPlaceholders(player, plugin.getConfig().getString("First" +
					"Join_Message").replace("%player%", player.getName()))));

//			First join titles
			if (plugin.getConfig().getBoolean("FirstJoin_Titles")) {


				player.sendTitle(Utils.chat(PlaceholderAPI.setPlaceholders(player, plugin.getConfig().getString("Title_FirstLine_FirstJoin"))
				),  Utils.chat(PlaceholderAPI.setPlaceholders(player, plugin.getConfig().getString("Title_SecondLine_FirstJoin").replace("%player%", player.getName()))
				), plugin.getConfig().getInt("Title_FadeIn"), plugin.getConfig().getInt("Title_FadeStay"), plugin.getConfig().getInt("Title_FadeOut"));
			}

		} else {


//			Add a count of player joins here of the day
//			File file = new File(plugin.getDataFolder(), "playerData.yml");
//			YamlConfiguration playerData = YamlConfiguration.loadConfiguration(file);
//
//
//			int count = playerData.getInt(player.getUniqueId() + ".joins");
//			count++;
//			playerData.set(player.getUniqueId() + ".joins", count);
//
//			try {
//				playerData.save(file);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}


			if (plugin.getConfig().getBoolean("JoinAnnouncements_Enabled")) {

//			Add minecraft items to the announcements


				for (String msg : plugin.getConfig().getStringList("JoinAnnouncement_Message")) {
					player.sendMessage(
							Utils.chat(PlaceholderAPI.setPlaceholders(player,msg.replace("%player%", player.getName()))));

//				Add minecraft items to the announcements


				}

			}
//			Pick a random message from the Random_Join_Message from config
			if (plugin.getConfig().getBoolean("Random_Message")) {
				String[] randomJoinMessage = plugin.getConfig().getStringList("Random_Join_Message").toArray(new String[0]);
				int random = (int) (Math.random() * randomJoinMessage.length);
				event.setJoinMessage( Utils.chat(PlaceholderAPI.setPlaceholders(player,randomJoinMessage[random].replace("%player%", player.getName()))));
			} else {
				event.setJoinMessage(Utils.chat(
						PlaceholderAPI.setPlaceholders(player, plugin.getConfig().getString("Join_Message")
								.replace("%player%", player.getName()))));


			}

			if (plugin.getConfig().getBoolean("Join_Titles")) {
//
				player.sendTitle(Utils.chat(PlaceholderAPI.setPlaceholders(player,plugin.getConfig().getString("Title_FirstLine_Join")).replace("%player%", player.getName())
				), Utils.chat(PlaceholderAPI.setPlaceholders(player,plugin.getConfig().getString("Title_SecondLine_Join")).replace("%player%", player.getName())
				), plugin.getConfig().getInt("Title_FadeIn"), plugin.getConfig().getInt("Title_FadeStay"), plugin.getConfig().getInt("Title_FadeOut"));
			}

		}


		if (plugin.getConfig().getBoolean("JoinMessage_Staff")) {
			if (player.hasPermission("joinplugin.staff.join")) {
				event.setJoinMessage(Utils.chat(PlaceholderAPI.setPlaceholders(
						player, plugin.getConfig().getString("Staff_Join_Message")
								.replace("%player%", player.getName()))));

			}
//
		} else if (plugin.getConfig().getBoolean("Staff_Silent_Join")) {
			if (player.hasPermission("joinplugin.staff.joinsilent")) {
				event.setJoinMessage(null);
			}
		}

//		Send firework on join
		if (plugin.getConfig().getBoolean("Spawn_Firework")) {

//			Delay the firework
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
					Utils.spawnFireworks(player.getLocation());
				}
			}, plugin.getConfig().getInt("Firework_Delay"));

		}

//		Delay the sound on Join
		if (plugin.getConfig().getBoolean("Join_Sound")) {

			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {



//					Play the sound from the config
					player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfig().getString("Join_Sound_Type"))
							, plugin.getConfig().getInt("Join_Sound_Volume"), plugin.getConfig().getInt("Join_Sound_Pitch"));
				}
			}, plugin.getConfig().getInt("Join_Sound_Delay"));

		}


//		Create a bossbar with custom texturepack
		if (plugin.getConfig().getBoolean("BossBar_Enabled")) {


//			Get the color of the bossbar out of the conifg
			BarColor color = BarColor.valueOf(plugin.getConfig().getString("BossBar_Color").toUpperCase());
//			 Get the style of the bossbar out of the config
			BarStyle style = BarStyle.valueOf(plugin.getConfig().getString("BossBar_Style").toUpperCase());

			BossBar bossBar = Bukkit.createBossBar(Utils.chat(PlaceholderAPI.setPlaceholders(
					player,plugin.getConfig().getString("BossBar_Message")
							.replace("%player%", player.getName()))), color, style);

			bossBar.addPlayer(player);
			bossBar.setVisible(true);

//			Add a timer to the bossbar
			int time = plugin.getConfig().getInt("BossBar_Timer");
			final int[] timeLeft = {time};
			Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
				@Override
				public void run() {
					if (timeLeft[0] == 0) {
						bossBar.setVisible(false);
						bossBar.removeAll();
						return;
					}
					bossBar.setTitle(

							Utils.chat(PlaceholderAPI.setPlaceholders(
									player, plugin.getConfig().getString("BossBar_Message")
											.replace("%player%", player.getName())
									.replace("%time%", String.valueOf(timeLeft[0])))));


					timeLeft[0]--;
				}
			}, 0, 20);

		}

		if (plugin.getConfig().getBoolean("FirstJoin_Spawn_Location")){
			if (player.hasPlayedBefore()){
				return;
			}
			else {
				if (plugin.getConfig().getString("FirstJoin_Spawn_Location_World").equals("None")){
					player.sendMessage(Utils.chat("&cThe world you set in the config.yml does not exist!"));
					return;
				}

				player.teleport(new Location(Bukkit.getWorld(plugin.getConfig().getString("FirstJoin_Spawn_Location_World")), plugin.getConfig().getDouble("FirstJoin_Spawn_Location_X"), plugin.getConfig().getDouble("FirstJoin_Spawn_Location_Y"), plugin.getConfig().getDouble("FirstJoin_Spawn_Location_Z")));
			}
		}
	}
}
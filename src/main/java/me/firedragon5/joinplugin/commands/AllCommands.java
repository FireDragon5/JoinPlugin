package me.firedragon5.joinplugin.commands;

import me.firedragon5.joinplugin.JoinPlugin;
import me.firedragon5.joinplugin.Utils;
import me.firedragon5.joinplugin.menu.Gui;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AllCommands implements CommandExecutor, TabCompleter {

	private final JoinPlugin plugin;

	public AllCommands(JoinPlugin plugin) {
		this.plugin = plugin;
	}


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (sender instanceof Player) {
			Player player = (Player) sender;

			if (command.getName().equalsIgnoreCase("join")) {
				if (args.length == 0) {

//					Show what all of the commands are
					player.sendMessage(Utils.chat("&a&lJoinPlugin Commands"));
					player.sendMessage(Utils.chat("&a/join reload - Reload the config"));
					player.sendMessage(Utils.chat("&a/join menu - Open the menu"));
					player.sendMessage(Utils.chat("&a/join firstspawn - Set the first spawn location"));
					player.sendMessage(Utils.chat("&a/join testfirstspawn - Teleport to the first spawn location"));



					return true;
				} else {
					if (args[0].equalsIgnoreCase("reload")) {

						if (player.hasPermission("joinplugin.reload")) {
							JoinPlugin.getInstance().reloadConfig();
							player.sendMessage(Utils.chat("&aConfig reloaded!"));
						} else {
							player.sendMessage(Utils.chat("&cYou do not have permissions for this command!"));
						}


					} else if (args[0].equalsIgnoreCase("menu")) {

						if (player.hasPermission("joinplugin.menu")) {
							Gui gui = new Gui(plugin);
							gui.createMenu(player);

						}
					} else if (args[0].equalsIgnoreCase("firstspawn")) {

						if (!player.hasPermission("joinplugin.firstspawn")) {
							player.sendMessage(Utils.chat("&cYou do not have permissions for this command!"));
							return true;
						}

//						When the player joins for the first time
						Location plocation = player.getLocation();
						plugin.getConfig().set("FirstJoin_Spawn_Location", plocation);
						player.sendMessage(Utils.chat("&aFirst spawn location set!"));
//						Show the x y and z values
						player.sendMessage(Utils.chat("&aX: " + plocation.getX()
								+ " Y: " + plocation.getY() +
								" Z: " + plocation.getZ()));

						try {
							plugin.saveConfig();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else if (args[0].equalsIgnoreCase("testfirstspawn")) {
						try {
							Location location = (Location) plugin.getConfig().get("FirstJoin_Spawn_Location");
							player.teleport(location);
							player.sendMessage(Utils.chat("&aTeleported to first spawn location!"));
						} catch (Exception e) {
							player.sendMessage(Utils.chat("&cFirst spawn location not set!"));
						}
					}

				}
			}
		} else {
			sender.sendMessage(Utils.chat("&cYou must be a player to use this command!"));
		}




		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

		List<String> list = new ArrayList<>();


		List <String> commands = new ArrayList<>();
//		Add multiple commands to the list
		commands.add("reload"); commands.add("jmessage"); commands.add("menu"); commands.add("firstspawn");


		if (command.getName().equalsIgnoreCase("join")) {
			if (args.length == 1) {
//			Check the players permission
				for (String permission : new String[]{"joinplugin.reload", "joinplugin.menu", "joinplugin.firstspawn"}) {
					switch (permission) {
						case "joinplugin.reload":
							if (sender.hasPermission(permission)) {
								list.add("reload");
							}
							break;
						case "joinplugin.menu":
							if (sender.hasPermission(permission)) {
								list.add("menu");
							}
							break;
						case "joinplugin.firstspawn":
							if (sender.hasPermission(permission)) {
								list.add("firstspawn");
								list.add("testfirstspawn");
							}
							break;


					}
				}

//

			}


		}

		for (String s : args) {
			list.removeIf(s1 -> !s1.toLowerCase().startsWith(s.toLowerCase()));
		}


		return list;
	}
}


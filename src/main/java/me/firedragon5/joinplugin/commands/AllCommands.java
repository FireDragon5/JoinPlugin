package me.firedragon5.joinplugin.commands;

import me.firedragon5.joinplugin.JoinPlugin;
import me.firedragon5.joinplugin.Utils;
import me.firedragon5.joinplugin.menu.Gui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

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
					player.sendMessage(Utils.chat("&cUsage: /join {reload|menu}"));
					return true;
				} else {
					if (args[0].equalsIgnoreCase("reload")) {

						JoinPlugin.getInstance().reloadConfig();
						player.sendMessage(Utils.chat("&aConfig reloaded!"));


					} else if (args[0].equalsIgnoreCase("jmessage")) {

						if (player.hasPermission("joinplugin.joinmessage")) {
							player.sendMessage(plugin.getConfig().getString("Join_Message").replace("%player%", player.getName()));
						} else {
							player.sendMessage(Utils.chat("&cYou do not have permissions for this command!"));
						}

					} else if (args[0].equalsIgnoreCase("menu")) {

						if (player.hasPermission("joinplugin.menu")) {
							Gui gui = new Gui(plugin);
							gui.createMenu(player);

						}
					}
				}
			}
		}


		return false;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

		List<String> list = new ArrayList<>();

		if (command.getName().equalsIgnoreCase("join")) {
			if (args.length == 1) {
				list.add("reload");
				list.add("menu");
			}
		}

		for (String s : args) {
			list.removeIf(s1 -> !s1.toLowerCase().startsWith(s.toLowerCase()));
		}


		return list;
	}
}

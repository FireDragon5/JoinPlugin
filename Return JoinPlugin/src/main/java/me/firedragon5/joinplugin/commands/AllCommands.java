package me.firedragon5.joinplugin.commands;

import me.firedragon5.joinplugin.JoinPlugin;
import me.firedragon5.joinplugin.Utils;
import me.firedragon5.joinplugin.menu.Gui;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AllCommands implements CommandExecutor {

	private final JoinPlugin plugin;

	public AllCommands(JoinPlugin plugin) {
		this.plugin = plugin;
	}


	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(sender instanceof Player) {
			Player player = (Player) sender;

			if (command.getName().equalsIgnoreCase("join")) {
				if (args.length == 0) {
					player.sendMessage(Utils.chat("&cUsage: /join {reload|menu}"));
					return true;
				} else {
					if (args[0].equalsIgnoreCase("reload")) {
						reloadCommand reloadCommand = new reloadCommand();

						try {
							reloadCommand.onCommand(sender);
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else if (args[0].equalsIgnoreCase("jmessage")) {

						if (player.hasPermission("joinplugin.joinmessage")) {
							JoinMessageCommand joinMessageCommand = new JoinMessageCommand(plugin);
							joinMessageCommand.onCommand(sender);
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
}

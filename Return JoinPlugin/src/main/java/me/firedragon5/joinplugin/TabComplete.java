package me.firedragon5.joinplugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TabComplete implements TabCompleter {
	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("join")) {
			if (args.length == 1) {
				List<String> list = new ArrayList<>();
				list.add("reload");
				list.add("menu");
				return list;
			}
		}



		return null;
	}
}

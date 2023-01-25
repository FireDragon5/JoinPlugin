package me.firedragon5.joinplugin.commands;

import me.firedragon5.joinplugin.JoinPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class reloadCommand{

	public void onCommand(CommandSender sender) {

		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("joinplugin.reload") || player.isOp()) {

				JoinPlugin.getInstance().reloadConfig();

				player.sendMessage(ChatColor.AQUA + "Config has been reloaded!");
			}else{

				player.sendMessage(ChatColor.RED + "You do not have permissions for this command!");
			}

		}


	}


}

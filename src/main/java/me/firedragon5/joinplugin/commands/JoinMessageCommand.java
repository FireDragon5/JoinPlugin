package me.firedragon5.joinplugin.commands;

import me.firedragon5.joinplugin.JoinPlugin;
import me.firedragon5.joinplugin.Utils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JoinMessageCommand {

	private final JoinPlugin plugin;

	public JoinMessageCommand(JoinPlugin plugin) {
		this.plugin = plugin;
	}

	public void onCommand(CommandSender sender){
		if (sender instanceof Player){
			Player player = (Player) sender;

			if (player.hasPermission("joinplugin.joinmessage")){
				player.sendMessage(plugin.getConfig().getString("Join_Message").replace("%player%", player.getName()));
			}else{
				player.sendMessage(Utils.chat("&cYou do not have permissions for this command!"));
			}
		}
	}


}

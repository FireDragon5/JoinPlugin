package me.firedragon5.joinplugin.manager;

import me.firedragon5.joinplugin.JoinPlugin;

public class configManager {

	private final JoinPlugin plugin;

	public configManager(JoinPlugin plugin) {
		this.plugin = plugin;
	}


	//	Set the default config
	public void setConfig() {
		plugin.getConfig().options().copyDefaults(true);
		plugin.saveDefaultConfig();
	}

	//	Set the default values for all the config options
	public void setDefaultValues() {

		plugin.getConfig().addDefault("First_Join_Message", "&aWelcome to the server!");
		plugin.getConfig().addDefault("Join_Message", "&a%player% has joined the server!");
		plugin.getConfig().addDefault("Leave_Message", "&c%player% has left the server!");
		plugin.getConfig().addDefault("Random_Message", true);


	}
}

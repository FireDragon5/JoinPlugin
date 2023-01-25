package me.firedragon5.joinplugin;

import me.firedragon5.joinplugin.commands.AllCommands;
import me.firedragon5.joinplugin.events.JoinEvent;
import me.firedragon5.joinplugin.events.LeaveEvent;
import me.firedragon5.joinplugin.menu.Gui;
import org.bstats.bukkit.Metrics;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;


public final class JoinPlugin extends JavaPlugin {

	private static JoinPlugin instance;



	public static Plugin getInstance() {
		return instance;
	}

// Auto update the config file if outdated to the newest version
//	Make a backup of their old config file and add a message to the console saying that the config file has been updated

	public void checkConfig(){

		File file = new File(getDataFolder(), "config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(file);


		if(config.getDouble("Config_Version") == 1.2 || !config.contains("Config_Version")){
			getLogger().info("Config is outdated, updating...");
//			Add the new content form the internal config file to the config file


//			saveResource("config.yml", true);
			getLogger().info("Config has been updated to the latest version!");
			getLogger().info("A backup of your old config file has been saved to the plugins folder");


		}



	}


	@Override
	public void onEnable() {
		// Plugin startup logic

//		Add bstats metrics
		int pluginId = 16536;

		Metrics metrics = new Metrics(this, pluginId);

		instance = this;

		this.getConfig().options().copyDefaults(false);
		saveConfig();





		getServer().getPluginManager().registerEvents(new Utils(this), this);
		getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
		getServer().getPluginManager().registerEvents(new LeaveEvent(this), this);
		getServer().getPluginManager().registerEvents(new Gui(this), this);




		getCommand("join").setExecutor(new AllCommands(this));



	}
}

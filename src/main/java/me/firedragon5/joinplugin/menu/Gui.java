package me.firedragon5.joinplugin.menu;

import me.firedragon5.joinplugin.JoinPlugin;
import me.firedragon5.joinplugin.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Gui implements Listener {


	public Inventory inv;
	private final JoinPlugin plugin;

	public Gui(JoinPlugin plugin) {
		this.plugin = plugin;
	}

	public void createMenu(Player player){

		inv = Bukkit.createInventory(null, 54, Utils.chat("&bJoin Menu"));



		ItemStack thanks = new ItemStack(Material.PAPER);
		ItemMeta thanksMeta = thanks.getItemMeta();
		thanksMeta.setDisplayName(Utils.chat("&bThanks for using my plugin!"));
		List<String> thanksLore = new ArrayList<>();
		thanksLore.add(Utils.chat("&bThanks!"));
		thanksLore.add(Utils.chat("&bPlugin version: " + plugin.getDescription().getVersion()));
		thanksMeta.setLore(thanksLore);
		thanks.setItemMeta(thanksMeta);
		inv.setItem(4, thanks);



		ItemStack reload = new ItemStack(Material.END_ROD);
		ItemMeta reloadmeta = reload.getItemMeta();

		reloadmeta.setDisplayName(Utils.chat("&bReload Config"));
		List<String> lore = new ArrayList<>();
		lore.add(Utils.chat("&aReload config!"));
		reloadmeta.setLore(lore);
		reload.setItemMeta(reloadmeta);
		inv.setItem(11, reload);

		ItemStack join = new ItemStack(Material.GREEN_WOOL);
		ItemMeta joinmeta = join.getItemMeta();

		joinmeta.setDisplayName(Utils.chat("&bJoin Message"));
		List<String> joinlore = new ArrayList<>();
		joinlore.add(Utils.chat("&aCheck the join message"));
		joinlore.add(Utils.chat(plugin.getConfig().getString("Join_Message"))
				.replace("%player%", player.getDisplayName()));
		joinmeta.setLore(joinlore);
		join.setItemMeta(joinmeta);
		inv.setItem(15,join);

		ItemStack leave = new ItemStack(Material.RED_WOOL);
		ItemMeta leavemeta = leave.getItemMeta();

		leavemeta.setDisplayName(Utils.chat("&bLeave Message"));
		List<String> leavelore = new ArrayList<>();
		leavelore.add(Utils.chat("&aCheck the leave message"));
		leavelore.add(Utils.chat(plugin.getConfig().getString("Leave_Message"))
				.replace("%player%", player.getDisplayName()));
		leavemeta.setLore(leavelore);
		leave.setItemMeta(leavemeta);
		inv.setItem(19,leave);





//		Plain glass pane for the rest of the menu
//		Add the number in a list that are not allowed

		ItemStack glass = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
		ItemMeta glassmeta = glass.getItemMeta();
		glassmeta.setDisplayName(Utils.chat("&b"));
//		Remove the nbt tags

		glassmeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);


		glass.setItemMeta(glassmeta);

		for (int i = 0; i < inv.getSize(); i++) {

		if (inv.getItem(i) == null) {
				inv.setItem(i, glass);
			}

		}

		ItemStack random = new ItemStack(Material.BARRIER);
		ItemMeta randommeta = random.getItemMeta();
		randommeta.setDisplayName(Utils.chat("&bRandom Join Message"));
		List<String> randomlore = new ArrayList<>();
		randomlore.add(Utils.chat("&aCheck the random join message"));
		randomlore.add(" ");
//		Add each random message on a new line
		for (String s : plugin.getConfig().getStringList("Random_Join_Message")) {
			randomlore.add(Utils.chat(s).replace("%player%", player.getDisplayName()));
		}
		randommeta.setLore(randomlore);
		random.setItemMeta(randommeta);
		inv.setItem(25,random);

		ItemStack bossbar = new ItemStack(Material.DRAGON_EGG);
		ItemMeta bossbarmeta = bossbar.getItemMeta();

		bossbarmeta.setDisplayName(Utils.chat("&bBossbar"));
		List<String> bossbarlore = new ArrayList<>();
		bossbarlore.add(Utils.chat("&aCheck the bossbar"));
		bossbarlore.add(" ");
		bossbarlore.add(Utils.chat(plugin.getConfig().getString("BossBar_Message")
						.replace("%player%", player.getDisplayName())));
		bossbarmeta.setLore(bossbarlore);
		bossbar.setItemMeta(bossbarmeta);
		inv.setItem(29,bossbar);



		ItemStack firstJoinSpawn = new ItemStack(Material.ENDER_PEARL);
		ItemMeta firstJoinSpawnMeta = firstJoinSpawn.getItemMeta();

		firstJoinSpawnMeta.setDisplayName(Utils.chat("&bFirst Join Spawn"));
		List<String> firstJoinSpawnLore = new ArrayList<>();
		firstJoinSpawnLore.add(Utils.chat("&aCheck the first join spawn"));
		firstJoinSpawnLore.add(Utils.chat("&aYou will be teleported to the spawn on your first join"));

		firstJoinSpawnMeta.setLore(firstJoinSpawnLore);
		firstJoinSpawn.setItemMeta(firstJoinSpawnMeta);
		inv.setItem(33, firstJoinSpawn);






		player.openInventory(inv);





	}

	@EventHandler()
	public void onClick(InventoryClickEvent event) {

		if (event.getView().getTitle().equals(Utils.chat("&bJoin Menu"))
				&& event.getCurrentItem() != null) {

			event.setCancelled(true);


			Player player = (Player) event.getWhoClicked();

			if (event.getCurrentItem().getType() == Material.END_ROD) {
				plugin.reloadConfig();
				player.sendMessage(Utils.chat("&aConfig reloaded!"));
				player.closeInventory();
			}

			if (event.getCurrentItem().getType() == Material.GREEN_WOOL) {
				player.sendMessage(Utils.chat(plugin.getConfig().getString("Join_Message"))
						.replace("%player%", player.getDisplayName()));
				player.closeInventory();
			}

			if (event.getCurrentItem().getType() == Material.RED_WOOL) {
				player.sendMessage(Utils.chat(plugin.getConfig().getString("Leave_Message"))
						.replace("%player%", player.getDisplayName()));
				player.closeInventory();
			}

			if (event.getCurrentItem().getType() == Material.BARRIER) {
				for (String s : plugin.getConfig().getStringList("Random_Join_Message")) {
					player.sendMessage(Utils.chat(s).replace("%player%", player.getDisplayName()));
				}
				player.closeInventory();
			}

			if (event.getCurrentItem().getType() == Material.DRAGON_EGG) {

				BarColor color = BarColor.valueOf(plugin.getConfig().getString("BossBar_Color").toUpperCase());
				BarStyle style = BarStyle.valueOf(plugin.getConfig().getString("BossBar_Style").toUpperCase());

				BossBar bossBar = Bukkit.createBossBar(Utils.chat(plugin.getConfig().getString("BossBar_Message")
						).replace("%player%", player.getName()), color, style);
				bossBar.addPlayer(player);
				bossBar.setVisible(true);

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
						bossBar.setTitle(Utils.chat(plugin.getConfig().getString("BossBar_Message")
								.replace("%player%", player.getName()))
								.replace("%time%", String.valueOf(timeLeft[0])));
						timeLeft[0]--;
					}
				}, 0, 20);

				player.closeInventory();
			}

			if (event.getCurrentItem().getType() == Material.ENDER_PEARL) {
				try{
					player.teleport(Objects.requireNonNull(plugin.getConfig().getLocation("FirstJoin_Spawn_Location")));
					player.sendMessage(Utils.chat("&aTeleported to the first join spawn!"));
					player.closeInventory();
				}catch (Exception e) {
					player.closeInventory();
					player.sendMessage(Utils.chat("&cThere is no spawn set!"));
				}

			}



		}
	}
}

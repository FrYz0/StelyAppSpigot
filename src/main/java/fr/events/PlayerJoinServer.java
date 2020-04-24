package fr.events;

import java.util.Date;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.lightmute.StelyAppSpigot.App;
import net.md_5.bungee.event.EventHandler;

public class PlayerJoinServer implements Listener{

	@EventHandler
	public void JoinServeur(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if(!App.sql.hasAccount(player.getName())) {
			Date date = new Date();
			java.sql.Date datesql = new java.sql.Date(date.getTime());
			Random rd = new Random();
			App.sql.createAccount(player.getName(), String.valueOf(rd.nextInt(99999999)), datesql, datesql, 0, Bukkit.getName(), Bukkit.getName());
		}
	}
}

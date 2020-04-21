package fr.lightmute.StelyAppSpigot;

import java.util.logging.Level;

import org.bukkit.plugin.java.JavaPlugin;

public class App extends JavaPlugin {

	//totog
	public void onEnable() {
		//test
		getLogger().log(Level.INFO, "StelyAppSpigot activé !");
	}
	public void onDisable() {
		getLogger().log(Level.INFO, "StelyAppSpigot désactivé !");
	}
}

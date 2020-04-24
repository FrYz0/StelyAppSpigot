package fr.lightmute.StelyAppSpigot;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import fr.events.PlayerJoinServer;

public class App extends JavaPlugin {
	
	public static Plugin instance;
	public static SqlApi sql;

	
	public void onEnable() {
		instance = this;
		getLogger().log(Level.INFO, "StelyAppSpigot activé !");
		SqlConnexion();
		Bukkit.getPluginManager().registerEvents(new PlayerJoinServer(), this);
	}
	public void onDisable() {
		getLogger().log(Level.INFO, "StelyAppSpigot désactivé !");
	}
	
	public void SqlConnexion() {
		ConfigApiStats config = new ConfigApiStats("MYSQL", this);
		if(config.isNull("URL")) {config.set("URL", "ICI"); config.saveConfig();}
		if(config.isNull("HOST")) {config.set("HOST", "ICI"); config.saveConfig();}
		if(config.isNull("DATABASE")) {config.set("DATABASE", "ICI"); config.saveConfig();}
		if(config.isNull("USER")) {config.set("USER", "ICI"); config.saveConfig();}
		if(config.isNull("PASS")) {config.set("PASS", "ICI"); config.saveConfig();}
		
		SqlApi sql = new SqlApi(this, config.getString("URL"), config.getString("HOST"), config.getString("DATABASE"), config.getString("USER"), config.getString("PASS"));
		sql.connection();
		App.sql = sql;
	}
}

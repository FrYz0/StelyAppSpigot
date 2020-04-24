package fr.lightmute.StelyAppSpigot;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigApiStats {
	
	private final Plugin plugin;
	private YamlConfiguration config;
	private File configFile, configFolder;
	private final String fileName;
	private final String path;
	
	public ConfigApiStats(final String file, final Plugin plugin) {
		this(file, new String("plugins" + File.separator + plugin.getDescription().getName()), plugin);
	}
	
	public ConfigApiStats(final String file, final String path) {
		this(file, path, null);
	}
	
	public ConfigApiStats(final String file, final String path, final Plugin plugin) {
		this.plugin = plugin;
		mkdir(path);
		mkFile(file);
		loadConfig();
		this.fileName = file;
		this.path = path;
	}
	
	public final void mkdir(final String path) {
		configFolder = new File(path);
		if(!configFolder.exists())
			configFolder.mkdirs();
	}
	
	public final void mkFile(final String file) {
		configFile = new File(configFolder, file);
		if(!configFile.exists()) {
			if(plugin != null && plugin.getResource(file) != null && !configFile.exists())
				try (InputStream in = plugin.getResource(file)) {
                    Files.copy(in, configFile.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
			else {
				try {
					configFile.createNewFile();
				} catch (IOException e) {
					System.out.println(plugin.getName() + " Le fichier n'a pas pu étre créé");
				}
			}
			
		}
	}
	
	public final void reload() {
		
		mkdir(path);
		mkFile(fileName);
		loadConfig();
		
	}
	
	public final void loadConfig() {
		config = YamlConfiguration.loadConfiguration(configFile);
	}
	
	public final void saveConfig(){
		try {
			config.save(configFile);
		} catch (IOException e) {
			System.out.println("La config n'a pas pu étre sauvegarder correctement");
		}
	}
	
	public final void reloadConfig(){
		loadConfig();
	}
	
	public final void set(final String path, Object value) {
		try{
			config.set(path, value);
		}catch(Exception e) {}
	}
	
	public final void setSave(final String path, Object value) {
		this.set(path, value);
		this.saveConfig();
	}
	
	public final boolean contains(final String path) {
		if(config.contains(path)) return true;
		else return false;
	}
	
	public final boolean isBoolean(final String path){
		try{
			Boolean.parseBoolean(config.getString(path));
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public final boolean isInt(final String path) {
		try{
			Integer.parseInt(config.getString(path));
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public final boolean isDouble(final String path) {
		try {
			Double.parseDouble(config.getString(path));
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public final boolean isLong(final String path) {
		try {
			Long.parseLong(config.getString(path));
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public final boolean isFloat(final String path) {
		try {
			Float.parseFloat(config.getString(path));
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	
	public final boolean isList(final String path) {
		return config.isList(path);
	}
	
	public final boolean isString(final String path) {
		
		try {
			final String output = config.getString(path);
			return output == null ? false : true;
		}catch(NullPointerException e) {
			return false;
		}
		
	}
	
	public final boolean getBoolean(final String path) {
		
		if(!this.isBoolean(path)) {
			
			 throw new IllegalArgumentException("The specified path is not a boolean");
			
		}
		
		return Boolean.valueOf(config.getString(path));
		
	}
	
	public final int getInt(final String path) {

		if(!this.isInt(path)) {
			
			 throw new IllegalArgumentException("The specified path is not a integer");
			
		}
		
		return Integer.valueOf(config.getString(path));
		
	}
	
	public final double getDouble(final String path) {


		if(!this.isDouble(path)) {
			
			 throw new IllegalArgumentException("The specified path is not a double");
			
		}
		
		return Double.valueOf(config.getString(path));
	}
	
	public final long getLong(final String path) {
		

		if(!this.isLong(path)) {
			
			 throw new IllegalArgumentException("The specified path is not a long");
			
		}
		
		return Long.valueOf(config.getString(path));
		
	}
	
	public final float getFloat(final String path) {
		

		if(!this.isFloat(path)) {
			
			 throw new IllegalArgumentException("The specified path is not a float");
			
		}
		
		return Float.valueOf(config.getString(path));
		
	}
	
	public final List<String> getStringList(final String path){
		

		if(!this.isList(path)) {
			
			 throw new IllegalArgumentException("The specified path is not a String list");
			
		}
		
		return config.getStringList(path);
	}
	
	public final List<?> getList(final String path){
		

		if(!this.isList(path)) {
			
			 throw new IllegalArgumentException("The specified path is not a List");
			
		}
		
		return config.getList(path);
		
	}
	
	public String getString(final String path) {
		

		if(!this.isString(path)) {
			
			 throw new IllegalArgumentException("The specified path is not a String");
			
		}
		
		return String.valueOf(config.getString(path));
		
	}
	
	public final Object get(final String path) {
		return config.get(path);
	}
	
	public final boolean isNull(final String path) {
		if(this.isString(path) && !config.getString(path).equalsIgnoreCase("")) return false;
		else return true;
	}
	
	public final YamlConfiguration toConfigurationSection() {
		return config;
	}

	public final String getName() {
		return fileName;
	}

}

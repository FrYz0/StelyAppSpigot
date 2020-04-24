package fr.lightmute.StelyAppSpigot;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

import org.bukkit.plugin.Plugin;

public class SqlApi {
	private Connection connexion;
	private String urlbase,host,database,user,pass;
	Plugin pl;

	public SqlApi(Plugin pl, String urlbase, String host, String database, String user, String pass) {
		this.urlbase = urlbase;
		this.host = host;
		this.database = database;
		this.user = user;
		this.pl = App.instance;
		this.pass = pass;
	}

	public void connection(){
		if(!isConnected()){
			try {
				connexion = DriverManager.getConnection(urlbase + host + "/" + database, user, pass);
				pl.getLogger().log(Level.INFO, "Base de données connecté au plugin !");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void disconnect(){
		if(isConnected()){
			try {
				connexion.close();
				pl.getLogger().log(Level.INFO, "Base de données déconnecté du plugin !");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * INSERT
	 * UPDATE
	 * DELETE
	 * SELECT
	 *
	 * PREPARER ?,?
	 * REMPLACER LES ? PAR DES VALEURS
	 * EXECUTE
	 *
	 */

	public boolean isConnected(){
		return connexion != null;
	}

	public void createAccount(String pseudo, String mdp, Date dateinscript, Date lastdate, int status, String lastserver, String serverpref){
		if(!hasAccount(pseudo)){
			//INSERT

			try {
				PreparedStatement q = connexion.prepareStatement("INSERT INTO "+database+"(pseudo,mdp,dateinscription,lastdate,status,lastserver,serverpref) VALUES (?,?,?,?,?,?,?)");
				q.setString(1, pseudo);
				q.setString(2, mdp);
				q.setDate(3, dateinscript);
				q.setDate(4, lastdate);
				q.setInt(5, status);
				q.setString(6, lastserver);
				q.setString(7, serverpref);
				q.execute();
				q.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}


		}
	}

	public boolean hasAccount(String playername){
		//SELECT

		try {
			PreparedStatement q = connexion.prepareStatement("SELECT pseudo FROM "+database+" WHERE pseudo = ?");
			q.setString(1, playername);
			ResultSet resultat = q.executeQuery();
			boolean hasAccount = resultat.next();
			q.close();
			return hasAccount;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	public String getMDP(String playername){
		//SELECT

		try {
			PreparedStatement q = connexion.prepareStatement("SELECT mdp FROM "+database+" WHERE pseudo = ?");
			q.setString(1, playername);
			ResultSet rs = q.executeQuery();
			String mdp = "";
			while(rs.next()){
				mdp = rs.getString("mdp");
			}
			q.close();
			return mdp;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";
	}
	
	public String getLastServeur(String playername){
		//SELECT

		try {
			PreparedStatement q = connexion.prepareStatement("SELECT lastserver FROM "+database+" WHERE pseudo = ?");
			q.setString(1, playername);
			ResultSet rs = q.executeQuery();
			String mdp = "";
			while(rs.next()){
				mdp = rs.getString("lastserver");
			}
			q.close();
			return mdp;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";
	}
	
	public String getServeurPref(String playername){
		//SELECT

		try {
			PreparedStatement q = connexion.prepareStatement("SELECT serverpref FROM "+database+" WHERE pseudo = ?");
			q.setString(1, playername);
			ResultSet rs = q.executeQuery();
			String mdp = "";
			while(rs.next()){
				mdp = rs.getString("serverpref");
			}
			q.close();
			return mdp;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";
	}
	
	public int getstatus(String playername){
		//SELECT

		try {
			PreparedStatement q = connexion.prepareStatement("SELECT status FROM "+database+" WHERE pseudo = ?");
			q.setString(1, playername);

			int balance = 0;
			ResultSet rs = q.executeQuery();

			while(rs.next()){
				balance = rs.getInt("status");
			}

			q.close();

			return balance;

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	public void setServeurPref(String pseudo, String serverpref){
		//UPDATE

		try {
			PreparedStatement rs = connexion.prepareStatement("UPDATE "+database+" SET serverpref = ? WHERE pseudo = ?");
			rs.setString(1, serverpref);
			rs.setString(2, pseudo);
			rs.executeUpdate();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void setLastServeur(String pseudo, String lastserver){
		//UPDATE

		try {
			PreparedStatement rs = connexion.prepareStatement("UPDATE "+database+" SET lastserver = ? WHERE pseudo = ?");
			rs.setString(1, lastserver);
			rs.setString(2, pseudo);
			rs.executeUpdate();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void setDateInscription(String pseudo, Date dateinscript){
		//UPDATE

		try {
			PreparedStatement rs = connexion.prepareStatement("UPDATE "+database+" SET dateinscription = ? WHERE pseudo = ?");
			rs.setDate(1, dateinscript);
			rs.setString(2, pseudo);
			rs.executeUpdate();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void setDateLastGame(String playername,Date date){
		//UPDATE

		try {
			PreparedStatement rs = connexion.prepareStatement("UPDATE "+database+" SET lastdate = ? WHERE pseudo = ?");
			rs.setDate(1, date);
			rs.setString(2, playername);
			rs.executeUpdate();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void setStatus(String pseudo, int status){
		//UPDATE

		try {
			PreparedStatement rs = connexion.prepareStatement("UPDATE "+database+" SET status = ? WHERE pseudo = ?");
			rs.setInt(1, status);
			rs.setString(2, pseudo);
			rs.executeUpdate();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void reset(String pseudo){
		//UPDATE

		try {
			PreparedStatement rs = connexion.prepareStatement("DELETE FROM "+database+" WHERE pseudo = ?");
			rs.setString(1, pseudo);
			rs.execute();
			rs.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}


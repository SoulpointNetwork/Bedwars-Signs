package com.Funergy.ss;

import java.sql.ResultSet;
import java.sql.SQLException;
import nl.soulpoint.api.mysql.SoulPointMySQL;

public class MySQL {
	private SoulPointMySQL connection;
	public MySQL() {
        connection = new SoulPointMySQL();
    }
	public void openconnection(){
        connection = new SoulPointMySQL();
	}
	 public void setupDB() throws SQLException {
  
      connection.executeUpdate("CREATE TABLE IF NOT EXISTS `SGservers`(`ID` integer,`NAME` varchar(100),`MAP` varchar(100),`PLAYERC` integer,`MAXP` integer,`STATE` varchar(100))");

 
	 }
	 
       public Integer getpc(int ID){
    	   
    	        try {
    	                ResultSet result = connection.select("select PLAYERC from SGservers where ID='" +ID + "'");    	               
    	                if (result.next()) {
    	                        return result.getInt("PLAYERC");
    	                } else {
    	                        return null;
    	                }
    	        } catch (Exception e) {
    	              
    	                return null;
    	        }
    	    }
       public Integer getmp(int ID){
    	   
	        try {
	                ResultSet result = connection.select("select MAXP from SGservers where ID='" +ID + "'");
	               
	                if (result.next()) {
	                        return result.getInt("MAXP");
	                } else {
	                        return null;
	                }
	        } catch (Exception e) {
	          
	                return null;
	        }
	    }
       public String getstate(int ID){
    	   
  	        try {
  	                ResultSet result = connection.select("select STATE from SGservers where ID='" +ID + "'");
  	               
  	                if (result.next()) {
  	                        return result.getString("STATE");
  	                } else {
  	                        return null;
  	                }
  	        } catch (Exception e) {
  	   
  	                return null;
  	        }
  	    }
       public String getmap(int ID){
    	   
 	        try {
 	                ResultSet result = connection.select("select MAP from SGservers where ID='" +ID + "'");
 	               
 	                if (result.next()) {
 	                        return result.getString("MAP");
 	                } else {
 	                        return null;
 	                }
 	        } catch (Exception e) {
 	             
 	                return null;
 	        }
 	    }
       public String getname(int ID){
    	   
	        try {
	                ResultSet result = connection.select("select NAME from SGservers where ID='" +ID + "'");
	               
	                if (result.next()) {
	                        return result.getString("NAME");
	                } else {
	                        return null;
	                }
	        } catch (Exception e) {
	             
	                return null;
	        }
	    }
       public void delete(int ID){
        connection.query("DELETE FROM SGservers WHERE ID='"+ID+"'");
       }

       public void closeconnection(){
    	   
    		   connection.disconnect();
       }
}

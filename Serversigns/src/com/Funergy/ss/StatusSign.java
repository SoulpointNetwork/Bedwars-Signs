package com.Funergy.ss;

import nl.soulpoint.api.mysql.SoulPointMySQL;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Sign;
 
public class StatusSign {
    private SoulPointMySQL connection;

        private Location location;
        private Sign sign;
        private String status;
        private int playercount;
        private int maxpcount;
        private int ID = 1;
        private MySQL mysql;
        private String name;
        
     
        
       
        public StatusSign(Location location,int ID) {
        	
                this.location = location;
                this.sign = (Sign) location.getBlock().getState();
                this.ID = ID;
          
    
        }
       
        public Location getLocation() {
                return location;
        }
        
        public String getName() {
        	return name;
    
    }
        
       
       
        
        public int getID() {
            return ID;
    }

       
        public int getPlayercount() {
            return playercount;
    }
        public int getMaxpcount() {
            return maxpcount;
    }
        public String getstatus() {
            return status;
    }
  
           public void update(){
        	 
        	   mysql = new MySQL();
               connection = new SoulPointMySQL();
               connection.connect();
             

        	   this.status = mysql.getstate(getID());
                  if(mysql.getstate(getID()) != null){
                   	  if(mysql.getstate(getID()).equalsIgnoreCase("LOBBY")){
                 this.name = mysql.getname(getID());
              
                  if(mysql.getpc(getID()) != mysql.getmp(getID())){
                  if(!(mysql.getpc(getID()) > mysql.getmp(getID())-4)){
                   		  sign.setLine(0, ChatColor.BOLD+"["+ChatColor.GREEN+"Join"+ChatColor.BLACK+ChatColor.BOLD+"]");
                   		  sign.setLine(1, ChatColor.BOLD+"Op-HG" );
                   	  }else{
                   		 sign.setLine(0, ChatColor.BOLD+"["+ChatColor.LIGHT_PURPLE+"Vip"+ChatColor.BLACK+ChatColor.BOLD+"]"); 
                   }
                  }else{
               		  sign.setLine(0, ChatColor.BOLD+"["+ChatColor.DARK_RED+"Full"+ChatColor.BLACK+ChatColor.BOLD+"]");
                  }
                   	  
                   		  sign.setLine(2,ChatColor.BOLD+""+mysql.getpc(getID())+"/"+ mysql.getmp(getID()));
                   		  this.playercount = mysql.getpc(getID());
                   		  this.maxpcount = mysql.getmp(getID());
                   	 
                   		  sign.setLine(3, ChatColor.BOLD+""+mysql.getmap(getID()));
                          sign.update(true);

                   	                    	 
        	   }else{
        		   sign.setLine(0, "§c§lSearching...");
        		   sign.setLine(1, "§lFinding a");
        		   sign.setLine(2, "§lnew server");
        		   sign.setLine(3, "§lsoon!");
                   mysql.delete(getID());
                   sign.update(true);


        	   }
                  }else{
                   sign.setLine(0, "§c§lSearching...");
          		   sign.setLine(1, "§lFinding a");
          		   sign.setLine(2, "§lnew server");
          		   sign.setLine(3, "§lsoon!"); 	
          	        sign.update(true);

                  }
        	               
                  
        connection.disconnect();
        
           }
           
           
           
           
          
        
}
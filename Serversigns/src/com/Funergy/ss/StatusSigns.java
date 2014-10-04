package com.Funergy.ss;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import nl.soulpoint.api.SoulPointAPI;
import nl.soulpoint.api.mysql.SoulPointMySQL;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

 
public class StatusSigns extends JavaPlugin implements Listener {
    
    private MySQL mysql;
    private SoulPointMySQL connection;

        private ArrayList<StatusSign> signs;
        public ArrayList<String> nospam = new ArrayList<String>();
        public ArrayList<Chunk> chunks = new ArrayList<Chunk>();
        public void onEnable() {
        	mysql = new MySQL();
            connection = new SoulPointMySQL();
            connection.connect();
            try {
				connection.executeUpdate("CREATE TABLE IF NOT EXISTS `SGservers`(`JOINABLE` varchar(200),`MAP` varchar(100),`ID` integer,`SIGN` varchar(10),`PLAYERC` integer,`MAXP` integer,`STATE` varchar(100))");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            connection.disconnect();

        	
                this.signs = new ArrayList<StatusSign>();
               
        
             
                signs.add(new StatusSign(new Location(Bukkit.getWorld("world"),277,101,53), 1));
                signs.add(new StatusSign(new Location(Bukkit.getWorld("world"),278,101,53), 2));
                signs.add(new StatusSign(new Location(Bukkit.getWorld("world"),279,101,53), 3));
                signs.add(new StatusSign(new Location(Bukkit.getWorld("world"),277,100,53), 4));
                signs.add(new StatusSign(new Location(Bukkit.getWorld("world"),278,100,53), 5));
                signs.add(new StatusSign(new Location(Bukkit.getWorld("world"),279,100,53), 6));

                for (StatusSign s : signs) {
					chunks.add(s.getLocation().getBlock().getChunk());
					
						
					
						
					}
                Bukkit.getServer().getPluginManager().registerEvents(this, this);
                Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
                reseter();

        }
        public void reseter(){
        	new BukkitRunnable(){
        		
				@Override
				public void run() {
					for (StatusSign s : signs) {
						
					s.update();
						
					
						
					}
					
				}
        		
        	}.runTaskTimer(this, 0, 20);
				
                
        }
        @EventHandler
        public void onPlayerInteract(PlayerInteractEvent e) {
                if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
               
                Block block = e.getClickedBlock();
               
                if (block.getType() != Material.SIGN && block.getType() != Material.SIGN_POST && block.getType() != Material.WALL_SIGN) return;
               
                for (StatusSign s : signs) {
                        if (s.getLocation().equals(block.getLocation())) {
                        	if(!nospam.contains(e.getPlayer().getName())){
                        	nospamdelete(e.getPlayer().getName());
                        	if(s.getstatus() != null){
                        	 if(s.getPlayercount() != s.getMaxpcount()){
                                 if(!(s.getPlayercount() > s.getMaxpcount()-3)){
                                try {
                                        ByteArrayOutputStream b = new ByteArrayOutputStream();
                                        DataOutputStream out = new DataOutputStream(b);
 
                                        out.writeUTF("Connect");
                                        out.writeUTF(s.getName());
                                       
                                        e.getPlayer().sendPluginMessage(this, "BungeeCord", b.toByteArray());
                                } catch (Exception ex) {
                                        ex.printStackTrace();
                                }
                        	 }else{
                        		 String s2 = null;
                 				try {
                 					s2 = SoulPointAPI.getRanks().getRankName(e.getPlayer(), false);
                 				} catch (SQLException e2) {
                 					// TODO Auto-generated catch block
                 					e2.printStackTrace();
                 				}
                 				if(!s2.equalsIgnoreCase("None")){
                        			 try {
                                         ByteArrayOutputStream b = new ByteArrayOutputStream();
                                         DataOutputStream out = new DataOutputStream(b);
  
                                         out.writeUTF("Connect");
                                         out.writeUTF(s.getName());
                                        
                                         e.getPlayer().sendPluginMessage(this, "BungeeCord", b.toByteArray());
                                 } catch (Exception ex) {
                                         ex.printStackTrace();
                                 } 
                        		 }else{
                        			 e.getPlayer().sendMessage("§cYou need to be a donator to join vip games!");
                        		 }
                        	 }
                        	}else{
                   			 e.getPlayer().sendMessage("§cThat server is full!");
	
                        	}
                        	}
                        	}else{
                        		return;
                        	}
                        }
                }
        }
        public void nospamdelete(final String pname){
        	nospam.add(pname);
        	new BukkitRunnable(){

				@Override
				public void run() {
					nospam.remove(pname);
				}
        		
        	}.runTaskLater(this, 20);
        }
        @EventHandler(priority=EventPriority.HIGHEST)
        public void onChunkUnload(ChunkUnloadEvent event)
        {
            Chunk currentChunk = event.getChunk();
         
            if (chunks.contains(currentChunk)) {
                event.setCancelled(true);
            }
        }
        
      
}
package br.net.fabiozumbi12.RedProtect.Bukkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import br.net.fabiozumbi12.RedProtect.Bukkit.Region;

public class RenameRegionEvent extends Event implements Cancellable {
	private static final HandlerList handlers = new HandlerList();
	private final Region region;
	private String newName;
	private final String oldName;
	private final Player player;
	private boolean isCancelled = false;
	
	public RenameRegionEvent(Region region, String newName, String oldName, Player p){
		this.region = region;
		this.newName = newName;
		this.oldName = oldName;
		this.player = p;
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	public Region getRegion(){
		return this.region;
	}
	
	public String getNewName(){
		return this.newName;
	}
	
	public void setNewName(String newName){
		this.newName = newName;
	}
	
	public String getOldName(){
		return this.oldName;
	}
	
	public String getNewID(){
		return this.newName+"@"+this.region.getWorld();
	}
	
	@Override
	public boolean isCancelled() {
		return this.isCancelled;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.isCancelled = arg0;
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}
	
	public static HandlerList getHandlerList() {
        return handlers;
    }

}

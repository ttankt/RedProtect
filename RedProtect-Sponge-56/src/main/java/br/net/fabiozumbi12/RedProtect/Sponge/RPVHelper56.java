package br.net.fabiozumbi12.RedProtect.Sponge;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.animal.Horse;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.Enchantments;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RPVHelper56 implements RPVHelper {
	
	RPVHelper56(){}
	
	@Override
	public Cause getCause(Player p) {
		return Cause.of(NamedCause.simulated(p));
	}

	@Override
	public void closeInventory(Player p) {
		p.closeInventory(getCause(p));
	}

	@Override
	public void openInventory(Inventory inv, Player p) {
		p.openInventory(inv, Cause.of(NamedCause.of(p.getName(),p)));
	}

	@Override
	public void setBlock(World w, Location<World> loc, BlockType type) {
		w.setBlockType(loc.getBlockPosition(), type, Cause.of(NamedCause.owner(RedProtect.plugin)));
	}

	@Override
	public void setBlock(Location<World> loc, BlockState block) {
		loc.setBlock(block, Cause.of(NamedCause.owner(RedProtect.plugin)));  
	}

	@Override
	public void digBlock(Player p, ItemStack item, Vector3i loc) {
		p.getWorld().digBlockWith(loc, item, Cause.of(NamedCause.owner(RedProtect.plugin)));
	}

	@Override
	public void removeBlock(Location<World> loc) {
		loc.removeBlock(Cause.of(NamedCause.owner(RedProtect.plugin)));
	}

	@Override
	public boolean checkCause(Cause cause, String toCompare) {
		return cause.containsNamed(toCompare);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean checkHorseOwner(Entity ent, Player p) {
		if (ent instanceof Horse && ((Horse)ent).getHorseData().get(Keys.TAMED_OWNER).isPresent()){
			Horse tam = (Horse) ent;
			Player owner = RedProtect.serv.getPlayer(tam.getHorseData().get(Keys.TAMED_OWNER).get().get()).get();
			return owner.getName().equals(p.getName());
		}
		return false;
	}

	@Override
	public List<String> getAllEnchants() {
		return Sponge.getRegistry().getAllOf(Enchantment.class).stream().map(Enchantment::getId).collect(Collectors.toList());
	}

	@Override
	public ItemStack offerEnchantment(ItemStack item) {
		item.offer(Keys.ITEM_ENCHANTMENTS, Collections.singletonList(new ItemEnchantment(Enchantments.UNBREAKING, 1)));
		return item;
	}


}

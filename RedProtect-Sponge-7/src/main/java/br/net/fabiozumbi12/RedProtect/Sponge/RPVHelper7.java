package br.net.fabiozumbi12.RedProtect.Sponge;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.animal.RideableHorse;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.cause.EventContextKey;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.enchantment.EnchantmentTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3i;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RPVHelper7 implements RPVHelper{
	
	RPVHelper7(){}
	
	@Override
	public Cause getCause(Player p){
		return Cause.of(EventContext.builder().add(EventContextKeys.PLAYER, p).build(), p);
	}

	@Override
	public void closeInventory(Player p) {
		p.closeInventory();
	}

	@Override
	public void openInventory(Inventory inv, Player p) {
		p.openInventory(inv);
	}

	@Override
	public void setBlock(World w, Location<World> loc, BlockType type) {
		w.setBlockType(loc.getBlockPosition(), type);
	}

	@Override
	public void setBlock(Location<World> loc, BlockState block) {
		loc.setBlock(block);
	}

	@Override
	public void digBlock(Player p, ItemStack item, Vector3i loc) {
		p.getWorld().digBlockWith(loc, item, p.getProfile());
	}

	@Override
	public void removeBlock(Location<World> loc) {
		loc.removeBlock();
		
	}

	@Override
	public boolean checkCause(Cause cause, String toCompare) {
        return RedProtect.game.getRegistry().getType(EventContextKey.class, toCompare).isPresent() && cause.contains(RedProtect.game.getRegistry().getType(EventContextKey.class, toCompare).get());
    }

	@Override
	public boolean checkHorseOwner(Entity ent, Player p) {
		if (ent instanceof RideableHorse && ((RideableHorse)ent).getHorseData().get(Keys.TAMED_OWNER).isPresent()){
			RideableHorse tam = (RideableHorse) ent;
			Player owner = RedProtect.serv.getPlayer(tam.getHorseData().get(Keys.TAMED_OWNER).get().get()).get();
			return owner.getName().equals(p.getName());
		}
		return false;
	}

	@Override
	public List<String> getAllEnchants() {
		return Sponge.getRegistry().getAllOf(EnchantmentType.class).stream().map(EnchantmentType::getId).collect(Collectors.toList());
	}

    @Override
    public ItemStack offerEnchantment(ItemStack item) {
        item.offer(Keys.ITEM_ENCHANTMENTS, Collections.singletonList(Enchantment.builder().type(EnchantmentTypes.UNBREAKING).level(1).build()));
        return item;
    }
}

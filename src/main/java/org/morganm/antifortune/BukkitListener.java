/**
 * 
 */
package org.morganm.antifortune;

import java.util.Map;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * @author morganm
 *
 */
public class BukkitListener implements Listener {
	private final AntiFortune plugin;
	
	public BukkitListener(final AntiFortune plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEnchantItem(EnchantItemEvent event) {
		Map<Enchantment, Integer> enchants = event.getEnchantsToAdd();
		Integer i = enchants.get(Enchantment.LOOT_BONUS_BLOCKS);
		
		if( i != null ) {
			int origSize = enchants.size();
			enchants.remove(Enchantment.LOOT_BONUS_BLOCKS);
			int newSize = enchants.size();
			plugin.debug("removed FORTUNE enchant: player=",event.getEnchanter(),", origSize=",origSize,", newSize=",newSize);
			
			// adjust the XP level down to account for the missing enchant. For example,
			// if there were originally 3 enchants before we dropped the FORTUNE enchant,
			// at a cost of 30, then this will reduce the cost by 2/3 to only 20.
			int origCost = event.getExpLevelCost();
			int newCost = (origCost * newSize) / origSize;
			event.setExpLevelCost(newCost);
			plugin.debug("original cost=",origCost,", newCost=",newCost);
		}
	}
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		ItemStack item = event.getPlayer().getItemInHand();
		if( item != null ) {
			if( item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS) )
				item.removeEnchantment(Enchantment.LOOT_BONUS_BLOCKS);
		}
	}
}

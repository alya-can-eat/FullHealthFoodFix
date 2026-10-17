package ch.alya.foodfix;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Logger;

/**
 * This plugin stops food use at full health.
 */
public class FoodFix extends JavaPlugin {

    private static final Logger LOG = Logger.getLogger("Minecraft");

    private static final int MAX_HEALTH = 20;

    private static final Set<Material> FOOD = EnumSet.of(
            Material.APPLE,
            Material.GOLDEN_APPLE,
            Material.BREAD,
            Material.PORK,
            Material.GRILLED_PORK,
            Material.RAW_FISH,
            Material.COOKED_FISH,
            Material.COOKIE,
            Material.MUSHROOM_SOUP
    );

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvent(
                Event.Type.PLAYER_INTERACT, new FoodListener(),
                Event.Priority.Normal, this);
        LOG.info("[" + getDescription().getName() + "] v"
                + getDescription().getVersion() + " enabled.");
    }

    @Override
    public void onDisable() {
        LOG.info("[" + getDescription().getName() + "] disabled.");
    }

    private class FoodListener extends PlayerListener {
        @Override
        public void onPlayerInteract(PlayerInteractEvent event) {
            Action action = event.getAction();
            if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
                return;
            }

            ItemStack item = event.getItem();
            if (item == null || !FOOD.contains(item.getType())) {
                return;
            }

            final Player player = event.getPlayer();
            if (player.getHealth() < MAX_HEALTH) {
                return;
            }

            // Only deny the item use. Block interaction (chests, doors, ...)
            // while holding food keeps working.
            event.setUseItemInHand(Event.Result.DENY);

            // The client already "ate" the food locally, resync the inventory
            // one tick later so the item (or soup instead of bowl) reappears.
            getServer().getScheduler().scheduleSyncDelayedTask(FoodFix.this, new Runnable() {
                public void run() {
                    if (player.isOnline()) {
                        player.updateInventory();
                    }
                }
            }, 1L);
        }
    }
}

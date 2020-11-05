package lordania.lordaniacombat.pvpchanges;

import lordania.lordaniacombat.tag.CombatTag;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ItemCooldown implements Listener {

    @EventHandler
    public void onPlayerUseEp(PlayerInteractEvent e) {

        Action action = e.getAction();

        if (action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR)) {
            if (e.getItem() != null) {
                if (e.getItem().getType() == Material.ENDER_PEARL) {

                    Player p = e.getPlayer();

                    if (!CombatTag.isCombatTagged(p)) {

                        if (p.getCooldown(Material.ENDER_PEARL) == 0) {

                            if (e.getItem().getAmount() > 1) {
                                e.getItem().setAmount(e.getItem().getAmount() - 1);
                            } else {
                                e.getItem().setAmount(0);
                            }

                            p.setCooldown(Material.ENDER_PEARL, 300);
                            p.launchProjectile(org.bukkit.entity.EnderPearl.class);

                        }
                    } else {
                        e.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerUseEp(EntityShootBowEvent e) {
        if (e.getEntity() instanceof Player) {
            if (e.getBow().getType().equals(Material.CROSSBOW)) {
                Player p = (Player) e.getEntity();

                p.setCooldown(Material.CROSSBOW, 28);

            }
        }
    }
}

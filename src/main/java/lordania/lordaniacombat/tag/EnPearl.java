package lordania.lordaniacombat.tag;

import lordania.lordaniacombat.main.LordaniaCombat;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Iterator;

public class EnPearl implements Listener {

    private static Plugin plugin = LordaniaCombat.getPlugin(LordaniaCombat.class);
    private static HashMap<Player, Long> epCoolDown = new HashMap<>();

    public EnPearl() {
        cooldownDecrement();
    }

    @EventHandler
    public void onPlayerUseEp(PlayerInteractEvent event) {

        Action action = event.getAction();

        if(action.equals(Action.RIGHT_CLICK_BLOCK) || action.equals(Action.RIGHT_CLICK_AIR)) {
            if(event.getItem() != null) {
                if(event.getItem().getType() == Material.ENDER_PEARL) {
                    if (!CombatTag.isCombatTagged(event.getPlayer())) {
                        if(!(onEPCooldown(event.getPlayer()))) {

                            if (event.getItem().getAmount() > 1) {
                                event.getItem().setAmount(event.getItem().getAmount() - 1);
                            } else {
                                event.getItem().setAmount(0);
                            }

                            event.getPlayer().setCooldown(Material.ENDER_PEARL, 100);
                            event.getPlayer().launchProjectile(org.bukkit.entity.EnderPearl.class);
                            epCoolDown.put(event.getPlayer(), System.currentTimeMillis()/1000 + 15);

                        }
                        else { //if on cooldown
                            long timeLeft = epCoolDown.get(event.getPlayer()) - System.currentTimeMillis()/1000;
                            event.getPlayer().sendMessage("§cYou are on cooldown for §e" + timeLeft + " §cmore seconds.");
                            event.setCancelled(true);
                        }
                    }
                    else { //if combat tagged
                        event.getPlayer().sendMessage("§cYou cannot do this while combat tagged.");
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    public void cooldownDecrement() {
        new BukkitRunnable() {

            @Override
            public void run() { //every second

                if (epCoolDown.isEmpty()) { //if empty dont waste time
                    return;
                }

                Iterator<Player> it = epCoolDown.keySet().iterator();

                while (it.hasNext()) {
                    Player p = it.next().getPlayer();

                    if (epCoolDown.get(p) == System.currentTimeMillis() / 1000) {
                        it.remove();
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }

    private static boolean onEPCooldown(Player player) {
        return epCoolDown.containsKey(player);
    }
}

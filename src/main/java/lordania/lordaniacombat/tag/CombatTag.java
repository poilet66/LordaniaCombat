package lordania.lordaniacombat.tag;

import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.utils.CombatUtil;
import lordania.lordaniacombat.main.LordaniaCombat;
import lordania.lordaniacombat.util.WorldGuardCheck;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Iterator;

public class CombatTag implements Listener {

    private static HashMap<Player, Long> combatList = new HashMap<Player, Long>();

    public CombatTag() {
        tagTimer();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onArmorDamage(EntityDamageByEntityEvent e) {

        if (e.getEntity() instanceof LivingEntity) {
            if (e.getEntity() instanceof Player) {
                if (e.getDamager() instanceof Player) {

                    Player victim = (Player) e.getEntity();
                    Player attacker = (Player) e.getDamager();

                    //You can't tag or be tagged if you or your victim are, not allowed to pvp because of towny, is vanished, or in a region safezone, or allied/in same town
                    if (!CombatUtil.preventDamageCall(Towny.getPlugin(), victim, attacker)) {
                        if (!(isVanished(victim)) && !(isVanished(attacker))) {
                            if (WorldGuardCheck.isPvpEnabled(victim) && WorldGuardCheck.isPvpEnabled(attacker)) {
                                if(!(areFriendly(victim, attacker))) {

                                    tagPlayer(victim);
                                    tagPlayer(attacker);

                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public static void tagPlayer(Player player) {

        if (!(isCombatTagged(player))) {
            player.sendMessage("§eCombatTag: §4[ON]");
        }

        combatList.put(player, System.currentTimeMillis() / 1000 + 30); //Current time PLUS 30 seconds, divide used to convert to seconds.

    }

    public static void tagTimer() {
        new BukkitRunnable() {

            @Override
            public void run() { //every second

                if (combatList.isEmpty()) { //if empty dont waste time
                    return;
                }

                Iterator<Player> it = combatList.keySet().iterator();

                while (it.hasNext()) {
                    Player p = it.next().getPlayer();

                    if (combatList.get(p) == System.currentTimeMillis() / 1000) {
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);//You lost your tagPlayer
                        p.sendMessage("§eCombatTag: §9[OFF]");
                        it.remove();
                    }

                }
            }
        }.runTaskTimer(LordaniaCombat.getPlugin(LordaniaCombat.class), 0, 20);
    }

    public static HashMap<Player, Long> getCombatList() {
        return combatList;
    }

    public static boolean isCombatTagged(Player player) {
        return (combatList.containsKey(player));
    }

    public static boolean isVanished(Player p) {
        for (MetadataValue meta : p.getMetadata("vanished")) {
            if (meta.asBoolean()) return true;
        }
        return false;
    }

    private boolean areFriendly(Player player1, Player player2) {
        try {
            Resident resident1 = TownyAPI.getInstance().getDataSource().getResident(player1.getName());
            Resident resident2 = TownyAPI.getInstance().getDataSource().getResident(player2.getName());
            if(resident1.getTown() == resident2.getTown() || resident1.getTown().getNation() == resident2.getTown().getNation()) {
                return true;
            }
            else if(resident1.getTown().getNation().getAllies().contains(resident2.getTown().getNation())) {
                return true;
            }
        } catch (NotRegisteredException e) {
            return false;
        }
        return false;
    }
}

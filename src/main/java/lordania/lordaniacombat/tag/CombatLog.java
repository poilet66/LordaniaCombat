package lordania.lordaniacombat.tag;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Resident;
import lordania.lordaniacombat.util.LogListSave;
import lordania.lordaniacombat.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.nio.file.Path;

public class CombatLog implements Listener {

    @EventHandler
    public void onCombatLog(PlayerQuitEvent event) {

        if (!event.getQuitMessage().equals("Server closed")) {

            if(event.getPlayer() != null) {

                Player p = event.getPlayer();

                if (CombatTag.isCombatTagged(p)) {

                    LogListSave logList;
                    String uuid = String.valueOf(p.getUniqueId());

                    Path dataFilePath = Util.getDataFile("LordaniaCombat");
                    logList = Util.getLogList(dataFilePath);

                    logList.add(uuid);
                    Util.writeObjectToJson(dataFilePath, logList);

                    for (ItemStack itemStack : p.getInventory().getContents()) {
                        if (itemStack != null) {
                            p.getWorld().dropItemNaturally(p.getLocation(), itemStack);
                        }
                    }

                    p.getInventory().clear();
                }
            }
        }
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        String uuid = String.valueOf(player.getUniqueId());
        Path dataFilePath = Util.getDataFile("LordaniaCombat");
        LogListSave logList = Util.getLogList(dataFilePath);

        if (logList.isLogged(uuid)) {
            player.getInventory().clear();
            player.setHealth(0);

            player.sendMessage("You Combat Logged and were killed in battle");

        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getEntity();

        if (CombatTag.isCombatTagged(p)) {
            //death should remove your combat tag
            CombatTag.getCombatList().remove(p);
        }

        String uuid = String.valueOf(p.getUniqueId());
        Path dataFilePath = Util.getDataFile("LordaniaCombat");
        LogListSave logList = Util.getLogList(dataFilePath);

        if (logList.isLogged(uuid)) {
            e.setDeathMessage("");
            logList.remove(uuid);
            Util.writeObjectToJson(dataFilePath, logList);

            //Should it notify nearby players "Playername has combat logged"
        }
    }

}
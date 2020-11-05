package lordania.lordaniacombat.tag;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BannedCommands implements Listener {

    @EventHandler
    public void onJoin(PlayerCommandPreprocessEvent e) {

        String messageSearch = e.getMessage();
        Player p = e.getPlayer();

        if (CombatTag.isCombatTagged(p)) {
            if (messageSearch.startsWith("/t spawn") || messageSearch.startsWith("/n spawn") || messageSearch.startsWith("/warp") || messageSearch.startsWith("/mounts") || messageSearch.startsWith("/mount")) {
                p.sendMessage("§8Banned Command while §4Combat Tagged!");
                e.setCancelled(true);
            }
        }
    }
}

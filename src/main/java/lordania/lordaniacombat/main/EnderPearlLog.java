package lordania.lordaniacombat.main;

import net.coreprotect.CoreProtectAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EnderPearlLog implements Listener {

    CoreProtectAPI CoreProtect = LordaniaCombat.getPlugin(LordaniaCombat.class).getCoreProtect();

    @EventHandler
    public void onTeleportWithEnder(PlayerTeleportEvent e) {

        if (e.getCause().equals(PlayerTeleportEvent.TeleportCause.ENDER_PEARL)) {

            Player p = e.getPlayer();

            CoreProtect.logPlacement(p.getDisplayName(), e.getFrom(), Material.ENDER_PEARL, null);//Where they were from
            CoreProtect.logPlacement(p.getDisplayName(), e.getTo(), Material.END_PORTAL, null);//Where they went to

        }
    }
}
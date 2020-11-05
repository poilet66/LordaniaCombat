package lordania.lordaniacombat.main;

import org.bukkit.entity.Donkey;
import org.bukkit.entity.WanderingTrader;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;


public class MiscDisable implements Listener {

    @EventHandler
    public void onElytra(EntityToggleGlideEvent e) {
        e.setCancelled(true);
    }

    //This is invoked when a totem is about to be used
    @EventHandler
    public void onTotem(EntityResurrectEvent e) {
        e.setCancelled(true);
    }

    //Stops withers and pigmen
    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {

        if (e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.BUILD_WITHER) || e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NETHER_PORTAL)) {
            e.setCancelled(true);
        }

        if (e.getEntity() instanceof WanderingTrader) {
            e.setCancelled(true);
        } else if (e.getEntity() instanceof Donkey){
            e.setCancelled(true);
        }

    }
}

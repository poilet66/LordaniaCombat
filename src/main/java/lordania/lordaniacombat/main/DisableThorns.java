package lordania.lordaniacombat.main;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DisableThorns implements Listener {

    @EventHandler
    public void onThorns(EntityDamageEvent e) {

        if (e.getCause() == EntityDamageEvent.DamageCause.THORNS) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void noThorns(PrepareItemEnchantEvent e) {

        EnchantmentOffer[] eo = e.getOffers();
        for (int i = 0; i <= 2; i++) {
            if (eo[i].getEnchantment().equals(Enchantment.THORNS)) {
                eo[i].setEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL);
                eo[i].setCost(5);
            }
        }
    }
}

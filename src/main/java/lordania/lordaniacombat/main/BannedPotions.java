package lordania.lordaniacombat.main;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPotionEffectEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BannedPotions implements Listener {

    //Stops the adding of potion effects that are forbidden via drinking
    @EventHandler
    public void onPotionAddEvent(EntityPotionEffectEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (e.getAction().equals(EntityPotionEffectEvent.Action.ADDED)) {
                if (isIllegal(e.getNewEffect().getType())) {
                    p.sendMessage("§cRemoved prohibited effect.");
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBrew(BrewEvent e) {

        if (e.getContents().getIngredient().equals(new ItemStack(Material.BLAZE_POWDER))) {
            e.setCancelled(true);
        }

        if (e.getContents().getIngredient().equals(new ItemStack(Material.FERMENTED_SPIDER_EYE))) {

            for (ItemStack item : e.getContents()) {
                if (item.getType() != null) {
                    if (item.getType().equals(Material.POTION)) {

                        PotionMeta meta = (PotionMeta) item.getItemMeta();
                        if (meta.getBasePotionData().getType().equals(PotionEffectType.NIGHT_VISION)) {
                            e.setCancelled(true);
                        }

                        if (meta.getBasePotionData().getType().toString().equals("WATER")) {
                            e.setCancelled(true);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onSplash(PotionSplashEvent e) {

        for (PotionEffect POT : e.getPotion().getEffects()) {
            if (isIllegal(POT.getType())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        if (e.getCurrentItem() != null) {
            if (!e.getCurrentItem().getType().equals(Material.AIR)) {
                if (e.getCurrentItem().getType().equals(Material.POTION)) {

                    ItemStack item = e.getCurrentItem();
                    PotionMeta meta = (PotionMeta) item.getItemMeta();

                    if (isIllegal(meta.getBasePotionData().getType().getEffectType())) {
                        Player p = (Player) e.getWhoClicked();
                        p.getInventory().removeItem(e.getCurrentItem());

                    }
                }
            }
        }
    }

    public static boolean isIllegal(PotionEffectType POT) {

        if (POT.equals(PotionEffectType.INCREASE_DAMAGE) || POT.equals(PotionEffectType.INVISIBILITY) || POT.equals(PotionEffectType.WEAKNESS)) {
            return true;
        } else {
            return false;
        }
    }
}
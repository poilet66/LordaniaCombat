package lordania.lordaniacombat.main;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import lordania.lordaniacombat.tag.CombatLog;
import lordania.lordaniacombat.tag.CombatTag;
import lordania.lordaniacombat.tag.BannedCommands;
import lordania.lordaniacombat.tag.EnPearl;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import net.coreprotect.CoreProtect;

import java.io.IOException;
import java.nio.file.Files;

public final class LordaniaCombat extends JavaPlugin {

    private ProtocolManager protocolManager;

    public void onLoad() {
        protocolManager = ProtocolLibrary.getProtocolManager();
    }

    @Override
    public void onEnable() {

        if (!(CorProtestInstalled() && protocolInstalled())) {
            getLogger().severe("Disabled due to no CoreProtect or ProtocolLib dependency found.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (!getDataFolder().exists()) {
            try {
                Files.createDirectory(getDataFolder().toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        PluginManager evenreg = Bukkit.getServer().getPluginManager();

        evenreg.registerEvents(new CombatTag(), this);
        evenreg.registerEvents(new EnPearl(), this);
        evenreg.registerEvents(new CombatLog(), this);
        evenreg.registerEvents(new BannedCommands(), this);
        evenreg.registerEvents(new BannedPotions(), this);
        evenreg.registerEvents(new DisableThorns(), this);
        evenreg.registerEvents(new MiscDisable(), this);
        evenreg.registerEvents(new EnderPearlLog(), this);
        CombatTag.tagTimer();

        getLogger().info("LordaniaCombat is enabled");

    }

    private boolean CorProtestInstalled() {
        if (getServer().getPluginManager().getPlugin("CoreProtect") == null) {
            return false;
        } else {
            return true;
        }
    }

    private boolean protocolInstalled() {
        if (getServer().getPluginManager().getPlugin("ProtocolLib") == null) {
            return false;
        } else {
            return true;
        }
    }

    //Never used, do we need it?
    public CoreProtectAPI getCoreProtect() {

        Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

        // Check that CoreProtect is loaded
        if (plugin == null || !(plugin instanceof CoreProtect)) {
            return null;
        }

        // Check that the API is enabled
        CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
        if (CoreProtect.isEnabled() == false) {
            return null;
        }

        // Check that a compatible version of the API is loaded
        if (CoreProtect.APIVersion() < 6) {
            return null;
        }

        return CoreProtect;
    }

}

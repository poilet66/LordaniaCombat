package lordania.lordaniacombat.util;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Util {

    // Turn a json file into an Object
    public static Object readJsonToObject(Path path, Object objectType) {
        Object output = null;
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(path);
            output = gson.fromJson(reader, (Type) objectType);
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    // write an Object to a json file
    public static void writeObjectToJson(Path path, Object object) {
        Writer writer = null;
        try {
            writer = Files.newBufferedWriter(path);
            Gson gson = new Gson();
            gson.toJson(object, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Path getDataFile(String pluginName) {
        //Get plugin name
        JavaPlugin plug = (JavaPlugin) Bukkit.getPluginManager().getPlugin(pluginName);
        assert plug != null;
        //Get plugin Folder
        File pluginPath = plug.getDataFolder();
        //Get the file path
        File dataFile = new File(pluginPath + "/combatloggedplayers.json");
        Path dataFilePath = Paths.get(String.valueOf(dataFile));
        // If it doesn't exist then make it because they need one
        if (!dataFile.exists()) {
            try {
                Files.createFile(dataFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dataFilePath;
    }

    // This is for error mitigation and convenience when retrieving PlayerFav Objects
    public static LogListSave getLogList(Path dataFilePath) {
        LogListSave logList = (LogListSave) readJsonToObject(dataFilePath, LogListSave.class);
        if (logList == null) logList = new LogListSave();
        return logList;
    }

}

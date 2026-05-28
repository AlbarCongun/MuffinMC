package com.monia.muffinmc;

import com.google.common.base.Throwables;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@SuppressWarnings("unused")
public class MuffinConfig {
    private static final String HEADER = "This is the main configuration file for Muffin.\n"
            + "As you can see, there's tons to configure. Some options may impact gameplay, so use\n"
            + "with caution, and make sure you know what each option does before configuring.\n"
            + "\n"
            + "If you need help with the configuration or have any questions related to Muffin,\n"
            + "join us at Discord or check the documentation.\n"
            + "\n"
            + "Website: https://muffinmc.org\n";

    private static YamlConfiguration config;
    private static int version;

    static Map<String, Command> commands;

    public static void init(File dataFolder) {
        config = new YamlConfiguration();
        try {
            config.load(new File(dataFolder, "muffin.yml"));
        } catch (IOException ignore) {
        } catch (InvalidConfigurationException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not load muffin.yml, please correct your syntax errors", ex);
            throw new RuntimeException(ex);
        }
        config.options().copyDefaults(true).header(HEADER);

        commands = new HashMap<>();
        version = getInt("config-version", 1);
        set("config-version", 1);

        messages();
        commands();
        blocks();
        entities();
        network();
        gameplay();

        config.addDefault("config-version", 1);
        config.addDefault("messages.colored-colon", true);
        config.addDefault("messages.afk-broadcast", true);

        save();
    }

    protected static void save() {
        try {
            config.save(new File(new File("muffin.yml").getAbsolutePath()));
        } catch (IOException ex) {
            Bukkit.getLogger().log(Level.SEVERE, "Could not save muffin.yml", ex);
        }
    }

    private static void messages() {
    }

    private static void commands() {
    }

    private static void blocks() {
        getString("blocks.barrel.rows", "6");
        getString("blocks.ender_chest.rows", "3");
        getBoolean("blocks.anvil.allow-colors", false);
    }

    private static void entities() {
        getBoolean("entities.enderman.aggressive-towards-players", false);
        getDouble("entities.giant.max-health", 100.0);
    }

    private static void network() {
        getBoolean("network.upnp.enabled", false);
        getBoolean("network.chat-validation", true);
    }

    private static void gameplay() {
        getBoolean("gameplay.water-placement-end", false);
        getBoolean("gameplay.tps-catchup", true);
    }

    public static boolean getBoolean(String path, boolean def) {
        config.addDefault(path, def);
        return config.getBoolean(path, def);
    }

    public static String getString(String path, String def) {
        config.addDefault(path, def);
        return config.getString(path, def);
    }

    public static double getDouble(String path, double def) {
        config.addDefault(path, def);
        return config.getDouble(path, def);
    }

    public static int getInt(String path, int def) {
        config.addDefault(path, def);
        return config.getInt(path, def);
    }

    public static List<?> getList(String path, List<?> def) {
        config.addDefault(path, def);
        return config.getList(path, def);
    }

    public static Map<String, Object> getMap(String path, Map<String, Object> def) {
        config.addDefault(path, def);
        ConfigurationSection section = config.getConfigurationSection(path);
        if (section == null) {
            return def;
        }
        Map<String, Object> map = new HashMap<>();
        for (String key : section.getKeys(false)) {
            map.put(key, section.get(key));
        }
        return map;
    }

    public static void set(String path, Object value) {
        config.set(path, value);
    }

    static void readConfig(Class<?> clazz, Object instance) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isPrivate(method.getModifiers())) {
                if (method.getParameterCount() == 0 && method.getReturnType() == Void.TYPE) {
                    try {
                        method.setAccessible(true);
                        method.invoke(instance);
                    } catch (Exception ex) {
                        Bukkit.getLogger().log(Level.SEVERE, "Error invoking " + method.getName(), ex);
                        throw new RuntimeException(ex);
                    }
                }
            }
        }
        save();
    }
}

package com.monia.muffinmc;

import org.bukkit.Bukkit;

public class MuffinVersionFetcher {

    public static String getMuffinVersion() {
        return Bukkit.getVersion();
    }

    public static String getMinecraftVersion() {
        return Bukkit.getMinecraftVersion();
    }

    public static String getServerInfo() {
        return "Muffin " + getMuffinVersion() + " (MC: " + getMinecraftVersion() + ")";
    }
}

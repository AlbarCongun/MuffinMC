package com.monia.muffinmc;

/**
 * Muffin API - Main API class for Muffin-specific features
 */
public final class Muffin {

    private static Muffin instance;

    private Muffin() {
    }

    public static Muffin getInstance() {
        if (instance == null) {
            instance = new Muffin();
        }
        return instance;
    }

    /**
     * Get the Muffin version string
     *
     * @return version string
     */
    public String getVersion() {
        return "26.1.2";
    }

    /**
     * Get the Minecraft version this Muffin build targets
     *
     * @return minecraft version
     */
    public String getMinecraftVersion() {
        return "26.1.2";
    }
}

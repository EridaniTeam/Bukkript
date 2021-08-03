package org.kotlinmc.bukkript.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class LibraryLoader {
    public static void load(JavaPlugin plugin) throws Exception {
        plugin.getLogger().info("Loading libraries");
        File dir = plugin.getDataFolder();
        if (!dir.exists()) {
            dir.mkdir();
        }

        File libDar = new File(dir, "libs");
        if (!libDar.exists()) {
            libDar.mkdir();
        }

        File[] libs = libDar.listFiles();
        if (libs != null) {
            for (File file : libs) {
                loadLibrary(file, plugin);
            }
        }

    }

    private static void loadLibrary(File file, JavaPlugin plugin) throws Exception {
        Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
        method.setAccessible(true);
        method.invoke(plugin.getClass().getClassLoader(), new Object[]{file.toURI().toURL()});
    }
}

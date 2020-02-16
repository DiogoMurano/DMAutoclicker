package br.com.diogomurano.autoclicker;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;

public class Services {

    private static Main plugin;

    private Services(Main plugin) {
        Services.plugin = plugin;
    }

    public static void create(Main plugin) {
        new Services(plugin);
    }

    public static <T> T add(Class<T> clazz, T instance) {
        plugin.getServer().getServicesManager().register(clazz, instance, plugin, ServicePriority.Normal);
        return instance;
    }

    public static <T> T get(Class<T> clazz) {
        final RegisteredServiceProvider<T> registration = plugin.getServer().getServicesManager().getRegistration(clazz);
        if (registration == null) {
            return null;
        }
        return registration.getProvider();
    }
}

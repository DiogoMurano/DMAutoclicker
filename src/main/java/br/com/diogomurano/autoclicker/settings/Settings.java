package br.com.diogomurano.autoclicker.settings;

import br.com.diogomurano.autoclicker.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class Settings {

    private Main plugin;

    public static int REACH_DISTANCE;
    public static boolean ONLY_MOBS;

    private static Map<Material, Integer> DAMAGE_ITEMS;
    private static Map<Enchantment, Double> DAMAGE_ENCHANTMENTS;
    private static Map<PotionEffectType, Double> DAMAGE_POTIONS;

    public static String MESSAGE_NO_PERMISSION;
    public static String MESSAGE_ENABLE;
    public static String MESSAGE_DISABLE;

    public Settings(Main plugin) {
        this.plugin = plugin;

        REACH_DISTANCE = 0;
        ONLY_MOBS = false;
        DAMAGE_ITEMS = new HashMap<>();
        DAMAGE_ENCHANTMENTS = new HashMap<>();
        DAMAGE_POTIONS = new HashMap<>();
    }

    public void loadConfig() {
        final FileConfiguration config = plugin.getConfig();

        REACH_DISTANCE = config.getInt("Autoclick.Distancia");
        ONLY_MOBS = config.getBoolean("Autoclick.ApenasMobs");

        config.getConfigurationSection("Dano.Itens").getValues(true).forEach((s, o) -> {
            final Material material = Material.getMaterial(s);
            if (material != null) {
                DAMAGE_ITEMS.put(material, (Integer) o);
            } else {
                System.out.println("Item " + s + " não foi encontrado!");
            }
        });

        config.getConfigurationSection("Dano.Encantamentos").getValues(true).forEach((s, o) -> {
            final Enchantment enchantment = Enchantment.getByName(s);
            if (enchantment != null) {
                DAMAGE_ENCHANTMENTS.put(enchantment, (Double) o);
            } else {
                System.out.println("Encantamento " + s + " não foi encontrado!");
            }
        });

        config.getConfigurationSection("Dano.Pocoes").getValues(true).forEach((s, o) -> {
            final PotionEffectType potionEffectType = PotionEffectType.getByName(s);
            if (potionEffectType != null) {
                DAMAGE_POTIONS.put(potionEffectType, (Double) o);
            } else {
                System.out.println("Poção " + s + " não foi encontrada!");
            }
        });

        MESSAGE_NO_PERMISSION = config.getString("Mensagens.SemPermissao");
        MESSAGE_ENABLE = config.getString("Mensagens.Ativado");
        MESSAGE_DISABLE = config.getString("Mensagens.Desativado");
    }

    public static int defineDamage(Player player) {
        int damage = 0;

        final ItemStack itemInHand = player.getItemInHand();
        damage += DAMAGE_ITEMS.getOrDefault(itemInHand.getType(), 1);

        for (Enchantment enchantment : DAMAGE_ENCHANTMENTS.keySet()) {
            if (itemInHand.containsEnchantment(enchantment)) {
                damage += DAMAGE_ENCHANTMENTS.get(enchantment) * itemInHand.getEnchantmentLevel(enchantment);
            }
        }

        for (PotionEffect activePotionEffect : player.getActivePotionEffects()) {
            final Double effectDamage = DAMAGE_POTIONS.getOrDefault(activePotionEffect.getType(), 1.0);
            damage *= effectDamage * (effectDamage == 1 ? 1 : activePotionEffect.getAmplifier());
        }

        return damage;
    }

}

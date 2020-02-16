package br.com.diogomurano.autoclicker.handler;

import br.com.diogomurano.autoclicker.Services;
import br.com.diogomurano.autoclicker.api.AutoClickerPlayerEvent;
import br.com.diogomurano.autoclicker.services.ClickerPlayerService;
import br.com.diogomurano.autoclicker.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ClickerHandler extends BukkitRunnable {

    private ClickerPlayerService playerService;
    private final int REACH_DISTANCE;
    private final boolean ONLY_MOBS;

    public ClickerHandler() {
        this.playerService = Services.get(ClickerPlayerService.class);

        REACH_DISTANCE = Settings.REACH_DISTANCE;
        ONLY_MOBS = Settings.ONLY_MOBS;
    }

    @Override
    public void run() {
        playerService.getActivatePlayers().forEach(clickerPlayer -> {
            Player player = clickerPlayer.getPlayer();
            for (Entity nearbyEntity : player.getNearbyEntities(REACH_DISTANCE, REACH_DISTANCE, REACH_DISTANCE)) {
                if (nearbyEntity instanceof Damageable) {
                    if (ONLY_MOBS && nearbyEntity instanceof Player) {
                        return;
                    }
                    final int damage = Settings.defineDamage(player);

                    final AutoClickerPlayerEvent event = new AutoClickerPlayerEvent(player, nearbyEntity, damage);
                    Bukkit.getPluginManager().callEvent(event);

                    if (!event.isCancelled()) {
                        Damageable d = (Damageable) nearbyEntity;
                        d.damage(event.getDamage(), player);

//                        EntityPlayer player = ((CraftPlayer) bukkitEntity).getHandle();
//
//                        PlayerConnection connection = player.playerConnection;
//                        PacketPlayOutAnimation armSwing = new PacketPlayOutAnimation(player, 0); // '0' is the id for arm swing
//                        connection.sendPacket(armSwing); // required for their client to see it too
//                        connection.a(new PacketPlayInArmAnimation(EnumHand.MAIN_HAND));
                    }
                    break;
                }
            }
        });
    }
}

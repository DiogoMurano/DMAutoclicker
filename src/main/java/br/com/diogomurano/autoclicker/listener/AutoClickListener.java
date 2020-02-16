package br.com.diogomurano.autoclicker.listener;

import br.com.diogomurano.autoclicker.Services;
import br.com.diogomurano.autoclicker.models.ClickerPlayer;
import br.com.diogomurano.autoclicker.services.ClickerPlayerService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class AutoClickListener implements Listener {

    private ClickerPlayerService playerService;

    public AutoClickListener() {
        playerService = Services.get(ClickerPlayerService.class);
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        playerService.addPlayer(ClickerPlayer.builder()
                .player(event.getPlayer())
                .clickActivate(false)
                .build());
    }

    @EventHandler
    public void onPlayerQuitEvent(PlayerQuitEvent event) {
        playerService.removePlayer(playerService.getPlayer(event.getPlayer()));
    }

    @EventHandler
    public void onPlayerKickEvent(PlayerKickEvent event) {
        playerService.removePlayer(playerService.getPlayer(event.getPlayer()));
    }
}

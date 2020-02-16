package br.com.diogomurano.autoclicker.services;

import br.com.diogomurano.autoclicker.models.ClickerPlayer;
import com.google.common.collect.ImmutableList;
import org.bukkit.entity.Player;

public interface ClickerPlayerService {

    ImmutableList<ClickerPlayer> getPlayers();

    ImmutableList<ClickerPlayer> getActivatePlayers();

    void addPlayer(ClickerPlayer clickerPlayer);

    void removePlayer(ClickerPlayer clickerPlayer);

    ClickerPlayer getPlayer(Player player);

}

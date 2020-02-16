package br.com.diogomurano.autoclicker.services;

import br.com.diogomurano.autoclicker.models.ClickerPlayer;
import com.google.common.collect.ImmutableList;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClickerPlayerServiceImpl implements ClickerPlayerService {

    private Set<ClickerPlayer> players;

    public ClickerPlayerServiceImpl() {
        players = new HashSet<>();
    }

    public ImmutableList<ClickerPlayer> getPlayers() {
        return ImmutableList.copyOf(players);
    }

    @Override
    public ImmutableList<ClickerPlayer> getActivatePlayers() {
        return ImmutableList.copyOf(players.stream().filter(ClickerPlayer::isClickActivate).collect(Collectors.toSet()));
    }

    public void addPlayer(ClickerPlayer clickerPlayer) {
        players.add(clickerPlayer);
    }

    public void removePlayer(ClickerPlayer clickerPlayer) {
        players.remove(clickerPlayer);
    }

    public ClickerPlayer getPlayer(final Player player) {
        return players.stream().filter(filter -> filter.getPlayer().equals(player)).findFirst().orElse(null);
    }
}

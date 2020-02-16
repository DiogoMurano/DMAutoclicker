package br.com.diogomurano.autoclicker.models;

import lombok.*;
import org.bukkit.entity.Player;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClickerPlayer {

    private Player player;
    private boolean clickActivate;

    public ClickerPlayer(Player player) {
        this.player = player;
        this.clickActivate = false;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isClickActivate() {
        return clickActivate;
    }

    public void setClickActivate(boolean clickActivate) {
        this.clickActivate = clickActivate;
    }
}

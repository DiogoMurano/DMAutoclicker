package br.com.diogomurano.autoclicker.commands;

import br.com.diogomurano.autoclicker.Services;
import br.com.diogomurano.autoclicker.models.ClickerPlayer;
import br.com.diogomurano.autoclicker.services.ClickerPlayerService;
import br.com.diogomurano.autoclicker.settings.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AutoClickCommand implements CommandExecutor {

    private ClickerPlayerService clickerPlayerService;

    public AutoClickCommand() {
        clickerPlayerService = Services.get(ClickerPlayerService.class);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("autoclicker.use")) {
                player.sendMessage(Settings.MESSAGE_NO_PERMISSION);
                return false;
            }

            final ClickerPlayer clickerPlayer = clickerPlayerService.getPlayer(player);
            clickerPlayer.setClickActivate(!clickerPlayer.isClickActivate());

            player.sendMessage(clickerPlayer.isClickActivate() ? Settings.MESSAGE_ENABLE : Settings.MESSAGE_DISABLE);
            return false;
        }
        sender.sendMessage("§cCommand for players");
        return false;
    }
}

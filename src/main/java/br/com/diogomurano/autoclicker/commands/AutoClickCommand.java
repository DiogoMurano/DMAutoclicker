package br.com.diogomurano.autoclicker.commands;

import br.com.diogomurano.autoclicker.Services;
import br.com.diogomurano.autoclicker.models.ClickerPlayer;
import br.com.diogomurano.autoclicker.services.ClickerPlayerService;
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
                player.sendMessage("§cVocê não possui permissão para utilizar esse comando.");
                return false;
            }

            final ClickerPlayer clickerPlayer = clickerPlayerService.getPlayer(player);
            clickerPlayer.setClickActivate(!clickerPlayer.isClickActivate());

            player.sendMessage(clickerPlayer.isClickActivate() ? "§aVocê ativou o autoclick" : "§cVocê desativou o autoclick");
            return false;
        }
        sender.sendMessage("§cCommand for players");
        return false;
    }
}

package br.com.diogomurano.autoclicker;

import br.com.diogomurano.autoclicker.commands.AutoClickCommand;
import br.com.diogomurano.autoclicker.handler.ClickerHandler;
import br.com.diogomurano.autoclicker.listener.AutoClickListener;
import br.com.diogomurano.autoclicker.models.ClickerPlayer;
import br.com.diogomurano.autoclicker.services.ClickerPlayerService;
import br.com.diogomurano.autoclicker.services.ClickerPlayerServiceImpl;
import br.com.diogomurano.autoclicker.settings.Settings;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        Services.create(this);
        ClickerPlayerServiceImpl playerService = new ClickerPlayerServiceImpl();
        Services.add(ClickerPlayerService.class, playerService);

        new Settings(this).loadConfig();

        new ClickerHandler().runTaskTimer(this, 1L, 3L);

        getCommand("autoclick").setExecutor(new AutoClickCommand());
        getServer().getPluginManager().registerEvents(new AutoClickListener(), this);

        Bukkit.getOnlinePlayers().forEach(o -> {
            playerService.addPlayer(new ClickerPlayer(o, false));
        });
    }
}

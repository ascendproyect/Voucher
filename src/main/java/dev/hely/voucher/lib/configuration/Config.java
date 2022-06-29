package dev.hely.voucher.lib.configuration;

/**
 * @author Leandro Figueroa (LeandroSSJ)
 * domingo, marzo 28, 2021
 */

import lombok.Getter;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

import static dev.hely.voucher.lib.Assert.assertNotNull;

@Getter
public class Config {
    private final File file;
    private YamlConfiguration configuration;

    public Config(JavaPlugin plugin, String name) throws IOException, InvalidConfigurationException {
        assertNotNull(plugin, name);

        file = new File(plugin.getDataFolder(), name );
        if (!file.exists()) {
            plugin.saveResource(name, false);
        }

        this.configuration = YamlConfiguration.loadConfiguration(this.getFile());
    }

    public void save() {
        try {
            this.configuration.save(this.file);
            this.reload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        this.configuration = YamlConfiguration.loadConfiguration(this.getFile());
    }

    public File getFile() {
        return this.file;
    }


    public YamlConfiguration getConfig() {
        return this.configuration;
    }

}

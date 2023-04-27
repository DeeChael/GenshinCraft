package net.deechael.genshincraft.util.storage.yaml;

import net.deechael.genshincraft.util.storage.DataHolder;
import net.deechael.genshincraft.util.storage.annotation.yaml.YamlConfig;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class YamlConfigLoader {

    private final File baseDirectory;

    public YamlConfigLoader(File baseDirectory) {
        this.baseDirectory = baseDirectory;
        if (!baseDirectory.exists())
            this.baseDirectory.mkdirs();
    }

    public YamlConfigLoader(Plugin plugin) {
        this(plugin.getDataFolder());
    }

    public <T extends DataHolder> T load(Class<T> clazz) {
        YamlConfig yamlConfig = clazz.getAnnotation(YamlConfig.class);
        if (yamlConfig == null)
            throw new RuntimeException("Not a yaml config class");
        if (yamlConfig.file().isEmpty())
            throw new RuntimeException("File not found");
        return this.load(clazz, new File(this.baseDirectory, yamlConfig.file()));
    }

    public <T extends DataHolder> T load(Class<T> clazz, String file) {
        YamlConfig yamlConfig = clazz.getAnnotation(YamlConfig.class);
        if (yamlConfig == null)
            throw new RuntimeException("Not a yaml config class");
        return this.load(clazz, new File(this.baseDirectory, file));
    }

    public <T extends DataHolder> T load(Class<T> clazz, File file) {
        if (!DataHolder.class.isAssignableFrom(clazz))
            throw new RuntimeException("Not a data holder");
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        return YamlUtils.parseResult(clazz, configuration);
    }

    public void save(Object object) {
        Class<?> clazz = object.getClass();
        YamlConfig yamlConfig = clazz.getAnnotation(YamlConfig.class);
        if (yamlConfig == null)
            throw new RuntimeException("Not a yaml config class");
        if (yamlConfig.file().isEmpty())
            throw new RuntimeException("File not found");
        this.save(object, new File(this.baseDirectory, yamlConfig.file()));
    }

    public void save(Object object, String file) {
        Class<?> clazz = object.getClass();
        YamlConfig yamlConfig = clazz.getAnnotation(YamlConfig.class);
        if (yamlConfig == null)
            throw new RuntimeException("Not a yaml config class");
        this.save(object, new File(this.baseDirectory, file));
    }

    public void save(Object object, File file) {
        if (!(object instanceof DataHolder))
            throw new RuntimeException("Not a data holder");
        YamlConfiguration configuration = YamlUtils.serialize(object);
        try {
            configuration.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

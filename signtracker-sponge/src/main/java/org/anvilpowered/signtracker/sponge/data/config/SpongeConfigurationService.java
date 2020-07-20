package org.anvilpowered.signtracker.sponge.data.config;

import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.anvilpowered.signtracker.common.data.config.CommonConfigurationService;
import org.spongepowered.api.config.DefaultConfig;

public class SpongeConfigurationService extends CommonConfigurationService {

    @Inject
    public SpongeConfigurationService(
        @DefaultConfig(sharedRoot = false)
            ConfigurationLoader<CommentedConfigurationNode> configLoader) {
        super(configLoader);
    }
}

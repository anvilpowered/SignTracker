package org.anvilpowered.signtracker.sponge.registry;

import com.google.inject.Inject;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.anvilpowered.signtracker.common.registry.CommonConfigurationService;
import org.spongepowered.api.config.DefaultConfig;

public class SpongeConfigurationService extends CommonConfigurationService {

    @Inject
    public SpongeConfigurationService(
        @DefaultConfig(sharedRoot = false)
            ConfigurationLoader<CommentedConfigurationNode> configLoader) {
        super(configLoader);
    }
}

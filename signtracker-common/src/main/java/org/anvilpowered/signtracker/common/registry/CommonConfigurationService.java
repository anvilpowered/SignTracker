package org.anvilpowered.signtracker.common.registry;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.anvilpowered.anvil.api.registry.Keys;
import org.anvilpowered.anvil.base.registry.BaseConfigurationService;
import org.anvilpowered.signtracker.api.registry.SignTrackerKeys;
import org.anvilpowered.signtracker.common.plugin.SignTrackerPluginInfo;

@Singleton
public class CommonConfigurationService extends BaseConfigurationService {

    @Inject
    public CommonConfigurationService(
        ConfigurationLoader<CommentedConfigurationNode> configLoader) {
        super(configLoader);
        withDefault();
        set(Keys.BASE_SCAN_PACKAGE, "org.anvilpowered.signtracker.common.model");
        setDefault(Keys.DATA_DIRECTORY, SignTrackerPluginInfo.id);
        setDefault(Keys.MONGODB_DBNAME, SignTrackerPluginInfo.id);
        setName(SignTrackerKeys.UPDATE_ON_JOIN, "update.onJoin");
        setName(SignTrackerKeys.UPDATE_TASK_INTERVAL_SECONDS, "update.intervalSeconds");
        setDescription(SignTrackerKeys.UPDATE_ON_JOIN, "\nWhether to update signs for a player on join.");
        setDescription(SignTrackerKeys.UPDATE_TASK_INTERVAL_SECONDS, "\nInterval for update task in seconds. Set to -1 to disable.");
    }
}

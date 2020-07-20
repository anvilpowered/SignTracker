package org.anvilpowered.signtracker.api.plugin;

import org.jetbrains.annotations.Nullable;

public interface PluginMessages<TCommandSource> {

    void sendFailedUpload(TCommandSource source, int x, int y, int z, @Nullable String userName);

    void getSuccessfulUpdate(TCommandSource source, int x, int y, int z, String status, String userName);
}

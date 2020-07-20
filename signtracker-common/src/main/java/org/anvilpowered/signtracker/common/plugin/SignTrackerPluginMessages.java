package org.anvilpowered.signtracker.common.plugin;

import com.google.inject.Inject;
import org.anvilpowered.anvil.api.plugin.PluginInfo;
import org.anvilpowered.anvil.api.util.TextService;
import org.anvilpowered.signtracker.api.plugin.PluginMessages;
import org.jetbrains.annotations.Nullable;

public class SignTrackerPluginMessages<TString, TCommandSource> implements PluginMessages<TCommandSource> {

    @Inject
    private PluginInfo<TString> pluginInfo;

    @Inject
    private TextService<TString, TCommandSource> textService;

    @Override
    public void sendFailedUpload(TCommandSource source, int x, int y, int z, @Nullable String userName) {
        TextService.Builder<TString, TCommandSource> builder = textService.builder()
            .append(pluginInfo.getPrefix())
            .red().append("Failed to register sign at ")
            .gold().append(x, ", ", y, ", ", z);
        if (userName != null) {
            builder
                .red().append(" for ")
                .gold().append(userName);
        }
        builder.sendTo(source);
    }

    @Override
    public void getSuccessfulUpdate(TCommandSource source, int x, int y, int z, String status, String userName) {
        textService.builder()
            .append(pluginInfo.getPrefix())
            .green().append("Successfully ", status, " sign at ")
            .gold().append(x, ", ", y, ", ", z)
            .green().append(" for ")
            .gold().append(userName)
            .sendTo(source);
    }
}

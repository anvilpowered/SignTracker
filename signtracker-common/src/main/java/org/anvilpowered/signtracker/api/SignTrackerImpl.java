package org.anvilpowered.signtracker.api;

import com.google.common.reflect.TypeToken;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.anvilpowered.anvil.api.Environment;
import org.anvilpowered.signtracker.common.plugin.SignTrackerPluginInfo;
import org.anvilpowered.signtracker.common.task.SignUpdateTask;

@SuppressWarnings("UnstableApiUsage")
public class SignTrackerImpl<TPlayer> extends SignTracker {

    public SignTrackerImpl(Injector injector, Module module) {
        super(SignTrackerPluginInfo.id, injector, module);
    }

    @Override
    protected void applyToBuilder(Environment.Builder builder) {
        super.applyToBuilder(builder);
        builder.withRootCommand();
        builder.addEarlyServices(new TypeToken<SignUpdateTask<TPlayer>>(getClass()) {
        });
    }
}

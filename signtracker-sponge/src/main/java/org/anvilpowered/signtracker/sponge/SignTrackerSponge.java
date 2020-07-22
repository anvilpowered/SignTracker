package org.anvilpowered.signtracker.sponge;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.anvilpowered.anvil.api.Environment;
import org.anvilpowered.signtracker.api.SignTrackerImpl;
import org.anvilpowered.signtracker.common.plugin.SignTrackerPluginInfo;
import org.anvilpowered.signtracker.sponge.listener.SpongePlayerListener;
import org.anvilpowered.signtracker.sponge.listener.SpongeSignListener;
import org.anvilpowered.signtracker.sponge.module.SpongeModule;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.plugin.Dependency;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
    id = SignTrackerPluginInfo.id,
    name = SignTrackerPluginInfo.name,
    version = SignTrackerPluginInfo.version,
    dependencies = @Dependency(
        id = "anvil",
        version = "0.2"
    ),
    description = SignTrackerPluginInfo.description,
    url = SignTrackerPluginInfo.url,
    authors = "Cableguy20"
)
public class SignTrackerSponge extends SignTrackerImpl<Player> {

    @Inject
    public SignTrackerSponge(Injector injector) {
        super(injector, new SpongeModule());
    }

    @Override
    protected void applyToBuilder(Environment.Builder builder) {
        super.applyToBuilder(builder);
        builder.addEarlyServices(SpongePlayerListener.class, t ->
            Sponge.getEventManager().registerListeners(this, t));
        builder.addEarlyServices(SpongeSignListener.class, t ->
            Sponge.getEventManager().registerListeners(this, t));
    }
}

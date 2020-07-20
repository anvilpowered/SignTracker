package org.anvilpowered.signtracker.sponge.listener;

import com.google.inject.Inject;
import org.anvilpowered.anvil.api.Anvil;
import org.anvilpowered.anvil.api.core.coremember.CoreMemberManager;
import org.anvilpowered.anvil.api.core.model.coremember.CoreMember;
import org.anvilpowered.signtracker.api.sign.SignManager;
import org.anvilpowered.signtracker.sponge.sign.SpongeSignUpdateService;
import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class SpongePlayerListener {

    private final CoreMemberManager coreMemberManager = Anvil.getCoreMemberManager();

    @Inject
    private Logger logger;

    @Inject
    private PluginContainer container;

    @Inject
    private SignManager<Player> signManager;

    @Inject
    private SpongeSignUpdateService signUpdateService;

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event) {
        Task.builder().execute(() -> {
            final UUID userUUID = event.getTargetEntity().getUniqueId();
            coreMemberManager.getPrimaryComponent().getOneForUser(userUUID).thenAcceptAsync(oc -> {
                if (!oc.isPresent()) {
                    return;
                }
                final CoreMember<?> coreMember = oc.get();
                signManager.getPrimaryComponent().getAll(userUUID).thenAcceptAsync(signs -> {
                    logger.info("Updating {} signs for {}!", signs.size(), coreMember.getUserName());
                    signs.forEach(sign -> signUpdateService.updateSync(sign, coreMember));
                });
            });
        }).async().delay(10, TimeUnit.SECONDS).submit(container);
    }
}

package org.anvilpowered.signtracker.common.task;

import com.google.inject.Inject;
import org.anvilpowered.anvil.api.Anvil;
import org.anvilpowered.anvil.api.coremember.CoreMemberManager;
import org.anvilpowered.anvil.api.model.coremember.CoreMember;
import org.anvilpowered.anvil.api.registry.Registry;
import org.anvilpowered.signtracker.api.model.sign.Sign;
import org.anvilpowered.signtracker.api.sign.SignManager;
import org.anvilpowered.signtracker.api.sign.SignUpdateService;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class SignUpdateTask<TPlayer> {

    private final CoreMemberManager coreMemberManager = Anvil.getCoreMemberManager();

    @Inject
    private Logger logger;

    @Inject
    private SignManager<TPlayer> signManager;

    @Inject
    protected SignUpdateService signUpdateService;

    protected final Registry registry;

    protected final Runnable getTask;
    protected BiConsumer<List<? extends Sign<?>>, List<? extends CoreMember<?>>> updateTask;

    protected SignUpdateTask(Registry registry) {
        this.registry = registry;
        registry.whenLoaded(() -> {
            stopTask();
            startTask();
        }).register();
        getTask = () -> signManager.getPrimaryComponent().getAll().thenAcceptAsync(signs -> {
            List<CoreMember<?>> coreMembers = new ArrayList<>();
            signs.forEach(sign -> coreMemberManager.getPrimaryComponent()
                .getOneForUser(sign.getTargetUserUUID())
                .thenAcceptAsync(optionalCoreMember -> {
                    if (optionalCoreMember.isPresent()) {
                        coreMembers.add(optionalCoreMember.get());
                    } else {
                        logger.error("Could not find CoreMember for " + sign.getTargetUserUUID());
                    }
                })
            );
            logger.info("Updating {} signs!", coreMembers.size());
            updateTask.accept(signs, coreMembers);
        });
    }

    protected abstract void startTask();

    protected abstract void stopTask();
}

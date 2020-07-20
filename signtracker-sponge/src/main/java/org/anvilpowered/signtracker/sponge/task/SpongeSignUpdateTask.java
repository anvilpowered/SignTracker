package org.anvilpowered.signtracker.sponge.task;

import com.google.inject.Inject;
import org.anvilpowered.anvil.api.data.registry.Registry;
import org.anvilpowered.signtracker.api.data.key.SignTrackerKeys;
import org.anvilpowered.signtracker.common.task.SignUpdateTask;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;

import java.util.concurrent.TimeUnit;

public class SpongeSignUpdateTask extends SignUpdateTask<Player> {

    @Inject
    private PluginContainer container;

    @Nullable
    private Task task;

    @Inject
    public SpongeSignUpdateTask(Registry registry) {
        super(registry);
        updateTask = (signs, coreMembers) -> Task.builder().execute(() -> {
            for (int i = 0; i < coreMembers.size(); i++) {
                signUpdateService.update(signs.get(i), coreMembers.get(i));
            }
        }).submit(container);
    }

    @Override
    protected void startTask() {
        int interval = registry.getOrDefault(SignTrackerKeys.UPDATE_TASK_INTERVAL_SECONDS);
        if (interval < 0) {
            return;
        }
        task = Task.builder()
            .async()
            .interval(interval, TimeUnit.SECONDS)
            .execute(getTask)
            .submit(container);
    }

    @Override
    protected void stopTask() {
        if (task != null) {
            task.cancel();
        }
    }
}

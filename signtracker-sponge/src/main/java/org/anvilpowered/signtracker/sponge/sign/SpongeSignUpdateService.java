package org.anvilpowered.signtracker.sponge.sign;

import com.google.inject.Inject;
import org.anvilpowered.anvil.api.core.model.coremember.CoreMember;
import org.anvilpowered.anvil.api.util.TimeFormatService;
import org.anvilpowered.signtracker.api.model.sign.Sign;
import org.anvilpowered.signtracker.api.sign.SignUpdateService;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.TileEntityTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SpongeSignUpdateService implements SignUpdateService {

    @Inject
    private TimeFormatService timeFormatService;

    @Inject
    private PluginContainer container;

    @Override
    public void update(Sign<?> sign, CoreMember<?> coreMember) {
        Sponge.getServer().getWorld(sign.getWorldUUID()).ifPresent(world -> {
            Optional<TileEntity> optionalTileEntity = world.getTileEntity(sign.getX(), sign.getY(), sign.getZ());
            if (!optionalTileEntity.isPresent()) {
                return;
            }
            TileEntity tileEntity = optionalTileEntity.get();
            if (!tileEntity.getType().equals(TileEntityTypes.SIGN)) {
                return;
            }
            tileEntity.offer(Keys.SIGN_LINES, sign.getLines().stream().map(text -> {
                if (coreMember.getUserName() != null) {
                    text = text.replace("%name%", coreMember.getUserName());
                }
                if (coreMember.getNickName() != null) {
                    text = text.replace("%nick%", coreMember.getNickName());
                }
                if (coreMember.getCreatedUtc() != null) {
                    text = text.replace("%first_seen%",
                        timeFormatService.format(coreMember.getCreatedUtc()).maxCharacters(15).toString());
                }
                if (coreMember.getLastJoinedUtc() != null) {
                    text = text.replace("%last_seen%",
                        timeFormatService.format(coreMember.getLastJoinedUtc()).maxCharacters(15).toString());
                }
                return text;
            }).map(Text::of).collect(Collectors.toList()));
        });
    }

    /**
     * Schedules a sign update on the main thread
     */
    @Override
    public CompletableFuture<Boolean> updateSync(Sign<?> sign, CoreMember<?> coreMember) {
        CompletableFuture<Boolean> future = new CompletableFuture<>();
        Task.builder().execute(() -> {
            update(sign, coreMember);
            future.complete(true);
        }).submit(container);
        return future;
    }
}

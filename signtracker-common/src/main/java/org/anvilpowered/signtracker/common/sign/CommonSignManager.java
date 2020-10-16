package org.anvilpowered.signtracker.common.sign;

import com.google.inject.Inject;
import org.anvilpowered.anvil.api.Anvil;
import org.anvilpowered.anvil.api.coremember.CoreMemberManager;
import org.anvilpowered.anvil.api.model.coremember.CoreMember;
import org.anvilpowered.anvil.api.plugin.PluginInfo;
import org.anvilpowered.anvil.api.registry.Registry;
import org.anvilpowered.anvil.api.util.PermissionService;
import org.anvilpowered.anvil.api.util.TextService;
import org.anvilpowered.anvil.api.util.UserService;
import org.anvilpowered.anvil.base.datastore.BaseManager;
import org.anvilpowered.signtracker.api.model.sign.Sign;
import org.anvilpowered.signtracker.api.plugin.PluginMessages;
import org.anvilpowered.signtracker.api.registry.SignTrackerKeys;
import org.anvilpowered.signtracker.api.sign.SignManager;
import org.anvilpowered.signtracker.api.sign.SignRepository;
import org.anvilpowered.signtracker.api.sign.SignUpdateService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unchecked")
public class CommonSignManager<TUser, TPlayer, TString, TCommandSource>
    extends BaseManager<SignRepository<?, ?>>
    implements SignManager<TPlayer> {

    private final CoreMemberManager coreMemberManager = Anvil.getCoreMemberManager();

    @Inject
    private PermissionService permissionService;

    @Inject
    private PluginInfo<TString> pluginInfo;

    @Inject
    private PluginMessages<TCommandSource> pluginMessages;

    @Inject
    private SignUpdateService signUpdateService;

    @Inject
    private TextService<TString, TCommandSource> textService;

    @Inject
    private UserService<TUser, TPlayer> userService;

    @Inject
    public CommonSignManager(Registry registry) {
        super(registry);
    }

    @Override
    public CompletableFuture<Void> deleteOne(
        TPlayer player,
        UUID worldUUID,
        int x,
        int y,
        int z
    ) {
        return getPrimaryComponent().deleteOne(worldUUID, x, y, z).thenAcceptAsync(result -> {
            if (result) {
                textService.builder()
                    .append(pluginInfo.getPrefix())
                    .green().append("Successfully removed sign at ")
                    .gold().append(x, ", ", y, ", ", z)
                    .sendTo((TCommandSource) player);
            }
            // don't send a message if unsuccessful
        });
    }

    @Override
    public CompletableFuture<Void> register(
        TPlayer player,
        UUID ownerUUID,
        UUID targetUserUUID,
        UUID worldUUID,
        int x,
        int y,
        int z,
        List<String> lines
    ) {
        TCommandSource source = (TCommandSource) player;
        return coreMemberManager.getPrimaryComponent().getOneForUser(targetUserUUID).thenAcceptAsync(oc -> {
            if (!oc.isPresent()) {
                pluginMessages.sendFailedUpload(source, x, y, z, null);
                return;
            }
            CoreMember<?> coreMember = oc.get();
            getPrimaryComponent().getOne(worldUUID, x, y, z).thenAcceptAsync(optionalSign -> {
                final Sign<?> sign;
                final String status;
                if (optionalSign.isPresent()) {
                    sign = optionalSign.get();
                    if (!ownerUUID.equals(sign.getOwnerUUID())
                        && !permissionService.hasPermission(player,
                        registry.getOrDefault(SignTrackerKeys.ADMIN_EDIT_PERMISSION))
                    ) {
                        textService.builder()
                            .append(pluginInfo.getPrefix())
                            .red().append("You do not have permission to edit this sign!")
                            .sendTo(source);
                        return;
                    }
                    if (targetUserUUID.equals(sign.getTargetUserUUID())) {
                        textService.builder()
                            .append(pluginInfo.getPrefix())
                            .gold().append(coreMember.getUserName())
                            .red().append(" is already the target for this sign!")
                            .sendTo(source);
                        return;
                    }
                    if (!getPrimaryComponent().updateTargetUserUUID(sign.getId(), targetUserUUID).join()) {
                        pluginMessages.sendFailedUpload(source, x, y, z, coreMember.getUserName());
                        return;
                    }
                    status = "updated existing";
                } else {
                    Optional<? extends Sign<?>> optionalNewSign = getPrimaryComponent()
                        .register(ownerUUID, targetUserUUID, worldUUID, x, y, z, lines)
                        .join();
                    if (!optionalNewSign.isPresent()) {
                        pluginMessages.sendFailedUpload(source, x, y, z, coreMember.getUserName());
                        return;
                    }
                    sign = optionalNewSign.get();
                    status = "registered new";
                }
                if (!signUpdateService.updateSync(sign, coreMember).join()) {
                    pluginMessages.sendFailedUpload(source, x, y, z, coreMember.getUserName());
                    return;
                }
                pluginMessages.getSuccessfulUpdate(source, x, y, z, status, coreMember.getUserName());
            }).join();
        });
    }
}

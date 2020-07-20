package org.anvilpowered.signtracker.sponge.module;

import com.google.inject.TypeLiteral;
import org.anvilpowered.anvil.api.command.CommandNode;
import org.anvilpowered.signtracker.api.sign.SignUpdateService;
import org.anvilpowered.signtracker.common.data.config.CommonConfigurationService;
import org.anvilpowered.signtracker.common.module.CommonModule;
import org.anvilpowered.signtracker.common.task.SignUpdateTask;
import org.anvilpowered.signtracker.sponge.command.SpongeSignTrackerCommandNode;
import org.anvilpowered.signtracker.sponge.data.config.SpongeConfigurationService;
import org.anvilpowered.signtracker.sponge.sign.SpongeSignUpdateService;
import org.anvilpowered.signtracker.sponge.task.SpongeSignUpdateTask;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;

public class SpongeModule extends CommonModule<User, Player, Text, CommandSource> {

    @Override
    protected void configure() {
        super.configure();
        bind(new TypeLiteral<CommandNode<CommandSource>>() {
        }).to(SpongeSignTrackerCommandNode.class);
        bind(CommonConfigurationService.class).to(SpongeConfigurationService.class);
        bind(new TypeLiteral<SignUpdateTask<Player>>() {
        }).to(SpongeSignUpdateTask.class);
        bind(SignUpdateService.class).to(SpongeSignUpdateService.class);
    }
}

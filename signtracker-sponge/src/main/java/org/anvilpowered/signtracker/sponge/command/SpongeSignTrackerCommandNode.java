package org.anvilpowered.signtracker.sponge.command;

import com.google.inject.Inject;
import org.anvilpowered.anvil.api.registry.Registry;
import org.anvilpowered.signtracker.common.command.CommonSignTrackerCommandNode;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpongeSignTrackerCommandNode
    extends CommonSignTrackerCommandNode<CommandExecutor, CommandSource> {

    @Inject
    private SpongeRegisterCommand registerCommand;

    @Inject
    public SpongeSignTrackerCommandNode(Registry registry) {
        super(registry);
    }

    @Override
    protected void loadCommands() {

        Map<List<String>, CommandSpec> subCommands = new HashMap<>();

        subCommands.put(REGISTER_ALIAS, CommandSpec.builder()
            .description(Text.of(REGISTER_DESCRIPTION))
            .arguments(GenericArguments.onlyOne(GenericArguments.user(Text.of("user"))))
            .executor(registerCommand)
            .build());

        subCommands.put(HELP_ALIAS, CommandSpec.builder()
            .description(Text.of(HELP_DESCRIPTION))
            .executor(commandService.generateHelpCommand(this))
            .build());

        subCommands.put(VERSION_ALIAS, CommandSpec.builder()
            .description(Text.of(VERSION_DESCRIPTION))
            .executor(commandService.generateVersionCommand(HELP_COMMAND))
            .build());

        CommandSpec root = CommandSpec.builder()
            .description(Text.of(ROOT_DESCRIPTION))
            .executor(commandService.generateRootCommand(HELP_COMMAND))
            .children(subCommands)
            .build();

        Sponge.getCommandManager()
            .register(environment.getPlugin(), root, "st", "sign", "signtracker");
    }
}

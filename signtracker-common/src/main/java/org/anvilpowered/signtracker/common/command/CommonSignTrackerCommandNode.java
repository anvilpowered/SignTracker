package org.anvilpowered.signtracker.common.command;

import com.google.inject.Inject;
import org.anvilpowered.anvil.api.Environment;
import org.anvilpowered.anvil.api.command.CommandNode;
import org.anvilpowered.anvil.api.command.CommandService;
import org.anvilpowered.anvil.api.data.registry.Registry;
import org.anvilpowered.signtracker.common.plugin.SignTrackerPluginInfo;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public abstract class CommonSignTrackerCommandNode<TCommandExecutor, TCommandSource>
    implements CommandNode<TCommandSource> {

    protected static final List<String> REGISTER_ALIAS = Arrays.asList("register", "r");
    protected static final List<String> HELP_ALIAS = Collections.singletonList("help");
    protected static final List<String> VERSION_ALIAS = Collections.singletonList("version");

    protected static final String REGISTER_DESCRIPTION = "Register a sign to a player (must be looking at it).";
    protected static final String HELP_DESCRIPTION = "Shows this help page.";
    protected static final String VERSION_DESCRIPTION = "Shows plugin version.";
    protected static final String ROOT_DESCRIPTION
        = String.format("%s root command", SignTrackerPluginInfo.name);

    protected static final String REGISTER_USAGE = "<user>";

    protected static final String HELP_COMMAND = "/st help";

    private boolean alreadyLoaded;
    protected Map<List<String>, Function<TCommandSource, String>> descriptions;
    protected Map<List<String>, Predicate<TCommandSource>> permissions;
    protected Map<List<String>, Function<TCommandSource, String>> usages;

    @Inject
    protected CommandService<TCommandExecutor, TCommandSource> commandService;

    @Inject
    protected Environment environment;

    protected Registry registry;

    protected CommonSignTrackerCommandNode(Registry registry) {
        this.registry = registry;
        registry.whenLoaded(() -> {
            if (alreadyLoaded) return;
            loadCommands();
            alreadyLoaded = true;
        }).register();
        alreadyLoaded = false;
        descriptions = new HashMap<>();
        permissions = new HashMap<>();
        usages = new HashMap<>();
        descriptions.put(REGISTER_ALIAS, c -> REGISTER_DESCRIPTION);
        descriptions.put(HELP_ALIAS, c -> HELP_DESCRIPTION);
        descriptions.put(VERSION_ALIAS, c -> VERSION_DESCRIPTION);
        usages.put(REGISTER_ALIAS, c -> REGISTER_USAGE);
    }

    protected abstract void loadCommands();

    private static final String ERROR_MESSAGE = "Sync command has not been loaded yet";

    @Override
    public Map<List<String>, Function<TCommandSource, String>> getDescriptions() {
        return Objects.requireNonNull(descriptions, ERROR_MESSAGE);
    }

    @Override
    public Map<List<String>, Predicate<TCommandSource>> getPermissions() {
        return Objects.requireNonNull(permissions, ERROR_MESSAGE);
    }

    @Override
    public Map<List<String>, Function<TCommandSource, String>> getUsages() {
        return Objects.requireNonNull(usages, ERROR_MESSAGE);
    }

    @Override
    public String getName() {
        return SignTrackerPluginInfo.id;
    }
}

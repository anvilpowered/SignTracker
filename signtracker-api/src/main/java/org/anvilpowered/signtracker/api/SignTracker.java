package org.anvilpowered.signtracker.api;

import com.google.common.base.Preconditions;
import com.google.inject.Injector;
import com.google.inject.Module;
import org.anvilpowered.anvil.api.Environment;
import org.anvilpowered.anvil.api.data.registry.Registry;
import org.anvilpowered.anvil.base.plugin.BasePlugin;

public class SignTracker extends BasePlugin {

    protected static Environment environment;
    private static final String NOT_LOADED = "SignTracker has not been loaded yet!";

    SignTracker(String name, Injector injector, Module module) {
        super(name, injector, module);
    }

    public static Environment getEnvironment() {
        return Preconditions.checkNotNull(environment, NOT_LOADED);
    }

    public static Registry getRegistry() {
        return getEnvironment().getInjector().getInstance(Registry.class);
    }
}

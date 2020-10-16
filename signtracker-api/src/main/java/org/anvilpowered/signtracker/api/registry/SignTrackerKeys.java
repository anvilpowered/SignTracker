package org.anvilpowered.signtracker.api.registry;

import org.anvilpowered.anvil.api.registry.Key;
import org.anvilpowered.anvil.api.registry.Keys;
import org.anvilpowered.anvil.api.registry.TypeTokens;

public final class SignTrackerKeys {

    private SignTrackerKeys() {
        throw new AssertionError("**boss music** No instance for you!");
    }

    public static final Key<Boolean> UPDATE_ON_JOIN =
        Key.builder(TypeTokens.BOOLEAN)
            .name("UPDATE_ON_JOIN")
            .fallback(true)
            .build();
    public static final Key<Integer> UPDATE_TASK_INTERVAL_SECONDS =
        Key.builder(TypeTokens.INTEGER)
            .name("UPDATE_TASK_INTERVAL_SECONDS")
            .fallback(60)
            .build();
    public static final Key<String> ADMIN_EDIT_PERMISSION =
        Key.builder(TypeTokens.STRING)
            .name("ADMIN_EDIT_PERMISSION")
            .fallback("signtracker.admin.edit")
            .build();

    static {
        Keys.startRegistration("signtracker")
            .register(UPDATE_ON_JOIN)
            .register(UPDATE_TASK_INTERVAL_SECONDS)
            .register(ADMIN_EDIT_PERMISSION)
        ;
    }
}

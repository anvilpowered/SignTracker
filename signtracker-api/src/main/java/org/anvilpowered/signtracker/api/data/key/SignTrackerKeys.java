package org.anvilpowered.signtracker.api.data.key;

import org.anvilpowered.anvil.api.data.key.Key;
import org.anvilpowered.anvil.api.data.key.Keys;

public final class SignTrackerKeys {

    private SignTrackerKeys() {
        throw new AssertionError("**boss music** No instance for you!");
    }

    public static final Key<Boolean> UPDATE_ON_JOIN
        = new Key<Boolean>("UPDATE_ON_JOIN", true) {
    };
    public static final Key<Integer> UPDATE_TASK_INTERVAL_SECONDS
        = new Key<Integer>("UPDATE_TASK_INTERVAL_SECONDS", 60) {
    };
    public static final Key<String> ADMIN_EDIT_PERMISSION
        = new Key<String>("ADMIN_EDIT_PERMISSION", "signtracker.admin.edit") {
    };

    static {
        Keys.startRegistration("signtracker")
            .register(UPDATE_ON_JOIN)
            .register(UPDATE_TASK_INTERVAL_SECONDS)
            .register(ADMIN_EDIT_PERMISSION)
        ;
    }
}

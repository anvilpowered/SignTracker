package org.anvilpowered.signtracker.common.data.registry;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.anvilpowered.anvil.api.data.key.Keys;
import org.anvilpowered.anvil.base.data.registry.BaseExtendedRegistry;

@Singleton
public class CommonRegistry extends BaseExtendedRegistry {

    @Inject
    public CommonRegistry() {
        set(Keys.BASE_SCAN_PACKAGE, "org.anvilpowered.signtracker.common.model");
    }
}

package org.anvilpowered.signtracker.common.module;

import com.google.common.reflect.TypeToken;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import jetbrains.exodus.entitystore.EntityId;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import org.anvilpowered.anvil.api.Anvil;
import org.anvilpowered.anvil.api.misc.BindingExtensions;
import org.anvilpowered.anvil.api.plugin.BasicPluginInfo;
import org.anvilpowered.anvil.api.plugin.PluginInfo;
import org.anvilpowered.anvil.api.registry.Registry;
import org.anvilpowered.signtracker.api.plugin.PluginMessages;
import org.anvilpowered.signtracker.api.sign.SignManager;
import org.anvilpowered.signtracker.api.sign.SignRepository;
import org.anvilpowered.signtracker.common.plugin.SignTrackerPluginInfo;
import org.anvilpowered.signtracker.common.plugin.SignTrackerPluginMessages;
import org.anvilpowered.signtracker.common.registry.CommonConfigurationService;
import org.anvilpowered.signtracker.common.sign.CommonMongoSignRepository;
import org.anvilpowered.signtracker.common.sign.CommonSignManager;
import org.anvilpowered.signtracker.common.sign.CommonXodusSignRepository;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

@SuppressWarnings("UnstableApiUsage")
public class CommonModule<TUser, TPlayer, TString, TCommandSource> extends AbstractModule {

    @Override
    protected void configure() {

        BindingExtensions be = Anvil.getBindingExtensions(binder());

        be.bind(new TypeToken<PluginInfo<TString>>(getClass()) {
        }, new TypeToken<SignTrackerPluginInfo<TString, TCommandSource>>(getClass()) {
        });

        be.bind(new TypeToken<BasicPluginInfo>(getClass()) {
        }, new TypeToken<SignTrackerPluginInfo<TString, TCommandSource>>(getClass()) {
        });

        be.bind(new TypeToken<PluginMessages<TCommandSource>>(getClass()) {
        }, new TypeToken<SignTrackerPluginMessages<TString, TCommandSource>>(getClass()) {
        });

        be.bind(
            new TypeToken<SignRepository<?, ?>>(getClass()) {
            },
            new TypeToken<SignRepository<ObjectId, Datastore>>(getClass()) {
            },
            new TypeToken<CommonMongoSignRepository>(getClass()) {
            },
            Names.named("mongodb")
        );

        be.bind(
            new TypeToken<SignRepository<?, ?>>(getClass()) {
            },
            new TypeToken<SignRepository<EntityId, PersistentEntityStore>>(getClass()) {
            },
            new TypeToken<CommonXodusSignRepository>(getClass()) {
            },
            Names.named("xodus")
        );

        be.bind(
            new TypeToken<SignManager<TPlayer>>(getClass()) {
            },
            new TypeToken<CommonSignManager<TUser, TPlayer, TString, TCommandSource>>(getClass()) {
            }
        );

        be.withMongoDB();
        be.withXodus();

        bind(Registry.class).to(CommonConfigurationService.class);
    }
}

package org.anvilpowered.signtracker.api.sign;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityId;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.StoreTransaction;
import org.anvilpowered.anvil.api.datastore.XodusRepository;
import org.anvilpowered.signtracker.api.model.sign.Sign;

import java.util.UUID;
import java.util.function.Function;

public interface XodusSignRepository
    extends SignRepository<EntityId, PersistentEntityStore>,
    XodusRepository<Sign<EntityId>> {

    Function<? super StoreTransaction, ? extends Iterable<Entity>> asQuery(UUID worldUUID, int x, int y, int z);

    Function<? super StoreTransaction, ? extends Iterable<Entity>> asQuery(UUID targetUserUUID);
}

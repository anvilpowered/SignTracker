package org.anvilpowered.signtracker.common.sign;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityId;
import jetbrains.exodus.entitystore.PersistentEntityStore;
import jetbrains.exodus.entitystore.StoreTransaction;
import org.anvilpowered.anvil.base.datastore.BaseXodusRepository;
import org.anvilpowered.signtracker.api.model.sign.Sign;
import org.anvilpowered.signtracker.api.sign.XodusSignRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class CommonXodusSignRepository
    extends CommonSignRepository<EntityId, PersistentEntityStore>
    implements BaseXodusRepository<Sign<EntityId>>,
    XodusSignRepository {

    @Override
    public Function<? super StoreTransaction, ? extends Iterable<Entity>> asQuery(
        UUID worldUUID, int x, int y, int z) {
        return txn -> txn.find(getTClass().getSimpleName(), "worldUUID", worldUUID.toString())
            .intersect(txn.find(getTClass().getSimpleName(), "x", x))
            .intersect(txn.find(getTClass().getSimpleName(), "y", y))
            .intersect(txn.find(getTClass().getSimpleName(), "z", z));
    }

    @Override
    public Function<? super StoreTransaction, ? extends Iterable<Entity>> asQuery(
        UUID targetUserUUID) {
        return txn -> txn.find(getTClass().getSimpleName(),
            "targetUserUUID", targetUserUUID.toString());
    }

    @Override
    public CompletableFuture<Optional<Sign<EntityId>>> getOne(UUID worldUUID, int x, int y, int z) {
        return getOne(asQuery(worldUUID, x, y, z));
    }

    @Override
    public CompletableFuture<List<Sign<EntityId>>> getAll(UUID targetUserUUID) {
        return getAll(asQuery(targetUserUUID));
    }

    @Override
    public CompletableFuture<Boolean> deleteOne(UUID worldUUID, int x, int y, int z) {
        return delete(asQuery(worldUUID, x, y, z));
    }

    @Override
    public CompletableFuture<Boolean> updateTargetUserUUID(Object id, UUID targetUserUUID) {
        return update(asQuery(parseUnsafe(id)), e -> e.setProperty("targetUserUUID", targetUserUUID));
    }
}

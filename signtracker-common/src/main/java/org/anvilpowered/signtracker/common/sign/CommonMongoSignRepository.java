package org.anvilpowered.signtracker.common.sign;

import dev.morphia.Datastore;
import dev.morphia.query.Query;
import org.anvilpowered.anvil.base.datastore.BaseMongoRepository;
import org.anvilpowered.signtracker.api.model.sign.Sign;
import org.anvilpowered.signtracker.api.sign.MongoSignRepository;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class CommonMongoSignRepository
    extends CommonSignRepository<ObjectId, Datastore>
    implements BaseMongoRepository<Sign<ObjectId>>,
    MongoSignRepository {

    @Override
    public Query<Sign<ObjectId>> asQuery(UUID worldUUID, int x, int y, int z) {
        return asQuery().field("worldUUID").equal(worldUUID)
            .field("x").equal(x)
            .field("y").equal(y)
            .field("z").equal(z);
    }

    @Override
    public Query<Sign<ObjectId>> asQuery(UUID targetUserUUID) {
        return asQuery().field("targetUserUUID").equal(targetUserUUID);
    }

    @Override
    public CompletableFuture<Optional<Sign<ObjectId>>> getOne(UUID worldUUID, int x, int y, int z) {
        return getOne(asQuery(worldUUID, x, y, z));
    }

    @Override
    public CompletableFuture<List<Sign<ObjectId>>> getAll(UUID targetUserUUID) {
        return getAll(asQuery(targetUserUUID));
    }

    @Override
    public CompletableFuture<Boolean> deleteOne(UUID worldUUID, int x, int y, int z) {
        return delete(asQuery(worldUUID, x, y, z));
    }

    @Override
    public CompletableFuture<Boolean> updateTargetUserUUID(Object id, UUID targetUserUUID) {
        return update(asQuery(parseUnsafe(id)), set("targetUserUUID", targetUserUUID));
    }
}

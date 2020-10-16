package org.anvilpowered.signtracker.api.sign;

import org.anvilpowered.anvil.api.datastore.MongoRepository;
import org.anvilpowered.signtracker.api.model.sign.Sign;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.UUID;

public interface MongoSignRepository
    extends SignRepository<ObjectId, Datastore>,
    MongoRepository<Sign<ObjectId>> {

    Query<Sign<ObjectId>> asQuery(UUID worldUUID, int x, int y, int z);

    Query<Sign<ObjectId>> asQuery(UUID targetUserUUID);
}

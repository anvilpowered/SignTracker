package org.anvilpowered.signtracker.api.sign.repository;

import org.anvilpowered.anvil.api.datastore.Repository;
import org.anvilpowered.signtracker.api.model.sign.Sign;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface SignRepository<TKey, TDataStore> extends Repository<TKey, Sign<TKey>, TDataStore> {

    CompletableFuture<Optional<Sign<TKey>>> getOne(UUID worldUUID, int x, int y, int z);

    CompletableFuture<List<Sign<TKey>>> getAll(UUID targetUserUUID);

    CompletableFuture<Boolean> deleteOne(UUID worldUUID, int x, int y, int z);

    CompletableFuture<Boolean> updateTargetUserUUID(Object id, UUID targetUserUUID);

    CompletableFuture<Optional<Sign<TKey>>> register(
        UUID ownerUUID,
        UUID targetUserUUID,
        UUID worldUUID,
        int x,
        int y,
        int z,
        List<String> lines
    );
}

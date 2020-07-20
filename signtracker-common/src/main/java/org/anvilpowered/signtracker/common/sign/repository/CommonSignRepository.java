package org.anvilpowered.signtracker.common.sign.repository;

import org.anvilpowered.anvil.api.datastore.DataStoreContext;
import org.anvilpowered.anvil.base.datastore.BaseRepository;
import org.anvilpowered.signtracker.api.model.sign.Sign;
import org.anvilpowered.signtracker.api.sign.repository.SignRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class CommonSignRepository<TKey, TDataStore>
    extends BaseRepository<TKey, Sign<TKey>, TDataStore>
    implements SignRepository<TKey, TDataStore> {

    protected CommonSignRepository(DataStoreContext<TKey, TDataStore> dataStoreContext) {
        super(dataStoreContext);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<Sign<TKey>> getTClass() {
        return (Class<Sign<TKey>>) getDataStoreContext().getEntityClassUnsafe("sign");
    }

    @Override
    public CompletableFuture<Optional<Sign<TKey>>> register(
        UUID ownerUUID,
        UUID targetUserUUID,
        UUID worldUUID,
        int x,
        int y,
        int z,
        List<String> lines
    ) {
        Sign<TKey> sign = generateEmpty();
        sign.setOwnerUUID(ownerUUID);
        sign.setTargetUserUUID(targetUserUUID);
        sign.setWorldUUID(worldUUID);
        sign.setX(x);
        sign.setY(y);
        sign.setZ(z);
        sign.setLines(lines);
        return insertOne(sign);
    }
}

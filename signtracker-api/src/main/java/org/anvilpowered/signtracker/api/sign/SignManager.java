package org.anvilpowered.signtracker.api.sign;

import org.anvilpowered.anvil.api.datastore.Manager;
import org.anvilpowered.signtracker.api.sign.repository.SignRepository;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface SignManager<TPlayer> extends Manager<SignRepository<?, ?>> {

    @Override
    default String getDefaultIdentifierSingularUpper() {
        return "Sign";
    }

    @Override
    default String getDefaultIdentifierPluralUpper() {
        return "Signs";
    }

    @Override
    default String getDefaultIdentifierSingularLower() {
        return "sign";
    }

    @Override
    default String getDefaultIdentifierPluralLower() {
        return "signs";
    }

    CompletableFuture<Void> deleteOne(
        TPlayer player,
        UUID worldUUID,
        int x,
        int y,
        int z
    );

    CompletableFuture<Void> register(
        TPlayer player,
        UUID ownerUUID,
        UUID targetUserUUID,
        UUID worldUUID,
        int x,
        int y,
        int z,
        List<String> lines
    );
}

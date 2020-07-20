package org.anvilpowered.signtracker.api.model.sign;

import org.anvilpowered.anvil.api.model.ObjectWithId;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface Sign<TKey> extends ObjectWithId<TKey> {

    UUID getOwnerUUID();
    void setOwnerUUID(UUID ownerUUID);

    UUID getTargetUserUUID();
    void setTargetUserUUID(UUID targetUserUUID);

    UUID getWorldUUID();
    void setWorldUUID(UUID worldUUID);

    Instant getSignUpdatedUtc();
    void setSignUpdatedUtc(Instant signUpdatedUtc);

    int getX();
    void setX(int x);

    int getY();
    void setY(int y);

    int getZ();
    void setZ(int z);

    List<String> getLines();
    void setLines(List<String> lines);
}

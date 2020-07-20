package org.anvilpowered.signtracker.common.model.sign;

import org.anvilpowered.anvil.base.model.MongoDbo;
import org.anvilpowered.signtracker.api.model.sign.Sign;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity("signs")
public class MongoSign extends MongoDbo implements Sign<ObjectId> {

    private UUID ownerUUID;
    private UUID targetUserUUID;
    private UUID worldUUID;
    private Instant signUpdatedUtc;
    private int x;
    private int y;
    private int z;
    private List<String> lines;

    @Override
    public UUID getOwnerUUID() {
        return ownerUUID;
    }

    @Override
    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID;
    }

    @Override
    public UUID getTargetUserUUID() {
        return targetUserUUID;
    }

    @Override
    public void setTargetUserUUID(UUID targetUserUUID) {
        this.targetUserUUID = targetUserUUID;
    }

    @Override
    public UUID getWorldUUID() {
        return worldUUID;
    }

    @Override
    public void setWorldUUID(UUID worldUUID) {
        this.worldUUID = worldUUID;
    }

    @Override
    public Instant getSignUpdatedUtc() {
        return signUpdatedUtc;
    }

    @Override
    public void setSignUpdatedUtc(Instant signUpdatedUtc) {
        this.signUpdatedUtc = signUpdatedUtc;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public int getZ() {
        return z;
    }

    @Override
    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public List<String> getLines() {
        return lines;
    }

    @Override
    public void setLines(List<String> lines) {
        this.lines = lines;
    }
}

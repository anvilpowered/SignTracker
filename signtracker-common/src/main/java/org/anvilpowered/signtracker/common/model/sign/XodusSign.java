package org.anvilpowered.signtracker.common.model.sign;

import jetbrains.exodus.entitystore.Entity;
import jetbrains.exodus.entitystore.EntityId;
import jetbrains.exodus.util.ByteArraySizedInputStream;
import org.anvilpowered.anvil.api.datastore.XodusEntity;
import org.anvilpowered.anvil.api.model.Mappable;
import org.anvilpowered.anvil.base.model.XodusDbo;
import org.anvilpowered.signtracker.api.model.sign.Sign;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@XodusEntity
public class XodusSign extends XodusDbo implements Sign<EntityId> {

    private String ownerUUID;
    private String targetUserUUID;
    private String worldUUID;
    private long signUpdatedUtcSeconds;
    private int signUpdatedUtcNanos;
    private int x;
    private int y;
    private int z;
    private List<String> lines;

    @Override
    public UUID getOwnerUUID() {
        return UUID.fromString(ownerUUID);
    }

    @Override
    public void setOwnerUUID(UUID ownerUUID) {
        this.ownerUUID = ownerUUID.toString();
    }

    @Override
    public UUID getTargetUserUUID() {
        return UUID.fromString(targetUserUUID);
    }

    @Override
    public void setTargetUserUUID(UUID targetUserUUID) {
        this.targetUserUUID = targetUserUUID.toString();
    }

    @Override
    public UUID getWorldUUID() {
        return UUID.fromString(worldUUID);
    }

    @Override
    public void setWorldUUID(UUID worldUUID) {
        this.worldUUID = worldUUID.toString();
    }

    @Override
    public Instant getSignUpdatedUtc() {
        return Instant.ofEpochSecond(signUpdatedUtcSeconds, signUpdatedUtcNanos);
    }

    @Override
    public void setSignUpdatedUtc(Instant signUpdatedUtc) {
        signUpdatedUtcSeconds = signUpdatedUtc.getEpochSecond();
        signUpdatedUtcNanos = signUpdatedUtc.getNano();
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
        if (lines == null) {
            lines = new ArrayList<>();
        }
        return lines;
    }

    @Override
    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public Entity writeTo(Entity object) {
        super.writeTo(object);
        object.setProperty("ownerUUID", ownerUUID);
        object.setProperty("targetUserUUID", targetUserUUID);
        object.setProperty("worldUUID", worldUUID);
        object.setProperty("signUpdatedUtcSeconds", signUpdatedUtcSeconds);
        object.setProperty("signUpdatedUtcNanos", signUpdatedUtcNanos);
        object.setProperty("x", x);
        object.setProperty("y", y);
        object.setProperty("z", z);
        try {
            object.setBlob("lines",
                new ByteArraySizedInputStream(Mappable.serializeUnsafe(getLines())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public void readFrom(Entity object) {
        super.readFrom(object);
        Comparable<?> ownerUUID = object.getProperty("ownerUUID");
        if (ownerUUID instanceof String) {
            this.ownerUUID = (String) ownerUUID;
        }
        Comparable<?> targetUserUUID = object.getProperty("targetUserUUID");
        if (targetUserUUID instanceof String) {
            this.targetUserUUID = (String) targetUserUUID;
        }
        Comparable<?> worldUUID = object.getProperty("worldUUID");
        if (worldUUID instanceof String) {
            this.worldUUID = (String) worldUUID;
        }
        Comparable<?> signUpdatedUtcSeconds = object.getProperty("signUpdatedUtcSeconds");
        if (signUpdatedUtcSeconds instanceof Long) {
            this.signUpdatedUtcSeconds = (Long) signUpdatedUtcSeconds;
        }
        Comparable<?> signUpdatedUtcNanos = object.getProperty("signUpdatedUtcNanos");
        if (signUpdatedUtcNanos instanceof Integer) {
            this.signUpdatedUtcNanos = (Integer) signUpdatedUtcNanos;
        }
        Comparable<?> x = object.getProperty("x");
        if (x instanceof Integer) {
            this.x = (Integer) x;
        }
        Comparable<?> y = object.getProperty("y");
        if (y instanceof Integer) {
            this.y = (Integer) y;
        }
        Comparable<?> z = object.getProperty("z");
        if (z instanceof Integer) {
            this.z = (Integer) z;
        }
        Mappable.<List<String>>deserialize(object.getBlob("lines")).ifPresent(t -> lines = t);
    }
}

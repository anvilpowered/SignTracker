package org.anvilpowered.signtracker.api.sign;

import org.anvilpowered.anvil.api.core.model.coremember.CoreMember;
import org.anvilpowered.signtracker.api.model.sign.Sign;

import java.util.concurrent.CompletableFuture;

public interface SignUpdateService {

    void update(Sign<?> sign, CoreMember<?> coreMember);

    CompletableFuture<Boolean> updateSync(Sign<?> sign, CoreMember<?> coreMember);
}

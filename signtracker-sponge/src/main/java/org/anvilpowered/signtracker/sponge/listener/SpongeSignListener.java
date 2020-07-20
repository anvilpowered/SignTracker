package org.anvilpowered.signtracker.sponge.listener;

import com.google.inject.Inject;
import org.anvilpowered.anvil.api.plugin.PluginInfo;
import org.anvilpowered.anvil.api.util.TextService;
import org.anvilpowered.anvil.api.util.UserService;
import org.anvilpowered.signtracker.api.sign.SignManager;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.value.mutable.ListValue;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class SpongeSignListener {

    private static final Pattern PLACEHOLDER_CHECK
        = Pattern.compile("\\s*%((first_seen)|(last_seen)|(nick))%\\s*");

    @Inject
    private PluginInfo<Text> pluginInfo;

    @Inject
    private SignManager<Player> signManager;

    @Inject
    private TextService<Text, CommandSource> textService;

    @Inject
    private UserService<User, Player> userService;

    @Listener
    public void onSignChange(ChangeSignEvent event, @Root Player player) {
        ListValue<Text> textLines = event.getText().lines();
        if (textLines.size() != 4) {
            return;
        }
        List<String> lines = new ArrayList<>();
        for (Text line : textLines) {
            lines.add(line.toPlainSingle());
        }
        boolean anyMatch = false;
        for (int i = 1; i < 4; i++) {
            if (PLACEHOLDER_CHECK.matcher(lines.get(i)).matches()) {
                anyMatch = true;
            }
        }
        if (!anyMatch) {
            return;
        }

        String userName = lines.get(0);
        Optional<User> optionalUser = userService.get(userName);
        if (!optionalUser.isPresent()) {
            textService.builder()
                .append(pluginInfo.getPrefix())
                .red().append("Could not find user for name ")
                .gold().append(userName)
                .red().append(". Please try again, or manually link this sign with ")
                .gold().append("/st register <user>")
                .red().append(" while looking at it.")
                .sendTo(player);
            return;
        }
        User user = optionalUser.get();
        Location<World> location = event.getTargetTile().getLocation();
        lines.set(0, "\u00a7b[ %name% ]");
        signManager.register(
            player,
            player.getUniqueId(),
            user.getUniqueId(),
            player.getWorld().getUniqueId(),
            location.getBlockX(),
            location.getBlockY(),
            location.getBlockZ(),
            lines);
    }

    @Listener
    public void onBlockBreak(ChangeBlockEvent.Break event, @Getter("getSource") Player player) {
        event.getTransactions().forEach(transaction -> {
            BlockSnapshot snapshot = transaction.getOriginal();
            BlockType type = snapshot.getState().getType();
            if (!type.equals(BlockTypes.WALL_SIGN)
                && !type.equals(BlockTypes.STANDING_SIGN)) {
                return;
            }
            snapshot.getLocation().ifPresent(location ->
                signManager.deleteOne(
                    player,
                    snapshot.getWorldUniqueId(),
                    location.getBlockX(),
                    location.getBlockY(),
                    location.getBlockZ()
                )
            );
        });
    }
}

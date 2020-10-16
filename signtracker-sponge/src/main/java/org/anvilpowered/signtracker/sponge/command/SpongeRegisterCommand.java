package org.anvilpowered.signtracker.sponge.command;

import com.google.inject.Inject;
import org.anvilpowered.anvil.api.plugin.PluginInfo;
import org.anvilpowered.anvil.api.util.TextService;
import org.anvilpowered.signtracker.api.sign.SignManager;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SpongeRegisterCommand implements CommandExecutor {

    private final static Predicate<BlockRayHit<World>> SIGN_PREDICATE
        = BlockRay.<World>blockTypeFilter(BlockTypes.WALL_SIGN)
        .or(BlockRay.blockTypeFilter(BlockTypes.STANDING_SIGN));

    @Inject
    private PluginInfo<Text> pluginInfo;

    @Inject
    private SignManager<Player> signManager;

    @Inject
    private TextService<Text, CommandSource> textService;

    @Override
    public CommandResult execute(CommandSource source, CommandContext context) {
        if (!(source instanceof Player)) {
            textService.builder()
                .append(pluginInfo.getPrefix())
                .red().append("Command must be run as player!")
                .sendTo(source);
            return CommandResult.empty();
        }
        Player player = (Player) source;

        Optional<Location<World>> optionalLocation = BlockRay.from(player)
            .select(SIGN_PREDICATE)
            .end()
            .map(BlockRayHit::getLocation);

        Optional<List<String>> optionalLines = optionalLocation
            .flatMap(Location::getTileEntity)
            .flatMap(b -> b.get(Keys.SIGN_LINES))
            .map(list -> list.stream().map(Text::toPlainSingle).collect(Collectors.toList()));

        if (!optionalLines.isPresent()) {
            textService.builder()
                .append(pluginInfo.getPrefix())
                .red().append("You are not looking at a sign!")
                .sendTo(source);
            return CommandResult.empty();
        }
        Location<World> location = optionalLocation.get();
        signManager.register(
            player,
            player.getUniqueId(),
            context.<User>requireOne("user").getUniqueId(),
            player.getWorld().getUniqueId(),
            location.getBlockX(),
            location.getBlockY(),
            location.getBlockZ(),
            optionalLines.get());
        return CommandResult.success();
    }
}

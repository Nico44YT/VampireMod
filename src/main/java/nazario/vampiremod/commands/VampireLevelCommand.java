package nazario.vampiremod.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import nazario.vampiremod.VampireClass;
import nazario.vampiremod.ModConstants;
import nazario.vampiremod.cardinal.VampireDataComponent;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;

public class VampireLevelCommand {
    public static ArgumentBuilder<ServerCommandSource, ?> create() {
        return CommandManager.literal("level")
                .then(CommandManager.literal("get")
                        .then(CommandManager.argument("player", EntityArgumentType.players())
                                .then(CommandManager.argument("class", new ClassArgumentType())
                                        .executes(VampireLevelCommand::getLevel))))
                .then(CommandManager.literal("reset")
                        .then(CommandManager.argument("player", EntityArgumentType.players())
                                .then(CommandManager.argument("class", new ClassArgumentType())
                                        .executes(VampireLevelCommand::resetLevel))))
                .then(CommandManager.literal("set")
                        .then(CommandManager.argument("player", EntityArgumentType.players())
                                .then(CommandManager.argument("class", new ClassArgumentType())
                                        .then(CommandManager.argument("level", IntegerArgumentType.integer(-1, ModConstants.VAMPIRE_MAX_LEVEL))
                                                .executes(VampireLevelCommand::setLevel)))));
    }

    private static int getLevel(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        VampireClass playerClass = ClassArgumentType.get(context, "class");
        Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(context, "player");

        players.forEach(player -> {
            int level = VampireDataComponent.getLevel(playerClass, player);

            context.getSource().sendFeedback(() -> Text.translatable("commands.vampire_mod.level_command.get", playerClass.asString(), player.getDisplayName(), level), true);
        });

        return 0;
    }

    private static int resetLevel(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        VampireClass playerClass = ClassArgumentType.get(context, "class");
        Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(context, "player");

        players.forEach(player -> {
            VampireDataComponent.setLevel(player, playerClass, -1);

            context.getSource().sendFeedback(() -> Text.translatable("commands.vampire_mod.level_command.reset", playerClass.name(), player.getDisplayName()), true);
        });

        return 0;
    }

    public static int setLevel(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        VampireClass playerClass = ClassArgumentType.get(context, "class");
        Collection<ServerPlayerEntity> players = EntityArgumentType.getPlayers(context, "player");
        int level = IntegerArgumentType.getInteger(context, "level");

        players.forEach(player -> {
            int prevLevel = VampireDataComponent.getLevel(playerClass, player);

            VampireDataComponent.setLevel(player, playerClass, level);

            context.getSource().sendFeedback(() -> Text.translatable("commands.vampire_mod.level_command.set", playerClass.name(), player.getDisplayName(), prevLevel, level), true);
        });

        return 0;
    }


}

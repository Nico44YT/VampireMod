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
import net.minecraft.text.Text;

public class VampireLevelCommand {
    public static ArgumentBuilder<ServerCommandSource, ?> create() {
        return CommandManager.literal("level")
                .then(CommandManager.literal("set")
                        .then(CommandManager.argument("player", EntityArgumentType.player())
                                .then(CommandManager.argument("class", new ClassArgumentType())
                                        .then(CommandManager.argument("level", IntegerArgumentType.integer(-1, ModConstants.VAMPIRE_MAX_LEVEL))
                                                .executes(VampireLevelCommand::setLevel)))));
    }

    public static int setLevel(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        VampireClass playerClass = ClassArgumentType.get(context, "class");
        PlayerEntity player = EntityArgumentType.getPlayer(context, "player");
        int level = IntegerArgumentType.getInteger(context, "level");
        int prevLevel = VampireDataComponent.getLevel(playerClass, player);

        VampireDataComponent.maybe(player).ifPresent(comp -> {
            comp.setLevel(playerClass, level);
        });

        context.getSource().sendFeedback(() -> Text.translatable("commands.vampire_mod.level_command_set", playerClass.name(), player.getDisplayName(), prevLevel+"", level+""), true);

        return 0;
    }


}

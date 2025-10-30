package nazario.vampiremod.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import nazario.vampiremod.Vampiremod;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;

public class ModCommands {
    protected static List<ArgumentBuilder> subCommands = new ArrayList<>();

    public ModCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal(Vampiremod.MOD_ID);

        subCommands.forEach(builder::then);

        dispatcher.register(builder);
    }

    public static void registerSubCommand(ArgumentBuilder<?, ?> argumentBuilder) {
        subCommands.add(argumentBuilder);
    }
}

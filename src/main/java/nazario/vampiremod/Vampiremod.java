package nazario.vampiremod;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import nazario.vampiremod.cardinal.VampireDataComponent;
import nazario.vampiremod.commands.ClassArgumentType;
import nazario.vampiremod.commands.ModCommands;
import nazario.vampiremod.commands.VampireLevelCommand;
import nazario.vampiremod.network.ModPackets;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class Vampiremod implements ModInitializer, EntityComponentInitializer {

    public static final String MOD_ID = "vampire_mod";

    @Override
    public void onInitialize() {
        ModCommands.registerSubCommand(VampireLevelCommand.create());
        ArgumentTypeRegistry.registerArgumentType(id("class_argument"), ClassArgumentType.class, ConstantArgumentSerializer.of(ClassArgumentType::new));

        CommandRegistrationCallback.EVENT.register(ModCommands::new);

        ModPackets.registerC2S();
        ModPackets.registerS2C();
    }

    public static Identifier id(String name) {
        return Identifier.of(MOD_ID, name);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(PlayerEntity.class, VampireDataComponent.KEY, VampireDataComponent::new);
    }
}

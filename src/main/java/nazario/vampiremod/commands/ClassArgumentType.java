package nazario.vampiremod.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.serialization.Codec;
import nazario.vampiremod.VampireClass;
import net.minecraft.command.argument.EnumArgumentType;
import net.minecraft.server.command.ServerCommandSource;

public class ClassArgumentType extends EnumArgumentType<VampireClass> {

    public static final Codec<VampireClass> CODEC = Codec.STRING.xmap(
            s -> {
                try {
                    return VampireClass.valueOf(s.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Invalid class name: " + s);
                }
            },
            Enum::name
    );

    public ClassArgumentType() {
        super(CODEC, VampireClass::values);
    }

    public static VampireClass get(CommandContext<ServerCommandSource> context, String id) {
        return context.getArgument(id, VampireClass.class);
    }
}
package nazario.vampiremod.cardinal;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import nazario.vampiremod.VampireClass;
import nazario.vampiremod.Vampiremod;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

import java.util.Optional;

public class VampireDataComponent implements AutoSyncedComponent {
    public static final ComponentKey<VampireDataComponent> KEY = ComponentRegistry.getOrCreate(Vampiremod.id("vampire_data"), VampireDataComponent.class);

    private final PlayerEntity player;
    protected int vampireLevel;

    public VampireDataComponent(PlayerEntity player) {
        this.player = player;
        this.vampireLevel = -1;
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound) {
        this.vampireLevel = nbtCompound.getInt("vampire_level");
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound) {
        nbtCompound.putInt("vampire_level", this.vampireLevel);
    }


    public static Optional<VampireDataComponent> maybe(PlayerEntity player) {
        return KEY.maybeGet(player);
    }

    public static int getVampireLevel(PlayerEntity player) {
        return maybe(player).map(vampireDataComponent -> vampireDataComponent.vampireLevel).orElse(-1);
    }

    public static boolean isVampire(PlayerEntity player) {
        return getVampireLevel(player) > -1;
    }

    public static boolean hasComponent(PlayerEntity player) {
        return maybe(player).isPresent();
    }

    public void setLevel(VampireClass playerClass, int level) {
        vampireLevel = level;
        sync(player);
    }

    public static int getLevel(VampireClass playerClass, PlayerEntity player) {
        return getVampireLevel(player);
    }

    public static void sync(PlayerEntity player) {
        KEY.sync(player);
    }
}

package nazario.vampiremod.cardinal;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import nazario.vampiremod.VampireClass;
import nazario.vampiremod.Vampiremod;
import nazario.vampiremod.util.ModNbtHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtString;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VampireDataComponent implements AutoSyncedComponent {
    public static final ComponentKey<VampireDataComponent> KEY = ComponentRegistry.getOrCreate(Vampiremod.id("vampire_data"), VampireDataComponent.class);

    private final PlayerEntity player;
    protected Map<VampireClass, Integer> levels;

    public VampireDataComponent(PlayerEntity player) {
        this.player = player;
        this.levels = new HashMap<>();

        for (VampireClass value : VampireClass.values()) {
            this.levels.put(value, -1);
        }
    }

    @Override
    public void readFromNbt(NbtCompound nbtCompound) {
        this.levels = convertMap(ModNbtHelper.getMap("levels", nbtCompound));
    }

    private static Map<VampireClass, Integer> convertMap(Map<Object, Object> map) {
        HashMap<VampireClass, Integer> newMap = new HashMap<>();

        map.forEach((key, value) -> {
            try{
                newMap.put(VampireClass.valueOf(((NbtString)key).asString().toUpperCase()), ((NbtInt)value).intValue());
            }catch (Exception e) {
                e.printStackTrace();
            }
        });

        return newMap;
    }

    @Override
    public void writeToNbt(NbtCompound nbtCompound) {
        ModNbtHelper.putMap("levels", this.levels, nbtCompound);
    }


    public static Optional<VampireDataComponent> maybe(PlayerEntity player) {
        return KEY.maybeGet(player);
    }

    public static boolean isVampire(PlayerEntity player) {
        return getLevel(VampireClass.VAMPIRE, player) > -1;
    }

    public static boolean isHunter(PlayerEntity player) {
        return getLevel(VampireClass.HUNTER, player) > -1;
    }

    public static boolean hasComponent(PlayerEntity player) {
        return maybe(player).isPresent();
    }

    public static int getLevel(VampireClass playerClass, PlayerEntity player) {
        sync(player);
        return maybe(player).map(vampireDataComponent -> vampireDataComponent.levels.get(playerClass)).orElse(-1);
    }

    public static void sync(PlayerEntity player) {
        KEY.sync(player);
    }

    public static void setLevel(PlayerEntity player, VampireClass playerClass, int level) {
        maybe(player).ifPresent(comp -> comp.levels.put(playerClass, level));

        sync(player);
    }

    public void setLevel(VampireClass playerClass, int level) {
        levels.put(playerClass, level);
        sync(player);
    }
}
package nazario.vampiremod.util;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.StringIdentifiable;

import java.util.HashMap;
import java.util.Map;

public class ModNbtHelper {
    public static void putMap(String id, Map<?, ?> map, NbtCompound nbtCompound) {
        NbtCompound keys = new NbtCompound();
        NbtCompound values = new NbtCompound();

        int[] index = {0};
        map.forEach((key, value) -> {
            putInCompound(index[0]+"", key, keys);
            putInCompound(index[0]+"", value, values);
            index[0]++;
        });

        NbtCompound finalNbt = new NbtCompound();
        finalNbt.put("values", values);
        finalNbt.put("keys", keys);
        finalNbt.putInt("size", map.size());

        nbtCompound.put(id, finalNbt);
    }

    public static Map<Object, Object> getMap(String id, NbtCompound nbtCompound) {
        NbtCompound keys = nbtCompound.getCompound(id).getCompound("keys");
        NbtCompound values = nbtCompound.getCompound(id).getCompound("values");

        int size = nbtCompound.getCompound(id).getInt("size");

        Map<Object, Object> map = new HashMap<>();
        for(int i = 0;i<size;i++) {
            // TODO
            NbtElement key = keys.get(i+"");
            NbtElement value = values.get(i+"");

            map.put(key, value);
        }

        return map;
    }

    private static void putInCompound(String id, Object object, NbtCompound nbtCompound) {
        if(object instanceof String str) {
            nbtCompound.putString(id, str);
            return;
        }

        if(object instanceof Boolean bool) {
            nbtCompound.putBoolean(id, bool);
            return;
        }

        if(object instanceof Integer integer) {
            nbtCompound.putInt(id, integer);
            return;
        }

        if(object instanceof Long lon) {
            nbtCompound.putLong(id, lon);
            return;
        }

        if(object instanceof Float floa) {
            nbtCompound.putFloat(id, floa);
            return;
        }

        if(object instanceof Double doubl) {
            nbtCompound.putDouble(id, doubl);
            return;
        }

        if(object instanceof StringIdentifiable stringIdentifiable) {
            nbtCompound.putString(id, stringIdentifiable.asString());
            return;
        }

        if(object instanceof Byte byt) {
            nbtCompound.putByte(id, byt);
            return;
        }

        throw new RuntimeException(id + object.toString());
    }
}

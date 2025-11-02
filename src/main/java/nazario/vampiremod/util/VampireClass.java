package nazario.vampiremod.util;

import net.minecraft.util.StringIdentifiable;

public enum VampireClass implements StringIdentifiable {
    VAMPIRE("vampire"),
    HUNTER("hunter");

    private final String name;

    VampireClass(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }
}

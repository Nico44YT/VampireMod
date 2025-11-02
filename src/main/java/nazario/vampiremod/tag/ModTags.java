package nazario.vampiremod.tag;

import nazario.vampiremod.Vampiremod;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModTags {
    public static class Items {
        public static TagKey<Item> VAMPIRE_EDIBLE = create("vampire_edible");

        private static TagKey<Item> create(String name) {
            return TagKey.of(RegistryKeys.ITEM, Vampiremod.id(name));
        }
    }

    public static class Entities {
        public static TagKey<EntityType<?>> BAD_BLOOD_TAG = create("bad_blood");
        public static TagKey<EntityType<?>> NO_BLOOD_TAG = create("no_blood");

        public static TagKey<EntityType<?>> VAMPIRE_HOSTILE_TAG = create("vampire_hostile");
        public static TagKey<EntityType<?>> VAMPIRE_NEUTRAL_TAG = create("vampire_neutral");
        public static TagKey<EntityType<?>> VAMPIRE_PASSIVE_TAG = create("vampire_passive");

        private static TagKey<EntityType<?>> create(String name) {
            return TagKey.of(RegistryKeys.ENTITY_TYPE, Vampiremod.id(name));
        }
    }
}

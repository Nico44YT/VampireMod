package nazario.vampiremod.tag;

import nazario.vampiremod.Vampiremod;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public class ModTags {
    public static class Entity {
        public static TagKey<EntityType<?>> BAD_BLOOD_TAG = TagKey.of(RegistryKeys.ENTITY_TYPE, Vampiremod.id("bad_blood"));
        public static TagKey<EntityType<?>> NO_BLOOD_TAG = TagKey.of(RegistryKeys.ENTITY_TYPE, Vampiremod.id("no_blood"));

        public static TagKey<EntityType<?>> VAMPIRE_HOSTILE_TAG = TagKey.of(RegistryKeys.ENTITY_TYPE, Vampiremod.id("vampire_hostile"));
        public static TagKey<EntityType<?>> VAMPIRE_NEUTRAL_TAG = TagKey.of(RegistryKeys.ENTITY_TYPE, Vampiremod.id("vampire_neutral"));
        public static TagKey<EntityType<?>> VAMPIRE_PASSIVE_TAG = TagKey.of(RegistryKeys.ENTITY_TYPE, Vampiremod.id("vampire_passive"));

    }
}

package nazario.vampiremod.util;

import nazario.vampiremod.tag.ModTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class ModVampireUtil {
    public static boolean canSuck(PlayerEntity player, Entity target) {
        return target.isInRange(player, 2) && !target.getType().isIn(ModTags.Entities.NO_BLOOD_TAG);
    }

    public static boolean hasBadBlood(Entity target) {
        return target.getType().isIn(ModTags.Entities.BAD_BLOOD_TAG);
    }
}

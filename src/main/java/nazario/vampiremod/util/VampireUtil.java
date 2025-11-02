package nazario.vampiremod.util;

import nazario.vampiremod.ModConstants;
import nazario.vampiremod.tag.ModTags;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

public class VampireUtil {
    public static boolean canSuck(PlayerEntity player, Entity target) {
        return target.isInRange(player, ModConstants.VAMPIRE_BITE_MAX_DISTANCE) && !target.getType().isIn(ModTags.Entities.NO_BLOOD_TAG);
    }

    public static boolean hasBadBlood(Entity target) {
        return target.getType().isIn(ModTags.Entities.BAD_BLOOD_TAG);
    }
}

package nazario.vampiremod.mixin;

import nazario.vampiremod.cardinal.VampireDataComponent;
import nazario.vampiremod.tag.ModTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TargetPredicate.class)
public abstract class TargetPredicateMixin {

    @Inject(method = "test", at = @At("HEAD"), cancellable = true)
    public void vampire$test(LivingEntity baseEntity, LivingEntity targetEntity, CallbackInfoReturnable<Boolean> cir) {
        if(targetEntity instanceof PlayerEntity player && VampireDataComponent.isVampire(player)) {
            if(baseEntity.isTeammate(targetEntity)) return;

            EntityType<?> attackerType = baseEntity.getType();

            if(attackerType.isIn(ModTags.Entity.VAMPIRE_HOSTILE_TAG)) cir.setReturnValue(true);
            else if(attackerType.isIn(ModTags.Entity.VAMPIRE_PASSIVE_TAG) || attackerType.isIn(ModTags.Entity.VAMPIRE_NEUTRAL_TAG)) cir.setReturnValue(false);
        }
    }
}

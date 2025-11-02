package nazario.vampiremod.mixin;

import nazario.vampiremod.cardinal.VampireDataComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    private PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "eatFood", at = @At("HEAD"), cancellable = true)
    public void vampire$restrictFood(World world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        PlayerEntity thisEntity = (PlayerEntity)(Object)this;

        if(VampireDataComponent.isVampire(thisEntity)) {
            cir.setReturnValue(stack);
        }
    }
}

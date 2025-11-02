package nazario.vampiremod.mixin;

import nazario.vampiremod.cardinal.VampireDataComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin {
    @Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/Item;isFood()Z", shift = At.Shift.AFTER), cancellable = true)
    public void vampire$use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if(VampireDataComponent.isVampire(user)) cir.setReturnValue(TypedActionResult.pass(user.getStackInHand(hand)));
    }
}

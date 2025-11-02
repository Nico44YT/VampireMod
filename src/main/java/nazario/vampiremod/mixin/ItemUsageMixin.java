package nazario.vampiremod.mixin;

import nazario.vampiremod.Vampiremod;
import nazario.vampiremod.cardinal.VampireDataComponent;
import nazario.vampiremod.tag.ModTags;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemUsage.class)
public abstract class ItemUsageMixin {

    @Inject(method = "consumeHeldItem", at = @At("HEAD"), cancellable = true)
    private static void vampire$consumeHeldItem(World world, PlayerEntity player, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack stack = player.getStackInHand(hand);

        if(VampireDataComponent.isVampire(player) && stack.isFood() && !stack.getItem().getRegistryEntry().isIn(ModTags.Items.VAMPIRE_EDIBLE)) cir.setReturnValue(TypedActionResult.pass(player.getStackInHand(hand)));
    }
}

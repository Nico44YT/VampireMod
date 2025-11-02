package nazario.vampiremod.mixin.client;

import nazario.vampiremod.Vampiremod;
import nazario.vampiremod.cardinal.VampireDataComponent;
import nazario.vampiremod.util.VampireUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow private int scaledHeight;
    @Shadow private int scaledWidth;

    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Unique private boolean vampire$isVampire = false;
    @Unique private boolean vampire$shouldRender = false;

    @Unique private final Identifier vampire$BLOOD_BAR_TEXTURE = Vampiremod.id("textures/gui/blood_bar.png");
    @Unique private final Identifier vampire$BITE_INDICATOR = Vampiremod.id("textures/gui/bite_indicator.png");

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V"))
    public void vampire$swap(Profiler instance, String string) {
        vampire$isVampire = VampireDataComponent.isVampire(getCameraPlayer());

        if(string.equals("food") && vampire$isVampire) {
            this.vampire$shouldRender = true;
            return;
        }

        this.vampire$shouldRender = false;
    }

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", shift = At.Shift.AFTER))
    public void vampire$render(DrawContext context, CallbackInfo ci) {
        if(this.vampire$isVampire && this.vampire$shouldRender) {
            HungerManager hungerManager = this.getCameraPlayer().getHungerManager();
            int width = 81;
            int height = 10;

            int y = this.scaledHeight - 39;
            int x = this.scaledWidth / 2 + 91;
            context.drawTexture(vampire$BLOOD_BAR_TEXTURE, x - width, y + 2, 0, 0, width, height/2, width, height); // Empty

            float fullness = hungerManager.getFoodLevel()/20f;
            int filledWidth = (int)(fullness * width);
            context.drawTexture(vampire$BLOOD_BAR_TEXTURE, x - width, y + 2, 0, 5, filledWidth, height/2, width, height); // Full

        }
    }

    @ModifyConstant(method = "renderStatusBars", constant = @Constant(intValue = 10))
    private int modifyLoopBound(int original) {
        return vampire$shouldRender ? 0 : original;
    }

    @Redirect(method = "renderCrosshair", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"))
    public void vampire$drawTexture(DrawContext instance, Identifier texture, int x, int y, int u, int v, int width, int height) {
        if(this.vampire$isVampire && MinecraftClient.getInstance().targetedEntity instanceof LivingEntity livingEntity && VampireUtil.canSuck(this.getCameraPlayer(), livingEntity)) {
            instance.drawTexture(vampire$BITE_INDICATOR, x, y, 0, 0, 16, 16, 16, 16);
            return;
        }

        instance.drawTexture(texture, x, y, u, v, width, height);
    }
}

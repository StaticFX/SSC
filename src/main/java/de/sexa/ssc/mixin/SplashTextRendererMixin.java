package de.sexa.ssc.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SplashTextRenderer.class)
public abstract class SplashTextRendererMixin {

    @Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
    public void render(DrawContext drawContext, int i, TextRenderer textRenderer, int j, CallbackInfo ci) {
        ci.cancel();

        drawContext.getMatrices().push();
        drawContext.getMatrices().translate((float)i / 2.0f + 123.0f, 69.0f, 0.0f);
        drawContext.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-20.0f));
        float f = 1.8f - MathHelper.abs(MathHelper.sin((float)(Util.getMeasuringTimeMs() % 1000L) / 1000.0f * ((float)Math.PI * 2)) * 0.1f);
        f = f * 100.0f / (float)(textRenderer.getWidth("HeXa SSC") + 32);
        drawContext.getMatrices().scale(f, f, f);
        drawContext.drawCenteredTextWithShadow(textRenderer, "HeXa SSC", 0, -8, 0x860000 | j);
        drawContext.getMatrices().pop();
    }
}

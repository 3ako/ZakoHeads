package me.zyouime.zakoheads.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import me.zyouime.zakoheads.client.ZakoHeadsClient;
import me.zyouime.zakoheads.config.ModConfig;
import me.zyouime.zakoheads.config.data.SizeData;
import me.zyouime.zakoheads.util.PvPUtil;
import me.zyouime.zakoheads.util.helper.DisplayEntityRenderStateHelper;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.DisplayRenderer;
import net.minecraft.client.renderer.entity.state.DisplayEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisplayRenderer.class)
public abstract class DisplayEntityRendererMixin {

    @Unique private final ModConfig modConfig = ZakoHeadsClient.getInstance().modConfig;

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/Display;Lnet/minecraft/client/renderer/entity/state/DisplayEntityRenderState;F)V", at = @At("TAIL"))
    private void updateHeadYOffset(Display displayEntity, DisplayEntityRenderState state, float f, CallbackInfo ci) {
        ((DisplayEntityRenderStateHelper) state).setHeadYOffset(headYOffset(displayEntity));
    }

    @Inject(method = "submit(Lnet/minecraft/client/renderer/entity/state/DisplayEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At("HEAD"))
    private void pushHeadYOffset(DisplayEntityRenderState state, PoseStack matrixStack, SubmitNodeCollector queue, CameraRenderState cameraRenderState, CallbackInfo ci) {
        matrixStack.pushPose();
        matrixStack.translate(0, ((DisplayEntityRenderStateHelper) state).headYOffset(), 0);
    }

    @Inject(method = "submit(Lnet/minecraft/client/renderer/entity/state/DisplayEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/level/CameraRenderState;)V", at = @At("RETURN"))
    private void popHeadYOffset(DisplayEntityRenderState state, PoseStack matrixStack, SubmitNodeCollector queue, CameraRenderState cameraRenderState, CallbackInfo ci) {
        matrixStack.popPose();
    }

    @Unique
    private float headYOffset(Display displayEntity) {
        if (!modConfig.enabled || !(displayEntity.getVehicle() instanceof Player player)) {
            return 0f;
        }
        if (modConfig.defaultPartsInPvP && PvPUtil.isPvP()) {
            return 0f;
        }
        SizeData data = ZakoHeadsClient.checkIsYourSize(player) ? modConfig.yourSize : modConfig.otherSize;
        // 0.5 - на столько поднимается макушка, остальное - зазор над ней
        return (data.getHeadSize() / 10f - 1f) * 0.65f;
    }
}

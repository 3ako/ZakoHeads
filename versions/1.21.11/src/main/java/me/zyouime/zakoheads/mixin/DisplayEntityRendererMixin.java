package me.zyouime.zakoheads.mixin;

import me.zyouime.zakoheads.client.ZakoHeadsClient;
import me.zyouime.zakoheads.config.ModConfig;
import me.zyouime.zakoheads.config.data.SizeData;
import me.zyouime.zakoheads.util.PvPUtil;
import me.zyouime.zakoheads.util.helper.DisplayEntityRenderStateHelper;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.DisplayEntityRenderer;
import net.minecraft.client.render.entity.state.DisplayEntityRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.decoration.DisplayEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisplayEntityRenderer.class)
public abstract class DisplayEntityRendererMixin {

    @Unique private final ModConfig modConfig = ZakoHeadsClient.getInstance().modConfig;

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/decoration/DisplayEntity;Lnet/minecraft/client/render/entity/state/DisplayEntityRenderState;F)V", at = @At("TAIL"))
    private void updateHeadYOffset(DisplayEntity displayEntity, DisplayEntityRenderState state, float f, CallbackInfo ci) {
        ((DisplayEntityRenderStateHelper) state).setHeadYOffset(headYOffset(displayEntity));
    }

    @Inject(method = "render(Lnet/minecraft/client/render/entity/state/DisplayEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V", at = @At("HEAD"))
    private void pushHeadYOffset(DisplayEntityRenderState state, MatrixStack matrixStack, OrderedRenderCommandQueue queue, CameraRenderState cameraRenderState, CallbackInfo ci) {
        matrixStack.push();
        matrixStack.translate(0, ((DisplayEntityRenderStateHelper) state).headYOffset(), 0);
    }

    @Inject(method = "render(Lnet/minecraft/client/render/entity/state/DisplayEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/command/OrderedRenderCommandQueue;Lnet/minecraft/client/render/state/CameraRenderState;)V", at = @At("RETURN"))
    private void popHeadYOffset(DisplayEntityRenderState state, MatrixStack matrixStack, OrderedRenderCommandQueue queue, CameraRenderState cameraRenderState, CallbackInfo ci) {
        matrixStack.pop();
    }

    @Unique
    private float headYOffset(DisplayEntity displayEntity) {
        if (!modConfig.enabled || !(displayEntity.getVehicle() instanceof PlayerEntity player)) {
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

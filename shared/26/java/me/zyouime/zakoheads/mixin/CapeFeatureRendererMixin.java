package me.zyouime.zakoheads.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import me.zyouime.zakoheads.client.ZakoHeadsClient;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeLayer.class)
public abstract class CapeFeatureRendererMixin extends RenderLayer<AvatarRenderState, PlayerModel> {

    public CapeFeatureRendererMixin(RenderLayerParent<AvatarRenderState, PlayerModel> context) {
        super(context);
    }

    @Inject(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/AvatarRenderState;FF)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = At.Shift.AFTER))
    private void modifyZ(PoseStack matrixStack, SubmitNodeCollector orderedRenderCommandQueue, int i, AvatarRenderState playerEntityRenderState, float f, float g, CallbackInfo ci) {
        if (checkModStateAndModel()) {
            PlayerModel model = this.getParentModel();
            float bodyScale = model.body.xScale;
            float zOffset = ((bodyScale - 1) * 1.2f) / 10f;
            matrixStack.translate(0, 0, zOffset);
        }
    }

    @WrapOperation(method = "submit(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/client/renderer/entity/state/AvatarRenderState;FF)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
    private void translateStack(PoseStack instance, float x, float y, float z, Operation<Void> original) {
        if (checkModStateAndModel()) {
            PlayerModel model = this.getParentModel();
            z *= model.body.xScale;
        }
        original.call(instance, x, y, z);
    }

    @Unique
    private boolean checkModStateAndModel() {
        PlayerModel model = this.getParentModel();
        return ZakoHeadsClient.getInstance().modConfig.enabled && model != null && model.body != null;
    }
}

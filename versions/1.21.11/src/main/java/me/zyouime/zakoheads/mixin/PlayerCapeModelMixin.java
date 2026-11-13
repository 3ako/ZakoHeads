package me.zyouime.zakoheads.mixin;

import me.zyouime.zakoheads.client.ZakoHeadsClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.PlayerCapeModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerCapeModel.class)
public abstract class PlayerCapeModelMixin extends PlayerEntityModel {

    public PlayerCapeModelMixin(ModelPart root, boolean thinArms) {
        super(root, thinArms);
    }

    @Inject(method = "setAngles(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/PlayerEntityModel;setAngles(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;)V", shift = At.Shift.AFTER))
    private void setAngles(PlayerEntityRenderState playerEntityRenderState, CallbackInfo ci) {
        if (ZakoHeadsClient.getInstance().modConfig.enabled) {
            this.resetTransforms();
        }
    }
}

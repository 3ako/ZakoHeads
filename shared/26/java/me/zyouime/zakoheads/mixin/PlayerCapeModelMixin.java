package me.zyouime.zakoheads.mixin;

import me.zyouime.zakoheads.client.ZakoHeadsClient;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.player.PlayerCapeModel;
import net.minecraft.client.model.player.PlayerModel;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerCapeModel.class)
public abstract class PlayerCapeModelMixin extends PlayerModel {

    public PlayerCapeModelMixin(ModelPart root, boolean thinArms) {
        super(root, thinArms);
    }

    @Inject(method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/player/PlayerModel;setupAnim(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;)V", shift = At.Shift.AFTER))
    private void setAngles(AvatarRenderState playerEntityRenderState, CallbackInfo ci) {
        if (ZakoHeadsClient.getInstance().modConfig.enabled) {
            this.resetPose();
        }
    }
}

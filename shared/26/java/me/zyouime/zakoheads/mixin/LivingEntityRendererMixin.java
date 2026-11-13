package me.zyouime.zakoheads.mixin;

import me.zyouime.zakoheads.client.ZakoHeadsClient;
import me.zyouime.zakoheads.config.ModConfig;
import me.zyouime.zakoheads.util.PvPUtil;
import me.zyouime.zakoheads.util.SizeType;
import me.zyouime.zakoheads.util.helper.PlayerEntityRenderStateHelper;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>>
        extends EntityRenderer<T, S>
        implements RenderLayerParent<S, M>{

    @Unique private final ModConfig modConfig = ZakoHeadsClient.getInstance().modConfig;

    protected LivingEntityRendererMixin(EntityRendererProvider.Context context) {
        super(context);
    }

    @Inject(method = "isBodyVisible", at = @At("RETURN"))
    private void isVisible(S state, CallbackInfoReturnable<Boolean> cir) {
        if (state instanceof AvatarRenderState && !cir.getReturnValue()) {
            ((PlayerEntityRenderStateHelper) state).setSizeType(SizeType.DEFAULT);
        }
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
    private void updateZakoState(T livingEntity, S state, float f, CallbackInfo ci) {
        if (livingEntity instanceof Player player && state instanceof AvatarRenderState playerState) {
            PlayerEntityRenderStateHelper stateHelper = (PlayerEntityRenderStateHelper) playerState;
            if (modConfig.enabled && (!modConfig.defaultPartsInPvP || !PvPUtil.isPvP())) {
                if (ZakoHeadsClient.checkIsYourSize(player)) {
                    stateHelper.setSizeType(SizeType.YOUR);
                } else {
                    stateHelper.setSizeType(SizeType.OTHER);
                }
            } else {
                stateHelper.setSizeType(SizeType.DEFAULT);
            }
        }
    }
}

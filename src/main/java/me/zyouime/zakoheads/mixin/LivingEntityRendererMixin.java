package me.zyouime.zakoheads.mixin;

import me.zyouime.zakoheads.client.ZakoHeadsClient;
import me.zyouime.zakoheads.config.ModConfig;
import me.zyouime.zakoheads.util.PvPUtil;
import me.zyouime.zakoheads.util.SizeType;
import me.zyouime.zakoheads.util.helper.PlayerEntityRenderStateHelper;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>>
        extends EntityRenderer<T, S>
        implements FeatureRendererContext<S, M>{

    @Unique private final ModConfig modConfig = ZakoHeadsClient.getInstance().modConfig;

    protected LivingEntityRendererMixin(EntityRendererFactory.Context context) {
        super(context);
    }

    @Inject(method = "isVisible", at = @At("RETURN"))
    private void isVisible(S state, CallbackInfoReturnable<Boolean> cir) {
        if (state instanceof PlayerEntityRenderState && !cir.getReturnValue()) {
            ((PlayerEntityRenderStateHelper) state).setSizeType(SizeType.DEFAULT);
        }
    }

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At("TAIL"))
    private void updateZakoState(T livingEntity, S state, float f, CallbackInfo ci) {
        if (modConfig.enabled && livingEntity instanceof PlayerEntity player && state instanceof PlayerEntityRenderState playerState) {
            PlayerEntityRenderStateHelper stateHelper = (PlayerEntityRenderStateHelper) playerState;
            if (!modConfig.defaultPartsInPvP || !PvPUtil.isPvP()) {
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

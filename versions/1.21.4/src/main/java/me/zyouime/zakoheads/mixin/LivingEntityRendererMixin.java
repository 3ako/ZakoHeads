package me.zyouime.zakoheads.mixin;

import me.zyouime.zakoheads.client.ZakoHeadsClient;
import me.zyouime.zakoheads.config.ModConfig;
import me.zyouime.zakoheads.util.helper.LivingEntityRendererHelper;
import me.zyouime.zakoheads.util.helper.PlayerEntityModelHelper;
import me.zyouime.zakoheads.util.PvPUtil;
import me.zyouime.zakoheads.util.SizeType;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, S extends LivingEntityRenderState, M extends EntityModel<? super S>> extends EntityRenderer<T, S> implements FeatureRendererContext<S, M>, LivingEntityRendererHelper {

    @Shadow protected M model;
    private final ModConfig modConfig = ZakoHeadsClient.getInstance().modConfig;
    private PlayerEntity currentEntity;

    protected LivingEntityRendererMixin(EntityRendererFactory.Context context) {
        super(context);
    }

    @Inject(method = "isVisible", at = @At("RETURN"))
    private void isVisible(S state,CallbackInfoReturnable<Boolean> cir) {
        if (state instanceof PlayerEntityRenderState && !cir.getReturnValue()) {
            ((PlayerEntityModelHelper) this.model).setDefaultSize();
        }
    }

    @Redirect(method = "render(Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;III)V"))
    private void redirectRender(EntityModel<S> instance, MatrixStack matrixStack, VertexConsumer vertexConsumer, int i, int j, int k) {
        if (currentEntity != null && instance instanceof PlayerEntityModel) {
            PlayerEntityModelHelper modelHelper = (PlayerEntityModelHelper) instance;
            if (modConfig.enabled && (!modConfig.defaultPartsInPvP || !PvPUtil.isPvP())) {
                if (ZakoHeadsClient.checkIsYourSize(currentEntity)) {
                    modelHelper.setPartsSize(SizeType.YOUR);
                } else {
                    modelHelper.setPartsSize(SizeType.OTHER);
                }
            } else {
                modelHelper.setDefaultSize();
            }
        }
        instance.render(matrixStack, vertexConsumer, i, j, k);
    }

    @Inject(method = "updateRenderState(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/render/entity/state/LivingEntityRenderState;F)V", at = @At("HEAD"))
    private void updateRenderState(T livingEntity, S livingEntityRenderState, float f, CallbackInfo ci) {
        if (livingEntity instanceof PlayerEntity) {
            this.currentEntity = (PlayerEntity) livingEntity;
        }
    }

    @Override
    public PlayerEntity getEntity() {
        return this.currentEntity;
    }
}

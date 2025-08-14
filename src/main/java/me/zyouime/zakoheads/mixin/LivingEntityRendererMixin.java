package me.zyouime.zakoheads.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.zyouime.zakoheads.client.ZakoHeadsClient;
import me.zyouime.zakoheads.config.ModConfig;
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
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {

    @Shadow protected M model;
    private final ModConfig config = ZakoHeadsClient.getInstance().config;

    protected LivingEntityRendererMixin(EntityRendererFactory.Context context) {
        super(context);
    }

    @Inject(method = "isVisible", at = @At("RETURN"))
    private void isVisible(T entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof PlayerEntity && !cir.getReturnValue()) {
            ((PlayerEntityModelHelper) this.model).setToDefaultSize();
        }
    }

    @Redirect(method = "render(Lnet/minecraft/entity/LivingEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/model/EntityModel;render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V"))
    private void renderModel(EntityModel<T> instance, MatrixStack matrixStack, VertexConsumer vertexConsumer, int i, int j, float v, float b, float n, float m, @Local(argsOnly = true) T livingEntity) {
        if (livingEntity instanceof PlayerEntity player && instance instanceof PlayerEntityModel) {
            PlayerEntityModelHelper modelHelper = (PlayerEntityModelHelper) instance;
            if (config.enabled && (!config.defaultPartsInPvP || !PvPUtil.isPvP())) {
                if (ZakoHeadsClient.checkIsYourSize(player)) {
                    modelHelper.setPartsSize(SizeType.YOUR);
                } else {
                    modelHelper.setPartsSize(SizeType.OTHER);
                }
            } else {
                modelHelper.setToDefaultSize();
            }
        }
        instance.render(matrixStack, vertexConsumer, i, j, v, b, n, m);
    }
}

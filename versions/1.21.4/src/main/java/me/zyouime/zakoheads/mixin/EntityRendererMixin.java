package me.zyouime.zakoheads.mixin;

import me.zyouime.zakoheads.client.ZakoHeadsClient;
import me.zyouime.zakoheads.config.ModConfig;
import me.zyouime.zakoheads.util.PvPUtil;
import me.zyouime.zakoheads.util.helper.LivingEntityRendererHelper;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity, S extends EntityRenderState> {

    private EntityRenderer<T, S> renderer = (EntityRenderer) (Object) this;
    private final ModConfig modConfig = ZakoHeadsClient.getInstance().modConfig;

    @ModifyArg(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(DDD)V"), index = 1)
    private double modifyY(double y) {
        if (renderer instanceof PlayerEntityRenderer && modConfig.enabled && (!modConfig.defaultPartsInPvP || !PvPUtil.isPvP())) {
            LivingEntityRendererHelper helper = (LivingEntityRendererHelper) renderer;
            float headSize;
            if (helper.getEntity() != null && ZakoHeadsClient.checkIsYourSize(helper.getEntity())) {
                headSize = modConfig.yourSize.yourHeadSize;
            } else {
                headSize = modConfig.otherSize.headSize;
            }
            return y + (headSize / 10f - 1f) / 2f;
        }
        return y;
    }
}

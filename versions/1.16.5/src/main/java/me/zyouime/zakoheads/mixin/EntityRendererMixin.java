package me.zyouime.zakoheads.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import me.zyouime.zakoheads.client.ZakoHeadsClient;
import me.zyouime.zakoheads.config.ModConfig;
import me.zyouime.zakoheads.util.PvPUtil;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity> {

    private final ModConfig config = ZakoHeadsClient.getInstance().config;

    @Redirect(method = "renderLabelIfPresent", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;translate(DDD)V"))
    private void modifyY(MatrixStack instance, double x, double y, double z, @Local(argsOnly = true) T entity) {
        if (entity instanceof PlayerEntity && config.enabled && (!config.defaultPartsInPvP || !PvPUtil.isPvP())) {
            PlayerEntity player = (PlayerEntity) entity;
            float headSize;
            if (ZakoHeadsClient.checkIsYourSize(player)) {
                headSize = config.yourSize.yourHeadSize;
            } else {
                headSize = config.otherSize.headSize;
            }
            y += (headSize / 10f - 1f) / 2f;
        }
        instance.translate(0, y, 0);
    }
}

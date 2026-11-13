package me.zyouime.zakoheads.mixin;

import me.zyouime.zakoheads.util.helper.ModelPartHelper;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelPart.class)
public abstract class ModelPartMixin implements ModelPartHelper {

    private float scaleX = 1;
    private float scaleY = 1;
    private float scaleZ = 1;

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumer;IIFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/ModelPart;rotate(Lnet/minecraft/client/util/math/MatrixStack;)V", shift = At.Shift.AFTER))
    private void applyScale(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha, CallbackInfo ci) {
        matrices.scale(scaleX, scaleY, scaleZ);
    }

    @Override
    public void setX(float scaleX) {
        this.scaleX = scaleX;
    }

    @Override
    public void setY(float scaleY) {
        this.scaleY = scaleY;
    }

    @Override
    public void setZ(float scaleZ) {
        this.scaleZ = scaleZ;
    }

    @Override
    public float getScaleX() {
        return scaleX;
    }
}

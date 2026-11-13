package me.zyouime.zakoheads.mixin;

import me.zyouime.zakoheads.client.ZakoHeadsClient;
import me.zyouime.zakoheads.config.ModConfig;
import me.zyouime.zakoheads.util.helper.PlayerEntityRenderStateHelper;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

@Mixin(PlayerEntityModel.class)
public abstract class PlayerEntityModelMixin extends BipedEntityModel<PlayerEntityRenderState> {

    @Shadow @Final private List<ModelPart> parts;
    @Unique private final ModConfig modConfig = ZakoHeadsClient.getInstance().modConfig;
    @Unique private final Map<ModelPart, BiConsumer<PlayerEntityRenderStateHelper, ModelPart>> MODELS_SIZE = Map.of(
            this.head, (state, model) -> model.yScale = setSize(model, state.headScale()),
            this.body, (state, model) -> setSize(model, state.bodyScale()),
            this.leftArm, (state, model) -> {
                float f = setSize(model, state.leftArmScale());
                model.originX = 5.0F + (f - 1) + (state.bodyScale() - 1) * 4;
            },
            this.rightArm, (state, model) -> {
                float f = setSize(model, state.rightArmScale());
                model.originX = -5.0F - (f - 1) - (state.bodyScale() - 1) * 4;
            },
            this.leftLeg, (state, model) -> {
                float f = setSize(model, state.leftLegScale());
                model.originX = 1.9F + (f - 1) + (state.bodyScale() - 1) * 1.9f;
            },
            this.rightLeg, (state, model) -> {
                float f = setSize(model, state.rightLegScale());
                model.originX = -1.9F - (f - 1) - (state.bodyScale() - 1) * 1.9f;
            }
    );

    public PlayerEntityModelMixin(ModelPart modelPart) {
        super(modelPart);
    }

    @Inject(method = "setAngles(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;)V", at = @At("TAIL"))
    private void setAngles(PlayerEntityRenderState renderState, CallbackInfo ci) {
        if (!modConfig.enabled) {
            return;
        }
        PlayerEntityRenderStateHelper helper = (PlayerEntityRenderStateHelper) renderState;
        for (ModelPart part : parts) {
            MODELS_SIZE.get(part).accept(helper, part);
        }
    }

    @Unique
    private float setSize(ModelPart part, float size) {
        part.xScale = size;
        part.zScale = size;
        return size;
    }
}

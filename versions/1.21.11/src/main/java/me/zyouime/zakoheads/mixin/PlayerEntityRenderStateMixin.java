package me.zyouime.zakoheads.mixin;

import me.zyouime.zakoheads.client.ZakoHeadsClient;
import me.zyouime.zakoheads.config.ModConfig;
import me.zyouime.zakoheads.config.data.SizeData;
import me.zyouime.zakoheads.util.SizeType;
import me.zyouime.zakoheads.util.helper.PlayerEntityRenderStateHelper;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public abstract class PlayerEntityRenderStateMixin implements PlayerEntityRenderStateHelper {

    @Unique public SizeType sizeType = SizeType.OTHER;

    @Override
    public void setSizeType(SizeType sizeType) {
        this.sizeType = sizeType;
    }

    @Override
    public float bodyScale() {
        return getData().getBodySize() / 10f;
    }

    @Override
    public float headScale() {
        return getData().getHeadSize() / 10f;
    }

    @Override
    public float leftArmScale() {
        return getData().getLeftArmSize() / 10f;
    }

    @Override
    public float rightArmScale() {
        return getData().getRightArmSize() / 10f;
    }

    @Override
    public float leftLegScale() {
        return getData().getLeftLegSize() / 10f;
    }

    @Override
    public float rightLegScale() {
        return getData().getRightLegSize() / 10f;
    }

    @Unique
    private SizeData getData() {
        ModConfig config = ZakoHeadsClient.getInstance().modConfig;
        return sizeType == SizeType.OTHER ? config.otherSize : sizeType == SizeType.YOUR ? config.yourSize : config.defaultSize;
    }
}

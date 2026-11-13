package me.zyouime.zakoheads.mixin;

import me.zyouime.zakoheads.client.ZakoHeadsClient;
import me.zyouime.zakoheads.config.ModConfig;
import me.zyouime.zakoheads.config.data.ModDataHelper;
import me.zyouime.zakoheads.util.helper.PlayerEntityModelHelper;
import me.zyouime.zakoheads.util.SizeType;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mixin(PlayerEntityModel.class)
public abstract class PlayerEntityModelMixin extends BipedEntityModel<PlayerEntityRenderState> implements PlayerEntityModelHelper {

    @Shadow @Final private List<ModelPart> parts;
    private ModConfig modConfig = ZakoHeadsClient.getInstance().modConfig;

    public PlayerEntityModelMixin(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public void setPartsSize(SizeType type) {
        for (ModelPart m : parts) {
            setPartSize(m, getSize(m, type));
        }
    }

    private void setPartSize(ModelPart m, float size) {
        m.xScale = size;
        if (m == this.head) {
            m.yScale = size;
        }
        m.zScale = size;
        float f = this.body.xScale;
        float off = m.originX;
        if (m == this.rightArm) {
            off = -5.0F - (size - 1) - (f - 1) * 4;
        } else if (m == this.leftArm) {
            off = 5.0F + (size - 1) + (f - 1) * 4;
        } else if (m == this.rightLeg) {
            off = -1.9F - (size - 1) - (f - 1) * 1.9f;
        } else if (m == this.leftLeg) {
            off = 1.9F + (size - 1) + (f - 1) * 1.9f;
        }
        m.originX = off;
    }

    @Override
    public void setDefaultSize() {
        for (ModelPart m : parts) {
            setPartSize(m, 1f);
        }
    }

    private float getSize(ModelPart part, SizeType type) {
        ModDataHelper data = type == SizeType.YOUR ? modConfig.yourSize : modConfig.otherSize;
        float returnSize = 10f;
        if (part == this.head) {
            returnSize = data.getHeadSize();
        }
        if (part == this.rightArm) {
            returnSize = data.getRightArmSize();
        }
        if (part == this.leftArm) {
            returnSize = data.getLeftArmSize();
        }
        if (part == this.rightLeg) {
            returnSize = data.getRightLegSize();
        }
        if (part == this.leftLeg) {
            returnSize = data.getLeftLegSize();
        }
        if (part == this.body) {
            returnSize = data.getBodySize();
        }
        return returnSize / 10f;
    }
}

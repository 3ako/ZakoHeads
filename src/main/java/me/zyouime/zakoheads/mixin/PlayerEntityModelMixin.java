package me.zyouime.zakoheads.mixin;

import me.zyouime.zakoheads.client.ZakoHeadsClient;
import me.zyouime.zakoheads.config.ModConfig;
import me.zyouime.zakoheads.config.data.ModDataHelper;
import me.zyouime.zakoheads.util.helper.PlayerEntityModelHelper;
import me.zyouime.zakoheads.util.SizeType;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Mixin(PlayerEntityModel.class)
public abstract class PlayerEntityModelMixin<T extends LivingEntity> extends BipedEntityModel<T> implements PlayerEntityModelHelper {

    @Shadow @Final private List<ModelPart> parts;
    @Shadow @Final private ModelPart ear;
    @Shadow @Final private ModelPart cloak;
    @Shadow @Final public ModelPart rightPants;
    @Shadow @Final public ModelPart rightSleeve;
    @Shadow @Final public ModelPart leftSleeve;
    @Shadow @Final public ModelPart leftPants;
    @Shadow @Final public ModelPart jacket;
    private final ModConfig config = ZakoHeadsClient.getInstance().config;

    public PlayerEntityModelMixin(ModelPart modelPart) {
        super(modelPart);
    }

    @Override
    public void setPartsSize(SizeType type) {
        float bodyScale = getSize(this.body, type);
        for (ModelPart m : parts) {
            setPartSize(m, getSize(m, type), bodyScale);
        }
    }

    private void setPartSize(ModelPart m, float size, float bodyScale) {
        m.xScale = size;
        if (isHead(m)) {
            m.yScale = size;
        }
        m.zScale = size;
        float off = m.pivotX;
        if (isRightArm(m)) {
            off = -5.0F - (size - 1) - (bodyScale - 1) * 4;
        } else if (isLeftArm(m)) {
            off = 5.0F + (size - 1) + (bodyScale - 1) * 4;
        } else if (isRightLeg(m)) {
            off = -1.9F - (size - 1) - (bodyScale - 1) * 1.9f;
        } else if (isLeftLeg(m)) {
            off = 1.9F + (size - 1) + (bodyScale - 1) * 1.9f;
        }
        m.pivotX = off;
    }

    @Override
    public void setToDefaultSize() {
        for (ModelPart m : parts) {
            setPartSize(m, 1f, 1f);
        }
    }

    private float getSize(ModelPart part, SizeType type) {
        ModDataHelper data = type == SizeType.YOUR ? config.yourSize : config.otherSize;
        float returnSize = 10f;
        if (isHead(part)) {
            returnSize = data.getHeadSize();
        }
        if (isRightArm(part)) {
            returnSize = data.getRightArmSize();
        }
        if (isLeftArm(part)) {
            returnSize = data.getLeftArmSize();
        }
        if (isRightLeg(part)) {
            returnSize = data.getRightLegSize();
        }
        if (isLeftLeg(part)) {
            returnSize = data.getLeftLegSize();
        }
        if (isBody(part)) {
            returnSize = data.getBodySize();
        }
        return returnSize / 10f;
    }

    private boolean isHead(ModelPart part) {
        return part == this.head || part == this.hat || part == this.ear;
    }

    private boolean isRightArm(ModelPart part) {
        return part == this.rightArm || part == rightSleeve;
    }

    private boolean isLeftArm(ModelPart part) {
        return part == this.leftArm || part == leftSleeve;
    }

    private boolean isRightLeg(ModelPart part) {
        return part == this.rightLeg || part == rightPants;
    }

    private boolean isLeftLeg(ModelPart part) {
        return part == this.leftLeg || part == leftPants;
    }

    private boolean isBody(ModelPart part) {
        return part == this.body || part == jacket;
    }
}

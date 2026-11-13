package me.zyouime.zakoheads.config.data;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class OtherSizeData implements ModDataHelper{

    @ConfigEntry.BoundedDiscrete(min=5, max=25)
    public int headSize = 20;
    @ConfigEntry.BoundedDiscrete(min=5, max=25)
    public int rightArmSize = 10;
    @ConfigEntry.BoundedDiscrete(min=5, max=25)
    public int leftArmSize = 10;
    @ConfigEntry.BoundedDiscrete(min=5, max=25)
    public int rightLegSize = 10;
    @ConfigEntry.BoundedDiscrete(min=5, max=25)
    public int leftLegSize = 10;
    @ConfigEntry.BoundedDiscrete(min=5, max=25)
    public int bodySize = 10;

    @Override
    public int getBodySize() {
        return bodySize;
    }

    @Override
    public int getHeadSize() {
        return headSize;
    }

    @Override
    public int getRightArmSize() {
        return rightArmSize;
    }

    @Override
    public int getLeftArmSize() {
        return leftArmSize;
    }

    @Override
    public int getRightLegSize() {
        return rightLegSize;
    }

    @Override
    public int getLeftLegSize() {
        return leftLegSize;
    }
}

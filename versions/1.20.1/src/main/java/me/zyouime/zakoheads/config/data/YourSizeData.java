package me.zyouime.zakoheads.config.data;

import me.shedaniel.autoconfig.annotation.ConfigEntry;

public class YourSizeData implements ModDataHelper{

    public boolean enableYourSize = false;
    @ConfigEntry.BoundedDiscrete(min=5, max=25)
    public int yourHeadSize = 20;
    @ConfigEntry.BoundedDiscrete(min=5, max=25)
    public int yourRightArmSize = 10;
    @ConfigEntry.BoundedDiscrete(min=5, max=25)
    public int yourLeftArmSize = 10;
    @ConfigEntry.BoundedDiscrete(min=5, max=25)
    public int yourRightLegSize = 10;
    @ConfigEntry.BoundedDiscrete(min=5, max=25)
    public int yourLeftLegSize = 10;
    @ConfigEntry.BoundedDiscrete(min=5, max=25)
    public int yourBodySize = 10;

    @Override
    public int getBodySize() {
        return yourBodySize;
    }

    @Override
    public int getHeadSize() {
        return yourHeadSize;
    }

    @Override
    public int getRightArmSize() {
        return yourRightArmSize;
    }

    @Override
    public int getLeftArmSize() {
        return yourLeftArmSize;
    }

    @Override
    public int getRightLegSize() {
        return yourRightLegSize;
    }

    @Override
    public int getLeftLegSize() {
        return yourLeftLegSize;
    }
}

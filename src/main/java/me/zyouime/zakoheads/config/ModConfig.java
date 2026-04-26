package me.zyouime.zakoheads.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.zyouime.zakoheads.config.data.DefaultSizeData;
import me.zyouime.zakoheads.config.data.OtherSizeData;
import me.zyouime.zakoheads.config.data.YourSizeData;

@Config(name = "zakoheads")
public class ModConfig implements ConfigData {

    @ConfigEntry.Gui.CollapsibleObject
    public YourSizeData yourSize = new YourSizeData();
    @ConfigEntry.Gui.CollapsibleObject
    public OtherSizeData otherSize = new OtherSizeData();
    @ConfigEntry.Gui.Excluded
    public transient DefaultSizeData defaultSize = new DefaultSizeData();
    public boolean enabled = true;
    public boolean defaultPartsInPvP = false;
}

package me.zyouime.zakoheads.platform;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.BossHealthOverlay;

public final class MinecraftAccess {
    private MinecraftAccess() {
    }

    public static BossHealthOverlay bossOverlay() {
        return Minecraft.getInstance().gui.hud.getBossOverlay();
    }
}

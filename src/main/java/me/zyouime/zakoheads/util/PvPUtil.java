package me.zyouime.zakoheads.util;

import me.zyouime.zakoheads.mixin.BossBarHudAccessor;
import net.minecraft.client.MinecraftClient;

import java.util.Arrays;

public class PvPUtil {

    private static final String[] PVP_TYPES = new String[]{"pvp", "пвп", "режим боя"};

    public static boolean isPvP() {
        BossBarHudAccessor bossBar = (BossBarHudAccessor) MinecraftClient.getInstance().inGameHud.getBossBarHud();
        return bossBar.getBossBars().values().stream().anyMatch(boss -> Arrays.stream(PVP_TYPES).anyMatch(pvpType -> boss.getName().getString().toLowerCase().contains(pvpType)));
    }
}

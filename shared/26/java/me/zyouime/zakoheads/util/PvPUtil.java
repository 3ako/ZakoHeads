package me.zyouime.zakoheads.util;

import me.zyouime.zakoheads.mixin.BossBarHudAccessor;
import me.zyouime.zakoheads.platform.MinecraftAccess;
import java.util.Locale;
import java.util.Arrays;

public class PvPUtil {

    private static final String[] PVP_TYPES = new String[]{"pvp", "пвп", "режим боя"};

    public static boolean isPvP() {
        BossBarHudAccessor bossBar = (BossBarHudAccessor) MinecraftAccess.bossOverlay();
        return bossBar.getBossBars().values().stream().anyMatch(boss -> Arrays.stream(PVP_TYPES).anyMatch(pvpType -> boss.getName().getString().toLowerCase(Locale.ROOT).contains(pvpType)));
    }
}

package me.zyouime.zakoheads.client;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.zyouime.zakoheads.config.ModConfig;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;

public class ZakoHeadsClient implements ClientModInitializer {

    public ModConfig modConfig;
    private static ZakoHeadsClient instance;

    @Override
    public void onInitializeClient() {
        instance = this;
        AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
        modConfig = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
    }

    public static ZakoHeadsClient getInstance() {
        return instance;
    }

    public static boolean checkIsYourSize(PlayerEntity player) {
        return ZakoHeadsClient.instance.modConfig.yourSize.enableYourSize && player instanceof ClientPlayerEntity;
    }
}

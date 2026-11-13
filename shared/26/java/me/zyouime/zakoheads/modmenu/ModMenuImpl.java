package me.zyouime.zakoheads.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfigClient;
import me.zyouime.zakoheads.config.ModConfig;

public class ModMenuImpl implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return f -> AutoConfigClient.getConfigScreen(ModConfig.class, f).get();
    }
}

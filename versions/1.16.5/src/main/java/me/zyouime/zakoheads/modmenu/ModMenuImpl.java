package me.zyouime.zakoheads.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import me.zyouime.zakoheads.config.ModConfig;

public class ModMenuImpl implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return f -> AutoConfig.getConfigScreen(ModConfig.class, f).get();
    }
}

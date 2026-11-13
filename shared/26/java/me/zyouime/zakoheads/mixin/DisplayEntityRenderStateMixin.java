package me.zyouime.zakoheads.mixin;

import me.zyouime.zakoheads.util.helper.DisplayEntityRenderStateHelper;
import net.minecraft.client.renderer.entity.state.DisplayEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(DisplayEntityRenderState.class)
public abstract class DisplayEntityRenderStateMixin implements DisplayEntityRenderStateHelper {

    @Unique public float headYOffset;

    @Override
    public void setHeadYOffset(float headYOffset) {
        this.headYOffset = headYOffset;
    }

    @Override
    public float headYOffset() {
        return headYOffset;
    }
}

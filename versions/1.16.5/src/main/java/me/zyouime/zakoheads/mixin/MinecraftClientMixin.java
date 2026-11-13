package me.zyouime.zakoheads.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "isOnlineChatEnabled", at = @At("RETURN"), cancellable = true)
    private void isOnlineChatEnabled(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "isMultiplayerEnabled", at = @At("RETURN"), cancellable = true)
    private void isOnlineChatEnabled1(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}

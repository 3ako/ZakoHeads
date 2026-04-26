package me.zyouime.zakoheads.util.helper;

import me.zyouime.zakoheads.util.SizeType;

public interface PlayerEntityRenderStateHelper {

    void setSizeType(SizeType sizeType);
    float bodyScale();
    float headScale();
    float rightArmScale();
    float leftArmScale();
    float rightLegScale();
    float leftLegScale();
}

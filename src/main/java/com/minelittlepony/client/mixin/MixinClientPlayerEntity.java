package com.minelittlepony.client.mixin;

import com.minelittlepony.client.MineLittlePony;
import com.minelittlepony.pony.IPony;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayerEntity.class)
abstract class MixinClientPlayerEntity extends AbstractClientPlayerEntity {
    public MixinClientPlayerEntity() { super(null, null); }

    @Override
    public float getActiveEyeHeight(EntityPose entityPose_1, EntityDimensions entitySize_1) {
        float value = super.getActiveEyeHeight(entityPose_1, entitySize_1);

        IPony pony = MineLittlePony.getInstance().getManager().getPony(this);

        if (!pony.getRace(false).isHuman()) {
            value *= pony.getMetadata().getSize().getEyeHeightFactor();
        }

        return value;
    }
}

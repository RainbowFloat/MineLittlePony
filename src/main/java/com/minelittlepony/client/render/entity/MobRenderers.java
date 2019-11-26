package com.minelittlepony.client.render.entity;

import com.minelittlepony.client.MineLittlePony;
import com.minelittlepony.client.PonyRenderManager;
import com.minelittlepony.client.render.entity.villager.*;
import com.minelittlepony.common.util.settings.Setting;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;

import net.minecraft.entity.EntityType;

/**
 * Central location where new entity renderers are registered and applied.
 */
public enum MobRenderers {
    VILLAGERS((state, pony) -> {
        pony.switchRenderer(state, EntityType.VILLAGER, RenderPonyVillager::new);
        pony.switchRenderer(state, EntityType.WITCH, RenderPonyWitch::new);
        pony.switchRenderer(state, EntityType.ZOMBIE_VILLAGER, RenderPonyZombieVillager::new);
        pony.switchRenderer(state, EntityType.WANDERING_TRADER, RenderPonyTrader::new);
    }),
    ILLAGERS((state, pony) -> {
        pony.switchRenderer(state, EntityType.VEX, RenderPonyVex::new);
        pony.switchRenderer(state, EntityType.EVOKER, RenderPonyIllager.Evoker::new);
        pony.switchRenderer(state, EntityType.VINDICATOR, RenderPonyIllager.Vindicator::new);
        pony.switchRenderer(state, EntityType.ILLUSIONER, RenderPonyIllager.Illusionist::new);
        pony.switchRenderer(state, EntityType.PILLAGER, RenderPonyPillager::new);
    }),
    ZOMBIES((state, pony) -> {
        pony.switchRenderer(state, EntityType.ZOMBIE, RenderPonyZombie::new);
        pony.switchRenderer(state, EntityType.HUSK, RenderPonyZombie.Husk::new);
        pony.switchRenderer(state, EntityType.GIANT, RenderPonyZombie.Giant::new);
        pony.switchRenderer(state, EntityType.DROWNED, RenderPonyZombie.Drowned::new);
    }),
    PIGZOMBIES((state, pony) -> {
        pony.switchRenderer(state, EntityType.ZOMBIE_PIGMAN, RenderPonyZombie.Pigman::new);
    }),
    SKELETONS((state, pony) -> {
        pony.switchRenderer(state, EntityType.SKELETON, RenderPonySkeleton::new);
        pony.switchRenderer(state, EntityType.STRAY, RenderPonySkeleton.Stray::new);
        pony.switchRenderer(state, EntityType.WITHER_SKELETON, RenderPonySkeleton.Wither::new);
    }),
    GUARDIANS((state, pony) -> {
        pony.switchRenderer(state, EntityType.GUARDIAN, RenderPonyGuardian::new);
        pony.switchRenderer(state, EntityType.ELDER_GUARDIAN, RenderPonyGuardian.Elder::new);
    }),
    ENDERMEN((state, pony) -> {
        pony.switchRenderer(state, EntityType.ENDERMAN, RenderEnderStallion::new);
    });

    public static final List<MobRenderers> registry = Arrays.asList(values());

    private final BiConsumer<Boolean, PonyRenderManager> changer;

    MobRenderers(BiConsumer<Boolean, PonyRenderManager> changer) {
        this.changer = changer;
    }

    public Setting<Boolean> option() {
        return MineLittlePony.getInstance().getConfig().<Boolean>get(name().toLowerCase());
    }

    public boolean set(boolean value) {
        value = option().set(value);
        apply(PonyRenderManager.getInstance());
        return value;
    }

    public boolean get() {
        return option().get();
    }

    public void apply(PonyRenderManager pony) {
        boolean state = get();
        changer.accept(state, pony);
        if (state) {
            MineLittlePony.logger.info(name() + " are now ponies.");
        } else {
            MineLittlePony.logger.info(name() + " are no longer ponies.");
        }
    }
}
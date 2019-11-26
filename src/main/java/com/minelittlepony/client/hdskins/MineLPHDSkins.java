package com.minelittlepony.client.hdskins;

import com.minelittlepony.client.MineLittlePony;
import com.minelittlepony.client.SkinsProxy;
import com.minelittlepony.common.event.ClientReadyCallback;
import com.minelittlepony.hdskins.SkinCacheClearCallback;
import com.minelittlepony.hdskins.dummy.DummyPlayer;
import com.minelittlepony.mson.api.Mson;

import net.minecraft.client.MinecraftClient;

import com.minelittlepony.client.pony.PonyManager;
import com.minelittlepony.hdskins.HDSkins;

/**
 * All the interactions with HD Skins.
 */
class MineLPHDSkins {

    MineLPHDSkins() {
        SkinsProxy.instance = new HDSkinsProxy();

        ClientReadyCallback.EVENT.register(this::postInit);

        // Preview on the select skin gui
        Mson.getInstance().getEntityRendererRegistry().registerEntityRenderer(DummyPlayer.TYPE, DummyPonyRenderer::new);
    }

    private void postInit(MinecraftClient minecraft) {
        HDSkins manager = HDSkins.getInstance();

        // Clear ponies when skins are cleared
        PonyManager ponyManager = (PonyManager) MineLittlePony.getInstance().getManager();
        SkinCacheClearCallback.EVENT.register(ponyManager::onSkinCacheCleared);

        // Ponify the skins GUI.
        manager.getSkinServerList().setSkinsGui(GuiSkinsMineLP::new);
    }
}

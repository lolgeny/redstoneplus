package net.lolad.redstoneplus.client

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.lolad.redstoneplus.RedstonePlus
import net.minecraft.client.render.RenderLayer

class ClientRedstonePlus: ClientModInitializer {
    override fun onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(), RedstonePlus.WALL_UNINVERTED_TORCH, RedstonePlus.UNINVERTED_TORCH)
    }
}
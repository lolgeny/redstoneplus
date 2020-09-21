package net.lolad.redstoneplus.block

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry
import net.lolad.redstoneplus.RedstonePlus
import net.lolad.redstoneplus.mixin.DyeColorAccessor
import net.minecraft.block.AbstractBlock.TypedContextPredicate
import net.minecraft.block.BlockState
import net.minecraft.block.RedstoneLampBlock
import net.minecraft.client.color.block.BlockColorProvider
import net.minecraft.entity.EntityType
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.DyeColor
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import java.util.function.ToIntFunction


class ColoredLamp(val color: DyeColor, settings: Settings) : RedstoneLampBlock(settings) {
    companion object {
        val luminance = ToIntFunction<BlockState>{ state -> if (state.get(LIT)) 15 else 0 }
        val allowsSpawning = TypedContextPredicate<EntityType<*>>{ _, _, _, _ -> true }
    }
    fun register() {
        Registry.register(Registry.BLOCK, namespacedId(), this)
        Registry.register(Registry.ITEM, namespacedId(), BlockItem(this, Item.Settings().group(ItemGroup.REDSTONE)))
        ColorProviderRegistry.BLOCK.register(tint, this)
    }
    private fun namespacedId(): Identifier {
        return Identifier(RedstonePlus.MODID, "${(color as DyeColorAccessor).getName()}_colored_lamp")
    }
    private val tint = BlockColorProvider{ _, _, _, _ -> (color as DyeColorAccessor).getColor()}
}
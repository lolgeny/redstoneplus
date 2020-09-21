package net.lolad.redstoneplus.mixin

import net.minecraft.block.BlockState
import net.minecraft.block.ComparatorBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow

// adds comparator output

@Mixin(ComparatorBlock::class)
abstract class ComparatorBlockMixin {
    @Shadow
    abstract fun calculateOutputSignal(world: World, pos: BlockPos, state: BlockState): Int

    @Override
    fun hasComparatorOutput(state: BlockState): Boolean {
        return true
    }
    @Override
    fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos): Int {
        return calculateOutputSignal(world, pos, state)
    }
}
package net.lolad.redstoneplus.mixin

import net.minecraft.block.AbstractRedstoneGateBlock
import net.minecraft.block.BlockState
import net.minecraft.block.RepeaterBlock
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.Shadow

// adds comparator output

@Mixin(RepeaterBlock::class)
abstract class RepeaterBlockMixin(settings: Settings?) : AbstractRedstoneGateBlock(settings) {
    override fun hasComparatorOutput(state: BlockState): Boolean {
        return true
    }

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos): Int {
        return getPower(world, pos, state)
    }
}
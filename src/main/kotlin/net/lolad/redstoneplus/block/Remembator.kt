package net.lolad.redstoneplus.block

import net.minecraft.block.AbstractBlock
import net.minecraft.block.AbstractRedstoneGateBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.item.ItemPlacementContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.IntProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.TickPriority
import net.minecraft.world.World
import java.util.*

class Remembator(settings: AbstractBlock.Settings): AbstractRedstoneGateBlock(settings) {
    init {
        defaultState = stateManager.defaultState.with(LEVEL, 0).with(FACING, Direction.NORTH).with(POWERED, true)
    }

    companion object {
        val LEVEL: IntProperty = IntProperty.of("level", 0, 15)
    }

    override fun getUpdateDelayInternal(state: BlockState?): Int {
        return 2
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(LEVEL)
        builder.add(FACING)
        builder.add(POWERED)
    }

    override fun hasComparatorOutput(state: BlockState?): Boolean {
        return true
    }

    override fun getComparatorOutput(state: BlockState, world: World?, pos: BlockPos?): Int {
        return state.get(LEVEL)
    }

    private fun updatePower(world: World, pos: BlockPos, state: BlockState) {
        val backPower = world.getEmittedRedstonePower(pos.offset(state.get(FACING)), state.get(FACING))
        val totalPower = world.getReceivedRedstonePower(pos)
        val level = if (backPower > 0) 0 else if (totalPower > 0) totalPower else state.get(LEVEL)
        world.setBlockState(pos, state.with(LEVEL, level))
    }

    override fun updatePowered(world: World, pos: BlockPos, state: BlockState) {
        if (!world.blockTickScheduler.isTicking(pos, this)) {
            updatePower(world, pos, state)
            world.blockTickScheduler.schedule(pos, this, 2)
        }
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        updatePowered(world, pos, state)
    }

    override fun neighborUpdate(state: BlockState?, world: World?, pos: BlockPos?, block: Block?, fromPos: BlockPos?, notify: Boolean) {
        super.neighborUpdate(state, world, pos, block, fromPos, notify)
    }


}
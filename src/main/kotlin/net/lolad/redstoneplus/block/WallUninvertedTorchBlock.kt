package net.lolad.redstoneplus.block

import net.minecraft.block.*
import net.minecraft.item.ItemPlacementContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.DirectionProperty
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.WorldView

class WallUninvertedTorchBlock(settings: AbstractBlock.Settings): UninvertedTorchblock(settings) {
    companion object {
        val FACING: DirectionProperty = HorizontalFacingBlock.FACING
    }
    init {
        defaultState = stateManager.defaultState.with(FACING, Direction.NORTH)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder?.add(FACING)
        super.appendProperties(builder)
    }

    override fun getOutlineShape(state: BlockState?, world: BlockView?, pos: BlockPos?, context: ShapeContext?): VoxelShape {
        return WallTorchBlock.getBoundingShape(state)
    }

    override fun canPlaceAt(state: BlockState?, world: WorldView?, pos: BlockPos?): Boolean {
        return Blocks.WALL_TORCH.canPlaceAt(state, world, pos)
    }

    override fun getPlacementState(ctx: ItemPlacementContext?): BlockState? {
        return defaultState.with(FACING, Blocks.WALL_TORCH.getPlacementState(ctx)?.get(FACING))
    }

    override fun getWeakRedstonePower(state: BlockState?, world: BlockView?, pos: BlockPos?, direction: Direction?): Int {
        return if(state?.get(POWERED) == true) 15 else 0
    }

    override fun updatePowered(state: BlockState?, world: ServerWorld?, pos: BlockPos?) {
        world?.setBlockState(pos, state?.with(POWERED, world.getEmittedRedstonePower(pos?.offset(state.get(FACING).opposite), state.get(FACING)) > 0))
    }

}
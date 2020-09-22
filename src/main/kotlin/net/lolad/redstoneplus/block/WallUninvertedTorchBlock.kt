package net.lolad.redstoneplus.block

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.block.*
import net.minecraft.item.ItemPlacementContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.DirectionProperty
import net.minecraft.util.BlockRotation
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World
import net.minecraft.world.WorldView
import java.util.*

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
        return defaultState.with(FACING, Blocks.WALL_TORCH.getPlacementState(ctx)?.get(FACING) ?: Direction.NORTH)
    }

    override fun rotate(state: BlockState?, rotation: BlockRotation?): BlockState {
        return Blocks.WALL_TORCH.rotate(state, rotation)
    }

    override fun updatePowered(state: BlockState?, world: ServerWorld?, pos: BlockPos?) {
        world?.setBlockState(pos, state?.with(POWERED, world.getEmittedRedstonePower(pos?.offset(state.get(FACING).opposite), state.get(FACING)) > 0))
    }

    override fun getTranslationKey(): String {
        return asItem().translationKey
    }

    @Environment(EnvType.CLIENT)
    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        if (state.get(POWERED)) {
            val direction = state.get(FACING).opposite
            val e = pos.x.toDouble() + 0.5 + (random.nextDouble() - 0.5) * 0.2 + 0.27 * direction.offsetX.toDouble()
            val f = pos.y.toDouble() + 0.7 + (random.nextDouble() - 0.5) * 0.2 + 0.22
            val g = pos.z.toDouble() + 0.5 + (random.nextDouble() - 0.5) * 0.2 + 0.27 * direction.offsetZ.toDouble()
            world.addParticle(particle, e, f, g, 0.0, 0.0, 0.0)
        }
    }

    override fun getWeakRedstonePower(state: BlockState?, world: BlockView?, pos: BlockPos?, direction: Direction?): Int {
        return super.getWeakRedstonePower(state, world, pos, direction)
    }

    override fun getStrongRedstonePower(state: BlockState?, world: BlockView?, pos: BlockPos?, direction: Direction?): Int {
        return super.getStrongRedstonePower(state, world, pos, direction)
    }
}
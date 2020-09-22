package net.lolad.redstoneplus.block

import net.lolad.redstoneplus.RedstonePlus
import net.lolad.redstoneplus.RedstonePlus.Companion.MODID
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.TorchBlock
import net.minecraft.entity.LivingEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.WallStandingBlockItem
import net.minecraft.particle.DustParticleEffect
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.*

open class UninvertedTorchblock(settings: AbstractBlock.Settings): TorchBlock(settings, DustParticleEffect.RED) {
    companion object {
        val POWERED: BooleanProperty = BooleanProperty.of("powered")
        fun register(stand: UninvertedTorchblock, wall: WallUninvertedTorchBlock) {
            Registry.register(Registry.BLOCK, Identifier(MODID, "uninverted_torch"), stand)
            Registry.register(Registry.BLOCK, Identifier(MODID, "wall_uninverted_torch"), wall)
            Registry.register(Registry.ITEM, Identifier(MODID, "uninverted_torch"), WallStandingBlockItem(stand, wall, RedstonePlus.REDGROUP))
        }
    }
    init {
        defaultState = stateManager.defaultState.with(POWERED, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder?.add(POWERED)
    }

    override fun emitsRedstonePower(state: BlockState?): Boolean {
        return true
    }

    override fun getWeakRedstonePower(state: BlockState?, world: BlockView?, pos: BlockPos?, direction: Direction?): Int {
        return if(state?.get(POWERED) == true) 15 else 0
    }

    protected open fun updatePowered(state: BlockState?, world: ServerWorld?, pos: BlockPos?) {
        world?.setBlockState(pos, state?.with(POWERED, world.getEmittedRedstonePower(pos?.offset(Direction.DOWN), Direction.UP) > 0))
    }

    override fun scheduledTick(state: BlockState?, world: ServerWorld?, pos: BlockPos?, random: Random?) {
        updatePowered(state, world, pos)
        world?.blockTickScheduler?.schedule(pos, this, 2)
    }

    override fun onPlaced(world: World?, pos: BlockPos?, state: BlockState?, placer: LivingEntity?, itemStack: ItemStack?) {
        world?.blockTickScheduler?.schedule(pos, this, 2)
        super.onPlaced(world, pos, state, placer, itemStack)
    }
}
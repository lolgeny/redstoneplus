package net.lolad.redstoneplus.block

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
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
import java.util.function.ToIntFunction

open class UninvertedTorchblock(settings: AbstractBlock.Settings): TorchBlock(settings, DustParticleEffect(0f, 255f, 0f, 1f)) {
    companion object {
        val POWERED: BooleanProperty = BooleanProperty.of("powered")
        fun register(stand: UninvertedTorchblock, wall: WallUninvertedTorchBlock) {
            Registry.register(Registry.BLOCK, Identifier(MODID, "uninverted_torch"), stand)
            Registry.register(Registry.BLOCK, Identifier(MODID, "wall_uninverted_torch"), wall)
            Registry.register(Registry.ITEM, Identifier(MODID, "uninverted_torch"), WallStandingBlockItem(stand, wall, RedstonePlus.REDGROUP))
        }
        val luminance = ToIntFunction<BlockState> { if(it.get(POWERED)) 7 else 0 }
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
        return if(state?.get(POWERED) == true && direction != Direction.UP) 15 else 0
    }

    override fun getStrongRedstonePower(state: BlockState?, world: BlockView?, pos: BlockPos?, direction: Direction?): Int {
        return if(direction == Direction.DOWN) state?.getWeakRedstonePower(world, pos, direction)?:0 else 0
    }

    protected open fun updatePowered(state: BlockState?, world: ServerWorld?, pos: BlockPos?) {
        world?.setBlockState(pos, state?.with(POWERED, world.getEmittedRedstonePower(pos?.offset(Direction.DOWN), Direction.UP) > 0))
    }

    override fun scheduledTick(state: BlockState?, world: ServerWorld?, pos: BlockPos?, random: Random?) {
        updatePowered(state, world, pos)
    }

    override fun neighborUpdate(state: BlockState?, world: World?, pos: BlockPos?, block: Block?, fromPos: BlockPos?, notify: Boolean) {
        world?.blockTickScheduler?.schedule(pos, this, 2)
    }

    override fun onPlaced(world: World?, pos: BlockPos?, state: BlockState?, placer: LivingEntity?, itemStack: ItemStack?) {
        world?.blockTickScheduler?.schedule(pos, this, 2)
        super.onPlaced(world, pos, state, placer, itemStack)
    }

    override fun onBlockAdded(state: BlockState?, world: World?, pos: BlockPos?, oldState: BlockState?, notify: Boolean) {
        for (direction in Direction.values()) {
            world?.updateNeighborsAlways(pos?.offset(direction), this)
        }
    }

    override fun onStateReplaced(state: BlockState?, world: World?, pos: BlockPos?, newState: BlockState?, moved: Boolean) {
        for (direction in Direction.values()) {
            if (!moved) world?.updateNeighborsAlways(pos?.offset(direction), this)
        }
    }

    @Environment(EnvType.CLIENT)
    override fun randomDisplayTick(state: BlockState, world: World, pos: BlockPos, random: Random) {
        if (state.get(POWERED)) {
            val d = pos.x.toDouble() + 0.5 + (random.nextDouble() - 0.5) * 0.2
            val e = pos.y.toDouble() + 0.7 + (random.nextDouble() - 0.5) * 0.2
            val f = pos.z.toDouble() + 0.5 + (random.nextDouble() - 0.5) * 0.2
            world.addParticle(particle, d, e, f, 0.0, 0.0, 0.0)
        }
    }
}
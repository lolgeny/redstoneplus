package net.lolad.redstoneplus.block

import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.LivingEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.IntProperty
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.*

abstract class AbstractSensorBlock(settings: AbstractBlock.Settings): Block(settings) {
    abstract fun getValue(state: BlockState, world: ServerWorld, pos: BlockPos): Int
    abstract fun getNamespacedId(): Identifier

    companion object {
        val POWER: IntProperty = IntProperty.of("power", 0, 15)
    }

    init {
        defaultState = stateManager.defaultState.with(POWER, 0)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(POWER)
    }

    override fun emitsRedstonePower(state: BlockState?): Boolean {
        return true
    }

    override fun getWeakRedstonePower(state: BlockState, world: BlockView, pos: BlockPos, direction: Direction): Int {
        return state.get(POWER)
    }

    override fun onPlaced(world: World, pos: BlockPos, state: BlockState, placer: LivingEntity?, itemStack: ItemStack) {
        world.blockTickScheduler.schedule(pos, this, 2)
        super.onPlaced(world, pos, state, placer, itemStack)
    }

    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        world.setBlockState(pos, state.with(POWER, getValue(state, world, pos)))
        world.blockTickScheduler.schedule(pos, this, 2)
    }

    fun register() {
        Registry.register(Registry.BLOCK, getNamespacedId(), this)
        Registry.register(Registry.ITEM, getNamespacedId(), BlockItem(this, Item.Settings().group(ItemGroup.REDSTONE)))
    }
}
package net.lolad.redstoneplus.block

import net.lolad.redstoneplus.RedstonePlus
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

class WeatherSensorBlock(settings: AbstractBlock.Settings): AbstractSensorBlock(settings) {

    enum class Type: StringIdentifiable {
        RAIN {
            override fun asString() = "rain"
        }, THUNDER {
            override fun asString() = "thunder"
        };

        abstract override fun asString(): String
    }

    companion object {
        val MODE: EnumProperty<Type> = EnumProperty.of("mode", Type::class.java)
    }

    init {
        defaultState = stateManager.defaultState.with(MODE, Type.RAIN)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(MODE)
        super.appendProperties(builder)
    }

    override fun getValue(state: BlockState, world: ServerWorld, pos: BlockPos): Int {
        val visible = world.isSkyVisible(pos)
        return if (when (state.get(MODE)!!) {
            Type.RAIN -> world.isRaining
            Type.THUNDER -> world.isThundering
        } && world.isSkyVisible(pos.offset(Direction.UP))) 15 else 0
    }

    override fun onUse(state: BlockState?, world: World?, pos: BlockPos?, player: PlayerEntity?, hand: Hand?, hit: BlockHitResult?): ActionResult {
        val newValue = when (state?.get(MODE)!!) {
            Type.RAIN -> Type.THUNDER
            Type.THUNDER -> Type.RAIN
        }
        world?.setBlockState(pos, state.with(MODE, newValue))
        return ActionResult.SUCCESS
    }

    override fun getNamespacedId(): Identifier {
        return Identifier(RedstonePlus.MODID, "weather_sensor")
    }
}
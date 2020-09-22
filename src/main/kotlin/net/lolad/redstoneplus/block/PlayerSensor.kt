package net.lolad.redstoneplus.block

import net.lolad.redstoneplus.RedstonePlus
import net.minecraft.block.AbstractBlock
import net.minecraft.block.BlockState
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Position

class PlayerSensor(settings: AbstractBlock.Settings): AbstractSensor(settings) {
    private fun getPositionFromBlockPos(pos: BlockPos): Position {
        return object: Position {
            override fun getX(): Double {return pos.x.toDouble()}
            override fun getY(): Double {return pos.y.toDouble()}
            override fun getZ(): Double {return pos.z.toDouble()}
        }
    }

    override fun getValue(state: BlockState, world: ServerWorld, pos: BlockPos): Int {
        val players = world.getPlayers { player -> player.pos.isInRange(getPositionFromBlockPos(pos), 5.0) }
        return minOf(15, maxOf(players.size, 0))
    }

    override fun getNamespacedId(): Identifier {
        return Identifier(RedstonePlus.MODID, "player_sensor")
    }
}
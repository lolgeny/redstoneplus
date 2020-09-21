package net.lolad.redstoneplus.mixin

import net.minecraft.util.DyeColor
import org.spongepowered.asm.mixin.Mixin
import org.spongepowered.asm.mixin.gen.Accessor

@Mixin(DyeColor::class)
interface DyeColorAccessor {
    @Accessor
    fun getColor(): Int
    @Accessor
    fun getName(): String
}
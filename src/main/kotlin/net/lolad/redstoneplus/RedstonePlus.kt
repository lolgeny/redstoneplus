package net.lolad.redstoneplus

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.lolad.redstoneplus.block.ColoredLampBlock
import net.lolad.redstoneplus.block.PlayerSensorBlock
import net.lolad.redstoneplus.block.RemembatorBlock
import net.lolad.redstoneplus.block.WeatherSensorBlock
import net.minecraft.block.Material
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.DyeColor
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

@Environment(EnvType.CLIENT)
class RedstonePlus: ModInitializer {

    companion object {
        val MODID = "redstoneplus"
        val REMEMBATOR = RemembatorBlock(FabricBlockSettings.of(Material.SUPPORTED).breakInstantly())
        val WHITE_COLORED_LAMP = ColoredLampBlock(DyeColor.WHITE, FabricBlockSettings.of(Material.REDSTONE_LAMP).luminance(ColoredLampBlock.luminance).strength(0.3F).sounds(BlockSoundGroup.GLASS).allowsSpawning(ColoredLampBlock.allowsSpawning))
        val ORANGE_COLORED_LAMP = ColoredLampBlock(DyeColor.ORANGE, FabricBlockSettings.of(Material.REDSTONE_LAMP).luminance(ColoredLampBlock.luminance).strength(0.3F).sounds(BlockSoundGroup.GLASS).allowsSpawning(ColoredLampBlock.allowsSpawning))
        val MAGENTA_COLORED_LAMP = ColoredLampBlock(DyeColor.MAGENTA, FabricBlockSettings.of(Material.REDSTONE_LAMP).luminance(ColoredLampBlock.luminance).strength(0.3F).sounds(BlockSoundGroup.GLASS).allowsSpawning(ColoredLampBlock.allowsSpawning))
        val LIGHT_BLUE_COLORED_LAMP = ColoredLampBlock(DyeColor.LIGHT_BLUE, FabricBlockSettings.of(Material.REDSTONE_LAMP).luminance(ColoredLampBlock.luminance).strength(0.3F).sounds(BlockSoundGroup.GLASS).allowsSpawning(ColoredLampBlock.allowsSpawning))
        val YELLOW_COLORED_LAMP = ColoredLampBlock(DyeColor.YELLOW, FabricBlockSettings.of(Material.REDSTONE_LAMP).luminance(ColoredLampBlock.luminance).strength(0.3F).sounds(BlockSoundGroup.GLASS).allowsSpawning(ColoredLampBlock.allowsSpawning))
        val LIME_COLORED_LAMP = ColoredLampBlock(DyeColor.LIME, FabricBlockSettings.of(Material.REDSTONE_LAMP).luminance(ColoredLampBlock.luminance).strength(0.3F).sounds(BlockSoundGroup.GLASS).allowsSpawning(ColoredLampBlock.allowsSpawning))
        val PINK_COLORED_LAMP = ColoredLampBlock(DyeColor.PINK, FabricBlockSettings.of(Material.REDSTONE_LAMP).luminance(ColoredLampBlock.luminance).strength(0.3F).sounds(BlockSoundGroup.GLASS).allowsSpawning(ColoredLampBlock.allowsSpawning))
        val CYAN_COLORED_LAMP = ColoredLampBlock(DyeColor.CYAN, FabricBlockSettings.of(Material.REDSTONE_LAMP).luminance(ColoredLampBlock.luminance).strength(0.3F).sounds(BlockSoundGroup.GLASS).allowsSpawning(ColoredLampBlock.allowsSpawning))
        val PURPLE_COLORED_LAMP = ColoredLampBlock(DyeColor.PURPLE, FabricBlockSettings.of(Material.REDSTONE_LAMP).luminance(ColoredLampBlock.luminance).strength(0.3F).sounds(BlockSoundGroup.GLASS).allowsSpawning(ColoredLampBlock.allowsSpawning))
        val BLUE_COLORED_LAMP = ColoredLampBlock(DyeColor.BLUE, FabricBlockSettings.of(Material.REDSTONE_LAMP).luminance(ColoredLampBlock.luminance).strength(0.3F).sounds(BlockSoundGroup.GLASS).allowsSpawning(ColoredLampBlock.allowsSpawning))
        val GREEN_COLORED_LAMP = ColoredLampBlock(DyeColor.GREEN, FabricBlockSettings.of(Material.REDSTONE_LAMP).luminance(ColoredLampBlock.luminance).strength(0.3F).sounds(BlockSoundGroup.GLASS).allowsSpawning(ColoredLampBlock.allowsSpawning))
        val RED_COLORED_LAMP = ColoredLampBlock(DyeColor.RED, FabricBlockSettings.of(Material.REDSTONE_LAMP).luminance(ColoredLampBlock.luminance).strength(0.3F).sounds(BlockSoundGroup.GLASS).allowsSpawning(ColoredLampBlock.allowsSpawning))
        val PLAYER_SENSOR = PlayerSensorBlock(FabricBlockSettings.of(Material.WOOD))
        val WEATHER_SENSOR = WeatherSensorBlock(FabricBlockSettings.of(Material.WOOD))
    }

    private fun registerBlocks() {
        Registry.register(Registry.BLOCK, Identifier(MODID, "remembator"), REMEMBATOR)
        Registry.register(Registry.ITEM, Identifier(MODID, "remembator"), BlockItem(REMEMBATOR, Item.Settings().group(ItemGroup.REDSTONE)))
        WHITE_COLORED_LAMP.register()
        ORANGE_COLORED_LAMP.register()
        MAGENTA_COLORED_LAMP.register()
        LIGHT_BLUE_COLORED_LAMP.register()
        YELLOW_COLORED_LAMP.register()
        LIME_COLORED_LAMP.register()
        PINK_COLORED_LAMP.register()
        CYAN_COLORED_LAMP.register()
        PURPLE_COLORED_LAMP.register()
        BLUE_COLORED_LAMP.register()
        GREEN_COLORED_LAMP.register()
        RED_COLORED_LAMP.register()
        PLAYER_SENSOR.register()
        WEATHER_SENSOR.register()
    }

    override fun onInitialize() {
        registerBlocks()
    }
}
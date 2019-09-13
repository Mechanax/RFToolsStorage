package mcjty.rftoolsstorage.blocks;

import mcjty.rftoolsstorage.RFToolsStorage;
import mcjty.rftoolsstorage.modules.modularstorage.blocks.ModularStorageBlock;
import mcjty.rftoolsstorage.modules.modularstorage.blocks.ModularStorageContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public final class ModBlocks {

    // ----- Blocks -----

    @ObjectHolder(RFToolsStorage.MODID + ":" + ModularStorageBlock.REGNAME)
    public static ModularStorageBlock MODULAR_STORAGE;

    // ----- Tile entities -----

    @ObjectHolder(RFToolsStorage.MODID + ":" + ModularStorageBlock.REGNAME)
    public static TileEntityType<?> TYPE_MODULAR_STORAGE;

    // ----- Containers -----

    @ObjectHolder(RFToolsStorage.MODID + ":" + ModularStorageBlock.REGNAME)
    public static ContainerType<ModularStorageContainer> CONTAINER_MODULAR_STORAGE;

}

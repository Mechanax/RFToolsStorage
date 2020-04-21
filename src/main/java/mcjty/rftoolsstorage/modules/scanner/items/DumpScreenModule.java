package mcjty.rftoolsstorage.modules.scanner.items;

import mcjty.lib.varia.BlockPosTools;
import mcjty.lib.varia.ItemStackList;
import mcjty.lib.varia.ItemStackTools;
import mcjty.rftoolsbase.api.screens.IScreenDataHelper;
import mcjty.rftoolsbase.api.screens.IScreenModule;
import mcjty.rftoolsbase.api.screens.data.IModuleData;
import mcjty.rftoolsbase.api.screens.data.IModuleDataBoolean;
import mcjty.rftoolsbase.api.storage.IStorageScanner;
import mcjty.rftoolsstorage.modules.scanner.StorageScannerConfiguration;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class DumpScreenModule implements IScreenModule<IModuleData> {

    public static int COLS = 7;
    public static int ROWS = 4;

    private ItemStackList stacks = ItemStackList.create(COLS * ROWS);
    protected DimensionType dim = DimensionType.OVERWORLD;
    protected BlockPos coordinate = BlockPosTools.INVALID;
    private boolean matchingTag = false;

    @Override
    public IModuleDataBoolean getData(IScreenDataHelper helper, World worldObj, long millis) {
        return null;
    }

    @Override
    public void setupFromNBT(CompoundNBT tagCompound, DimensionType dim, BlockPos pos) {
        if (tagCompound != null) {
            setupCoordinateFromNBT(tagCompound, dim, pos);
            for (int i = 0; i < stacks.size(); i++) {
                if (tagCompound.contains("stack" + i)) {
                    stacks.set(i, ItemStack.read(tagCompound.getCompound("stack" + i)));
                }
            }
        }
    }

    protected void setupCoordinateFromNBT(CompoundNBT tagCompound, DimensionType dim, BlockPos pos) {
        coordinate = BlockPosTools.INVALID;
        matchingTag = tagCompound.getBoolean("matchingTag");
        if (tagCompound.contains("monitorx")) {
            this.dim = DimensionType.byName(new ResourceLocation(tagCompound.getString("monitordim")));
            if (dim.equals(this.dim)) {
                BlockPos c = new BlockPos(tagCompound.getInt("monitorx"), tagCompound.getInt("monitory"), tagCompound.getInt("monitorz"));
                int dx = Math.abs(c.getX() - pos.getX());
                int dy = Math.abs(c.getY() - pos.getY());
                int dz = Math.abs(c.getZ() - pos.getZ());
                if (dx <= 64 && dy <= 64 && dz <= 64) {
                    coordinate = c;
                }
            }
        }
    }

    private boolean isShown(ItemStack stack) {
        if (stack.isEmpty()) {
            return false;
        }
        for (ItemStack s : stacks) {
            if (isItemEqual(stack, s)) {
                return true;
            }
            if (matchingTag) {
                if (ItemStackTools.hasCommonTag(s.getItem().getTags())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isItemEqual(ItemStack thisItem, ItemStack other) {
        if (other.isEmpty()) {
            return false;
        }
        return thisItem.isItemEqual(other);
    }

    @Override
    public void mouseClick(World world, int x, int y, boolean clicked, PlayerEntity player) {
        if ((!clicked) || player == null) {
            return;
        }
        if (BlockPosTools.INVALID.equals(coordinate)) {
            player.sendStatusMessage(new StringTextComponent(TextFormatting.RED + "Module is not linked to storage scanner!"), false);
            return;
        }

        IStorageScanner scannerTileEntity = StorageControlScreenModule.getStorageScanner(world, dim, coordinate);
        if (scannerTileEntity == null) {
            return;
        }
        int xoffset = 5;
        if (x >= xoffset) {
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                if (isShown(player.inventory.getStackInSlot(i))) {
                    ItemStack stack = scannerTileEntity.injectStackFromScreen(player.inventory.getStackInSlot(i), player);
                    player.inventory.setInventorySlotContents(i, stack);
                }
            }
            player.openContainer.detectAndSendChanges();
            return;
        }
    }

    @Override
    public int getRfPerTick() {
        return StorageScannerConfiguration.DUMP_RFPERTICK.get();
    }
}
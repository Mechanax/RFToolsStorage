package mcjty.rftoolsstorage.craftinggrid;

import mcjty.lib.varia.ItemStackList;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;

public class CraftingGridInventory implements IItemHandlerModifiable {

    public static int SLOT_GHOSTOUTPUT = 0;
    public static int SLOT_GHOSTINPUT = 1;

    public static int GRID_WIDTH = 66;
    public static int GRID_HEIGHT = 208;
    public static int GRID_XOFFSET = -GRID_WIDTH - 2 + 7;
    public static int GRID_YOFFSET = 127;

    private ItemStackList stacks = ItemStackList.create(10);

    public ItemStack getResult() {
        return stacks.get(SLOT_GHOSTOUTPUT);
    }

    public ItemStack[] getIngredients() {
        ItemStack[] ing = new ItemStack[9];
        for (int i = 0; i < ing.length; i++) {
            ing[i] = stacks.get(i + SLOT_GHOSTINPUT);
        }
        return ing;
    }

    @Override
    public int getSlots() {
        return 20;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        // @todo 1.14
        return null;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        // @todo 1.14
        return null;
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        stacks.set(slot, stack);
    }

    @Override
    public int getSlotLimit(int slot) {
        return 0;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        return stacks.get(index);
    }
}
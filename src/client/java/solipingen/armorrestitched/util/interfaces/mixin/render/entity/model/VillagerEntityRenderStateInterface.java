package solipingen.armorrestitched.util.interfaces.mixin.render.entity.model;


import net.minecraft.item.ItemStack;

public interface VillagerEntityRenderStateInterface {

    public ItemStack getEquippedHeadStack();
    public ItemStack getEquippedChestStack();
    public ItemStack getEquippedLegsStack();
    public ItemStack getEquippedFeetStack();

    public void setEquippedHeadStack(ItemStack stack);
    public void setEquippedChestStack(ItemStack stack);
    public void setEquippedLegsStack(ItemStack stack);
    public void setEquippedFeetStack(ItemStack stack);

}

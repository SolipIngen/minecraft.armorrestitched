package solipingen.armorrestitched.mixin.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.ItemHolderEntityRenderState;
import net.minecraft.client.render.entity.state.VillagerDataRenderState;
import net.minecraft.client.render.entity.state.VillagerEntityRenderState;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.VillagerEntityRenderStateInterface;


@Mixin(VillagerEntityRenderState.class)
@Environment(EnvType.CLIENT)
public abstract class VillagerEntityRenderStateMixin extends ItemHolderEntityRenderState implements VillagerDataRenderState, VillagerEntityRenderStateInterface {
    @Unique private ItemStack equippedHeadStack;
    @Unique private ItemStack equippedChestStack;
    @Unique private ItemStack equippedLegsStack;
    @Unique private ItemStack equippedFeetStack;


    @Override
    public ItemStack getEquippedHeadStack() {
        return this.equippedHeadStack;
    }

    @Override
    public void setEquippedHeadStack(ItemStack stack) {
        this.equippedHeadStack = stack;
    }

    @Override
    public ItemStack getEquippedChestStack() {
        return this.equippedChestStack;
    }

    @Override
    public void setEquippedChestStack(ItemStack stack) {
        this.equippedChestStack = stack;
    }

    @Override
    public ItemStack getEquippedLegsStack() {
        return this.equippedLegsStack;
    }

    @Override
    public void setEquippedLegsStack(ItemStack stack) {
        this.equippedLegsStack = stack;
    }

    @Override
    public ItemStack getEquippedFeetStack() {
        return this.equippedFeetStack;
    }

    @Override
    public void setEquippedFeetStack(ItemStack stack) {
        this.equippedFeetStack = stack;
    }


}

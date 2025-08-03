package solipingen.armorrestitched.mixin.client.render.entity.state;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.LivingHorseEntityRenderState;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import solipingen.armorrestitched.util.interfaces.mixin.render.entity.model.LivingHorseEntityRenderStateInterface;


@Mixin(LivingHorseEntityRenderState.class)
@Environment(EnvType.CLIENT)
public abstract class LivingHorseEntityRenderStateMixin extends LivingEntityRenderState implements LivingHorseEntityRenderStateInterface {
    @Unique private ItemStack armor;

    @Override
    public ItemStack getArmor() {
        return this.armor;
    }

    @Override
    public void setArmor(ItemStack stack) {
        this.armor = stack;
    }


}

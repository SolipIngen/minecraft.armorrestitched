package solipingen.armorrestitched.mixin.enchantment;

import net.minecraft.registry.tag.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.ThornsEnchantment;
import net.minecraft.entity.EquipmentSlot;


@Mixin(ThornsEnchantment.class)
public abstract class ThornsEnchantmentMixin extends Enchantment {


    protected ThornsEnchantmentMixin(Enchantment.Properties properties) {
        super(properties);
    }

    @ModifyVariable(method = "<init>", at = @At("HEAD"), index = 1)
    private static Enchantment.Properties modifiedInit(Enchantment.Properties properties) {
        return Enchantment.properties(ItemTags.CHEST_ARMOR_ENCHANTABLE, properties.weight(), properties.maxLevel(), properties.minCost(), properties.maxCost(), properties.anvilCost(), EquipmentSlot.CHEST);
    }
    
    @Override
    public boolean isTreasure() {
        return true;
    }

    
}

package solipingen.armorrestitched.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;


public class SoaringEnchantment extends Enchantment {


    public SoaringEnchantment(Enchantment.Properties properties) {
        super(properties);
    }

    @Override
    public boolean isTreasure() {
        return true;
    }

    @Override
    public boolean isAvailableForEnchantedBookOffer() {
        return false;
    }

    @Override
    public boolean isAvailableForRandomSelection() {
        return false;
    }

    @Override
    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof ElytraItem;
    }

    
}

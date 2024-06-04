package solipingen.armorrestitched.mixin.enchantment;

import net.minecraft.enchantment.BreachEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import solipingen.armorrestitched.registry.tag.ModItemTags;


@Mixin(BreachEnchantment.class)
public abstract class BreachEnchantmentMixin extends Enchantment {


    public BreachEnchantmentMixin(Properties properties) {
        super(properties);
    }

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/enchantment/Enchantment;properties(Lnet/minecraft/registry/tag/TagKey;IILnet/minecraft/enchantment/Enchantment$Cost;Lnet/minecraft/enchantment/Enchantment$Cost;ILnet/minecraft/resource/featuretoggle/FeatureSet;[Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/enchantment/Enchantment$Properties;"),
            index = 0)
    private static TagKey<Item> modifiedApplicableTag(TagKey<Item> itemTag) {
        return ModItemTags.BREACH_ENCHANTABLE;
    }




}

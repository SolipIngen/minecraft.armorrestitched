package solipingen.armorrestitched.mixin.item;

import java.util.Collection;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;


@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements FabricItemStack {
    

    @ModifyVariable(method = "getTooltip", at = @At("STORE"), ordinal = 0)
    private Multimap<EntityAttribute, EntityAttributeModifier> modifiedMultiMap(Multimap<EntityAttribute, EntityAttributeModifier> originalMultimap, @Nullable PlayerEntity player, TooltipContext context) {
        if (((ItemStack)(Object)this).getItem() instanceof ArmorItem) {
            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
            Collection<EntityAttributeModifier> armorModifiers = originalMultimap.get(EntityAttributes.GENERIC_ARMOR);
            for (EntityAttributeModifier armorModifier : armorModifiers) {
                builder.put(EntityAttributes.GENERIC_ARMOR, armorModifier);
            }
            originalMultimap.removeAll(EntityAttributes.GENERIC_ARMOR);
            Collection<EntityAttributeModifier> toughnessModifiers = originalMultimap.get(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
            for (EntityAttributeModifier toughnessModifier : toughnessModifiers) {
                builder.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, toughnessModifier);
            }
            originalMultimap.removeAll(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
            Collection<EntityAttributeModifier> knockbackResistanceModifiers = originalMultimap.get(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            for (EntityAttributeModifier knockbackResistanceModifier : knockbackResistanceModifiers) {
                builder.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, knockbackResistanceModifier);
            }
            originalMultimap.removeAll(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            originalMultimap.forEach((entityAttribute, entityAttributeModifier) -> builder.put(entityAttribute, entityAttributeModifier));
            return builder.build();
        }
        return originalMultimap;
    }


}

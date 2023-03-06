package solipingen.armorrestitched.mixin.item;

import java.util.UUID;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.google.common.collect.ImmutableMultimap;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorMaterials;
import net.minecraft.item.Item;


@Mixin(ArmorItem.class)
public abstract class ArmorItemMixin extends Item {
    @Shadow @Final private static UUID[] MODIFIERS;
    @Shadow @Final protected float knockbackResistance;


    public ArmorItemMixin(Settings settings) {
        super(settings);
    }

    @ModifyVariable(method = "<init>", at = @At("STORE"), ordinal = 0)
    private ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> modifiedBuilder(ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder, ArmorMaterial material, EquipmentSlot slot, Item.Settings settings) {
        UUID uUID = MODIFIERS[slot.getEntitySlotId()];
        if (material != ArmorMaterials.NETHERITE) {
            builder.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, new EntityAttributeModifier(uUID, "Armor knockback resistance", (double)this.knockbackResistance, EntityAttributeModifier.Operation.ADDITION));
        }
        return builder;
    }
    
}


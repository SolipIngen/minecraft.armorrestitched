package solipingen.armorrestitched.mixin.item;

import net.minecraft.block.DyedCarpetBlock;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;

import java.util.UUID;


@Mixin(BlockItem.class)
public abstract class BlockItemMixin extends Item {


    public BlockItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public AttributeModifiersComponent getAttributeModifiers() {
        if (((BlockItem)(Object)this).getBlock() instanceof DyedCarpetBlock) {
            AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
            UUID uUID = UUID.fromString("C1C72771-8B8E-BA4A-ACE0-81A93C8928B2");
            builder.add(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(uUID, "Armor modifier", 3.0, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.BODY);
            return builder.build();
        }
        return super.getAttributeModifiers();
    }


}

package solipingen.armorrestitched.mixin.item;

import net.minecraft.block.DyedCarpetBlock;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(BlockItem.class)
public abstract class BlockItemMixin extends Item {


    public BlockItemMixin(Settings settings) {
        super(settings);
    }

    @Override
    public AttributeModifiersComponent getAttributeModifiers() {
        if (((BlockItem)(Object)this).getBlock() instanceof DyedCarpetBlock) {
            AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
            Identifier identifier = Identifier.ofVanilla("armor." + ArmorItem.Type.BODY.getName());
            builder.add(EntityAttributes.GENERIC_ARMOR, new EntityAttributeModifier(identifier, 3.0, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.BODY);
            return builder.build();
        }
        return super.getAttributeModifiers();
    }


}

package solipingen.armorrestitched.mixin.accessors.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.entity.attribute.EntityAttributeModifier;


@Mixin(EntityAttributeModifier.class)
public interface EntityAttributeModifierAccessor {

    @Accessor("name")
    public String getName();
    
}

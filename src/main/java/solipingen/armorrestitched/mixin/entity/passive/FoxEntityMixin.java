package solipingen.armorrestitched.mixin.entity.passive;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import solipingen.armorrestitched.ArmorRestitched;


@Mixin(FoxEntity.class)
public abstract class FoxEntityMixin extends AnimalEntity {


    protected FoxEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "createFoxAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/entity/attribute/EntityAttribute;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;"))
    private static DefaultAttributeContainer.Builder redirectedCreateFoxAttributes(DefaultAttributeContainer.Builder attributeBuilder, EntityAttribute entityAttribute, double baseValue) {
        if (entityAttribute == EntityAttributes.GENERIC_MAX_HEALTH) {
            return attributeBuilder.add(entityAttribute, baseValue).add(EntityAttributes.GENERIC_ARMOR, 3.0);
        }
        return attributeBuilder.add(entityAttribute, baseValue);
    }

    @ModifyConstant(method = "computeFallDamage", constant = @Constant(floatValue = 5.0f))
    private float modifiedComputeFallDamageFactor(float originalf) {
        return 10.0f;
    }

    @Override
    public Identifier getLootTableId() {
        return switch (((FoxEntity)(Object)this).getVariant()) {
            default -> throw new IncompatibleClassChangeError();
            case RED -> new Identifier(ArmorRestitched.MOD_ID, "entities/fox/red");
            case SNOW -> new Identifier(ArmorRestitched.MOD_ID, "entities/fox/snow");
        };
    }

    
}

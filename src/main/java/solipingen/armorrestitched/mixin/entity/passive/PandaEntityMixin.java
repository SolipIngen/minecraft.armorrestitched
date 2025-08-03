package solipingen.armorrestitched.mixin.entity.passive;

import net.minecraft.entity.passive.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(PandaEntity.class)
public abstract class PandaEntityMixin extends AnimalEntity {


    protected PandaEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "createPandaAttributes", at = @At("HEAD"), cancellable = true)
    private static void injectedPandaAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cbireturn) {
        cbireturn.setReturnValue(AnimalEntity.createAnimalAttributes().add(EntityAttributes.MOVEMENT_SPEED, 0.15f)
                .add(EntityAttributes.ATTACK_DAMAGE, 6.0)
                .add(EntityAttributes.ARMOR, 6.0));
    }

//    @Override
//    public RegistryKey<LootTable> getLootTableId() {
//        if (((PandaEntity)(Object)this).isBrown()) {
//            return RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ArmorRestitched.MOD_ID, "entities/panda_brown"));
//        }
//        return super.getLootTableId();
//    }

    
}

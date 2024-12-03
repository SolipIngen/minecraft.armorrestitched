package solipingen.armorrestitched.mixin.entity.passive;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import solipingen.armorrestitched.ArmorRestitched;


@Mixin(FoxEntity.class)
public abstract class FoxEntityMixin extends AnimalEntity {


    protected FoxEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "createFoxAttributes", at = @At("HEAD"), cancellable = true)
    private static void injectedFoxAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cbireturn) {
        cbireturn.setReturnValue(MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 32.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0)
                .add(EntityAttributes.GENERIC_SAFE_FALL_DISTANCE, 10.0)
                .add(EntityAttributes.GENERIC_ARMOR, 3.0));
    }

    @Override
    public RegistryKey<LootTable> getLootTableId() {
        return switch (((FoxEntity)(Object)this).getVariant()) {
            case RED -> RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ArmorRestitched.MOD_ID, "entities/fox/red"));
            case SNOW -> RegistryKey.of(RegistryKeys.LOOT_TABLE, Identifier.of(ArmorRestitched.MOD_ID, "entities/fox/snow"));
            default -> throw new IncompatibleClassChangeError();
        };
    }

    
}

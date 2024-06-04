package solipingen.armorrestitched.mixin.entity.passive;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.WolfVariant;
import net.minecraft.entity.passive.WolfVariants;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import solipingen.armorrestitched.ArmorRestitched;

import static net.minecraft.entity.passive.WolfVariants.ASHEN;


@Mixin(WolfEntity.class)
public abstract class WolfEntityMixin extends TameableEntity {


    protected WolfEntityMixin(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "createWolfAttributes", at = @At("HEAD"), cancellable = true)
    private static void injectedWolfAttributes(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cbireturn) {
        cbireturn.setReturnValue(MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0)
                .add(EntityAttributes.GENERIC_ARMOR, 6.0));
    }

    @ModifyConstant(method = "updateAttributesForTamed", constant = @Constant(doubleValue = 8.0))
    private double modifiedUntamedWolfHealth(double originald) {
        return 10.0;
    }

    @Override
    public RegistryKey<LootTable> getLootTableId() {
        RegistryEntry<WolfVariant> variant = ((WolfEntity)(Object)this).getVariant();
        String name = "";
        if (variant.matchesKey(WolfVariants.ASHEN)) {
            name = "ashen";
        }
        else if (variant.matchesKey(WolfVariants.BLACK)) {
            name = "black";
        }
        else if (variant.matchesKey(WolfVariants.CHESTNUT)) {
            name = "chestnut";
        }
        else if (variant.matchesKey(WolfVariants.PALE)) {
            name = "pale";
        }
        else if (variant.matchesKey(WolfVariants.RUSTY)) {
            name = "rusty";
        }
        else if (variant.matchesKey(WolfVariants.SNOWY)) {
            name = "snowy";
        }
        else if (variant.matchesKey(WolfVariants.SPOTTED)) {
            name = "spotted";
        }
        else if (variant.matchesKey(WolfVariants.STRIPED)) {
            name = "striped";
        }
        else if (variant.matchesKey(WolfVariants.WOODS)) {
            name = "woods";
        }
        if (!name.matches("")) {
            return RegistryKey.of(RegistryKeys.LOOT_TABLE, new Identifier(ArmorRestitched.MOD_ID, "entities/wolf/" + name));
        }
        return super.getLootTableId();
    }

    
}

package solipingen.armorrestitched.mixin.entity.projectile;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.world.World;
import solipingen.armorrestitched.enchantment.ModEnchantments;


@Mixin(FireworkRocketEntity.class)
public abstract class FireworkRocketEntityMixin extends ProjectileEntity {
    @Shadow @Nullable private LivingEntity shooter;


    public FireworkRocketEntityMixin(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyConstant(method = "tick", constant = @Constant(doubleValue = 0.1))
    private double modifiedFallFlyingParameter(double originald) {
        RegistryEntryLookup<Enchantment> enchantmentLookup = this.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);
        int soaringLevel = EnchantmentHelper.getLevel(enchantmentLookup.getOrThrow(ModEnchantments.SOARING), this.shooter.getEquippedStack(EquipmentSlot.CHEST));
        return originald + 0.33*soaringLevel;
    }

    
}

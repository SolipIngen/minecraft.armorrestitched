package solipingen.armorrestitched.mixin.item;

import java.util.Collection;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;

import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterials;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.random.Random;


@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements FabricItemStack {
    

    @Inject(method = "damage(ILnet/minecraft/util/math/random/Random;Lnet/minecraft/server/network/ServerPlayerEntity;)Z", at = @At("HEAD"), cancellable = true)
    private <T extends LivingEntity> void injectedDamage(int amount, Random random, @Nullable ServerPlayerEntity player, CallbackInfoReturnable<Boolean> cbireturn) {
        if (player != null) {
            Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(player.world.getRegistryManager(), ((ItemStack)(Object)this));
            if (trimOptional.isPresent() && trimOptional.get().getMaterial().matchesKey(ArmorTrimMaterials.IRON) && random.nextInt(4) == 0) {
                cbireturn.setReturnValue(false);
            }
        }
    }

    @ModifyVariable(method = "getTooltip", at = @At("STORE"), ordinal = 0)
    private Multimap<EntityAttribute, EntityAttributeModifier> modifiedTooltipMultiMap(Multimap<EntityAttribute, EntityAttributeModifier> originalMultimap, @Nullable PlayerEntity player, TooltipContext context) {
        if (player != null && ((ItemStack)(Object)this).getItem() instanceof ArmorItem) {
            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
            Collection<EntityAttributeModifier> armorModifiers = originalMultimap.get(EntityAttributes.GENERIC_ARMOR);
            Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(player.world.getRegistryManager(), ((ItemStack)(Object)this));
            for (EntityAttributeModifier armorModifier : armorModifiers) {
                builder.put(EntityAttributes.GENERIC_ARMOR, armorModifier);
            }
            originalMultimap.removeAll(EntityAttributes.GENERIC_ARMOR);
            Collection<EntityAttributeModifier> toughnessModifiers = originalMultimap.get(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
            for (EntityAttributeModifier toughnessModifier : toughnessModifiers) {
                if (trimOptional.isPresent() && trimOptional.get().getMaterial().matchesKey(ArmorTrimMaterials.DIAMOND)) {
                    toughnessModifier = new EntityAttributeModifier(toughnessModifier.getId(), toughnessModifier.getName(), 1.25*toughnessModifier.getValue(), toughnessModifier.getOperation());
                }
                builder.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, toughnessModifier);
            }
            originalMultimap.removeAll(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
            Collection<EntityAttributeModifier> knockbackResistanceModifiers = originalMultimap.get(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            for (EntityAttributeModifier knockbackResistanceModifier : knockbackResistanceModifiers) {
                if (trimOptional.isPresent() && trimOptional.get().getMaterial().matchesKey(ArmorTrimMaterials.NETHERITE)) {
                    knockbackResistanceModifier = new EntityAttributeModifier(knockbackResistanceModifier.getId(), knockbackResistanceModifier.getName(), 1.25*knockbackResistanceModifier.getValue(), knockbackResistanceModifier.getOperation());
                }
                builder.put(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, knockbackResistanceModifier);
            }
            originalMultimap.removeAll(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            originalMultimap.forEach((entityAttribute, entityAttributeModifier) -> builder.put(entityAttribute, entityAttributeModifier));
            return builder.build();
        }
        return originalMultimap;
    }


}

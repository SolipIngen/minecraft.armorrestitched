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
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.item.trim.ArmorTrimMaterials;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.random.Random;


@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements FabricItemStack {
    

    @Inject(method = "damage(ILnet/minecraft/util/math/random/Random;Lnet/minecraft/server/network/ServerPlayerEntity;)Z", at = @At("HEAD"), cancellable = true)
    private void injectedDamage(int amount, Random random, @Nullable ServerPlayerEntity player, CallbackInfoReturnable<Boolean> cbireturn) {
        if (player != null) {
            Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(player.world.getRegistryManager(), ((ItemStack)(Object)this));
            if (trimOptional.isPresent() && trimOptional.get().getMaterial().matchesKey(ArmorTrimMaterials.IRON) && random.nextInt(4) == 0) {
                cbireturn.setReturnValue(false);
            }
        }
    }

    @ModifyVariable(method = "getTooltip", at = @At("STORE"), ordinal = 0)
    private Multimap<EntityAttribute, EntityAttributeModifier> modifiedTooltipMultiMap(Multimap<EntityAttribute, EntityAttributeModifier> originalMultimap, @Nullable PlayerEntity player, TooltipContext context) {
        if (player != null && (((ItemStack)(Object)this).getItem() instanceof ArmorItem || ((ItemStack)(Object)this).isIn(ItemTags.TRIMMABLE_ARMOR))) {
            Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(player.world.getRegistryManager(), ((ItemStack)(Object)this));
            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
            Collection<EntityAttributeModifier> armorModifiers = originalMultimap.get(EntityAttributes.GENERIC_ARMOR);
            for (EntityAttributeModifier armorModifier : armorModifiers) {
                if (trimOptional.isPresent() && ((ItemStack)(Object)this).isOf(Items.ELYTRA)) {
                    RegistryEntry<ArmorTrimMaterial> trimMaterial = trimOptional.get().getMaterial();
                    double addition = 0.0;
                    if (trimMaterial.matchesKey(ArmorTrimMaterials.COPPER)) {
                        addition = 1.0;
                    }
                    else if (trimMaterial.matchesKey(ArmorTrimMaterials.IRON)) {
                        addition = 2.0;
                    }
                    else if (trimMaterial.matchesKey(ArmorTrimMaterials.GOLD)) {
                        addition = 1.0;
                    }
                    else if (trimMaterial.matchesKey(ArmorTrimMaterials.DIAMOND)) {
                        addition = 3.0;
                    }
                    else if (trimMaterial.matchesKey(ArmorTrimMaterials.EMERALD)) {
                        addition = 2.0;
                    }
                    else if (trimMaterial.matchesKey(ArmorTrimMaterials.NETHERITE)) {
                        addition = 3.0;
                    }
                    armorModifier = new EntityAttributeModifier(armorModifier.getId(), armorModifier.getName(), armorModifier.getValue() + addition, armorModifier.getOperation());
                }
                builder.put(EntityAttributes.GENERIC_ARMOR, armorModifier);
            }
            originalMultimap.removeAll(EntityAttributes.GENERIC_ARMOR);
            Collection<EntityAttributeModifier> toughnessModifiers = originalMultimap.get(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
            for (EntityAttributeModifier toughnessModifier : toughnessModifiers) {
                if (trimOptional.isPresent() && ((ItemStack)(Object)this).isOf(Items.ELYTRA)) {
                    RegistryEntry<ArmorTrimMaterial> trimMaterial = trimOptional.get().getMaterial();
                    double addition = 0.0;
                    if (trimMaterial.matchesKey(ArmorTrimMaterials.COPPER)) {
                        addition = 0.5;
                    }
                    else if (trimMaterial.matchesKey(ArmorTrimMaterials.IRON)) {
                        addition = 1.0;
                    }
                    else if (trimMaterial.matchesKey(ArmorTrimMaterials.GOLD)) {
                        addition = 0.5;
                    }
                    else if (trimMaterial.matchesKey(ArmorTrimMaterials.DIAMOND)) {
                        addition = 2.0;
                    }
                    else if (trimMaterial.matchesKey(ArmorTrimMaterials.NETHERITE)) {
                        addition = 2.0;
                    }
                    toughnessModifier = new EntityAttributeModifier(toughnessModifier.getId(), toughnessModifier.getName(), toughnessModifier.getValue() + addition, toughnessModifier.getOperation());
                }
                if (trimOptional.isPresent() && ((ItemStack)(Object)this).getItem() instanceof ArmorItem && trimOptional.get().getMaterial().matchesKey(ArmorTrimMaterials.DIAMOND)) {
                    toughnessModifier = new EntityAttributeModifier(toughnessModifier.getId(), toughnessModifier.getName(), toughnessModifier.getValue() + 1.0, toughnessModifier.getOperation());
                }
                builder.put(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, toughnessModifier);
            }
            originalMultimap.removeAll(EntityAttributes.GENERIC_ARMOR_TOUGHNESS);
            Collection<EntityAttributeModifier> knockbackResistanceModifiers = originalMultimap.get(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            for (EntityAttributeModifier knockbackResistanceModifier : knockbackResistanceModifiers) {
                if (trimOptional.isPresent() && trimOptional.get().getMaterial().matchesKey(ArmorTrimMaterials.NETHERITE)) {
                    double addition = ((ItemStack)(Object)this).isOf(Items.ELYTRA) ? 0.025 : 0.05;
                    knockbackResistanceModifier = new EntityAttributeModifier(knockbackResistanceModifier.getId(), knockbackResistanceModifier.getName(), knockbackResistanceModifier.getValue() + addition, knockbackResistanceModifier.getOperation());
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

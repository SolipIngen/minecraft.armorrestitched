package solipingen.armorrestitched.mixin.item;

import java.util.function.BiConsumer;

import net.minecraft.component.ComponentHolder;
import net.minecraft.component.DataComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.*;
import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fabricmc.fabric.api.item.v1.FabricItemStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterials;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;


@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ComponentHolder, FabricItemStack {

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract boolean isOf(Item item);

    @Shadow
    public abstract boolean isIn(TagKey<Item> tag);

    @Shadow
    @Nullable
    public abstract <T> T set(DataComponentType<? super T> type, @Nullable T value);

    @Inject(method = "getMaxDamage", at = @At("HEAD"), cancellable = true)
    private void injectedGetMaxDamage(CallbackInfoReturnable<Integer> cbireturn) {
        if (this.getItem() instanceof ArmorItem) {
            ArmorItem.Type type = ((ArmorItem)this.getItem()).getType();
            RegistryEntry<ArmorMaterial> material = ((ArmorItem)this.getItem()).getMaterial();
            if (material == ArmorMaterials.LEATHER) {
                cbireturn.setReturnValue(type.getMaxDamage(9));
            }
            else if (material == ArmorMaterials.CHAIN) {
                cbireturn.setReturnValue(type.getMaxDamage(15));
            }
            else if (material == ArmorMaterials.IRON) {
                cbireturn.setReturnValue(type.getMaxDamage(20));
            }
            else if (material == ArmorMaterials.DIAMOND) {
                cbireturn.setReturnValue(type.getMaxDamage(33));
            }
            else if (material == ArmorMaterials.GOLD) {
                cbireturn.setReturnValue(type.getMaxDamage(7));
            }
            else if (material == ArmorMaterials.NETHERITE) {
                cbireturn.setReturnValue(type.getMaxDamage(37));
            }
            if (this.getItem() instanceof AnimalArmorItem animalArmorItem && animalArmorItem.getType() == AnimalArmorItem.Type.CANINE) {
                cbireturn.setReturnValue(ArmorItem.Type.BODY.getMaxDamage(15));
            }
        }
        if (this.isOf(Items.ELYTRA)) {
            int durability = Math.max(this.getOrDefault(DataComponentTypes.MAX_DAMAGE, 432), 888);
            if (this.get(DataComponentTypes.TRIM) != null) {
                RegistryEntry<ArmorTrimMaterial> material = this.get(DataComponentTypes.TRIM).getMaterial();
                if (material.matchesKey(ArmorTrimMaterials.LAPIS) || material.matchesKey(ArmorTrimMaterials.REDSTONE)) {
                    durability += 40;
                }
                else if (material.matchesKey(ArmorTrimMaterials.QUARTZ) || material.matchesKey(ArmorTrimMaterials.AMETHYST)) {
                    durability += 60;
                }
                else if (material.matchesKey(ArmorTrimMaterials.COPPER) || material.matchesKey(ArmorTrimMaterials.GOLD) || material.matchesKey(ArmorTrimMaterials.EMERALD)) {
                    durability += 100;
                }
                else if (material.matchesKey(ArmorTrimMaterials.IRON)) {
                    durability += 200;
                }
                else if (material.matchesKey(ArmorTrimMaterials.DIAMOND)) {
                    durability += 240;
                }
                else if (material.matchesKey(ArmorTrimMaterials.NETHERITE)) {
                    durability += 300;
                }
            }
            cbireturn.setReturnValue(durability);
        }
    }

    @Inject(method = "damage(ILnet/minecraft/util/math/random/Random;Lnet/minecraft/server/network/ServerPlayerEntity;Ljava/lang/Runnable;)V", at = @At("HEAD"), cancellable = true)
    private void injectedDamage(int amount, Random random, ServerPlayerEntity player, Runnable breakCallback, CallbackInfo cbi) {
        if (player != null) {
            ArmorTrim trim = this.get(DataComponentTypes.TRIM);
            if (trim != null && trim.getMaterial().matchesKey(ArmorTrimMaterials.IRON) && random.nextInt(4) == 0) {
                cbi.cancel();
            }
        }
    }

    @Inject(method = "applyAttributeModifiers", at = @At("TAIL"), cancellable = true)
    private void injectedAttributeModifiers(EquipmentSlot slot, BiConsumer<RegistryEntry<EntityAttribute>, EntityAttributeModifier> attributeModifierConsumer, CallbackInfo cbi) {
        if (this.isIn(ItemTags.TRIMMABLE_ARMOR)) {
            ArmorTrim trim = this.get(DataComponentTypes.TRIM);
            if (trim != null) {
                AttributeModifiersComponent attributeModifiersComponent = (this.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT).modifiers().isEmpty()) ?
                        this.getItem().getAttributeModifiers() : this.getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
                RegistryEntry<ArmorTrimMaterial> material = trim.getMaterial();
                AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
                float armorAddition = 0.0f;
                float toughnessAddition = (this.getItem() instanceof ArmorItem && material.matchesKey(ArmorTrimMaterials.DIAMOND)) ? 1.0f : 0.0f;
                float knockbackResistanceAddition = 0.0f;
                if (this.isOf(Items.ELYTRA)) {
                    if (material.matchesKey(ArmorTrimMaterials.COPPER) || material.matchesKey(ArmorTrimMaterials.GOLD)) {
                        armorAddition = 1.0f;
                        toughnessAddition = 0.5f;
                    }
                    else if (material.matchesKey(ArmorTrimMaterials.IRON)) {
                        armorAddition = 2.0f;
                        toughnessAddition = 1.0f;
                    }
                    else if (material.matchesKey(ArmorTrimMaterials.DIAMOND)) {
                        armorAddition = 2.0f;
                        toughnessAddition = 2.0f;
                    }
                    else if (material.matchesKey(ArmorTrimMaterials.EMERALD)) {
                        armorAddition = 2.0f;
                    }
                    else if (material.matchesKey(ArmorTrimMaterials.NETHERITE)) {
                        armorAddition = 2.0f;
                        toughnessAddition = 3.0f;
                        knockbackResistanceAddition = 0.04f;
                    }
                }
                for (AttributeModifiersComponent.Entry entry : attributeModifiersComponent.modifiers()) {
                    double addition = (entry.attribute().value() == EntityAttributes.GENERIC_ARMOR) ? armorAddition
                            : ((entry.attribute().value() == EntityAttributes.GENERIC_ARMOR_TOUGHNESS) ? toughnessAddition
                            : ((entry.attribute().value() == EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) ? knockbackResistanceAddition
                            : 0.0f));
                    builder.add(entry.attribute(),
                            new EntityAttributeModifier(entry.modifier().uuid(), entry.modifier().name(), entry.modifier().value() + addition, entry.modifier().operation()),
                            entry.slot());
                }
                this.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, builder.build());
            }
        }

    }

    @Inject(method = "onItemEntityDestroyed", at = @At("HEAD"))
    private void injectedOnItemEntityDestroyed(ItemEntity entity, CallbackInfo cbi) {
        ArmorTrim trim = this.get(DataComponentTypes.TRIM);
        if (entity.getWorld() instanceof ServerWorld && trim != null && trim.getMaterial().matchesKey(ArmorTrimMaterials.NETHERITE)) {
            ItemEntity itemEntity = new ItemEntity(entity.getWorld(), entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.NETHERITE_SCRAP, 4), 0.0, 0.0, 0.0);
            entity.getWorld().spawnEntity(itemEntity);
        }
    }

    // One-off re-mapper for Flax Stem/Straw, to remove for 1.21 update.
    @ModifyVariable(method = "fromNbt", at = @At("HEAD"), index = 1)
    private static NbtElement remappingNbtElement(NbtElement nbt, RegistryWrapper.WrapperLookup registries) {
        if (nbt.asString().contains("armorrestitched:flax_stem")) {
            NbtCompound nbtCompound = ((NbtCompound)nbt).copy();
            nbtCompound.remove("id");
            nbtCompound.putString("id", "armorrestitched:flax_straw");
            return nbtCompound;
        }
        return nbt;
    }


}

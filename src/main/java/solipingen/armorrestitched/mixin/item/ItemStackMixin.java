package solipingen.armorrestitched.mixin.item;

import net.minecraft.component.ComponentHolder;
import net.minecraft.component.ComponentType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
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
import solipingen.armorrestitched.registry.tag.ModItemTags;

import java.util.function.Consumer;


@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ComponentHolder, FabricItemStack {

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract boolean isOf(Item item);

    @Shadow
    public abstract boolean isIn(TagKey<Item> tag);


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

    @Inject(method = "damage(ILnet/minecraft/server/world/ServerWorld;Lnet/minecraft/server/network/ServerPlayerEntity;Ljava/util/function/Consumer;)V", at = @At("HEAD"), cancellable = true)
    private void injectedDamage(int amount, ServerWorld world, ServerPlayerEntity player, Consumer<Item> breakCallback, CallbackInfo cbi) {
        if (player != null) {
            ArmorTrim trim = this.get(DataComponentTypes.TRIM);
            if (trim != null && trim.getMaterial().matchesKey(ArmorTrimMaterials.IRON) && player.getRandom().nextInt(4) == 0) {
                cbi.cancel();
            }
        }
    }

    @Override
    public <T> T get(ComponentType<? extends T> type) {
        if (this.isIn(ItemTags.TRIMMABLE_ARMOR) && type == DataComponentTypes.ATTRIBUTE_MODIFIERS) {
            ArmorTrim trim = this.getComponents().get(DataComponentTypes.TRIM);
            if (trim != null) {
                AttributeModifiersComponent attributeModifiersComponent = this.getComponents().get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
                RegistryEntry<ArmorTrimMaterial> material = trim.getMaterial();
                AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
                float armorAddition = 0.0f;
                float toughnessAddition = (this.getItem() instanceof ArmorItem && material.matchesKey(ArmorTrimMaterials.DIAMOND)) ? 1.0f : 0.0f;
                float knockbackResistanceAddition = 0.0f;
                if ((this.isIn(ModItemTags.TRIMMABLE_ELYTRA) || this.getItem() instanceof ElytraItem) && attributeModifiersComponent != null) {
                    armorAddition = 1.0f;
                    if (material.matchesKey(ArmorTrimMaterials.COPPER) || material.matchesKey(ArmorTrimMaterials.GOLD) || material.matchesKey(ArmorTrimMaterials.EMERALD)) {
                        toughnessAddition = 0.5f;
                    }
                    else if (material.matchesKey(ArmorTrimMaterials.IRON)) {
                        toughnessAddition = 1.0f;
                    }
                    else if (material.matchesKey(ArmorTrimMaterials.DIAMOND)) {
                        toughnessAddition = 2.0f;
                    }
                    else if (material.matchesKey(ArmorTrimMaterials.NETHERITE)) {
                        toughnessAddition = 3.0f;
                        knockbackResistanceAddition = 0.04f;
                    }
                }
                for (AttributeModifiersComponent.Entry entry : attributeModifiersComponent.modifiers()) {
                    double addition = (entry.attribute() == EntityAttributes.GENERIC_ARMOR) ? armorAddition
                            : ((entry.attribute() == EntityAttributes.GENERIC_ARMOR_TOUGHNESS) ? toughnessAddition
                            : ((entry.attribute() == EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) ? knockbackResistanceAddition
                            : 0.0f));
                    builder.add(entry.attribute(),
                            new EntityAttributeModifier(entry.modifier().id(), entry.modifier().value() + addition, entry.modifier().operation()),
                            entry.slot());
                }
                return (T)builder.build();
            }
        }
        return this.getComponents().get(type);
    }

    @Override
    public <T> T getOrDefault(ComponentType<? extends T> type, T fallback) {
        if (this.isIn(ItemTags.TRIMMABLE_ARMOR) && type == DataComponentTypes.ATTRIBUTE_MODIFIERS) {
            ArmorTrim trim = this.getComponents().get(DataComponentTypes.TRIM);
            if (trim != null) {
                AttributeModifiersComponent attributeModifiersComponent = (this.getComponents().getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT).modifiers().isEmpty()) ?
                        this.getItem().getAttributeModifiers() : this.getComponents().getOrDefault(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.DEFAULT);
                RegistryEntry<ArmorTrimMaterial> material = trim.getMaterial();
                AttributeModifiersComponent.Builder builder = AttributeModifiersComponent.builder();
                float armorAddition = 0.0f;
                float toughnessAddition = (this.getItem() instanceof ArmorItem && material.matchesKey(ArmorTrimMaterials.DIAMOND)) ? 1.0f : 0.0f;
                float knockbackResistanceAddition = 0.0f;
                if (this.isIn(ModItemTags.TRIMMABLE_ELYTRA) || this.getItem() instanceof ElytraItem) {
                    armorAddition = 1.0f;
                    if (material.matchesKey(ArmorTrimMaterials.COPPER) || material.matchesKey(ArmorTrimMaterials.GOLD) || material.matchesKey(ArmorTrimMaterials.EMERALD)) {
                        toughnessAddition = 0.5f;
                    }
                    else if (material.matchesKey(ArmorTrimMaterials.IRON)) {
                        toughnessAddition = 1.0f;
                    }
                    else if (material.matchesKey(ArmorTrimMaterials.DIAMOND)) {
                        toughnessAddition = 2.0f;
                    }
                    else if (material.matchesKey(ArmorTrimMaterials.NETHERITE)) {
                        toughnessAddition = 3.0f;
                        knockbackResistanceAddition = 0.04f;
                    }
                }
                for (AttributeModifiersComponent.Entry entry : attributeModifiersComponent.modifiers()) {
                    double addition = (entry.attribute() == EntityAttributes.GENERIC_ARMOR) ? armorAddition
                            : ((entry.attribute() == EntityAttributes.GENERIC_ARMOR_TOUGHNESS) ? toughnessAddition
                            : ((entry.attribute() == EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE) ? knockbackResistanceAddition
                            : 0.0f));
                    builder.add(entry.attribute(),
                            new EntityAttributeModifier(entry.modifier().id(), entry.modifier().value() + addition, entry.modifier().operation()),
                            entry.slot());
                }
                return (T)builder.build();
            }
        }
        return this.getComponents().getOrDefault(type, fallback);
    }

    @Inject(method = "onItemEntityDestroyed", at = @At("HEAD"))
    private void injectedOnItemEntityDestroyed(ItemEntity entity, CallbackInfo cbi) {
        ArmorTrim trim = this.get(DataComponentTypes.TRIM);
        if (entity.getWorld() instanceof ServerWorld && trim != null && trim.getMaterial().matchesKey(ArmorTrimMaterials.NETHERITE)) {
            ItemEntity itemEntity = new ItemEntity(entity.getWorld(), entity.getX(), entity.getY(), entity.getZ(), new ItemStack(Items.NETHERITE_SCRAP, 4), 0.0, 0.0, 0.0);
            entity.getWorld().spawnEntity(itemEntity);
        }
    }

    // One-off re-mapper for Flax Stem/Straw.
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

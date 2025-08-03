package solipingen.armorrestitched.client.render.item.property;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.equipment.trim.ArmorTrim;
import net.minecraft.item.equipment.trim.ArmorTrimMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import org.jetbrains.annotations.Nullable;
import solipingen.armorrestitched.registry.tag.ModItemTags;


@Environment(EnvType.CLIENT)
public record BrokenElytraTrimMaterialProperty() implements SelectProperty<RegistryKey<ArmorTrimMaterial>> {
    public static final Codec<RegistryKey<ArmorTrimMaterial>> VALUE_CODEC = RegistryKey.createCodec(RegistryKeys.TRIM_MATERIAL);
    public static final SelectProperty.Type<BrokenElytraTrimMaterialProperty, RegistryKey<ArmorTrimMaterial>> TYPE = Type.create(MapCodec.unit(new BrokenElytraTrimMaterialProperty()), VALUE_CODEC);


    @Override
    @Nullable
    public RegistryKey<ArmorTrimMaterial> getValue(ItemStack itemStack, @Nullable ClientWorld clientWorld, @Nullable LivingEntity livingEntity, int i, ItemDisplayContext itemDisplayContext) {
        ArmorTrim armorTrim = itemStack.get(DataComponentTypes.TRIM);
        return itemStack.isIn(ModItemTags.TRIMMABLE_ELYTRA) && itemStack.willBreakNextUse() && armorTrim != null ?
                armorTrim.material().getKey().orElse(null) : null;
    }

    @Override
    public SelectProperty.Type<BrokenElytraTrimMaterialProperty, RegistryKey<ArmorTrimMaterial>> getType() {
        return TYPE;
    }

    @Override
    public Codec<RegistryKey<ArmorTrimMaterial>> valueCodec() {
        return VALUE_CODEC;
    }

}

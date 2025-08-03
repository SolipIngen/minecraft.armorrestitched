package solipingen.armorrestitched.item.equipment;

import net.minecraft.item.equipment.EquipmentAsset;
import net.minecraft.item.equipment.EquipmentAssetKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import solipingen.armorrestitched.ArmorRestitched;


public class ModEquipmentAssetKeys {

    public static final RegistryKey<EquipmentAsset> COTTON = ModEquipmentAssetKeys.register("cotton");
    public static final RegistryKey<EquipmentAsset> FUR = ModEquipmentAssetKeys.register("fur.json");
    public static final RegistryKey<EquipmentAsset> LINEN = ModEquipmentAssetKeys.register("linen");
    public static final RegistryKey<EquipmentAsset> SILK = ModEquipmentAssetKeys.register("silk");
    public static final RegistryKey<EquipmentAsset> WOOL = ModEquipmentAssetKeys.register("wool");
    public static final RegistryKey<EquipmentAsset> PAPER = ModEquipmentAssetKeys.register("paper");
    public static final RegistryKey<EquipmentAsset> COPPER = ModEquipmentAssetKeys.register("copper");


    private static RegistryKey<EquipmentAsset> register(String name) {
        return RegistryKey.of(EquipmentAssetKeys.REGISTRY_KEY, Identifier.of(ArmorRestitched.MOD_ID, name));
    }

}

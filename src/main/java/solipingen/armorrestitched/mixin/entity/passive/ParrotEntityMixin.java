package solipingen.armorrestitched.mixin.entity.passive;

import java.util.ArrayList;
import java.util.Set;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.TameableShoulderEntity;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import solipingen.armorrestitched.item.ModItems;


@Mixin(ParrotEntity.class)
public abstract class ParrotEntityMixin extends TameableShoulderEntity {
    @Shadow @Final private static Set<Item> TAMING_INGREDIENTS;


    protected ParrotEntityMixin(EntityType<? extends TameableShoulderEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "interactMob", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/passive/ParrotEntity;TAMING_INGREDIENTS:Ljava/util/Set;", opcode = Opcodes.GETSTATIC))
    private Set<Item> redirectedTamingIngredients() {
        ArrayList<Item> itemList = new ArrayList<Item>();
        for (Item item : TAMING_INGREDIENTS) {
            itemList.add(item);
        }
        itemList.add(ModItems.FLAXSEEDS);
        Set<Item> breedingItems = Set.copyOf(itemList);
        return breedingItems;
    }

    
}

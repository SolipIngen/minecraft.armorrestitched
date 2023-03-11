package solipingen.armorrestitched.mixin.entity.mob;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.IllagerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.SpellcastingIllagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;


@Mixin(SpellcastingIllagerEntity.class)
public abstract class SpellcastingIllagerEntityMixin extends IllagerEntity {


    protected SpellcastingIllagerEntityMixin(EntityType<? extends SpellcastingIllagerEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.initEquipment(this.world.getRandom(), difficulty);
        if (spawnReason == SpawnReason.STRUCTURE || this.isPatrolLeader()) {
            for (EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
                if (equipmentSlot.getType() != EquipmentSlot.Type.ARMOR) continue;
                if (equipmentSlot == EquipmentSlot.HEAD) break;
                Item armorItem = MobEntity.getEquipmentForSlot(equipmentSlot, this.random.nextFloat() < 0.08f ? 4 : 1);
                this.equipStack(equipmentSlot, new ItemStack(armorItem));
            }
            this.updateEnchantments(world.getRandom(), difficulty);
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initEquipment(Random random, LocalDifficulty localDifficulty) {
        super.initEquipment(random, localDifficulty);
    }


    
}


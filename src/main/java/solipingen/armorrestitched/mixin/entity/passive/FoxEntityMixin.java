package solipingen.armorrestitched.mixin.entity.passive;

import org.spongepowered.asm.mixin.Mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import solipingen.armorrestitched.ArmorRestitched;


@Mixin(FoxEntity.class)
public abstract class FoxEntityMixin extends AnimalEntity {


    protected FoxEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public Identifier getLootTableId() {
        return switch (((FoxEntity)(Object)this).getVariant()) {
            default -> throw new IncompatibleClassChangeError();
            case RED -> new Identifier(ArmorRestitched.MOD_ID, "entities/fox/red");
            case SNOW -> new Identifier(ArmorRestitched.MOD_ID, "entities/fox/snow");
        };
    }

    
}

package solipingen.armorrestitched.mixin.accessors;

import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.WolfVariant;
import net.minecraft.registry.entry.RegistryEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;


@Mixin(WolfEntity.class)
public interface WolfEntityAccessor {

    @Invoker("getVariant")
    public abstract RegistryEntry<WolfVariant> invokeGetVariant();

}

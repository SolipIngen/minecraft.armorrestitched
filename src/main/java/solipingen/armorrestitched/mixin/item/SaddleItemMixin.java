package solipingen.armorrestitched.mixin.item;

import net.minecraft.item.Item;
import net.minecraft.item.SaddleItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;


@Mixin(SaddleItem.class)
public abstract class SaddleItemMixin extends Item {


    public SaddleItemMixin(Settings settings) {
        super(settings);
    }

    @ModifyVariable(method = "<init>", at = @At("HEAD"), ordinal = 0)
    private static Item.Settings modifiedSettings(Item.Settings settings) {
        return settings.maxCount(16);
    }



}

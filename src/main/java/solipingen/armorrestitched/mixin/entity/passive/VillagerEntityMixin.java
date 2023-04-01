package solipingen.armorrestitched.mixin.entity.passive;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.InteractionObserver;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.DyeItem;
import net.minecraft.item.DyeableArmorItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimMaterials;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerData;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerGossips;
import net.minecraft.village.VillagerProfession;
import net.minecraft.village.TradeOffers.Factory;
import net.minecraft.world.World;
import solipingen.armorrestitched.item.ModArmorMaterials;
import solipingen.armorrestitched.item.ModItems;
import solipingen.armorrestitched.village.ModVillagerProfessions;


@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends MerchantEntity implements InteractionObserver, VillagerDataContainer {
    @Shadow @Final private VillagerGossips gossip;


    @Invoker("sayNo")
    public abstract void invokeSayNo();


    public VillagerEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/VillagerEntity;isBaby()Z"), cancellable = true)
    private void injectedInteractMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cbireturn) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.getItem() instanceof ArmorItem && !this.isSleeping()) {
            EquipmentSlot slot = MobEntity.getPreferredEquipmentSlot(itemStack);
            ItemStack itemStack2 = this.getEquippedStack(slot);
            if (itemStack2.isEmpty() || (itemStack2.getItem() instanceof ArmorItem && ((ArmorItem)itemStack2.getItem()).getProtection() <= ((ArmorItem)itemStack.getItem()).getProtection())) {
                this.equipStack(slot, itemStack);
                player.setStackInHand(hand, ItemUsage.exchangeStack(itemStack, player, itemStack2));
                this.playSound(SoundEvents.ENTITY_VILLAGER_YES, this.getSoundVolume(), this.getSoundPitch());
            }
            else {
                this.invokeSayNo();
            }
            player.incrementStat(Stats.TALKED_TO_VILLAGER);
            cbireturn.setReturnValue(ActionResult.success(this.world.isClient));
        }
    }

    @ModifyVariable(method = "fillRecipes", at = @At("STORE"), ordinal = 0)
    private Int2ObjectMap<TradeOffers.Factory[]> modifiedFilledRecipes(Int2ObjectMap<TradeOffers.Factory[]> int2ObjectMap) {
        VillagerData villagerData = this.getVillagerData();
        Map<VillagerProfession, Int2ObjectMap<Factory[]>> tradeOffers = TradeOffers.PROFESSION_TO_LEVELED_TRADE;
        if (villagerData.getProfession() == VillagerProfession.ARMORER) {
            ModVillagerProfessions.replaceArmorerProfessionToLeveledTrade(tradeOffers);
        }
        else if (villagerData.getProfession() == VillagerProfession.LEATHERWORKER) {
            ModVillagerProfessions.replaceLeatherworkerProfessionToLeveledTrade(tradeOffers, this);
        }
        else if (villagerData.getProfession() == VillagerProfession.SHEPHERD) {
            ModVillagerProfessions.replaceShepherdProfessionToLeveledTrade(tradeOffers, this);
        }
        return tradeOffers.get(villagerData.getProfession());
    }

    @Inject(method = "talkWithVillager", at = @At("TAIL"))
    private void injectedTalkWithVillager(ServerWorld world, VillagerEntity villager, long time, CallbackInfo cbi) {
        if (villager.getVillagerData().getProfession() == VillagerProfession.LEATHERWORKER && !this.isBaby()) {
            int leatherworkerLevel = villager.getVillagerData().getLevel();
            if (leatherworkerLevel >= 1) {
                if (this.getEquippedStack(EquipmentSlot.FEET).isEmpty()) {
                    this.equipStack(EquipmentSlot.FEET, this.getRandomlyDyedClothing(Items.LEATHER_BOOTS, false));
                }
            }
            if (leatherworkerLevel >= 2) {
                if (this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
                    this.equipStack(EquipmentSlot.HEAD, this.getRandomlyDyedClothing(Items.LEATHER_HELMET, false));
                }
            }
            if (leatherworkerLevel >= 3) {
                if (this.getEquippedStack(EquipmentSlot.LEGS).isEmpty()) {
                    this.equipStack(EquipmentSlot.LEGS, this.getRandomlyDyedClothing(Items.LEATHER_LEGGINGS, false));
                }
            }
            if (leatherworkerLevel >= 4) {
                if (this.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
                    this.equipStack(EquipmentSlot.CHEST, this.getRandomlyDyedClothing(Items.LEATHER_CHESTPLATE, false));
                }
            }
            if (leatherworkerLevel == 5) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                    if (this.getEquippedStack(slot).isEmpty() || !this.getEquippedStack(slot).hasEnchantments()) {
                        if (slot == EquipmentSlot.HEAD) {
                            this.equipStack(EquipmentSlot.HEAD, this.getRandomlyDyedClothing(Items.LEATHER_HELMET, true));
                        }
                        else if (slot == EquipmentSlot.CHEST) {
                            this.equipStack(EquipmentSlot.CHEST, this.getRandomlyDyedClothing(Items.LEATHER_CHESTPLATE, true));
                        }
                        else if (slot == EquipmentSlot.LEGS) {
                            this.equipStack(EquipmentSlot.LEGS, this.getRandomlyDyedClothing(Items.LEATHER_LEGGINGS, true));
                        }
                        else if (slot == EquipmentSlot.FEET) {
                            this.equipStack(EquipmentSlot.FEET, this.getRandomlyDyedClothing(Items.LEATHER_BOOTS, true));
                        }
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
        }
        else if (villager.getVillagerData().getProfession() == VillagerProfession.SHEPHERD && !this.isBaby()) {
            int shepherdLevel = villager.getVillagerData().getLevel();
            if (shepherdLevel >= 1) {
                if (this.getEquippedStack(EquipmentSlot.FEET).isEmpty()) {
                    this.equipStack(EquipmentSlot.FEET, this.getRandomlyDyedClothing(ModItems.WOOL_BOOTS, false));
                }
            }
            if (shepherdLevel >= 2) {
                if (this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
                    this.equipStack(EquipmentSlot.HEAD, this.getRandomlyDyedClothing(ModItems.WOOL_HELMET, false));
                }
            }
            if (shepherdLevel >= 3) {
                if (this.getEquippedStack(EquipmentSlot.LEGS).isEmpty()) {
                    this.equipStack(EquipmentSlot.LEGS, this.getRandomlyDyedClothing(ModItems.WOOL_LEGGINGS, false));
                }
            }
            if (shepherdLevel >= 4) {
                if (this.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
                    this.equipStack(EquipmentSlot.CHEST, this.getRandomlyDyedClothing(ModItems.WOOL_CHESTPLATE, false));
                }
            }
            if (shepherdLevel == 5) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                    if (this.getEquippedStack(slot).isEmpty() || !this.getEquippedStack(slot).hasEnchantments()) {
                        if (slot == EquipmentSlot.HEAD) {
                            this.equipStack(EquipmentSlot.HEAD, this.getRandomlyDyedClothing(ModItems.WOOL_HELMET, true));
                        }
                        else if (slot == EquipmentSlot.CHEST) {
                            this.equipStack(EquipmentSlot.CHEST, this.getRandomlyDyedClothing(ModItems.WOOL_CHESTPLATE, true));
                        }
                        else if (slot == EquipmentSlot.LEGS) {
                            this.equipStack(EquipmentSlot.LEGS, this.getRandomlyDyedClothing(ModItems.WOOL_LEGGINGS, true));
                        }
                        else if (slot == EquipmentSlot.FEET) {
                            this.equipStack(EquipmentSlot.FEET, this.getRandomlyDyedClothing(ModItems.WOOL_BOOTS, true));
                        }
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
        }
        else if (villager.getVillagerData().getProfession() == VillagerProfession.ARMORER && !this.isBaby()) {
            int armorerLevel = villager.getVillagerData().getLevel();
            if (armorerLevel >= 2) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                    if (this.random.nextFloat() >= 0.2f*this.world.getDifficulty().getId()) continue;
                    if (slot == EquipmentSlot.HEAD) {
                        ItemStack itemStack = new ItemStack(ModItems.COPPER_HELMET);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.HEAD, itemStack);
                    }
                    else if (slot == EquipmentSlot.CHEST) {
                        ItemStack itemStack = new ItemStack(ModItems.COPPER_CHESTPLATE);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.CHEST, itemStack);
                    }
                    else if (slot == EquipmentSlot.LEGS) {
                        ItemStack itemStack = new ItemStack(ModItems.COPPER_LEGGINGS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.LEGS, itemStack);
                    }
                    else if (slot == EquipmentSlot.FEET) {
                        ItemStack itemStack = new ItemStack(ModItems.COPPER_BOOTS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.FEET, itemStack);
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
            if (armorerLevel >= 3) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                    if (this.random.nextFloat() >= 0.15f*this.world.getDifficulty().getId()) continue;
                    if (slot == EquipmentSlot.HEAD) {
                        ItemStack itemStack = new ItemStack(Items.CHAINMAIL_HELMET);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.HEAD, itemStack);
                    }
                    else if (slot == EquipmentSlot.CHEST) {
                        ItemStack itemStack = new ItemStack(Items.CHAINMAIL_CHESTPLATE);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.CHEST, itemStack);
                    }
                    else if (slot == EquipmentSlot.LEGS) {
                        ItemStack itemStack = new ItemStack(Items.CHAINMAIL_LEGGINGS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.LEGS, itemStack);
                    }
                    else if (slot == EquipmentSlot.FEET) {
                        ItemStack itemStack = new ItemStack(Items.CHAINMAIL_BOOTS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.FEET, itemStack);
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
            if (armorerLevel >= 4) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                    if (this.random.nextFloat() >= 0.1f*this.world.getDifficulty().getId()) continue;
                    if (slot == EquipmentSlot.HEAD) {
                        ItemStack itemStack = new ItemStack(Items.IRON_HELMET);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.HEAD, itemStack);
                    }
                    else if (slot == EquipmentSlot.CHEST) {
                        ItemStack itemStack = new ItemStack(Items.IRON_CHESTPLATE);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.CHEST, itemStack);
                    }
                    else if (slot == EquipmentSlot.LEGS) {
                        ItemStack itemStack = new ItemStack(Items.IRON_LEGGINGS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.LEGS, itemStack);
                    }
                    else if (slot == EquipmentSlot.FEET) {
                        ItemStack itemStack = new ItemStack(Items.IRON_BOOTS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.FEET, itemStack);
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
            if (armorerLevel >= 5) {
                for (EquipmentSlot slot : EquipmentSlot.values()) {
                    if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
                    if (this.random.nextFloat() >= 0.05f*this.world.getDifficulty().getId()) continue;
                    if (slot == EquipmentSlot.HEAD) {
                        ItemStack itemStack = new ItemStack(Items.DIAMOND_HELMET);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.HEAD, itemStack);
                    }
                    else if (slot == EquipmentSlot.CHEST) {
                        ItemStack itemStack = new ItemStack(Items.DIAMOND_CHESTPLATE);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.CHEST, itemStack);
                    }
                    else if (slot == EquipmentSlot.LEGS) {
                        ItemStack itemStack = new ItemStack(Items.DIAMOND_LEGGINGS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.LEGS, itemStack);
                    }
                    else if (slot == EquipmentSlot.FEET) {
                        ItemStack itemStack = new ItemStack(Items.DIAMOND_BOOTS);
                        if (!this.getEquippedStack(slot).isEmpty() && this.getEquippedStack(slot).getMaxDamage() >= itemStack.getItem().getMaxDamage()) continue;
                        this.equipStack(EquipmentSlot.FEET, itemStack);
                    }
                    this.setEquipmentDropChance(slot, 0.0f);
                }
            }
        }
    }

    @Inject(method = "getReputation", at = @At("HEAD"), cancellable = true)
    private void injectedGetReputation(PlayerEntity player, CallbackInfoReturnable<Integer> cbireturn) {
        int reputation = this.gossip.getReputationFor(player.getUuid(), gossipType -> true);
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
            ItemStack equippedStack = player.getEquippedStack(slot);
            Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(player.world.getRegistryManager(), equippedStack);
            if (trimOptional.isPresent() && trimOptional.get().getMaterial().matchesKey(ArmorTrimMaterials.EMERALD)) {
                reputation += 1;
                reputation = MathHelper.ceil(1.25f*reputation);
            }
        }
        cbireturn.setReturnValue(reputation);
    }

    @Inject(method = "onStruckByLightning", at = @At("HEAD"), cancellable = true)
    private void injectedOnStruckByLightning(ServerWorld world, LightningEntity lightning, CallbackInfo cbi) {
        boolean bl = false;
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            if (slot.getType() != EquipmentSlot.Type.ARMOR) continue;
            ItemStack equippedStack = this.getEquippedStack(slot);
            if (equippedStack.getItem() instanceof ArmorItem && ((ArmorItem)equippedStack.getItem()).getMaterial() == ModArmorMaterials.COPPER) {
                super.onStruckByLightning(world, lightning);
                bl = true;
            }
            Optional<ArmorTrim> trimOptional = ArmorTrim.getTrim(this.world.getRegistryManager(), equippedStack);
            if (trimOptional.isEmpty()) continue;
            if (trimOptional.get().getMaterial().matchesKey(ArmorTrimMaterials.COPPER)) {
                super.onStruckByLightning(world, lightning);
                bl = true;
            }
        }
        if (bl) {
            cbi.cancel();
        }
    }

    private ItemStack getRandomlyDyedClothing(Item dyeableArmorItem, boolean enchanted) {
        if (dyeableArmorItem instanceof DyeableArmorItem) {
            int i = 5 + random.nextInt(15);
            ItemStack itemStack = new ItemStack(dyeableArmorItem);
            ArrayList<DyeItem> list = Lists.newArrayList();
            list.add(ModVillagerProfessions.SellDyedItemFactory.getDye(this.random));
            if (random.nextFloat() > 0.7f) {
                list.add(ModVillagerProfessions.SellDyedItemFactory.getDye(this.random));
            }
            if (random.nextFloat() > 0.8f) {
                list.add(ModVillagerProfessions.SellDyedItemFactory.getDye(this.random));
            }
            itemStack = DyeableItem.blendAndSetColor(itemStack, list);
            if (enchanted) {
                itemStack = EnchantmentHelper.enchant(this.random, itemStack, i, false);
            }
            return itemStack;
        }
        return ItemStack.EMPTY;
    }

    
}


package solipingen.armorrestitched.entity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.AboveGroundTargeting;
import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.state.property.Properties;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.BiomeKeys;
import solipingen.armorrestitched.block.ModBlocks;
import solipingen.armorrestitched.sound.ModSoundEvents;

import java.util.EnumSet;

import org.jetbrains.annotations.Nullable;


public class SilkMothEntity extends AnimalEntity implements Flutterer {
    private static final TrackedData<Byte> SILK_MOTH_FLAGS = DataTracker.registerData(SilkMothEntity.class, TrackedDataHandlerRegistry.BYTE);
    private static final String TICKS_SINCE_LAYING_EGGS_KEY = "TicksSinceLayingEggs";
    private float currentPitch;
    @Nullable private BlockPos mulberryPos;
    private MoveToMulberryGoal moveToMulberryGoal;
    private MoveToLightGoal moveToLightGoal;
    private SilkMothWanderAroundGoal silkMothWanderAroundGoal;
    private int ticksSinceLayingEggs;
    private int ticksInsideWater;


    public SilkMothEntity(EntityType<? extends SilkMothEntity> entityType, World world) {
        super((EntityType<? extends AnimalEntity>)entityType, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
        this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER, -1.0f);
        this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 16.0f);
        this.setPathfindingPenalty(PathNodeType.COCOA, -1.0f);
        this.setPathfindingPenalty(PathNodeType.FENCE, -1.0f);
    }

    @Override
    public boolean isFlappingWings() {
        return this.isInAir() && this.age % 3 == 0;
    }

    @Override
    public boolean isInAir() {
        return !this.isOnGround();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SILK_MOTH_FLAGS, (byte)0);
    }

    @Override
    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        if (world.getBlockState(pos).isAir()) {
            return 10.0f;
        }
        return 0.0f;
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.moveToMulberryGoal = new MoveToMulberryGoal(this, 1.0f, 8);
        this.moveToLightGoal = new MoveToLightGoal(this, 1.0f, 15);
        this.silkMothWanderAroundGoal = new SilkMothWanderAroundGoal();
        this.goalSelector.add(1, new SilkMothEscapeDangerGoal(this, 1.2f));
        this.goalSelector.add(2, this.moveToLightGoal);
        this.goalSelector.add(2, new TemptGoal(this, 1.0, Ingredient.ofItems(ModBlocks.MULBERRY_LEAVES), false));
        this.goalSelector.add(3, this.moveToMulberryGoal);
        this.goalSelector.add(4, this.silkMothWanderAroundGoal);
    }

    @Override
    protected void mobTick(ServerWorld serverWorld) {
        super.mobTick(serverWorld);
        this.ticksInsideWater = this.isTouchingWaterOrRain() ? ++this.ticksInsideWater : 0;
        if (this.ticksInsideWater > 20) {
            this.damage(serverWorld, this.getDamageSources().drown(), 1.0f);
        }
        ++this.ticksSinceLayingEggs;
        if (this.ticksSinceLayingEggs > 2400) {
            this.setLaidEggs(false);
        }
        this.updateBodyPitch();
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void pushAway(Entity entity) {
    }

    @Override
    protected void tickCramming() {
    }

    public static DefaultAttributeContainer.Builder createSilkMothAttributes() {
        return AnimalEntity.createAnimalAttributes().add(EntityAttributes.MAX_HEALTH, 4.0)
                .add(EntityAttributes.FLYING_SPEED, 0.5)
                .add(EntityAttributes.MOVEMENT_SPEED, 0.4);
    }

    public boolean hasLaidEggs() {
        return (this.dataTracker.get(SILK_MOTH_FLAGS) & 1) != 0;
    }

    public void setLaidEggs(boolean laidEggs) {
        byte b = this.dataTracker.get(SILK_MOTH_FLAGS);
        if (laidEggs) {
            this.dataTracker.set(SILK_MOTH_FLAGS, (byte)(b | 1));
            this.ticksSinceLayingEggs = 0;
        } 
        else {
            this.dataTracker.set(SILK_MOTH_FLAGS, (byte)(b & 0xFFFFFFFE));
        }
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world){

            @Override
            public boolean isValidPosition(BlockPos pos) {
                return !this.world.getBlockState(pos.down()).isAir();
            }
        };
        birdNavigation.setCanOpenDoors(false);
        birdNavigation.setCanSwim(false);
        birdNavigation.setMaxFollowRange(32.0f);
        return birdNavigation;
    }

    public float getBodyPitch(float tickProgress) {
        return MathHelper.lerp(tickProgress, this.lastPitch, this.currentPitch);
    }

    private void updateBodyPitch() {
        this.lastPitch = this.currentPitch;
            this.currentPitch = Math.max(0.0f, this.currentPitch - 0.24f);

    }

    @Override
    protected Entity.MoveEffect getMoveEffect() {
        return Entity.MoveEffect.EVENTS;
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Override
    public boolean canAvoidTraps() {
        return true;
    }

    @Override
    public boolean canBeLeashed() {
        return false;
    }

    @Override
    protected void writeCustomData(WriteView view) {
        super.writeCustomData(view);
        view.putByte("SilkMothFlags", this.dataTracker.get(SILK_MOTH_FLAGS));
        view.putInt(TICKS_SINCE_LAYING_EGGS_KEY, this.ticksSinceLayingEggs);
    }

    @Override
    protected void readCustomData(ReadView view) {
        super.readCustomData(view);
        this.dataTracker.set(SILK_MOTH_FLAGS, view.getByte("SilkMothFlags", (byte)0));
        this.ticksSinceLayingEggs = view.getInt(TICKS_SINCE_LAYING_EGGS_KEY, 0);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    public static boolean canSpawn(EntityType<SilkMothEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (!(world.getBiome(pos).matchesKey(BiomeKeys.DARK_FOREST) || (world.getLightLevel(LightType.SKY, pos) - world.getAmbientDarkness() < 12 && world.getLightLevel(LightType.SKY, pos) - world.getAmbientDarkness() > 0))) {
            return false;
        }
        return SilkMothEntity.canMobSpawn(type, world, spawnReason, pos, random);
    }

    @Override
    @Nullable
    public SilkMothEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        return null;
    }

    @Override
    protected float getSoundVolume() {
        return 0.1f;
    }

    @Override
    public float getSoundPitch() {
        return 1.75f*super.getSoundPitch();
    }

    @Override
    @Nullable
    public SoundEvent getHurtSound(DamageSource source) {
        return ModSoundEvents.SILK_MOTH_HURT;
    }

    @Override
    @Nullable
    public SoundEvent getDeathSound() {
        return ModSoundEvents.SILK_MOTH_DEATH;
    }

    @Override
    @Nullable
    public SoundEvent getAmbientSound() {
        return null;
    }


    public class MoveToMulberryGoal extends MoveToTargetPosGoal {


        public MoveToMulberryGoal(SilkMothEntity silkMoth, double speed, int range) {
            super(silkMoth, speed, range);
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            return this.findTargetPos() && this.shouldMoveToMulberry();
        }

        @Override
        public boolean shouldContinue() {
            return this.canStart() && !this.hasReached();
        }

        @Override
        public boolean hasReached() {
            boolean bl = false;
            BlockPos blockPos = SilkMothEntity.this.getBlockPos();
            for (Direction direction : Direction.values()) {
                blockPos = SilkMothEntity.this.getBlockPos().offset(direction);
                bl = this.isTargetPos(SilkMothEntity.this.getWorld(), blockPos);
                if (bl) {
                    SilkMothEntity.this.getWorld().setBlockState(blockPos, ModBlocks.MULBERRY_SILKWORM_LEAVES.getDefaultState());
                    if (SilkMothEntity.this.getWorld() instanceof ServerWorld serverWorld) {
                        for (int j = 0; j < 8; j++) {
                            serverWorld.spawnParticles(ParticleTypes.HAPPY_VILLAGER, (double)blockPos.getX() + 0.5 + SilkMothEntity.this.random.nextGaussian(), (double)blockPos.getY() + 0.5 + SilkMothEntity.this.random.nextGaussian(), (double)blockPos.getZ() + 0.5 + SilkMothEntity.this.random.nextGaussian(), 1, 0.0, 0.0, 0.0, 0.0);
                        }
                    }
                    SilkMothEntity.this.setLaidEggs(true);
                    break;
                }
            }
            return bl;
        }

        @Override
        public void stop() {
            SilkMothEntity.this.navigation.stop();
            SilkMothEntity.this.navigation.resetRangeMultiplier();
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            return world.getBlockState(pos).isOf(ModBlocks.MULBERRY_LEAVES) && !world.getBlockState(pos).get(Properties.WATERLOGGED);
        }

        private boolean shouldMoveToMulberry() {
            return !SilkMothEntity.this.hasLaidEggs();
        }

    }

    public class MoveToLightGoal extends MoveToTargetPosGoal {
        private int lightLevel;


        public MoveToLightGoal(SilkMothEntity silkMoth, double speed, int range) {
            super(silkMoth, speed, range);
            this.setControls(EnumSet.of(Goal.Control.MOVE));
            this.lightLevel = 1;
        }

        @Override
        public boolean canStart() {
            return SilkMothEntity.this.getWorld().getLightLevel(SilkMothEntity.this.getBlockPos()) > 0 && this.findTargetPos();
        }

        @Override
        public boolean shouldContinue() {
            return this.canStart() && !this.hasReached();
        }

        @Override
        public void stop() {
            SilkMothEntity.this.navigation.stop();
            SilkMothEntity.this.navigation.resetRangeMultiplier();
        }

        @Override
        protected boolean isTargetPos(WorldView world, BlockPos pos) {
            boolean bl = world.getBlockState(pos).getLuminance() >= this.lightLevel;
            if (bl) {
                this.lightLevel = world.getBlockState(pos).getLuminance();
            }
            return bl;
        }

    }

    class SilkMothWanderAroundGoal extends Goal {


        public SilkMothWanderAroundGoal() {
            this.setControls(EnumSet.of(Goal.Control.MOVE));
        }

        @Override
        public boolean canStart() {
            return SilkMothEntity.this.navigation.isIdle() && SilkMothEntity.this.random.nextInt(10) == 0;
        }

        @Override
        public boolean shouldContinue() {
            return SilkMothEntity.this.navigation.isFollowingPath();
        }

        @Override
        public void start() {
            Vec3d vec3d = this.getRandomLocation();
            if (vec3d != null) {
                SilkMothEntity.this.navigation.startMovingAlong(SilkMothEntity.this.navigation.findPathTo(BlockPos.ofFloored(vec3d), 1), 1.0);
            }
        }

        @Nullable
        private Vec3d getRandomLocation() {
            Vec3d vec3d2 = SilkMothEntity.this.getRotationVec(0.0f);
            Vec3d vec3d3 = AboveGroundTargeting.find(SilkMothEntity.this, 8, 7, vec3d2.x, vec3d2.z, 1.5707964f, 3, 1);
            if (vec3d3 != null) {
                return vec3d3;
            }
            return NoPenaltySolidTargeting.find(SilkMothEntity.this, 8, 4, -2, vec3d2.x, vec3d2.z, 1.5707963705062866);
        }
    }

    public static class SilkMothEscapeDangerGoal extends EscapeDangerGoal {

        
        public SilkMothEscapeDangerGoal(PathAwareEntity mob, double speed) {
            super(mob, speed);
        }

        @Override
        @Nullable
        protected BlockPos locateClosestWater(BlockView world, Entity entity, int rangeX) {
            return null;
        }
        

    }



}

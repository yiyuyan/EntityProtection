package cn.ksmcbrigade.eo.mixin;

import cn.ksmcbrigade.eo.EntityProtection;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingMixin extends Entity {
    @Shadow protected boolean dead;

    @Shadow public int deathTime;

    @Shadow public int hurtTime;

    @Shadow @Final private static EntityDataAccessor<Float> DATA_HEALTH_ID;

    @Shadow public abstract float getMaxHealth();

    @Shadow @Nullable public abstract AttributeInstance getAttribute(Attribute p_21052_);

    @Shadow public abstract void kill();

    @Shadow public int hurtDuration;

    @Shadow private int lastHurtMobTimestamp;

    @Shadow protected float lastHurt;

    @Shadow @Nullable private DamageSource lastDamageSource;

    @Shadow @Nullable private LivingEntity lastHurtByMob;

    @Shadow @Nullable protected Player lastHurtByPlayer;

    @Shadow protected int lastHurtByPlayerTime;

    @Shadow private long lastDamageStamp;

    @Shadow public abstract float getHealth();

    public LivingMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Inject(method = "setHealth",at = @At("HEAD"), cancellable = true)
    public void health(float p_21154_, CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "getHealth",at = @At("RETURN"), cancellable = true)
    public void health(CallbackInfoReturnable<Float> cir){
        if(EntityProtection.safes.contains(this.uuid) && (cir.getReturnValue()<=0f)){
            this.entityData.set(DATA_HEALTH_ID, Mth.clamp(4F, 0.0F, this.getMaxHealth()));
            cir.setReturnValue(4F);
            cir.cancel();
        }
    }

    @Inject(method = "getMaxHealth",at = @At("RETURN"), cancellable = true)
    public void health2(CallbackInfoReturnable<Float> cir){
        if(EntityProtection.safes.contains(this.uuid) && (cir.getReturnValue()<=0f)){
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(20D);
            cir.setReturnValue(20F);
            cir.cancel();
        }
    }

    @Inject(method = "isDeadOrDying",at = @At("HEAD"), cancellable = true)
    public void dead(CallbackInfoReturnable<Boolean> cir){
        if(EntityProtection.safes.contains(this.uuid)){
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "isAlive",at = @At("HEAD"), cancellable = true)
    public void alive(CallbackInfoReturnable<Boolean> cir){
        if(EntityProtection.safes.contains(this.uuid)){
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Inject(method = "isDamageSourceBlocked",at = @At("HEAD"), cancellable = true)
    public void block(CallbackInfoReturnable<Boolean> cir){
        if(EntityProtection.safes.contains(this.uuid)) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

    @Inject(method = "die",at = @At("HEAD"), cancellable = true)
    public void die(CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "setLastHurtByPlayer",at = @At("HEAD"), cancellable = true)
    public void hbp(CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "setLastHurtByMob",at = @At("HEAD"), cancellable = true)
    public void hbm(CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "dropAllDeathLoot",at = @At("HEAD"), cancellable = true)
    public void drop(CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "dropCustomDeathLoot",at = @At("HEAD"), cancellable = true)
    public void drop2(CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "animateHurt",at = @At("HEAD"), cancellable = true)
    public void damage(float p_265265_, CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "hurtArmor",at = @At("HEAD"), cancellable = true)
    public void damage(DamageSource p_21122_, float p_21123_, CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "hurtHelmet",at = @At("HEAD"), cancellable = true)
    public void damage2(DamageSource p_147213_, float p_147214_, CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "hurtCurrentlyUsedShield",at = @At("HEAD"), cancellable = true)
    public void damage3(float p_265265_, CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "actuallyHurt",at = @At("HEAD"), cancellable = true)
    public void damage4(DamageSource p_21240_, float p_21241_, CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "tick",at = @At("HEAD"))
    public void tick(CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)){
            this.dead = false;
            this.deathTime=0;
            this.hurtTime=0;
            this.hurtDuration = 0;
            this.lastHurtMobTimestamp = 0;
            this.lastHurt = 0;
            this.lastDamageStamp = 0;
            this.lastDamageSource = null;
            this.lastHurtByMob = null;
            this.lastHurtByPlayer = null;
            this.lastHurtByPlayerTime = 0;
            this.getHealth();
        }
        else if(this.entityData.get(DATA_HEALTH_ID)<=0){
            this.kill();
        }
    }
}

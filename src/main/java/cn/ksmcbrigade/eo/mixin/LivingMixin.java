package cn.ksmcbrigade.eo.mixin;

import cn.ksmcbrigade.eo.EntityProtection;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingMixin extends Entity {
    public LivingMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    @Inject(method = "setHealth",at = @At("HEAD"), cancellable = true)
    public void health(float p_21154_, CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "isDeadOrDying",at = @At("HEAD"), cancellable = true)
    public void dead(CallbackInfoReturnable<Boolean> cir){
        if(EntityProtection.safes.contains(this.uuid)) cir.setReturnValue(false);cir.cancel();
    }

    @Inject(method = "isDamageSourceBlocked",at = @At("HEAD"), cancellable = true)
    public void block(CallbackInfoReturnable<Boolean> cir){
        if(EntityProtection.safes.contains(this.uuid)) cir.setReturnValue(false);cir.cancel();
    }

    @Inject(method = "die",at = @At("HEAD"), cancellable = true)
    public void die(CallbackInfo ci){
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
}

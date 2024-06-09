package cn.ksmcbrigade.eo.mixin;

import cn.ksmcbrigade.eo.EntityProtection;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(Entity.class)
public class EntityMixin {
    @Shadow protected UUID uuid;

    @Inject(method = "isAttackable",at = @At("HEAD"), cancellable = true)
    public void Attackable(CallbackInfoReturnable<Boolean> cir){
        if(EntityProtection.safes.contains(this.uuid)) cir.setReturnValue(false);cir.cancel();
    }

    @Inject(method = "skipAttackInteraction",at = @At("HEAD"), cancellable = true)
    public void skipAttack(CallbackInfoReturnable<Boolean> cir){
        if(EntityProtection.safes.contains(this.uuid)) cir.setReturnValue(true);cir.cancel();
    }

    @Inject(method = "hurt",at = @At("HEAD"), cancellable = true)
    public void damage(CallbackInfoReturnable<Boolean> cir){
        if(EntityProtection.safes.contains(this.uuid)) cir.setReturnValue(false);cir.cancel();
    }

    @Inject(method = "causeFallDamage",at = @At("HEAD"), cancellable = true)
    public void fallDamage(CallbackInfoReturnable<Boolean> cir){
        if(EntityProtection.safes.contains(this.uuid)) cir.setReturnValue(false);cir.cancel();
    }

    @Inject(method = "lavaHurt",at = @At("HEAD"), cancellable = true)
    public void lava(CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "kill",at = @At("HEAD"), cancellable = true)
    public void kill(CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "remove",at = @At("HEAD"), cancellable = true)
    public void remove(CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "setRemoved",at = @At("HEAD"), cancellable = true)
    public void removed(CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "setId",at = @At("HEAD"), cancellable = true)
    public void id(CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "setUUID",at = @At("HEAD"), cancellable = true)
    public void uuid(CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }
}

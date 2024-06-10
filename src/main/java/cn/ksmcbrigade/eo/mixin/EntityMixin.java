package cn.ksmcbrigade.eo.mixin;

import cn.ksmcbrigade.eo.EntityProtection;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
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

    @Inject(method = "hurt",at = @At("HEAD"), cancellable = true)
    public void damage(CallbackInfoReturnable<Boolean> cir){
        if(EntityProtection.safes.contains(this.uuid)){
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "causeFallDamage",at = @At("HEAD"), cancellable = true)
    public void fallDamage(CallbackInfoReturnable<Boolean> cir){
        if(EntityProtection.safes.contains(this.uuid)){
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "lavaHurt",at = @At("HEAD"), cancellable = true)
    public void lava(CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "kill",at = @At("HEAD"), cancellable = true)
    public void kill(CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "killedEntity",at = @At("HEAD"), cancellable = true)
    public void kill2(ServerLevel p_216988_, LivingEntity p_216989_, CallbackInfoReturnable<Boolean> cir){
        if(EntityProtection.safes.contains(this.uuid)){
            cir.setReturnValue(false);
            cir.cancel();
        }
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

    @Inject(method = "discard",at = @At("HEAD"), cancellable = true)
    public void die(CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }

    @Inject(method = "setUUID",at = @At("HEAD"), cancellable = true)
    public void uuid(CallbackInfo ci){
        if(EntityProtection.safes.contains(this.uuid)) ci.cancel();
    }
}

package cn.ksmcbrigade.eo.mixin;

import cn.ksmcbrigade.eo.EntityProtection;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.SculkCatalystBlockEntity;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SculkCatalystBlockEntity.CatalystListener.class)
public class EventMixin {
    @Inject(method = "handleGameEvent",at = @At("HEAD"), cancellable = true)
    public void handle(ServerLevel p_283470_, GameEvent p_282184_, GameEvent.Context p_283014_, Vec3 p_282350_, CallbackInfoReturnable<Boolean> cir){
        if(p_282184_.equals(GameEvent.ENTITY_DIE) || p_282184_.equals(GameEvent.ENTITY_DAMAGE)){
            if(EntityProtection.safes.contains(p_283014_.sourceEntity().getUUID())){
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
}

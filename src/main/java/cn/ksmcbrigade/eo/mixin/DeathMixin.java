package cn.ksmcbrigade.eo.mixin;

import cn.ksmcbrigade.eo.EntityProtection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(DeathScreen.class)
public abstract class DeathMixin extends Screen{

    @Unique
    private final Minecraft MC = Minecraft.getInstance();

    protected DeathMixin(Component p_96550_) {
        super(p_96550_);
    }

    @Inject(method = "shouldCloseOnEsc",at = @At("RETURN"), cancellable = true)
    public void close(CallbackInfoReturnable<Boolean> cir) throws IOException {
        if(entityProtection$con()){
            System.out.println("Check death close!");
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "init",at = @At("TAIL"))
    public void init(CallbackInfo ci) throws IOException {
        if(entityProtection$con()){
            System.out.println("Check screen!");
            Screen r = new TitleScreen();
            this.onClose();
            MC.setScreen(r);
            MC.setScreen(r);
            MC.setScreen(r);
            MC.screen = r;
            MC.screen = r;
            MC.screen = r;
            MC.setScreen(null);
            MC.setScreen(null);
            MC.setScreen(null);
            MC.screen = null;
            MC.screen = null;
            MC.screen = null;
        }
    }

    @Unique
    private boolean entityProtection$con() throws IOException {
        return Files.readString(EntityProtection.path).contains(MC.player.getUUID().toString());
    }
}

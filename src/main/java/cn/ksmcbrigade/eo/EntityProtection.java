package cn.ksmcbrigade.eo;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EntityProtection.MODID)
@Mod.EventBusSubscriber(modid = EntityProtection.MODID)
public class EntityProtection {
    public static final String MODID = "ep";

    public static final FinalArrayList<UUID> safes = new FinalArrayList<>();

    public EntityProtection() {
        MinecraftForge.EVENT_BUS.register(this);
        UUID uuid = UUID.randomUUID();
        safes.add(uuid);
        safes.remove(uuid);
    }

    @SubscribeEvent
    public void commands(RegisterCommandsEvent event){
        event.getDispatcher().register(Commands.literal("safe").then(Commands.argument("entity", EntityArgument.entity()).executes(new safe())));

        event.getDispatcher().register(Commands.literal("un-safe").then(Commands.argument("entity", EntityArgument.entity()).executes(new unsafe())));
    }

    protected static final class safe implements Command<CommandSourceStack> {

        @Override
        public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
            safes.add(EntityArgument.getEntity(context,"entity").getUUID());
            return 0;
        }
    }

    protected static final class unsafe implements Command<CommandSourceStack>{

        @Override
        public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
            safes.remove(EntityArgument.getEntity(context,"entity").getUUID());
            return 0;
        }
    }
}

package cn.ksmcbrigade.eo;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EntityProtection.MODID)
@Mod.EventBusSubscriber(modid = EntityProtection.MODID)
public class EntityProtection {
    public static final String MODID = "ep";

    public static final FinalArrayList<UUID> safes = new FinalArrayList<>();

    public static final Path path = Paths.get("ep-temp.txt");

    protected static final SimpleChannel channel = NetworkRegistry.newSimpleChannel(new ResourceLocation("ep","sync"),()->"340",(s) -> true, (s) -> true);


    public EntityProtection() throws IOException {
        MinecraftForge.EVENT_BUS.register(this);
        Files.writeString(path,"");
        registerMessages();
        UUID uuid = UUID.randomUUID();
        safes.add(uuid);
        safes.remove(uuid);
    }

    @SubscribeEvent
    public void commands(RegisterCommandsEvent event){
        event.getDispatcher().register(Commands.literal("safe").then(Commands.argument("entity", EntityArgument.entity()).executes(new safe())));

        event.getDispatcher().register(Commands.literal("un-safe").then(Commands.argument("entity", EntityArgument.entity()).executes(new unsafe())));
    }

    protected final class safe implements Command<CommandSourceStack> {

        @Override
        public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
            EntityArgument.getEntity(context,"entity").setInvulnerable(true);
            if(EntityArgument.getEntity(context,"entity") instanceof LivingEntity e){
                e.hurtTime = 0;
                e.deathTime = 0;
            }
            context.getSource().sendSystemMessage(Component.nullToEmpty(String.valueOf(safes.add(EntityArgument.getEntity(context,"entity").getUUID()))));
            if(context.getSource().getPlayer()!=null){
                sendPacketToClient("add;"+EntityArgument.getEntity(context,"entity").getUUID());
                System.out.println("Sync!");
            }
            return 0;
        }
    }

    protected final void sendPacketToClient(String str) {
        channel.send(PacketDistributor.ALL.noArg(),new Message(str));
    }

    protected final class unsafe implements Command<CommandSourceStack>{

        @Override
        public int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
            context.getSource().sendSystemMessage(Component.nullToEmpty(String.valueOf(safes.remove(EntityArgument.getEntity(context,"entity").getUUID()))));
            EntityArgument.getEntity(context,"entity").setInvulnerable(false);
            if(EntityArgument.getEntity(context,"entity") instanceof LivingEntity e){
                e.hurtTime = 0;
                e.deathTime = 0;
                e.updateSwimming();
                e.setHealth(e.getHealth()<=0?2f:e.getHealth());
            }
            if(context.getSource().getPlayer()!=null){
                sendPacketToClient("remove;"+EntityArgument.getEntity(context,"entity").getUUID());
                System.out.println("Sync!");
            }
            return 0;
        }
    }

    protected final static class Message {
        private final String message;

        public Message(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public static void encode(Message msg, FriendlyByteBuf buf) {
            buf.writeUtf(msg.message);
        }

        public static Message decode(FriendlyByteBuf buf) {
            return new Message(buf.readUtf());
        }


    }

    protected final void registerMessages() {
        channel.registerMessage(0,Message.class,Message::encode,Message::decode,((message, contextSupplier) -> {
            contextSupplier.get().enqueueWork(()->{
                DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> EntityProtection.handlePacket(message,contextSupplier));
            });
            contextSupplier.get().setPacketHandled(true);
        }));
    }

    protected static void handlePacket(Message msg, Supplier<NetworkEvent.Context> ctx) {
        String[] me = msg.message.split(";");
        if(me[0].equalsIgnoreCase("add")){
            try {
                Files.writeString(path,Files.readString(path)+";"+ me[1]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                Files.writeString(path,Files.readString(path).replace(";"+ me[1],""));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("File change!");
    }
}

package net.modfest.epoch;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.GAME)
public class EpochCommands {

    @SubscribeEvent
    public static void registerEvent(RegisterCommandsEvent event) {
        register(event.getDispatcher());

    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> command = dispatcher.register(
                Commands.literal("epoch_reset").requires(
                        s -> s.hasPermission(2)
                ).executes(EpochCommands::resetWorldCommand)
        );
    }

    public static int resetWorldCommand(CommandContext<CommandSourceStack> source) {
        if (!Config.isClientServer) {
            source.getSource().sendFailure(Component.literal("cannot run this command outside aux server"));
        }
        ServerLevel result = DimensionControl.createRandomDimension(source.getSource().getServer());
        source.getSource().sendSuccess(() -> Component.literal("created dim " + result.getDescriptionKey()), true);
        return 1;
    }
}


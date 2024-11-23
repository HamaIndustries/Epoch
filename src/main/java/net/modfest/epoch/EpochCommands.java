package net.modfest.epoch;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.mynamesraph.mystcraft.item.DescriptiveBookItem;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
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
        source.getSource().sendSuccess(() -> Component.literal("resetting epoch"), true);


        return 1;
    }
}


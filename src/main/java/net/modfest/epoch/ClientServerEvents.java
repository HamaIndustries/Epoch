package net.modfest.epoch;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.*;

@EventBusSubscriber(modid = Epoch.MODID, bus = EventBusSubscriber.Bus.GAME)
public class ClientServerEvents {
    static boolean isClientServer() {
        return Config.isClientServer;
    }
    private static final Queue<ServerPlayer> playersToSpawn = new LinkedList<>();

    @SubscribeEvent
    public static void loadOtherPlayerData(PlayerEvent.PlayerLoggedInEvent event) {
        if (!isClientServer()) return;
        var player = (ServerPlayer)event.getEntity();
        player.setGameMode(player.getServer().getDefaultGameType());

        ResourceKey<Level> currentDim = DimensionControl.currentDim();
        if (currentDim != null) {
            player.setRespawnPosition(currentDim, null, 0, true, false);
        }
        playersToSpawn.add(player);
    }

    @SubscribeEvent
    public static void spawnLoadedPlayers(ServerTickEvent.Pre event) {
        ServerPlayer player = playersToSpawn.poll();
        while (player != null) {
            var p = player.adjustSpawnLocation(player.serverLevel(), player.level().getSharedSpawnPos()).getBottomCenter();
            player.connection.teleport(p.x, p.y, p.z, player.getRotationVector().y, player.getRotationVector().x);
            player = playersToSpawn.poll();
        }
    }
}

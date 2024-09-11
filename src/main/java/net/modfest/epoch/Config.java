package net.modfest.epoch;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
@EventBusSubscriber(modid = Epoch.MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue IS_CLIENT = BUILDER
            .comment("Whether this serves as epoch's transfer target")
            .define("isClientServer", false);

    private static final ModConfigSpec.ConfigValue<? extends String> LEVEL_SOURCE = BUILDER
            .comment("(Transfer target only) Path to target's source save folder")
            .define("worldSaveSourcePath", "world");

    private static final ModConfigSpec.ConfigValue<? extends String> PLAYER_SOURCE = BUILDER
            .comment("(Transfer target only) Path to player data copy folder")
            .define("playerDataSourcePath", "world/playerdata");

    static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean isClientServer;
    public static String levelSourcePath;
    public static String playerSourcePath;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        isClientServer = IS_CLIENT.get();
        levelSourcePath = LEVEL_SOURCE.get();
        playerSourcePath = PLAYER_SOURCE.get();
    }
}

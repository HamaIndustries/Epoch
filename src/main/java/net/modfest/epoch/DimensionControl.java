package net.modfest.epoch;

import com.mynamesraph.mystcraft.component.BiomeSymbolsComponent;
import com.mynamesraph.mystcraft.registry.MystcraftComponents;
import com.mynamesraph.mystcraft.registry.MystcraftItems;
import net.commoble.infiniverse.api.InfiniverseAPI;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.modfest.epoch.mixin.DescriptiveBookItemAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DimensionControl {

    private static int dimCounter = 0;
    private static ResourceKey<Level> currentDim = null;

    public static ServerLevel createRandomDimension(MinecraftServer server) {

        Registry<Biome> biomeRegistry = server.registries().compositeAccess().registry(Registries.BIOME).orElseThrow();

        List<ResourceLocation> randomizedBiomes = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            var selectedBiome = biomeRegistry.getRandom(server.overworld().getRandom()).get();
            if (!randomizedBiomes.contains(selectedBiome)) {
                randomizedBiomes.add(selectedBiome.key().location());
            }
        }

        var biomeSymbolsComponent = MystcraftComponents.INSTANCE.getBIOME_SYMBOLS().get();
        var book = MystcraftItems.INSTANCE.getDESCRIPTIVE_BOOK().get().getDefaultInstance();

        book.set(biomeSymbolsComponent, new BiomeSymbolsComponent(randomizedBiomes));

        var levelKey = ResourceKey.create(
                Registries.DIMENSION,
                ResourceLocation.fromNamespaceAndPath(Epoch.MODID, "test_dim_"  + dimCounter++)
        );

        currentDim = levelKey;
        return InfiniverseAPI.get().getOrCreateLevel(
                server,
                levelKey,
                () -> ((DescriptiveBookItemAccessor)MystcraftItems.INSTANCE.getDESCRIPTIVE_BOOK().asItem())
                        .invokeCreateNoiseLevel(server, book)
        );
    }

    @Nullable
    public static ResourceKey<Level> currentDim() {
        return currentDim;
    }
}

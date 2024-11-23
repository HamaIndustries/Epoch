package net.modfest.epoch.mixin;

import com.mynamesraph.mystcraft.item.DescriptiveBookItem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.dimension.LevelStem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value = DescriptiveBookItem.class, remap = false)
public interface DescriptiveBookItemAccessor {
    @Invoker
    LevelStem invokeCreateNoiseLevel(MinecraftServer server, ItemStack book);
}

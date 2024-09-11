package net.modfest.epoch.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.PlayerDataStorage;
import net.modfest.epoch.Config;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.io.File;
import java.io.IOException;

@Mixin(PlayerDataStorage.class)
public class PlayerDataStorageMixin {
    @ModifyExpressionValue(
            method = "Lnet/minecraft/world/level/storage/PlayerDataStorage;load(Lnet/minecraft/world/entity/player/Player;Ljava/lang/String;)Ljava/util/Optional;",
            at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/storage/PlayerDataStorage;playerDir:Ljava/io/File;")
    )
    private File wrapPlayerLoad(File original, Player player, String suffix) throws IOException {
        return Config.isClientServer ? new File(Config.playerSourcePath) : original;
    }
}

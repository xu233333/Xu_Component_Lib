package xu_mod.xu_component_lib.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xu_mod.xu_component_lib.api.SerializableComponent;

import java.util.function.Function;

public class ExpectPlatformImpl {
    public static void registerPlayerComponent(ResourceLocation id, Function<Player, SerializableComponent<Player>> component) {
        throw new AssertionError();
    }

    public static void registerEntityComponent(ResourceLocation id, Function<LivingEntity, SerializableComponent<LivingEntity>> component) {
        throw new AssertionError();
    }

    public static SerializableComponent<Player> getPlayerComponent(Player player, ResourceLocation id) {
        throw new AssertionError();
    }

    public static void syncPlayerComponent(Player player, ResourceLocation id) {
        throw new AssertionError();
    }

    public static SerializableComponent<LivingEntity> getEntityComponent(LivingEntity entity, ResourceLocation id) {
        throw new AssertionError();
    }

    public static void syncEntityComponent(LivingEntity entity, ResourceLocation id) {
        throw new AssertionError();
    }
}

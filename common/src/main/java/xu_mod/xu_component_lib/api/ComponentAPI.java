package xu_mod.xu_component_lib.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xu_mod.xu_component_lib.Platform;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class ComponentAPI {
    public static HashMap<ResourceLocation, Function<Player, SerializableComponent<Player>>> playerComponents = new HashMap<>();
    public static HashMap<ResourceLocation, Function<LivingEntity, SerializableComponent<LivingEntity>>> entityComponents = new HashMap<>();

    public static ResourceLocation registerPlayerComponent(ResourceLocation id, Function<Player, SerializableComponent<Player>> component) {
        playerComponents.put(id, component);
        Platform.registerPlayerComponent(id, component);
        return id;
    }

    public static ResourceLocation registerEntityComponent(ResourceLocation id, Function<LivingEntity, SerializableComponent<LivingEntity>> component) {
        entityComponents.put(id, component);
        Platform.registerEntityComponent(id, component);
        return id;
    }

    public static SerializableComponent<Player> getPlayerComponent(Player player, ResourceLocation id) {
        return Platform.getPlayerComponent(player, id);
    }

    public static void syncPlayerComponent(Player player, ResourceLocation id) {
        Platform.syncPlayerComponent(player, id);
    }

    public static SerializableComponent<LivingEntity> getEntityComponent(LivingEntity entity, ResourceLocation id) {
        return Platform.getEntityComponent(entity, id);
    }

    public static void syncEntityComponent(LivingEntity entity, ResourceLocation id) {
        Platform.syncEntityComponent(entity, id);
    }
}

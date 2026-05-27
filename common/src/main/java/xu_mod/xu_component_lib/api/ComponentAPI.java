package xu_mod.xu_component_lib.api;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;

public class ComponentAPI {
    public static HashMap<ResourceLocation, SerializableComponent<Player>> playerComponents = new HashMap<>();
    public static HashMap<ResourceLocation, SerializableComponent<LivingEntity>> entityComponents = new HashMap<>();

    public static ResourceLocation registerPlayerComponent(ResourceLocation id, SerializableComponent<Player> component) {
        playerComponents.put(id, component);
        return id;
    }

    public static ResourceLocation registerEntityComponent(ResourceLocation id, SerializableComponent<LivingEntity> component) {
        entityComponents.put(id, component);
        return id;
    }

    // TODO
    public static SerializableComponent<Player> getPlayerComponent(Player player, ResourceLocation id) {
        return null;
    }

    public static void syncPlayerComponent(Player player, ResourceLocation id) {
        return;
    }

    public static SerializableComponent<LivingEntity> getEntityComponent(LivingEntity entity, ResourceLocation id) {
        return null;
    }

    public static void syncEntityComponent(LivingEntity entity, ResourceLocation id) {
        return;
    }
}

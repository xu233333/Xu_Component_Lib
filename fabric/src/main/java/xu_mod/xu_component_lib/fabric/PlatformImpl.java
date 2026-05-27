package xu_mod.xu_component_lib.fabric;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xu_mod.xu_component_lib.api.SerializableComponent;

import java.util.function.Function;

public class PlatformImpl {
    public static void registerPlayerComponent(ResourceLocation id, Function<Player, SerializableComponent<Player>> component) {
    }

    public static void registerEntityComponent(ResourceLocation id, Function<LivingEntity, SerializableComponent<LivingEntity>> component) {
    }

    public static SerializableComponent<Player> getPlayerComponent(Player player, ResourceLocation id) {
        return ComponentSystemImpl_CCA.getComponentKey_Player(id, player).get(player).component;
    }

    public static void syncPlayerComponent(Player player, ResourceLocation id) {
        ComponentSystemImpl_CCA.getComponentKey_Player(id, player).sync(player);
    }

    public static SerializableComponent<LivingEntity> getEntityComponent(LivingEntity entity, ResourceLocation id) {
        return ComponentSystemImpl_CCA.getComponentKey_Entity(id, entity).get(entity).component;
    }

    public static void syncEntityComponent(LivingEntity entity, ResourceLocation id) {
        ComponentSystemImpl_CCA.getComponentKey_Entity(id, entity).sync(entity);
    }
}

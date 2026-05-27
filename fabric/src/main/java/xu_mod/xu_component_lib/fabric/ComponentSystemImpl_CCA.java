package xu_mod.xu_component_lib.fabric;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xu_mod.xu_component_lib.api.ComponentAPI;
import xu_mod.xu_component_lib.api.SerializableComponent;
import xu_mod.xu_component_lib.fabric.component.EntityComponentBase;
import xu_mod.xu_component_lib.fabric.component.PlayerComponentBase;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ComponentSystemImpl_CCA implements EntityComponentInitializer {
    public static final HashMap<ResourceLocation, ComponentKey<PlayerComponentBase>> playerComponentRegistry = new HashMap<>();
    public static final HashMap<ResourceLocation, ComponentKey<EntityComponentBase>> entityComponentRegistry = new HashMap<>();

    public static ComponentKey<PlayerComponentBase> getComponentKey_Player(ResourceLocation id, Player player) {
        return playerComponentRegistry.get(id);
    }

    public static ComponentKey<EntityComponentBase> getComponentKey_Entity(ResourceLocation id, LivingEntity entity) {
        return entityComponentRegistry.get(id);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        for (Map.Entry<ResourceLocation, Function<Player, SerializableComponent<Player>>> entry : ComponentAPI.playerComponents.entrySet()) {
            ResourceLocation id = entry.getKey();
            Function<Player, SerializableComponent<Player>> component = entry.getValue();
            ComponentKey<PlayerComponentBase> componentKey = ComponentRegistry.getOrCreate(id, PlayerComponentBase.class);
            registry.registerForPlayers(
                    componentKey,
                    player -> {
                        SerializableComponent<Player> componentBase = component.apply(player);
                        componentBase.init(player);
                        return new PlayerComponentBase(componentBase);
                    },
                    RespawnCopyStrategy.ALWAYS_COPY
            );
            playerComponentRegistry.put(id, componentKey);
        }
        for (Map.Entry<ResourceLocation, Function<LivingEntity, SerializableComponent<LivingEntity>>> entry : ComponentAPI.entityComponents.entrySet()) {
            ResourceLocation id = entry.getKey();
            Function<LivingEntity, SerializableComponent<LivingEntity>> component = entry.getValue();
            ComponentKey<EntityComponentBase> componentKey = ComponentRegistry.getOrCreate(id, EntityComponentBase.class);
            registry.registerFor(
                    LivingEntity.class,
                    componentKey,
                    entity -> {
                        SerializableComponent<LivingEntity> componentBase = component.apply(entity);
                        componentBase.init(entity);
                        return new EntityComponentBase(componentBase);
                    }
            );
            entityComponentRegistry.put(id, componentKey);
        }
    }
}

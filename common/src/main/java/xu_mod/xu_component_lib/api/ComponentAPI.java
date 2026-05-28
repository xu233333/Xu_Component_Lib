package xu_mod.xu_component_lib.api;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;
import xu_mod.xu_component_lib.Platform;

import java.util.HashMap;
import java.util.function.Function;
import java.util.function.Supplier;

public class ComponentAPI {
    // 不推荐用
    public static HashMap<ResourceLocation, Function<Player, SerializableComponent<Player>>> playerComponents = new HashMap<>();
    public static HashMap<ResourceLocation, Function<LivingEntity, SerializableComponent<LivingEntity>>> entityComponents = new HashMap<>();

    // 这不是注册表 需要在Impl里实现对应的逻辑 类似Enum
    public static ComponentType<Player> PLAYER = new ComponentType<>(Player.class, playerComponents);
    public static ComponentType<LivingEntity> ENTITY = new ComponentType<>(LivingEntity.class, entityComponents);

    public static <T> ResourceLocation registerComponent(ComponentType<T> type, ResourceLocation id, Function<T, SerializableComponent<T>> component) {
        return type.registerComponent(id, component);
    }

    public static <CLASS, DATA extends SerializableComponent<CLASS>> ComponentProvider<CLASS, DATA> registerComponent_Provider(ComponentType<CLASS> type, ResourceLocation id, Function<CLASS, SerializableComponent<CLASS>> component) {
        type.registerComponent(id, component);
        return (ComponentProvider<CLASS, DATA>) type.createProvider(id);
    }

    public static <T> SerializableComponent<T> getComponent(ComponentType<T> type, T owner, ResourceLocation id) {
        return type.getComponent(owner, id);
    }

    public static <T> void syncComponent(ComponentType<T> type, T owner, ResourceLocation id) {
        type.syncComponent(owner, id);
    }

    public static void onPlayerRespawn(Player oldPlayer, Player newPlayer, boolean wasRespawn) {
        for (ResourceLocation id : PLAYER.getRegisterMap().keySet()) {
            SerializableComponent<Player> oldComp = Platform.getComponent(PLAYER, oldPlayer, id);
            SerializableComponent<Player> newComp = Platform.getComponent(PLAYER, newPlayer, id);
            if (oldComp != null && newComp != null) {
                CompoundTag temp = new CompoundTag();
                oldComp.save(temp, false);
                newComp.load(temp, false);
                if (wasRespawn) {
                    newComp.onRespawn(oldPlayer, newPlayer);
                }
                Platform.syncComponent(PLAYER, newPlayer, id);
            }
        }
        for (ResourceLocation id : ENTITY.getRegisterMap().keySet()) {
            SerializableComponent<LivingEntity> oldComp = Platform.getComponent(ENTITY, oldPlayer, id);
            SerializableComponent<LivingEntity> newComp = Platform.getComponent(ENTITY, newPlayer, id);
            if (oldComp != null && newComp != null) {
                CompoundTag temp = new CompoundTag();
                oldComp.save(temp, false);
                newComp.load(temp, false);
                if (wasRespawn) {
                    newComp.onRespawn(oldPlayer, newPlayer);
                }
                Platform.syncComponent(ENTITY, newPlayer, id);
            }
        }
    }
}

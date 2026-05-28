package xu_mod.xu_component_lib.fabric;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import xu_mod.xu_component_lib.XuComponentLib;
import xu_mod.xu_component_lib.api.ComponentAPI;
import xu_mod.xu_component_lib.api.ComponentType;
import xu_mod.xu_component_lib.api.SerializableComponent;

import java.util.function.Function;

public class PlatformImpl {
    public static <T> void registerComponent(ComponentType<T> type, ResourceLocation id, Function<T, SerializableComponent<T>> component) {
        // 提醒一下注册晚了
        if (ComponentSystemImpl_CCA.IsInitialized) {
            XuComponentLib.LOGGER.error("Cannot register player component after capability registry has been initialized");
            return;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> SerializableComponent<T> getComponent(ComponentType<T> type, T owner, ResourceLocation id) {
        if (type == ComponentAPI.PLAYER && owner instanceof Player player) {
            return (SerializableComponent<T>) (Object) ComponentSystemImpl_CCA.getComponentKey_Player(id, player).get(player).component;
        }
        if (type == ComponentAPI.ENTITY && owner instanceof LivingEntity entity) {
            return (SerializableComponent<T>) (Object) ComponentSystemImpl_CCA.getComponentKey_Entity(id, entity).get(entity).component;
        }
        throw new AssertionError();
    }

    public static <T> void syncComponent(ComponentType<T> type, T owner, ResourceLocation id) {
        if (type == ComponentAPI.PLAYER && owner instanceof Player player) {
            ComponentSystemImpl_CCA.getComponentKey_Player(id, player).sync(player);
            return;
        }
        if (type == ComponentAPI.ENTITY && owner instanceof LivingEntity entity) {
            ComponentSystemImpl_CCA.getComponentKey_Entity(id, entity).sync(entity);
            return;
        }
        throw new AssertionError();
    }
}

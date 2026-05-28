package xu_mod.xu_component_lib;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import xu_mod.xu_component_lib.api.ComponentType;
import xu_mod.xu_component_lib.api.SerializableComponent;

import java.util.function.Function;

public class Platform {
    @ExpectPlatform
    public static <T> void registerComponent(ComponentType<T> type, ResourceLocation id, Function<T, SerializableComponent<T>> component) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> SerializableComponent<T> getComponent(ComponentType<T> type, T owner, ResourceLocation id) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T> void syncComponent(ComponentType<T> type, T owner, ResourceLocation id) {
        throw new AssertionError();
    }
}

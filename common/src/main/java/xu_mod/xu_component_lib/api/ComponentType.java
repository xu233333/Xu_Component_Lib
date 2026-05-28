package xu_mod.xu_component_lib.api;

import net.minecraft.resources.ResourceLocation;
import xu_mod.xu_component_lib.Platform;

import java.util.HashMap;
import java.util.function.Function;

public class ComponentType<T> {
    private final Class<T> componentClass;
    private final HashMap<ResourceLocation, Function<T, SerializableComponent<T>>> registerMap = new HashMap<>();

    ComponentType(Class<T> componentClass, HashMap<ResourceLocation, Function<T, SerializableComponent<T>>> registerMap) {
        this.componentClass = componentClass;
    }

    public Class<T> getComponentClass() {
        return componentClass;
    }

    public HashMap<ResourceLocation, Function<T, SerializableComponent<T>>> getRegisterMap() {
        return registerMap;
    }

    public ResourceLocation registerComponent(ResourceLocation id, Function<T, SerializableComponent<T>> component) {
        this.registerMap.put(id, component);
        Platform.registerComponent(this, id, component);
        return id;
    }

    public SerializableComponent<T> getComponent(T owner, ResourceLocation id) {
        return Platform.getComponent(this, owner, id);
    }

    public void syncComponent(T owner, ResourceLocation id) {
        Platform.syncComponent(this, owner, id);
    }

    public ComponentProvider<T, SerializableComponent<T>> createProvider(ResourceLocation id) {
        return new ComponentProvider<>(this, id);
    }
}

package xu_mod.xu_component_lib.api;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class ComponentProvider<CLASS, DATA extends SerializableComponent<CLASS>> {
    public final ComponentType<CLASS> type;
    public final ResourceLocation id;

    public ComponentProvider(ComponentType<CLASS> type, ResourceLocation id) {
        this.type = type;
        this.id = id;
    }

    public DATA getComponent(CLASS owner) {
        return (DATA) type.getComponent(owner, id);
    }

    public void syncComponent(CLASS owner) {
        type.syncComponent(owner, id);
    }
}

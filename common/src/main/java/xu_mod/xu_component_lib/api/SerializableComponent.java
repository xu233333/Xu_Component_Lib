package xu_mod.xu_component_lib.api;

import net.minecraft.nbt.CompoundTag;

public interface SerializableComponent<T> {
    void save(CompoundTag nbt, boolean formSync);

    void load(CompoundTag nbt, boolean formSync);

    default void init(T componentOwner) {}
}

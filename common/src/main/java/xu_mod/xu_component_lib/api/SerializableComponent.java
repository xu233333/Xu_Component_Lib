package xu_mod.xu_component_lib.api;

import net.minecraft.nbt.CompoundTag;

public interface SerializableComponent<T> {
    CompoundTag save(CompoundTag nbt);
    void load(CompoundTag nbt);

    default void init(T componentOwner) {}
}

package xu_mod.xu_component_lib.api;

import net.minecraft.nbt.CompoundTag;

public interface SerializableComponent<T> {
    void save(CompoundTag nbt, boolean formSync);

    void load(CompoundTag nbt, boolean formSync);

    default void init(T componentOwner) {}

    // 触发load(formSync = false) 后调用
    default void onRespawn(T oldObject, T newObject) {};
}

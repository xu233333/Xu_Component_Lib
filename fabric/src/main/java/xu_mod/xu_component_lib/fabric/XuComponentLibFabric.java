package xu_mod.xu_component_lib.fabric;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import xu_mod.xu_component_lib.XuComponentLib;
import net.fabricmc.api.ModInitializer;
import xu_mod.xu_component_lib.api.ComponentAPI;

public class XuComponentLibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        XuComponentLib.init();
        // 让玩家复活时继承旧数据 并触发onRespawn
        ServerPlayerEvents.COPY_FROM.register(
                (oldPlayer, newPlayer, alive) -> {
                    ComponentAPI.onPlayerRespawn(oldPlayer, newPlayer, !alive);
                }
        );
    }
}

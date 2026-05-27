package xu_mod.xu_component_lib.fabric;

import xu_mod.xu_component_lib.XuComponentLib;
import net.fabricmc.api.ModInitializer;

public class XuComponentLibFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        XuComponentLib.init();
    }
}

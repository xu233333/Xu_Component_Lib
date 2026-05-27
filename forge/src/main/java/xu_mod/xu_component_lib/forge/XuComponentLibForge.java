package xu_mod.xu_component_lib.forge;

import dev.architectury.platform.forge.EventBuses;
import xu_mod.xu_component_lib.XuComponentLib;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(XuComponentLib.MOD_ID)
public class XuComponentLibForge {
    public XuComponentLibForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(XuComponentLib.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        XuComponentLib.init();
    }
}

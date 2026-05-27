package xu_mod.xu_component_lib.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import xu_mod.xu_component_lib.XuComponentLib;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import xu_mod.xu_component_lib.forge.capability.CapabilityRegistry;
import xu_mod.xu_component_lib.forge.network.NetworkHandler;

@Mod(XuComponentLib.MOD_ID)
public class XuComponentLibForge {
    public XuComponentLibForge() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(XuComponentLib.MOD_ID, modBus);
        XuComponentLib.init();
        modBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            NetworkHandler.register();
            CapabilityRegistry.registerAll();
        });
    }
}

package xu_mod.xu_component_lib.forge.capability;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xu_mod.xu_component_lib.XuComponentLib;
import xu_mod.xu_component_lib.api.ComponentAPI;
import xu_mod.xu_component_lib.api.SerializableComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = XuComponentLib.MOD_ID)
public class CapabilityRegistry {
    public static boolean IsInitialized = false;

    public static final HashMap<ResourceLocation, Capability<ComponentCapability<Player>>> PLAYER_CAPS = new HashMap<>();
    public static final Map<ResourceLocation, Capability<ComponentCapability<LivingEntity>>> ENTITY_CAPS = new HashMap<>();

    public static void registerAll() {
        for (Map.Entry<ResourceLocation, Function<Player, SerializableComponent<Player>>> entry : ComponentAPI.playerComponents.entrySet()) {
            ResourceLocation id = entry.getKey();
            Capability<ComponentCapability<Player>> cap = CapabilityManager.get(new CapabilityToken<>() {});
            PLAYER_CAPS.put(id, cap);
        }
        for (Map.Entry<ResourceLocation, Function<LivingEntity, SerializableComponent<LivingEntity>>> entry : ComponentAPI.entityComponents.entrySet()) {
            ResourceLocation id = entry.getKey();
            Capability<ComponentCapability<LivingEntity>> cap = CapabilityManager.get(new CapabilityToken<>() {});
            ENTITY_CAPS.put(id, cap);
        }
        IsInitialized = true;
    }

    public static Capability<ComponentCapability<Player>> getPlayerCap(ResourceLocation id) {
        return PLAYER_CAPS.get(id);
    }

    public static Capability<ComponentCapability<LivingEntity>> getEntityCap(ResourceLocation id) {
        return ENTITY_CAPS.get(id);
    }

    @SubscribeEvent
    public static void attachPlayerCapabilities(AttachCapabilitiesEvent<Player> event) {
        Player player = event.getObject();
        for (var entry : ComponentAPI.playerComponents.entrySet()) {
            ResourceLocation id = entry.getKey();
            var componentFactory = entry.getValue();
            SerializableComponent<Player> comp = componentFactory.apply(player);
            ComponentCapability<Player> capability = new ComponentCapability<>(comp, player);
            event.addCapability(id, new SimpleCapabilityProvider<>(CapabilityRegistry.getPlayerCap(id), capability));
        }
    }

    @SubscribeEvent
    public static void attachEntityCapabilities(AttachCapabilitiesEvent<LivingEntity> event) {
        LivingEntity entity = event.getObject();
        for (var entry : ComponentAPI.entityComponents.entrySet()) {
            ResourceLocation id = entry.getKey();
            var componentFactory = entry.getValue();
            SerializableComponent<LivingEntity> comp = componentFactory.apply(entity);
            ComponentCapability<LivingEntity> capability = new ComponentCapability<>(comp, entity);
            event.addCapability(id, new SimpleCapabilityProvider<>(CapabilityRegistry.getEntityCap(id), capability));
        }
    }

    @SubscribeEvent
    public static void registerCaps(RegisterCapabilitiesEvent event) {
        event.register(ComponentCapability.class);
    }
}

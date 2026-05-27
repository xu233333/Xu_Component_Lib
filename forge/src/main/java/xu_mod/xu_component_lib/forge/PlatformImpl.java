package xu_mod.xu_component_lib.forge;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.PacketDistributor;
import xu_mod.xu_component_lib.XuComponentLib;
import xu_mod.xu_component_lib.api.SerializableComponent;
import xu_mod.xu_component_lib.forge.capability.CapabilityRegistry;
import xu_mod.xu_component_lib.forge.capability.ComponentCapability;
import xu_mod.xu_component_lib.forge.network.ComponentSyncPacket;
import xu_mod.xu_component_lib.forge.network.NetworkHandler;

import java.util.function.Function;

public class PlatformImpl {
    public static void registerPlayerComponent(ResourceLocation id, Function<Player, SerializableComponent<Player>> component) {
        // 提醒一下注册晚了
        if (CapabilityRegistry.IsInitialized) {
            XuComponentLib.LOGGER.error("Cannot register player component after capability registry has been initialized");
            return;
        }
    }

    public static void registerEntityComponent(ResourceLocation id, Function<LivingEntity, SerializableComponent<LivingEntity>> component) {
        // 提醒一下注册晚了
        if (CapabilityRegistry.IsInitialized) {
            XuComponentLib.LOGGER.error("Cannot register entity component after capability registry has been initialized");
            return;
        }
    }

    public static SerializableComponent<Player> getPlayerComponent(Player player, ResourceLocation id) {
        var cap = CapabilityRegistry.getPlayerCap(id);
        if (cap == null) return null;
        return player.getCapability(cap).map(ComponentCapability::getComponent).orElse(null);
    }

    public static void syncPlayerComponent(Player player, ResourceLocation id) {
        if (player.level().isClientSide) return;
        var cap = CapabilityRegistry.getPlayerCap(id);
        if (cap == null) return;
        player.getCapability(cap).ifPresent(compCap -> {
            CompoundTag syncData = compCap.getSyncData();
            ComponentSyncPacket packet = new ComponentSyncPacket(player.getId(), id, syncData, true);
            NetworkHandler.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (ServerPlayer) player), packet);
        });
    }

    public static SerializableComponent<LivingEntity> getEntityComponent(LivingEntity entity, ResourceLocation id) {
        var cap = CapabilityRegistry.getEntityCap(id);
        if (cap == null) return null;
        return entity.getCapability(cap).map(ComponentCapability::getComponent).orElse(null);
    }

    public static void syncEntityComponent(LivingEntity entity, ResourceLocation id) {
        if (entity.level().isClientSide) return;
        var cap = CapabilityRegistry.getEntityCap(id);
        if (cap == null) return;
        entity.getCapability(cap).ifPresent(compCap -> {
            CompoundTag syncData = compCap.getSyncData();
            ComponentSyncPacket packet = new ComponentSyncPacket(entity.getId(), id, syncData, false);
            NetworkHandler.CHANNEL.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), packet);
        });
    }
}

package xu_mod.xu_component_lib.fabric.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import xu_mod.xu_component_lib.api.SerializableComponent;

public class PlayerComponentBase implements AutoSyncedComponent {
    public SerializableComponent<Player> component;

    public PlayerComponentBase(SerializableComponent<Player> component) {
        this.component = component;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        this.component.load(tag, false);
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        this.component.save(tag, false);
    }

    @Override
    public void writeSyncPacket(FriendlyByteBuf buf, ServerPlayer recipient) {
        CompoundTag tag = new CompoundTag();
        this.component.save(tag, true);
        buf.writeNbt(tag);
    }

    @Override
    public void applySyncPacket(FriendlyByteBuf buf) {
        CompoundTag tag = buf.readNbt();
        if (tag != null) {
            this.component.load(tag, true);
        }
    }
}

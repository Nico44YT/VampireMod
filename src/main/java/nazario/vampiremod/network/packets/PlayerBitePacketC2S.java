package nazario.vampiremod.network.packets;

import nazario.vampiremod.Vampiremod;
import nazario.vampiremod.util.VampireUtil;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public record PlayerBitePacketC2S(int entityId) implements FabricPacket, ServerPlayNetworking.PlayPacketHandler<PlayerBitePacketC2S> {

    public static final Identifier ID = Vampiremod.id("player_bite");
    public static final PacketType<PlayerBitePacketC2S> TYPE = PacketType.create(ID, PlayerBitePacketC2S::fromByteBuf);

    private static PlayerBitePacketC2S fromByteBuf(PacketByteBuf packetByteBuf) {
        return new PlayerBitePacketC2S(packetByteBuf.readInt());
    }

    @Override
    public void write(PacketByteBuf packetByteBuf) {
        packetByteBuf.writeInt(entityId);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    @Override
    public void receive(PlayerBitePacketC2S packet, ServerPlayerEntity serverPlayerEntity, PacketSender packetSender) {
        World world = serverPlayerEntity.getWorld();
        Entity bittenEntity = world.getEntityById(packet.entityId());

        if(bittenEntity instanceof LivingEntity livingEntity && livingEntity.isInRange(serverPlayerEntity, 2) && serverPlayerEntity.getHungerManager().isNotFull()) {
            if(!VampireUtil.canSuck(serverPlayerEntity, livingEntity)) return;
            if(VampireUtil.hasBadBlood(livingEntity)) {
                livingEntity.damage(world.getDamageSources().magic(), 1);
                serverPlayerEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 40, 0, false, false));
                return;
            }

            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20, 1, false, false));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 20, 255, false, false));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20, 255, false, false));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20, 0, false, false));

            livingEntity.damage(world.getDamageSources().magic(), 1);
            serverPlayerEntity.getHungerManager().add(1, 0);
        }
    }
}

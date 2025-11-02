package nazario.vampiremod.network;

import nazario.vampiremod.network.packets.PlayerBitePacketC2S;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class ModPackets {
    public static void registerC2S() {
        ServerPlayNetworking.registerGlobalReceiver(PlayerBitePacketC2S.TYPE, new PlayerBitePacketC2S(0));
    }

    public static void registerS2C() {

    }
}

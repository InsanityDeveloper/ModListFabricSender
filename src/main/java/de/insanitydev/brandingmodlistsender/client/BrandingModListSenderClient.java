package de.insanitydev.brandingmodlistsender.client;

import de.insanitydev.brandingmodlistsender.BrandingModListSender;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.LiteralText;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class BrandingModListSenderClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(BrandingModListSender.getBrandingModIdentity(), (client, handler, buf, responseSender) -> {
            System.out.println("[ModListSender] Received request to send mods.");
            MinecraftClient.getInstance().inGameHud.addChatMessage(MessageType.CHAT, new LiteralText("§8[§dModListSender§8] §aReceived request to send mods."), MinecraftClient.getInstance().player.getUuid());
            List<String> modList = new ArrayList<>();
            FabricLoader.getInstance().getAllMods().forEach(modContainer -> modList.add(modContainer.getMetadata().getId()));
            String[] modListArray = modList.toArray(new String[0]);
            System.out.println("[BrandingModListSender] Gathered all mods.");
            PacketByteBuf packetByteBuf = PacketByteBufs.create();
            StringBuilder modListBuffer = new StringBuilder();
            for (String mod : modListArray) {
                modListBuffer.append(mod).append("--/--");
            }
            byte[] modListBytes = modListBuffer.substring(0, modListBuffer.toString().length() - 5).getBytes(StandardCharsets.UTF_8);
            packetByteBuf.writeByteArray(modListBytes);
            ClientPlayNetworking.send(BrandingModListSender.getBrandingModIdentity(), packetByteBuf);
            System.out.println("[ModListSender] Sent all mods.");
        });
    }
}

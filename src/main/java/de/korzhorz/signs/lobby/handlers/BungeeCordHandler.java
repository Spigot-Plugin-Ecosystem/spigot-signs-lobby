package de.korzhorz.signs.lobby.handlers;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import de.korzhorz.signs.lobby.Main;
import de.korzhorz.signs.lobby.util.ServerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BungeeCordHandler implements PluginMessageListener {
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if(!(channel.equals("BungeeCord"))) {
            return;
        }

        ByteArrayDataInput byteArrayDataInput = ByteStreams.newDataInput(message);
        String subChannel = byteArrayDataInput.readUTF();

        if(subChannel.equals("Forward")) {
            String plugin = byteArrayDataInput.readUTF();
            if(!(plugin.equals("signs:data-updated"))) {
                return;
            }

            String serverName = byteArrayDataInput.readUTF();

            // TODO: Retrieve server data from database

            // TODO: Update signs
        }
    }

    public void sendPluginMessage(String subChannel, String[] message) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        try {
            dataOutputStream.write(subChannel.getBytes());
            for(String string : message) {
                dataOutputStream.write(string.getBytes());
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        Bukkit.getServer().sendPluginMessage(JavaPlugin.getPlugin(Main.class), "BungeeCord", byteArrayOutputStream.toByteArray());
    }
}

package net.minecraft.realms;

import org.apache.logging.log4j.LogManager;
import java.net.UnknownHostException;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.login.ServerboundHelloPacket;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.handshake.ClientIntentionPacket;
import net.minecraft.network.ConnectionProtocol;
import net.minecraft.network.PacketListener;
import net.minecraft.network.chat.Component;
import java.util.function.Consumer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.client.Minecraft;
import java.net.InetAddress;
import net.minecraft.network.Connection;
import org.apache.logging.log4j.Logger;

public class RealmsConnect {
    private static final Logger LOGGER;
    private final RealmsScreen onlineScreen;
    private volatile boolean aborted;
    private Connection connection;
    
    public RealmsConnect(final RealmsScreen realmsScreen) {
        this.onlineScreen = realmsScreen;
    }
    
    public void connect(final String string, final int integer) {
        Realms.setConnectedToRealms(true);
        Realms.narrateNow(Realms.getLocalizedString("mco.connect.success"));
        new Thread("Realms-connect-task") {
            public void run() {
                InetAddress inetAddress2 = null;
                try {
                    inetAddress2 = InetAddress.getByName(string);
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    RealmsConnect.this.connection = Connection.connectToServer(inetAddress2, integer, Minecraft.getInstance().options.useNativeTransport());
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    RealmsConnect.this.connection.setListener(new ClientHandshakePacketListenerImpl(RealmsConnect.this.connection, Minecraft.getInstance(), RealmsConnect.this.onlineScreen.getProxy(), (Consumer<Component>)(jo -> {})));
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    RealmsConnect.this.connection.send(new ClientIntentionPacket(string, integer, ConnectionProtocol.LOGIN));
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    RealmsConnect.this.connection.send(new ServerboundHelloPacket(Minecraft.getInstance().getUser().getGameProfile()));
                }
                catch (UnknownHostException unknownHostException3) {
                    Realms.clearResourcePack();
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    RealmsConnect.LOGGER.error("Couldn't connect to world", (Throwable)unknownHostException3);
                    Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.this.onlineScreen, "connect.failed", (Component)new TranslatableComponent("disconnect.genericReason", new Object[] { "Unknown host '" + string + "'" })));
                }
                catch (Exception exception3) {
                    Realms.clearResourcePack();
                    if (RealmsConnect.this.aborted) {
                        return;
                    }
                    RealmsConnect.LOGGER.error("Couldn't connect to world", (Throwable)exception3);
                    String string4 = exception3.toString();
                    if (inetAddress2 != null) {
                        final String string5 = new StringBuilder().append(inetAddress2).append(":").append(integer).toString();
                        string4 = string4.replaceAll(string5, "");
                    }
                    Realms.setScreen(new DisconnectedRealmsScreen(RealmsConnect.this.onlineScreen, "connect.failed", (Component)new TranslatableComponent("disconnect.genericReason", new Object[] { string4 })));
                }
            }
        }.start();
    }
    
    public void abort() {
        this.aborted = true;
        if (this.connection != null && this.connection.isConnected()) {
            this.connection.disconnect(new TranslatableComponent("disconnect.genericReason", new Object[0]));
            this.connection.handleDisconnection();
        }
    }
    
    public void tick() {
        if (this.connection != null) {
            if (this.connection.isConnected()) {
                this.connection.tick();
            }
            else {
                this.connection.handleDisconnection();
            }
        }
    }
    
    static {
        LOGGER = LogManager.getLogger();
    }
}

package me.goodmanson.socket;

import javax.websocket.Session;
import java.io.IOException;

/**
 * Created by u6062536 on 2/6/2018.
 */
public class SocketUtil {

    public static synchronized void sendMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

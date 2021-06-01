package bd;

import bd.net.Connection;
import bd.vier_gewinnt.*;

/**
 * Write a description of class Main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Main
{
    public final static int port = 8080;
    
    public static void connectTo(String ip, int port)
    {
        new Thread(() -> {
                try {
                    Connection con = new Connection();
                    con.connect(ip, port);
                    new VierGewinntFeld(new VierGewinnt(con, true));
                } catch(Exception e) {
                    System.out.println(e);
                }
            }).start();
    }

    public static void acceptConnection(int port)
    {
        new Thread(() -> {
                try {
                    Connection con = new Connection();
                    con.waitForConnection(port);
                    new VierGewinntFeld(new VierGewinnt(con, false));
                } catch(Exception e) {
                    System.out.println(e);
                }
            }).start();
    }
    
    public static void acceptConnection()
    {
        acceptConnection(port);
    }
    public static void connectTo()
    {
        connectTo("127.0.0.1", port);
    }
    
    public static void main(String[] args){
        GUI gui = new GUI();
    }
}


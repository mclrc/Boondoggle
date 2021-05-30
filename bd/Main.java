package bd;

import bd.net.Connection;
import bd.vier_gewinnt.VierGewinnt;

/**
 * Write a description of class Main here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Main
{
    public static void connectTo(String ip, int port)
    {
        try {
            Connection con = new Connection();
            con.connect(ip, port);
            new VierGewinnt(con);
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void acceptConnection(int port)
    {
        try {
            Connection con = new Connection();
            con.waitForConnection(port);
            new VierGewinnt(con);
        } catch(Exception e) {
            System.out.println(e);
        }
    }
}


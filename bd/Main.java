package bd;


import bd.net.Connection;
import bd.vier_gewinnt.*;

/**
 * 
 *
 * @author (Moritz, Henry)
 * @version (01.06.2021)
 */
public class Main
{
    public final static int port = 8080;
    
    public static void connectTo(String ip, int port, Connection con)
    {
        new Thread(() -> {
                try
                {
                    con.connect(ip, port);
                    new VierGewinntFeld(new VierGewinnt(con, false));
                }
                catch(Exception e)
                {
                    System.out.println(e);
                    new Startfenster(new Connection());
                }
            }).start();
    }

    public static void acceptConnection(int port)
    {
        new Thread(() -> {
                try
                {
                    Connection con = new Connection();
                    con.waitForConnection(port);
                    new VierGewinntFeld(new VierGewinnt(con, false));
                }
                catch(Exception e)
                {
                    System.out.println(e);
                }
            }).start();
    }
    
    public static void acceptConnection()
    {
        acceptConnection(port);
    }
    public static void connectTo(String ip)
    {
        Connection con = new Connection();
        connectTo(ip, port, con);
    }
    
    public static void main(String[] args)
    {
        Connection con = new Connection();
        Startfenster gui = new Startfenster(con);
    }
    
    public static void testA()
    {
        new Startfenster(new Connection());
        connectTo(Connection.getLocalIpv4());
    }
}

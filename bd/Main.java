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
    
    public static void connectTo(String ip, int port,Connection con)
    {
        new Thread(() -> {
                try {
                    
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
        Connection con = new Connection();
        connectTo("127.0.0.1", port, con);
    }
    
    public static void main(String[] args){
        Connection con = new Connection();
        GUI gui = new GUI(con);
    }
}


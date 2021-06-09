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
        try
        {
            // Versuche, Verbindung zu etablieren
            con.connect(ip, port);
            // Neues Spiel ueber etablierte Verbindung starten
            new VierGewinntFeld(new VierGewinnt(con, false));
        }
        catch(Exception e)
        {
            System.out.println(e);
            // Verbindung ist fehlgeschlagen. Zurueck zum Startfenster
            new Startfenster();
        }
    }

    public static void connectTo(String ip)
    {
        Connection con = new Connection();
        connectTo(ip, port, con);
    }

    public static void main(String[] args)
    {
        new Startfenster();
    }

}

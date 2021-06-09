package bd.net;

import java.io.*;
import java.net.*;
import java.util.function.Consumer;
import java.util.Enumeration;
/**
 * @author (Moritz, Henry)
 * @version (01.06.2021)
 */
public class Connection {
    // Socket fuer die eigentliche Verbindung
    Socket socket = null;

    // IO-Streams fuer den socket
    private DataOutputStream out = null;
    private DataInputStream in = null;

    // ServerSocket zum annehmen von Verbindungen
    ServerSocket serverSocket = null;

    // Lambda - Wird bei Empfang einer Nachricht aufgerufen
    private Consumer<String> msgCallback = null;

    // Schleifenvariable fuer Listener-Thread
    private boolean isListening = false;

    /**
     * Setze den bei Empfang einer Nachricht aufzurufenden Callback
     * @param msgCallback
     *  Der neue Callback
     */
    public void setMsgCallback(Consumer<String> callback)
    {
        this.msgCallback = callback;
    }

    /**
     * Versuche, eine Verbindung zu initiieren
     * @param ip
     *  IP des anderen Rechners
     * @param port
     *  Port, auf dem der andere Rechner verbindungen Akzeptiert
     * @throws SocketException
     *  Tritt auf, wenn die Verbindung schon verwendet wird oder gerade auf Verbindungsanfragen hoert
     * Driver: Moritz, Reader: Henry
     */
    public void connect(String ip, int port) throws SocketException
    {
        // Wenn schon eine Verbindung besteht, Exception 
        if (socket != null && !socket.isClosed()) 
            throw new SocketException("Connection already in use");

        // Wenn auf Anfragen gehoert wird, Exception
        else if (serverSocket != null && !serverSocket.isClosed()) 
            throw new SocketException("Currently accepting other connections");

        try
        {
            // Initialisiere Socket
            socket = new Socket(ip, port);
            // Extrahiere Streams
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            // Anfangen, auf Nachrichten zu hoeren
            listen();
        }
        catch(Exception e) { System.out.println(e); }
    }

    /**
     * Gebe die eigene (lokale) IPv4-Adresse zurück
     * Driver: Henry & Moritz, Reader: Moritz & Henry
     */
    public static String getLocalIpv4()
    {   
        try {
            // Ueber Netzwerkkarten iterieren
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                // Ueber Addressen der aktuellen Netzwerkkarte iterieren
                NetworkInterface i = interfaces.nextElement();
                for (Enumeration<InetAddress> addresses = i.getInetAddresses(); addresses.hasMoreElements();) {
                    InetAddress addr = addresses.nextElement();
                    // Aktuelle Addresse zurueckgeben, falls sie nicht loopback oder IPv6 ist
                    if (!(addr.isLoopbackAddress() || addr instanceof Inet6Address))
                        return addr.getHostAddress();
                }
            }
        } 
        catch (SocketException e)
        {
            System.out.println(e);
        }
        return null;
    }

    /**
     * Akzeptiere die naechste Verbindungsanfrage asynchron
     * @param port
     *  Port, auf dem die Anfrage akzeptiert werden soll
     * @param callback
     *  Callback, der nach dem Etablieren einer Verbindung mit der IP des Remotes aufgerufen wird.
     * @throws SocketException
     *  Tritt auf, wenn bereits eine Verbindung besteht
     *  Driver: Moritz, Reader: Henry
     */
    public void acceptConnection(int port, Consumer<String> callback) throws SocketException
    {
        // Wenn schon eine Verbindung besteht, Exception
        if (socket != null && !socket.isClosed())
            throw new SocketException("Connection already in use");

        try
        {
            // // Initialisiere ServerSocket
            // serverSocket = new ServerSocket(port);

            // // Seperater Thread, denn waitForConnection blockiert
            new Thread(() -> {
                    try
                    {
                        waitForConnection(port);
                        // Remote-IP herausfinden
                        String ip = ((Inet4Address)((InetSocketAddress)socket.getRemoteSocketAddress()).getAddress()).toString();
                        // Callback-aufruf
                        callback.accept(ip);
                    }
                    catch (Exception e) { System.out.println(e); }
                }).start();

        }
        catch(Exception e) { System.out.println(e); }
    }

    /**
     * Akzeptiere die naechste Verbindungsanfrage (blockierend)
     * @param port
     *  Port, auf dem die Anfrage akzeptiert werden soll
     * @throws SocketException
     *  Tritt auf, wenn bereits eine Verbindung besteht
     *  Driver: Moritz, Reader: Henry
     */
    public void waitForConnection(int port) throws SocketException
    {
        // Wenn schon eine Verbindung besteht, Exception
        if (socket != null && !socket.isClosed())
            throw new SocketException("Connection already in use");

        try
        {
            // Initialisiere ServerSocket
            serverSocket = new ServerSocket(port);

            // Akzeptiere Verbindung und speichere Socket
            socket = serverSocket.accept();
            // Extrahiere Streams
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());

            // Schliesse ServerSocket und gebe Listening-Port frei 
            serverSocket.close();

            // Anfangen, auf Nachrichten zu hoeren
            listen();
        }
        catch(Exception e) { System.out.println(e); }
    }

    /**
     * Hoere auf, Verbindungen zu Akzeptieren
     * Driver: Moritz, Reader: Henry
     */
    public void stopAcceptingConnection()
    {
        // Wenn ServerSocket nicht besteht oder geschlossen ist, ist nichts zu tun
        if (serverSocket == null || serverSocket.isClosed()) return;

        // ServerSocket schliessen
        try{ serverSocket.close(); }
        catch(IOException e) { System.out.println(e); }
    }

    /**
     * Starte, auf Nachrichten zu hoeren
     * Driver: Moritz, Reader: Henry
     */
    private void listen() throws SocketException
    {
        // Wenn keine Verbindung besteht, Exception
        if (socket == null || socket.isClosed())
            throw new SocketException("Not connected");

        // Schleifenvariable zuruecksetzen
        isListening = true;

        System.out.println("Listening");

        // Listener-Thread - Denn in.readUTF blockiert
        new Thread(() -> {
                while (!socket.isClosed() && isListening)
                {
                    // Nachrichten aus Stream einlesen und Callback aufrufen
                    try { if (msgCallback != null) msgCallback.accept(in.readUTF()); }
                    catch(EOFException e) // End-of-file - Verbindung unterbrochen
                    {
                        System.out.println("Connection closed");
                        isListening = false;
                    }
                    catch(IOException e) // Anderer Fehler. Verbindung schliessen
                    {
                        System.out.println(e);
                        close();
                    }
                }
            }).start();
    }

    /**
     * Sende eine Nachricht
     * @param data
     *  Zu sendende Nachricht
     * @throws SocketException
     *  Tritt auf, wenn keine Verbindung besteht
     *  Driver: Henry, Reader: Moritz
     */
    public void send(String data) throws SocketException
    {
        // Wenn keien Verbindung besteht, Exception
        if (socket == null || socket.isClosed())
            throw new SocketException("Not connected");

        System.out.println("Sending " + data);

        // Daten in Stream schreiben
        try { out.writeUTF(data); }
        catch(IOException e) // Fehler beim Schreiben. Verbindung schliessen
        {
            System.out.println(e);
            close();
        }
    }

    /**
     * Beende die Verbindung. Hoere auf, Anfragen zu akzeptieren
     * Driver: Henry, Reader: Moritz
     */
    public void close()
    {
        // Ggf. ServerSocket schliessen
        if (serverSocket != null && !serverSocket.isClosed())
            try { serverSocket.close(); }
            catch (Exception e) { System.out.println(e); }
        // Wenn keine Verbindung besteht, ist nichts zu tun
        if (socket == null || socket.isClosed()) return;

        // Schleifenvariable aktualisieren
        isListening = false;

        try
        {
            // Verbindung schliessen
            socket.close();
            in.close();
            out.close();

            System.out.println("Connection closed");            
        }
        catch(IOException e) { System.out.println(e); }
    }

    public static void test()
    {
        Connection a = new Connection();
        a.setMsgCallback((String msg) -> System.out.println("a received: " + msg));

        Connection b = new Connection();
        b.setMsgCallback((String msg) -> System.out.println("b received: " + msg));

        try {
            a.acceptConnection(4000, (String ip) -> System.out.println(ip));
            b.connect("127.0.0.1", 4000);

            a.send("hey from a");
            b.send("hey from b");

            Thread.sleep(100);
            a.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
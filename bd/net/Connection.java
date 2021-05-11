package bd.net;

import java.io.*;
import java.net.*;
import java.util.function.Consumer;

class Connection {
    Socket socket = null;
    ServerSocket serverSocket = null;
    private DataOutputStream out = null;
    private DataInputStream in = null;

    private Consumer<String> msgCallback;

    private boolean isListening = false;

    public void setMsgCallback(Consumer<String> msgCallback)
    {
        this.msgCallback = msgCallback;
    }

    public void connect(String ip, int port) throws SocketException
    {
        if (socket != null && !socket.isClosed())
            throw new SocketException("Connection already in use");
        else if (serverSocket != null && !serverSocket.isClosed())
            throw new SocketException("Currently accepting other connections");

        try
        {
            socket = new Socket(ip, port);
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
            listen();
        }
        catch(Exception e) { System.out.println(e); }
    }

    public void acceptConnection(int port) throws SocketException
    {
        if (socket != null && !socket.isClosed())
            throw new SocketException("Connection already in use");

        try
        {
            serverSocket = new ServerSocket(port);

            new Thread(() -> {
                    try
                    {
                        socket = serverSocket.accept();
                        out = new DataOutputStream(socket.getOutputStream());
                        in = new DataInputStream(socket.getInputStream());

                        serverSocket.close();

                        listen();
                    }
                    catch(Exception e) { System.out.println(e); }
                }).start();
        }
        catch(Exception e) { System.out.println(e); }
    }

    public void stopAcceptingConnection()
    {
        if (serverSocket == null || serverSocket.isClosed()) return;
        try{ serverSocket.close(); }
        catch(IOException e) { System.out.println(e); }
    }

    public void listen() throws SocketException
    {
        if (socket == null || socket.isClosed())
            throw new SocketException("Not connected");

        isListening = true;

        System.out.println("Listening");

        new Thread(() -> {
                while (!socket.isClosed() && isListening)
                {
                    try { msgCallback.accept(in.readUTF()); }
                    catch(EOFException e) 
                    {
                        System.out.println("Connection closed");
                        isListening = false;
                    }
                    catch(IOException e) { close(); }
                }
            }).start();
    }

    public void send(String data) throws SocketException
    {
        if (socket == null || socket.isClosed())
            throw new SocketException("Not connected");

        System.out.println("sending " + data);

        try { out.writeUTF(data); }
        catch(IOException e)
        {
            System.out.println(e);
            close();
        }
    }

    public void close()
    {
        if (serverSocket != null && !serverSocket.isClosed())
            try { serverSocket.close(); }
            catch (Exception e) { System.out.println(e); }
        if (socket == null || socket.isClosed()) return;

        isListening = false;

        try
        {
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
            a.acceptConnection(4000);
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
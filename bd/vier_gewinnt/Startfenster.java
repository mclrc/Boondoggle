package bd.vier_gewinnt;

import bd.net.Connection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import bd.Main;


/**
 * Beschreiben Sie hier die Klasse GUI.
 * 
 * @author (Moritz & andere) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Startfenster extends JFrame
{
    private int width = 800;
    private int height = 455;
    
    /**
     * Konstruktor für Objekte der Klasse GUI
     */
    public Startfenster()
    {
        super();
        setTitle("Spielerauswahl");
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // Hintergrundbild
        JLabel background = new JLabel(new ImageIcon("img/Background03.png"));
        add(background);
        GridLayout grid = new GridLayout(0,1);
        background.setLayout(grid);
        
        JLabel lbldeineIP = new JLabel();
        lbldeineIP.setHorizontalAlignment(SwingConstants.CENTER);
        lbldeineIP.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lbldeineIP.setText("Deine IP:");
        //lbldeineIP.setBounds(300, 20, 200, 50); //braucht man nicht wenn man ein GridLayout hat
        lbldeineIP.setForeground(Color.WHITE);
        
        JLabel lblIP = new JLabel();
        lblIP.setHorizontalAlignment(SwingConstants.CENTER);
        lblIP.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblIP.setText(Connection.getLocalIpv4());
        //lblIP.setBounds(300, 80, 200, 50);
        lblIP.setForeground(Color.WHITE);
        
        JLabel lblMitspielerIP = new JLabel();
        lblMitspielerIP.setHorizontalAlignment(SwingConstants.CENTER);
        lblMitspielerIP.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblMitspielerIP.setText("Mitspieler IP:");
        //lblMitspielerIP.setBounds(300, 160, 200, 50);
        lblMitspielerIP.setForeground(Color.WHITE);
        
        JTextField tfMitspielerIP = new JTextField();
        tfMitspielerIP.setHorizontalAlignment(SwingConstants.CENTER);
        tfMitspielerIP.setFont(new Font("Segoe UI", Font.BOLD, 26));
        //tfMitspielerIP.setBounds(275, 220, 250, 50);
        tfMitspielerIP.setForeground(Color.WHITE);
        tfMitspielerIP.setOpaque(false);
        tfMitspielerIP.setBorder(null);
        tfMitspielerIP.setText("127.0.0.1");
        
        JButton bAnfrage = new JButton();
        bAnfrage.setOpaque(false);
        bAnfrage.setFocusPainted(false);
        bAnfrage.setBorderPainted(false);
        bAnfrage.setContentAreaFilled(true);
        bAnfrage.setBackground(Color.BLUE);
        bAnfrage.setHorizontalAlignment(SwingConstants.CENTER);
        bAnfrage.setFont(new Font("Segoe UI", Font.BOLD, 26));
        bAnfrage.setText("Herausfordern");
        //bAnfrage.setBounds(275, 280, 250, 50);
        bAnfrage.setForeground(Color.WHITE);

        background.add(lbldeineIP);
        background.add(lblIP);
        background.add(lblMitspielerIP);
        background.add(tfMitspielerIP);
        background.add(bAnfrage);
        
        setVisible(true);
        //Action Listener zum Schließen des Spielerauswahlsfenster wenn Herausforderung angenommen fehlt
        
        bAnfrage.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Main.connectTo(tfMitspielerIP.getText());//Versucht sich mit eingegebener IP-Adresse zu verbinden
                dispose();//Fenster verschwindet
            }
        });
        
        Connection con = new Connection();
        
        try 
        {
            // Die naechste Verbindungsanfrage akzeptieren
            con.acceptConnection(Main.port, (String ip) -> {
                System.out.println("Accepted connection from " + ip);
                // Neues Spiel mit etablierter Verbindung starten
                new VierGewinntFeld(new VierGewinnt(con, true));
                // Startfenster schliessen
                dispose();
            });
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

        repaint();
    }
}

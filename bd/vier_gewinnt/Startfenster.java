package bd.vier_gewinnt;

import bd.net.Connection;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.imageio.ImageIO;
import java.io.File;
import bd.Main;
import java.net.*;


/**
 * Beschreiben Sie hier die Klasse GUI.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Startfenster extends JFrame
{
    private int width = 800;
    private int height = 455;
    
    /**
     * Konstruktor für Objekte der Klasse GUI
     */
    public Startfenster(Connection con)
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
        lblIP.setText(con.getLocalIpv4());
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
                Main.connectTo(tfMitspielerIP.getText());
                dispose();
            }
        });
        
        Connection standbyCon = new Connection();
        
        try 
        {
            standbyCon.acceptConnection(Main.port, (String ip) -> {
                System.out.println("Accepted connection from " + ip);
                new VierGewinntFeld(new VierGewinnt(standbyCon, true));
                dispose();
            });
        }
        catch (Exception e) {
            System.out.println(e);
        }

        repaint();
    }
    /**
     * Wird nicht benutzt
     * Ein Popup-Fenster um eine Herausforderung anzunehmen
     */
    public void popup(String ip, Connection con)
    {
        JLabel plbl = new JLabel();
        plbl.setText("Herausforderung von:");
        plbl.setHorizontalAlignment(SwingConstants.CENTER);
        plbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        
        JLabel plbl2 = new JLabel();
        plbl2.setText(ip); 
        plbl2.setHorizontalAlignment(SwingConstants.CENTER);
        plbl2.setFont(new Font("Segoe UI", Font.BOLD, 15));
        
        JButton pbAnnehmen = new JButton();
        pbAnnehmen.setFocusPainted(false);
        pbAnnehmen.setBorderPainted(false);
        pbAnnehmen.setText("Annehmen");
        pbAnnehmen.setFont(new Font("Segoe UI", Font.BOLD, 15));
        pbAnnehmen.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Main.connectTo(plbl2.getText(), Main.port, con);
                dispose();
            }
        });
        
        JButton pbAblehnen = new JButton();
        pbAblehnen.setFocusPainted(false);
        pbAblehnen.setBorderPainted(false);
        pbAblehnen.setText("Ablehnen");
        pbAblehnen.setFont(new Font("Segoe UI", Font.BOLD, 15));
        
        
        JPanel popupPanel = new JPanel(new GridLayout(3,1));
        JPanel popupPanel2 = new JPanel(new GridLayout(1,2));
        
        popupPanel2.add(pbAnnehmen);
        popupPanel2.add(pbAblehnen);
        
        popupPanel.add(plbl);
        popupPanel.add(plbl2);
        popupPanel.add(popupPanel2);
        
        JDialog dlgFenster = new JDialog();
        dlgFenster.setTitle("Herausforderung");
        dlgFenster.setSize(250,150);
        dlgFenster.add(popupPanel);
        dlgFenster.setVisible(true);
        //dlgFenster.setModal(true);
        //dlgFenster.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        //dlgFenster.setAlwaysOnTop(true);
        dlgFenster.setLocationRelativeTo(null);
        
        pbAblehnen.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dlgFenster.dispose();
            }
        });
        
        pbAnnehmen.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                dlgFenster.dispose();
            }
        });
    }
    
    
}

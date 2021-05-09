import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.imageio.ImageIO;
import java.io.File;

/**
 * Beschreiben Sie hier die Klasse VierGewinntFeld.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */


public class VierGewinntFeld extends JFrame // implements ActionListener 
{
    private int width = 800;
    private int height = 455;
    private VierGewinnt spiel;

    /**
     * Konstruktor für Objekte der Klasse VierGewinntFeld
     */
    public VierGewinntFeld(VierGewinnt spiel)
    {
        this.spiel = spiel;

        setTitle("4 wins p2p");
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        
        // Hintergrundbild
        setLayout(new BorderLayout());
        JLabel background=new JLabel(new ImageIcon("Background01.png"));
        add(background);
        background.setLayout(null);

        // ab hier Start-GUI-Elemente
        JLabel lblSpielname = new JLabel();
        lblSpielname.setHorizontalAlignment(SwingConstants.CENTER);
        lblSpielname.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblSpielname.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblSpielname.setBounds(300, 20, 200, 50);
        lblSpielname.setText("Boondoggle");
        lblSpielname.setForeground(Color.WHITE);
        
        JLabel lblAktiverSpieler = new JLabel();
        lblAktiverSpieler.setHorizontalAlignment(SwingConstants.CENTER);
        lblAktiverSpieler.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblAktiverSpieler.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        lblAktiverSpieler.setBounds(200, 60, 400, 50);
        lblAktiverSpieler.setText("\"name\" ist am Zug");
        lblAktiverSpieler.setForeground(Color.BLACK);
        
        // Spierlfeld als Grafik 600x334px
        // JLabel lblFeld = new JLabel(new ImageIcon("4gewinnt.png"));
        // lblFeld.setBounds(100, 93, 600, 334);
        JPanel panBrett = new VierGewinntPanel();
        panBrett.setBounds(261, 123, 288, 304); // Bild 288x304px
       
        JButton btnZurueck = new JButton("Zurück");
        btnZurueck.setBounds(10, 380, 80, 25);
        btnZurueck.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        /* 
        btnSpielen.setBounds(220, 200, 200, 35);
        btnSpielen.setAlignmentX(Component.CENTER_ALIGNMENT);

        
        JButton btnEinstellungen = new JButton("Einstellungen");
        btnEinstellungen.setBounds(220, 275, 200, 35);
        btnEinstellungen.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton btnAnleitung = new JButton("Anleitung");
        btnAnleitung.setBounds(220, 350, 200, 35);  
        btnAnleitung.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        background.add(Box.createVerticalStrut(20));
        background.add(lblSpielname);
        background.add(Box.createVerticalStrut(200));
        background.add(btnSpielen);
        background.add(Box.createVerticalStrut(20));
        background.add(btnEinstellungen);
        background.add(Box.createVerticalStrut(20));
        background.add(btnAnleitung);
        */


       
        background.add(lblSpielname);
        background.add(lblAktiverSpieler);
        // background.add(lblFeld);
        background.add(panBrett);
        background.add(btnZurueck);


        

        // Just for refresh :) Not optional!
        setSize(width-1, height-1);
        setSize(width, height);
        
       
    }

    
    public static void main(String args[]) {
        VierGewinnt spiel = new VierGewinnt();
        new VierGewinntFeld(spiel);
    }
    
    
    
    
    public class VierGewinntPanel extends JPanel implements MouseListener
{
        private int width = 800;
        private int height = 455;
        // private int [][] field;
        private int rows = 6; 
        private int columns = 7;
        private BufferedImage imageBackground;
          private int xStart = 19;
          private int yStart = 13;
          private int xDelta = 5;
          private int yDelta = 20;
          private int size = 31;
    
    /**
     * Konstruktor für Objekte der Klasse VierGewinntFeld
     */
    public VierGewinntPanel()
    {
        // imageBackground = ImageIO.read(getClass().getResource("4gewinnt.png"));
        try {
            imageBackground = ImageIO.read(new File("4gewinnt.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        spiel.feldAusgeben();
        addMouseListener(this);
    }
    
     @Override
     public void paintComponent(Graphics g) {
      super.paintComponent(g);
      int xPos, yPos;
      
      // Spielbrett als Hintergrund rendern
      g.drawImage(imageBackground, 0, 0, null);
      
      xPos = xStart;
      yPos = yStart;
      for(int i = 0; i < rows; i++) {
         for(int j = 0; j < columns; j++) {

               if(spiel.feld[i][j] == 0) {
                   // nichts darstellen
               } else {
                    if(spiel.feld[i][j] == 1) {
                        g.setColor(Color.red);
                    } else {
                        g.setColor(Color.yellow);
                    }
                   g.fillOval(xPos, yPos, size, size);
                }
            xPos += size + xDelta; //Position für Grafik Spalte rechts berechnen
         }
         xPos = xStart; //neue Zeile, zeichnen wieder links beginnen
         yPos += size  + yDelta; //Position für Grafik Zeile tiefer berechnen
      }
    }
    
    public void mouseClicked(MouseEvent e) {
      int xPos = e.getX();
      int spalte = Math.min(columns - 1, (xPos - xStart) / (size + xDelta));

      System.out.println("mouseClicked: " + xPos + ", " + spalte);
      spiel.chipPlatzieren(spalte);
      spiel.feldAusgeben();
     
      if (spiel.gewonnen() > 0) {
          System.out.println("Spieler " + spiel.gewonnen() + " hat gewonnen!");
      }
        
      repaint();
    }
    
    public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
      setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
   }

   public void mouseExited(MouseEvent e) {
      setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
   }
    
}
    
}


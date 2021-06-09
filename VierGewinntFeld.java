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
    private int height = 464;
    private VierGewinnt spiel;

    /**
     * Konstruktor für Objekte der Klasse VierGewinntFeld
     */
    public VierGewinntFeld(VierGewinnt spiel)
    {
        this.spiel = spiel;
        spiel.setFeld(this);
        
        setTitle("4 wins p2p");
        setSize(width, height);
        setLocationRelativeTo(null);
        setVisible(true);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Hintergrundbild
        JLabel background=new JLabel(new ImageIcon("img/Background01.png"));
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
        lblAktiverSpieler.setText(spiel.istAmZug());
        if(spiel.gegnerIstDran) {
             setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
        else {
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));   
        }
        lblAktiverSpieler.setForeground(Color.BLACK);
        
        
        // Spierlfeld als Grafik 600x334px
        JPanel panBrett = new VierGewinntPanel(lblAktiverSpieler);
        panBrett.setBounds(261, 123, 288, 304); // Bild 288x304px

        JButton btnZurueck = new JButton("Zurück");
        btnZurueck.setBounds(10, 380, 80, 25);
        btnZurueck.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnZurueck.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Main.main(new String[0]);
                dispose();
            }
        });
        
        background.add(lblSpielname);
        background.add(lblAktiverSpieler);

        background.add(panBrett);
        background.add(btnZurueck);

        
        // Just for refresh :) Not optional!
        setSize(width-1, height-1);
        setSize(width, height);

    }
    
    public class VierGewinntPanel extends JPanel implements MouseListener
    {
        private int rows = 6; 
        private int columns = 7;
        private BufferedImage imageBackground;
        private int xStart = 19;
        private int yStart = 13;
        private int xDelta = 5;
        private int yDelta = 20;
        private int size = 31;
        private JLabel lblAktiverSpieler;
        private boolean spielAktiv = true;
        
        /**
         * Konstruktor für Objekte der Klasse VierGewinntFeld
         */
        public VierGewinntPanel(JLabel lblAktiverSpieler)
        {
            this.lblAktiverSpieler = lblAktiverSpieler;
            // imageBackground = ImageIO.read(getClass().getResource("4gewinnt.png"));
            try {
                imageBackground = ImageIO.read(new File("img/4gewinnt.png"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // spiel.feldAusgeben();
            addMouseListener(this);
        }

        // @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int xPos, yPos;

            // Spielbrett als Hintergrund rendern
            g.drawImage(imageBackground, 0, 0, null);

            xPos = xStart;
            yPos = yStart;
            
            // im Spielfeld die Chips malen
            for(int i = 0; i < rows; i++) { // solange die Variable i kleiner ist als "rows":
                for(int j = 0; j < columns; j++) { // solange die Variable j kleiner ist als "columns":
                    if(spiel.spielfeld[i][j] == null) {
                        // nichts darstellen
                    } else {
                        if(spiel.spielfeld[i][j].getFarbe() == 1) { // wenn an dem Feld der Position [i][j] der Wert 1 beträgt
                            g.setColor(Color.red); // das Feld wird rot dargestellt
                        } else { // wenn der Wert > 1 ist
                            g.setColor(Color.yellow); // das Feld wird gelb dargestellt
                        }
                        g.fillOval(xPos, yPos, size, size); // das Oval wird an der bestimmten x- und y-Position an der untersten Stelle gezeichnet
                    }
                    xPos += size + xDelta; //Position für Grafik Spalte rechts berechnen
                }
                xPos = xStart; //neue Zeile, zeichnen wieder links beginnen
                yPos += size  + yDelta; //Position für Grafik Zeile tiefer berechnen
            }
            updateSpielStatus();
        }

        // Spielstatus ausgeben (nächster Spieler, unentschieden)
        public void updateSpielStatus() {
            if(!spielAktiv || spiel.gegnerIstDran) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            
            if(!spiel.istSpielfeldVoll()) {
                if (spiel.spielEnde) { // wenn ein/e Spieler*in gewonnen hat, wird dies ausgegeben
                    spielAktiv = false;
                    lblAktiverSpieler.setForeground(Color.GREEN);
                    lblAktiverSpieler.setText(spiel.hatGewonnen());
                }
                else {
                    lblAktiverSpieler.setText(spiel.istAmZug());
                    
                    if(!spielAktiv || spiel.gegnerIstDran) {
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                    else {
                        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));    
                    }
                }    
            }
            else {
                spielAktiv = false;
                lblAktiverSpieler.setForeground(Color.GREEN);
                lblAktiverSpieler.setText("Das Spielfeld ist voll: Unentschieden!");
            }
        }
        
        
        // Wird aufgerufen bei Mausklick
        public void mouseClicked(MouseEvent e) {
            if(!spielAktiv || spiel.gegnerIstDran) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                return;
            }
            setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            
            int xPos = e.getX(); // bei Benutzung der linken Maustaste wird die x-Position gespeichert
            int spalte = Math.min(columns - 1, (xPos - xStart) / (size + xDelta)); //  Spalte wird berechnet // Sonderfall bei Spalte rechts neben dem Spielfeld: wird durch Math.min abgefangen

            spiel.makeMove(spalte);
            
            if(!spiel.istSpielfeldVoll()) {
                if (spiel.zugGewonnen(spalte)) { // wenn ein/e Spieler*in gewonnen hat, wird dies ausgegeben
                    spielAktiv = false;
                    lblAktiverSpieler.setForeground(Color.GREEN);
                    lblAktiverSpieler.setText(spiel.hatGewonnen());
                }
                else {
                    lblAktiverSpieler.setText(spiel.istAmZug());
                    
                    if(!spielAktiv || spiel.gegnerIstDran) {
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                    else {
                        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }
                }    
            }
            else {
                spielAktiv = false;
                lblAktiverSpieler.setForeground(Color.GREEN);
                lblAktiverSpieler.setText("Das Spielfeld ist voll: Unentschieden!");
            }
            
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
            if(spielAktiv) {
                setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
            }
        }

        public void mouseExited(MouseEvent e) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }

    }
}


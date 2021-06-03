package bd.vier_gewinnt;

import bd.net.Connection;
/**
 * Beschreiben Sie hier die Klasse vierGewinnt.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class VierGewinnt
{
    public Chip[][] spielfeld; 
    int feldgroesse=6*7;
    int rows = 6;
    int columns = 7;
    boolean gegnerIstDran;
    boolean initialGegnerIstDran;
    boolean spielEnde;
    int anzahlChips = 0;

    VierGewinntFeld feld = null;
    Connection con;
    

    /**
     * Konstruktor für Objekte der Klasse vierGewinnt
     */
    public VierGewinnt(Connection con, boolean gegnerIstDran)
    {
        spielfeld = new Chip[6][7]; 
        this.gegnerIstDran = gegnerIstDran;
        this.initialGegnerIstDran = gegnerIstDran == true;
        this.con = con;
        this.spielEnde = false;

        con.setMsgCallback((String msg) -> this.onOpponentMessage(msg));
    }
    
    public void setFeld(VierGewinntFeld feld)
    {
        this.feld = feld;
    }

    private void onOpponentMessage(String msg)
    {
        System.out.println("Received: " + msg);
        if (!gegnerIstDran) return;

        if (msg.startsWith("zug"))
        {
            int spalte = Integer.parseInt(msg.split(" ")[1]);
            chipPlatzieren(spalte);
        }
    }
    
    public void makeMove(int spalte)
    {
        try {
            chipPlatzieren(spalte);
            con.send("zug " + spalte);
        } catch (Exception e) { System.out.println(e); }
    }

    /**
     * Fügt eine Zahl (für eine Farbe) in der vorgegebenen
     * Spalte auf der richtigen Höhe ein
     * Spalte 0 ist ganz links, Spalte 6 ist ganz rechts
     * Autor(Tobias, Emre)
     */
    public void chipPlatzieren(int spalte)
    {
        if(spalte > 6||chipsInSpalte(spalte) == 6)//checkt ob der chip in einer existierenden Spalte platziert werden soll oder ob die Spalte schon voll ist
        {
            return;
        }

        if(gegnerIstDran)//wenn Rot dran ist wird ein roter Chip an dem Platz eingefuegt
        {
            spielfeld[5-chipsInSpalte(spalte)][spalte] = new Chip(1); 

            if(!zugGewonnen(spalte)) {
                gegnerIstDran = false;
            }
        }
        else//wenn nicht dann ein gelber Chip
        {
            spielfeld[5-chipsInSpalte(spalte)][spalte] = new Chip(2); 

            if(!zugGewonnen(spalte)) {
                gegnerIstDran = true;
            }
        }
        feld.repaint(); // ruft VierGewinntFeld.paintComponent(); auf
        anzahlChips++;
    }

    public boolean istSpielfeldVoll() {
        if(anzahlChips < feldgroesse) {
            return false;
        } 
        else {
            return true;
        }
    }

    /**
     * Gibt die Anzahl der belegten Platze in der Spalte zurueck
     * Wird auch benutzt um die Reihe eines platzierten Chips herauszufinden
     * Autor(Tobias, Emre)
     */
    public int chipsInSpalte(int spalte)
    {
        int anzahl = 0;
        for(int i=0; i<=5; i++)
        {
            if(spielfeld[i][spalte]!= null)
            {
                anzahl++;
            }
        }
        return anzahl;
    }

    public boolean zugGewonnen(int spalte)
    {
        boolean gewonnen = false;
        
        if(vertikalGewonnen(spalte)) gewonnen=true;
        else if(horizontalGewonnen(spalte)) gewonnen=true;
        else if(diagonalLUROGewonnen(spalte)) gewonnen=true;
        else if(diagonalRULOGewonnen(spalte)) gewonnen=true;
        
        if (gewonnen) {
            this.spielEnde = gewonnen;
        }
        
        return gewonnen;
    }
    
    public boolean zugGewonnen()
    {
        for (int i = 0; i < columns; i++) {
            if (zugGewonnen(i)) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * Checkt, ob der platzierte Chip eine vertikale Reihe von 4 bildet
     * Autor (Emre, Tobias)
     */
    public boolean vertikalGewonnen(int spalte)
    {
        if(chipsInSpalte(spalte) >= 4)
        {
            int reihe = 6-chipsInSpalte(spalte);
            int farbe = spielfeld[6-chipsInSpalte(spalte)][spalte].getFarbe();
            if(spielfeld[reihe][spalte].getFarbe() == farbe 
            && spielfeld[reihe+1][spalte].getFarbe() == farbe 
            && spielfeld[reihe+2][spalte].getFarbe() == farbe 
            && spielfeld[reihe+3][spalte].getFarbe() == farbe) return true;
            else return false;
        }
        else return false;
    }

    /**
     * Checkt, ob der platzierte Chip eine horizontale Reihe von
     * 4 bildet
     * Autor (Tobias, Emre)
     */
    public boolean horizontalGewonnen(int spalte)
    {
        int count = 0;
        int x = spalte;
        int y = 6-chipsInSpalte(spalte);
        int farbe = spielfeld[y][x].getFarbe();
        x = 0;
        while(x<=6)
        {
            if(spielfeld[y][x]!= null)
            {
                if(spielfeld[y][x].getFarbe() == farbe) count++;
                else count = 0;
            }

            else count = 0;
            if(count == 4) return true;
            x++;
        }
        return false;
    }

    /**
     * Checkt, ob der platzierte Chip eine diagonale Reihe 
     * von links unten nach rechts oben von 4 bildet
     * Autor (Tobias, Emre)
     */
    public boolean diagonalLUROGewonnen(int spalte)
    {
        int count = 0;
        int y = 6-chipsInSpalte(spalte);
        int x = spalte;
        int farbe = spielfeld[y][x].getFarbe();
        while(y>0 && x<6)//solange im Feld
        {
            y--;
            x++;
        }//x und y nehmen die Koordinaten von ganz oben rechts in der Linie des platzierten Chips an
        while(y<=5 && x>=0)//solange im Feld
        {
            if(spielfeld[y][x]!= null)
            {
                if(spielfeld[y][x].getFarbe() == farbe) count++;
                else count = 0;
            }

            else count = 0;
            if(count == 4) return true;//wenn 4 in einer Reihe true (spiel gewonnen)
            y++;
            x--;
        }
        return false;
    }

    /**
     * Checkt, ob der platzierte Chip eine diagonale Reihe von 
     * rechts unten nach links oben von 4 bildet
     * Autor (Tobias, Emre)
     */
    public boolean diagonalRULOGewonnen(int spalte)
    {
        int count = 0;
        int y = 6-chipsInSpalte(spalte);
        int x = spalte;
        int farbe = spielfeld[y][x].getFarbe();
        while(y>0 && x>0)//solange im Feld
        {
            y--;
            x--;
        }//x und y nehmen die Koordinaten von ganz oben links in der Linie des platzierten Chips an
        while(y<=5 && x<=6)//solange im Feld
        {
            if(spielfeld[y][x]!= null)
            {
                if(spielfeld[y][x].getFarbe() == farbe) count++;
                else count = 0;
            }
            else count = 0;
            if(count == 4) return true;//wenn 4 in einer Reihe true (spiel gewonnen)
            y++;
            x++;
        }
        return false;
    }

    /**
     * Gibt Feld nach System.out aus
     * Wurde benutzt für erste Tests
     * Autor(Malte, Neele)
     */
    public void feldAusgeben() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print("" + spielfeld[i][j]  + " ");
            }
            System.out.println();
        }
    }

    /**
     * Gibt an welcher Spieler gerade am Zug ist
     * Autor(Malte)
     */
    public String istAmZug() {
        if(gegnerIstDran) {
            return "Dein Mitspieler ist am Zug.";
        }
        else {
            return "Du bist am Zug.";
        }
    }
    
    /**
     * Gibt an welcher Spieler gewonnen hat
     * Autor(Malte)
     */
    public String hatGewonnen() {
        if(gegnerIstDran) {
            return "Dein Mitspieler hat gewonnen.";
        }
        else {
            return "Du hast gewonnen.";
        }
    }
}

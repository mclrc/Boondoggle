package bd.vier_gewinnt;
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
    boolean rotIstDran;
    int anzahlChips = 0;

    /**
     * Konstruktor für Objekte der Klasse vierGewinnt
     */
    public VierGewinnt()
    {
        spielfeld = new Chip[6][7]; 
        rotIstDran = true;//Rot soll beginnen
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
        
        if(rotIstDran)//wenn Rot dran ist wird ein roter Chip an dem Platz eingefuegt
        {
            spielfeld[5-chipsInSpalte(spalte)][spalte] = new Chip(1); 
            
            if(!zugGewonnen(spalte)) {
                rotIstDran = false;
            }
        }
        else//wenn nicht dann ein gelber Chip
        {
            spielfeld[5-chipsInSpalte(spalte)][spalte] = new Chip(2); 

            if(!zugGewonnen(spalte)) {
                rotIstDran = true;
            }
        }
        
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
        if(vertikalGewonnen(spalte)) return true;
        else if(horizontalGewonnen(spalte)) return true;
        else if(diagonalLUROGewonnen(spalte)) return true;
        else if(diagonalRULOGewonnen(spalte)) return true;
        else return false;
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
        while(x>0)
        {
            x--;
        }
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
     * Autor(Malte, Neele)
     */
    public String istAmZug() {
        if(rotIstDran) {
            return "1 / rot";
        }
        else {
            return "2 / gelb";
        }
    }
}

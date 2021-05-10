
/**
 * Beschreiben Sie hier die Klasse vierGewinnt.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class VierGewinnt
{
    int[][] feld;
    int rows = 6;
    int columns = 7;
    int turn;//noch unbenutzt
    boolean rotIstDran;
    
    /**
     * Konstruktor für Objekte der Klasse vierGewinnt
     */
    public VierGewinnt()
    {
        feld = new int[6][7];
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
            
        }
        else if(rotIstDran)//wenn Rot dran ist wird 1 (fuer Rot) an den Platz eingefuegt
        {
            feld[5-chipsInSpalte(spalte)][spalte] = 1;
            rotIstDran = false;
        }
        else//wenn nicht dann 2 (fuer Gelb)
        {
            feld[5-chipsInSpalte(spalte)][spalte] = 2;
            rotIstDran = true;
        }
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
            int farbe = feld[6-chipsInSpalte(spalte)][spalte];
            if(feld[reihe][spalte] == farbe 
            && feld[reihe+1][spalte] == farbe 
            && feld[reihe+2][spalte] == farbe 
            && feld[reihe+3][spalte] == farbe) return true;
            else return false;
        }
        else return false;
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
        int farbe = feld[y][x];
        while(y>0 && x<6)//solange im Feld
        {
            y--;
            x++;
        }//x und y nehmen die Koordinaten von ganz oben rechts in der Linie des platzierten Chips an
        while(y<=5 && x>=0)//solange im Feld
        {
            if(feld[y][x] == farbe) count++;
            else count = 0;//wenn ein Chip nicht die richtige Farbe hat wird  count auf 0 gesetzt da somit die Reihe unterbrochen wird
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
        int farbe = feld[y][x];
        while(y>0 && x>0)//solange im Feld
        {
            y--;
            x--;
        }//x und y nehmen die Koordinaten von ganz oben links in der Linie des platzierten Chips an
        while(y<=5 && x<=6)//solange im Feld
        {
            if(feld[y][x] == farbe) count++;
            else count = 0;//wenn ein Chip nicht die richtige Farbe hat wird  count auf 0 gesetzt da somit die Reihe unterbrochen wird
            if(count == 4) return true;//wenn 4 in einer Reihe true (spiel gewonnen)
            y++;
            x++;
        }
        return false;
    }
    
    /**
     * Gibt zurück, ob das Spiel gewonnen wurde
     *   0 = nein
     *   1 = rot
     *   2 = gelb
     * Autor(Malte)
     */    
    public int gewonnen() 
    {
        // eine Reihe mit 4 x hintereinander
        for (int i = 0; i < rows; i++) {
            int rotCounter = 0, gelbCounter = 0;
            for (int j = 0; j < columns; j++) {
                if (feld[i][j]==1) {
                    rotCounter++;
                } else {
                    rotCounter = 0;
                }
                if (rotCounter >= 4) {
                    return 1; // rot
                }

                if (feld[i][j]==2) {
                    gelbCounter++;
                } else {
                    gelbCounter = 0;
                }
                if (gelbCounter >= 4) {
                    return 2; // gelb
                }
            }
        }
        
        // eine Spalte mit 4 x hintereinander
        for (int j = 0; j < columns; j++) {
            int rotCounter = 0, gelbCounter = 0;
            for (int i = 0; i < rows; i++) {
                if (feld[i][j]==1) {
                    rotCounter++;
                } else {
                    rotCounter = 0;
                }
                if (rotCounter >= 4) {
                    return 1; // rot
                }

                if (feld[i][j]==2) {
                    gelbCounter++;
                } else {
                    gelbCounter = 0;
                }
                if (gelbCounter >= 4) {
                    return 2; // gelb
                }
            }
        }
        return 0;
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
            if(feld[i][spalte]==1 || feld[i][spalte]==2)
            {
                anzahl++;
            }
        }
        return anzahl;
    }
    
    /**
     * Gibt Feld nach System.out aus
     * Autor(Malte)
     */
    public void feldAusgeben() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print("" + feld[i][j]  + " ");
            }
            System.out.println();
        }
    }
}

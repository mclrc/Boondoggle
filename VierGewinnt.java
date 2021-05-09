
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
     * Fügt einen Buchstaben (für eine Farbe) in der vorgegebenen
     * Spalte auf der richtigen Höhe ein
     * Autor(Tobias, Emre)
     */
    public void chipPlatzieren(int spalte)
    {
        if(spalte > 6||chipsInSpalte(spalte) == 6)//checkt ob der chip in einer existierenden Spalte platziert werden soll oder ob die Spalte schon voll ist
        {
            
        }
        else if(rotIstDran)//wenn Rot dran ist wird "r" (fuer Rot) an den Platz eingefuegt
        {
            feld[5-chipsInSpalte(spalte)][spalte] = 1;
            rotIstDran = false;
        }
        else//wenn nicht dann "G" (fuer Gelb)
        {
            feld[5-chipsInSpalte(spalte)][spalte] = 2;
            rotIstDran = true;
        }
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
     * Autor(Tobias, Emre)
     */
    private int chipsInSpalte(int spalte)
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

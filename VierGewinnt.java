
/**
 * Beschreiben Sie hier die Klasse vierGewinnt.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class VierGewinnt
{
    String[][] feld;
    int turn;//noch unbenutzt
    boolean rotIstDran;
    
    /**
     * Konstruktor für Objekte der Klasse vierGewinnt
     */
    public VierGewinnt()
    {
        feld = new String[6][7];
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
            feld[5-chipsInSpalte(spalte)][spalte] = "R";
            rotIstDran = false;
        }
        else//wenn nicht dann "G" (fuer Gelb)
        {
            feld[5-chipsInSpalte(spalte)][spalte] = "G";
            rotIstDran = true;
        }
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
            if(feld[i][spalte]=="R"||feld[i][spalte]=="G")
            {
                anzahl++;
            }
        }
        return anzahl;
    }
}


/**
 * Beschreiben Sie hier die Klasse vierGewinnt.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class VierGewinnt
{
    char[][] feld;
    int turn;
    boolean spieler;
    
    /**
     * Konstruktor für Objekte der Klasse vierGewinnt
     */
    public VierGewinnt()
    {
        feld = new char[6][7];
    }

    public void chipPlatzieren(int spalte)
    {
        
    }
    
    public int chipsInSpalte(int spalte)
    {
        int count = 0;
        for(int i=0; i<=6; i++)
        {
            if(getChar(feld[i][spalte]) )
            {
                count++;
            }
        }
        return count;
    }
}

/*
 * @author (Emre, Tobias) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Chip
{
    private int farbe;//1 = Rot, 2 = Gelb
    
    /**
     * Konstruktor für Objekte der Klasse Chip
     */
    public Chip(int farbe)
    {
        this.farbe = farbe;
    }
    
    public int getFarbe()
    {
        return farbe;
    }
}

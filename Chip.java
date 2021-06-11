/**
 * Chip-Klasse für eine objektorientierte Programmierung.
 * Chips im VierGewinnt sind Objekte.
 * 
 * @author (Emre, Tobias) 
 * @version (V.3)
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

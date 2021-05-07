
/**
 * Beschreiben Sie hier die Klasse Spielfeld.
 * 
 * @author (Neele) 
 * @version (1)
 */
public class Spielfeld
{
    int reihen;
    int spalten; 
    /**
     * Konstruktor für Objekte der Klasse Spielfeld. 
     * Anzahl Reihen und Spalten kann nachträglich festgelegt werden. 
     */
    public Spielfeld(int reihen, int spalten)
    {
        this.reihen = reihen;
        this.spalten = spalten;
        erstelleSpielfeldLeer(reihen,spalten);
    }
    /**
     *Konstruktor für Objekte der Klasse Spielfeld.
     *Es wird ein Spielfeld mit 6 Reihen und 7 Spalten erstellt.
     */
    public Spielfeld(){
        erstelleSpielfeldLeer(6,7);
    }
    
    /**
     * Erstellt ein leeres Spielfeld 
     */
    public Spielfeld erstelleSpielfeldLeer(int reihen, int spalten)
    {
        for(int i = 0; i<reihen; i ++){ // Zählschleife für Reihen
            for (int j = 0; j<spalten; j++){ //Zählschleife für Spalten
                String feld = " x "; //Inhalt eines Feldes
                System.out.print(feld + " | "); // In einer Spalte werden felder mithilfe von "|" getrennt und gemäß der Anzahl der Spalten hintereinander Ausgegeben.
            }
            System.out.println(" "); // Umbruch
        }
        return this; //Spielfeld wird zurückgegeben
    }
}

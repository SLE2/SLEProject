package de.gss.toilette;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Basisklasse für virtuelle Toiletten.
 * In dieser Klasse sind lediglich ein paar Hilfsfunktionen, Zaehler und die Status-Anzeige implementiert.
 * 
 * @author bade
 */
public abstract class ToiletteAbstrakt
{
	/** Dieser Zähler wird erhöht, sobald Frauen und Maenner gleichzeitig auf der Toilette sind */
	private int frauUndMannGleichzeitigZaehler = 0;
	
	/** Dieser Zähler zeigt an, wieviele Frauen zu einem Zeitpunkt auf der Toilette sind */
	protected AtomicInteger anzahlFrauenAufDerToilette = new AtomicInteger(0);
	
	/** Dieser Zähler zeigt an, wieviele Maenner zu einem Zeitpunkt auf der Toilette sind */
	protected AtomicInteger anzahlMaennerAufDerToilette = new AtomicInteger(0);

	/** Die Status-Anzeige zeigt an, ob die Toilette FREI ist, >= 1 FRAU bzw >= 1 MANN auf der Toilette ist */
	protected ToiletteStatusAnzeige statusAnzeige;
	
	/**
	 * Erzeugt und initialisiert eine neue virtuelle Toilette.
	 */
	public ToiletteAbstrakt()
	{
		statusAnzeige = new ToiletteStatusAnzeige();
	}
	
	/**
	 * Diese Methode wird aufgerufen, sobald eine Person auf Toilette gehen moechte.
	 * Sie muss von einer erbenden Klasse implementiert werden.
	 * @param istFrau
	 */
	public abstract void aufToiletteGehen(boolean istFrau);
	
	/**
	 * Gib die Status-Anzeige für eine Toilette.
	 * @return  Die Status-Anzeige.
	 */
	public ToiletteStatusAnzeige gibStatusAnzeige()
	{
		return statusAnzeige;
	}
	
	/**
	 * Schreibe eine Log-Ausgabe in die Konsole und führe Buch, ob Frauen und Maenner gleichzeitig auf der Toilette waren.
	 * @param logString  Die Log-Ausgabe.
	 */
	protected synchronized void log(String logString)
	{
		if ((logString.contains("Mann") && logString.contains("betritt") && (anzahlFrauenAufDerToilette.get() > 0)) ||
				(logString.contains("Frau") && logString.contains("betritt") && (anzahlMaennerAufDerToilette.get() > 0)))
		{
			System.err.println(logString);
			frauUndMannGleichzeitigZaehler++;
		}
		else
			System.out.println(logString);
	}
	
	/**
	 * Wartet eine gewisse Zeit und belegt dabei die CPU
	 * @param milisec Zeit in Millisekunden
	 */
	protected void busyWait(long milisec) {
		long start = System.currentTimeMillis();
		while(System.currentTimeMillis()-start < milisec);
	}
	
	/**
	 * Liefert den Zähler zurück, der angibt wie oft Frauen und Maenner gleichzeitig auf der Toilette waren 
	 * (=> zählt die Fehler)
	 * @return  Zaehler für Fehler 
	 */
	public int getFrauUndMannGleichzeitigZaehler()
	{
		return frauUndMannGleichzeitigZaehler;
	}
}

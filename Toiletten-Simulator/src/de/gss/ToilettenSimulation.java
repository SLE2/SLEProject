package de.gss;

import java.util.Random;

import de.gss.aufgabe.g01KrachtSchlieperSchmittScheike.ToiletteSemaphore;
import de.gss.aufgabe.g01KrachtSchlieperSchmittScheike.ToiletteTSL;
import de.gss.toilette.*;

/**
 * test test test test test
 * Hauptklasse zum Starten der Toiletten-Simulation.
 * In dieser Klasse wird die Implemntierung der virtuellen Toilette festgelegt und 
 * es werden die Threads der virtuellen Personen erzeugt, initialisiert und gestartet.
 * ghgfgfhsdfsdfsdfsxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxsdddss
 * reregreg333322sdrtgggtrtgtrsssssfggggyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy
 * zzzzzzzzzzzzzzzzzzxdjjj2468165421874sfzurebuzrebrbywert
 * @author bade
 */
public class ToilettenSimulation
{

	/** Konstante, die angibt, wieviele virtuelle Personen (Threads) erzeugt werden sollen */
	public final static int ANZAHL_PERSONEN = 10;
	
	/** Konstante, die angibt, wie oft jede Person auf Toilette gehen moechte */
	public final static int ANZAHL_DURCHLAEUFE = 5;

	/** Die virtuelle Toilette */
	private static ToiletteAbstrakt toilette;
	
	public ToilettenSimulation()
	{
		// Erzeuge eine neue Instanz einer virtuellen Toilette
		toilette = new ToiletteNaiv();
//		toilette = new ToiletteTSL();
//		toilette = new ToiletteSemaphore();
		
		// Personen-Threads initialisieren und starten
		Thread[] threads = new Thread[ANZAHL_PERSONEN];
		for(int i=0; i < ANZAHL_PERSONEN; i++) 
		{
			threads[i] = new PersonenThread(i+1, (i % 2 == 0));
			threads[i].start();
		}
		
		// Warten bis Personen-Threads fertig sind (bis alle Personen ANZAHL_DURCHLAEUFE-mal auf Toilette waren)
		for(int i=0; i<threads.length; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
			}
		}
		
		// Nachdem die Simulation beendet wurde, zeige eine Zusammenfassung der Ergebnisse an
		if (toilette.getFrauUndMannGleichzeitigZaehler() > 0)
			System.err.println("Frauen und Maenner waren " + toilette.getFrauUndMannGleichzeitigZaehler() + " mal gleichzeitig auf Toilette.");
		else
			System.out.println("Frauen und Maenner waren " + toilette.getFrauUndMannGleichzeitigZaehler() + " mal gleichzeitig auf Toilette.");
	}
	
	/**
	 * Diese innere Klasse repraesentiert eine virtuelle Person.
	 * 
	 * @author bade
	 */
	class PersonenThread extends Thread
	{
		/** Diese Variable zeigt an, ob eine Frau oder ein Mann repraesentiert wird */
		private boolean istFrau;
		
		/**
		 * Erzeuge eine neue virtuelle Person.
		 * @param nr  Fortlaufende Nummer zur Identifikation der Person
		 * @param istFrau  true, falls es sich um eine weibliche virtuelle Person handelt, sonst false.
		 */
		public PersonenThread(int nr, boolean istFrau)
		{
			super((istFrau ? "Frau" : "Mann") + " # " + nr); // Name des Threads (f�r die Log-Ausgaben)
			this.istFrau = istFrau;
		}
		
		/**
		 * Wird beim Starten des Threads aufgerufen.
		 */
		public void run()
		{
			for (int i=0; i < ANZAHL_DURCHLAEUFE; i++)
			{
				// ein bisschen schlafen, bis man (wieder) auf Toilette muss
				try {
					Thread.sleep((int)(new Random().nextFloat()*2000));
				} catch (InterruptedException e) { 
					e.printStackTrace();
				}
				
				// dann wirklich auf Toilette gehen (umfasst warten, bis die Toilette frei ist)
				toilette.aufToiletteGehen(istFrau);
			}
		}
	}
	
	/**
	 * �ber diese Methode wird die Simulation gestartet.
	 * @param args  Argumente f�r die Simulation (wird nicht verwendet).
	 */
	public static void main(String[] args)
	{
		new ToilettenSimulation();
	}
}

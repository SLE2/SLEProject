package de.gss.aufgabe.g01KrachtSchlieperSchmittScheike;
import de.gss.sync.Semaphore;
import de.gss.toilette.ToiletteAbstrakt;

public class ToiletteSemaphore extends ToiletteAbstrakt
{
	private static Semaphore semaphore;
	
	public ToiletteSemaphore()
	{
		super();
		/*
		 * Wir sahen uns gezwungen f�r dieses Problem n = 1 zu w�hlen,
		 * da nur auf diese Weise der wechselseiteige Ausschluss gew�hrleistet
		 * werden kann.
		 */
		semaphore = new Semaphore(1);
	}
	
	public void aufToiletteGehen(boolean istFrau)
	{
			semaphore.P();
			
			log(Thread.currentThread().getName() + " betritt die Toilette");
			
			busyWait(250); // Frau ist "beschaeftigt"
			
			semaphore.V();
			
			log(Thread.currentThread().getName() + " verlaesst die Toilette");
	}
}

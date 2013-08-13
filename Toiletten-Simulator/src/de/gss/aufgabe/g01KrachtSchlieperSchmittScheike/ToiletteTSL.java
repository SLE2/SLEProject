package de.gss.aufgabe.g01KrachtSchlieperSchmittScheike;
import de.gss.sync.TSL;
import de.gss.toilette.ToiletteAbstrakt;

public class ToiletteTSL extends ToiletteAbstrakt
{
	/*
	 * Der Wert der Lockvariablen bedeutet:
	 * 0 = frei
	 * 1 = Mann
	 * 2 = Frau
	 */
	private static TSL tsl;
	int indikator;
	
	public ToiletteTSL()
	{
		super();
		tsl = new TSL();
		indikator = 0;
	}
	
	public void aufToiletteGehen(boolean istFrau)
	{
		if (istFrau)
		{
			indikator = tsl.tsl();
			tsl.set(indikator);
			while (indikator == 1) // warten auf FREI bzw. FRAU
			{
				busyWait(250); // Wahrscheinlichkeit erhöhen, dass Race Condition tatsächlich zu Problemen führt
				indikator = tsl.tsl();
				tsl.set(indikator);
			}
			tsl.set(2);
			anzahlFrauenAufDerToilette.incrementAndGet();
			
			log(Thread.currentThread().getName() + " betritt die Toilette");
			
			busyWait(250); // Frau ist "beschaeftigt"
			
			if (anzahlFrauenAufDerToilette.decrementAndGet() == 0)
				tsl.set(0);
			
			log(Thread.currentThread().getName() + " verlaesst die Toilette");
		}
		else
		{
			indikator = tsl.tsl();
			tsl.set(indikator);
			while (indikator == 2) // warten auf FREI bzw. MANN
			{
				busyWait(250); // Wahrscheinlichkeit erhöhen, dass Race Condition tatsächlich zu Problemen führt
				indikator = tsl.tsl();
				tsl.set(indikator);
			}
			log(Thread.currentThread().getName() + " betritt die Toilette");
			
			tsl.set(1);
			anzahlMaennerAufDerToilette.incrementAndGet();
			
			busyWait(250); // Mann ist "beschaeftigt"
			
			if (anzahlMaennerAufDerToilette.decrementAndGet() == 0)
				tsl.set(0);
			
			log(Thread.currentThread().getName() + " verlaesst die Toilette");
		}
	}
}

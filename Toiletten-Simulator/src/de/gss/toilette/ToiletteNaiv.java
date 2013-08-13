package de.gss.toilette;


public class ToiletteNaiv extends ToiletteAbstrakt
{
	public ToiletteNaiv()
	{
		super();
	}
	
	public synchronized void aufToiletteGehen(boolean istFrau)
	{
		if (istFrau)
		{
			while (statusAnzeige.istMann()); // warten auf FREI bzw. FRAU
			{
				busyWait(250); // Wahrscheinlichkeit erhöhen, dass Race Condition tatsächlich zu Problemen führt
			}
			statusAnzeige.setzStatus(ToiletteStatusAnzeige.Status.FRAU);
			anzahlFrauenAufDerToilette.incrementAndGet();
			
			log(Thread.currentThread().getName() + " betritt die Toilette");
			
			busyWait(250); // Frau ist "beschaeftigt"
			
			if (anzahlFrauenAufDerToilette.decrementAndGet() == 0)
				statusAnzeige.setzStatus(ToiletteStatusAnzeige.Status.FREI);
			
			log(Thread.currentThread().getName() + " verlaesst die Toilette");
		}
		else
		{
			while (statusAnzeige.istFrau()); // warten auf FREI bzw. MANN
			{
				busyWait(250); // Wahrscheinlichkeit erhöhen, dass Race Condition tatsächlich zu Problemen führt
			}
			log(Thread.currentThread().getName() + " betritt die Toilette");
			
			statusAnzeige.setzStatus(ToiletteStatusAnzeige.Status.MANN);
			anzahlMaennerAufDerToilette.incrementAndGet();
			
			busyWait(250); // Mann ist "beschaeftigt"
			
			if (anzahlMaennerAufDerToilette.decrementAndGet() == 0)
				statusAnzeige.setzStatus(ToiletteStatusAnzeige.Status.FREI);
			
			log(Thread.currentThread().getName() + " verlaesst die Toilette");
		}
	}
}

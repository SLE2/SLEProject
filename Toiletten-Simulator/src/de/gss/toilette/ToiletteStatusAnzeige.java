package de.gss.toilette;

public class ToiletteStatusAnzeige
{
	public enum Status {
	    FREI, FRAU, MANN 
	}
	
	private Status status;
	
	public ToiletteStatusAnzeige()
	{
		status = Status.FREI;
	}
	
	public synchronized void setzStatus(Status status)
	{
		this.status = status;
	}
	
	public synchronized Status gibStatus()
	{
		return status;
	}
	
	public boolean istFrei()
	{
		return gibStatus() == Status.FREI;
	}
	
	public boolean istFrau()
	{
		return gibStatus() == Status.FRAU;
	}
	
	public boolean istMann()
	{
		return gibStatus() == Status.MANN;
	}
}

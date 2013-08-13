package de.gss.sync;

/**
 * Simuliert den Assembler-Befehl TSL (Test and Set Lock)
 */
public class TSL {
	private int value = 0;

	/**
	 * Liest den gespeicherten Wert und ändert ihn auf 1.
	 * Diese Methode kann nicht durch nebenläufigen Zugriff auf den gespeicherten Wert unterbrochen werden.
	 * @return alter Wert
	 */
	public synchronized int tsl() {
		int result = value;
		value = 1;
		return result;
	}

	/**
	 * Speichert einen neuen Wert ab.
	 * @param newValue neuer Wert
	 */
	public synchronized void set(int newValue) {
		value = newValue;
	}
}

package de.gss.sync;

/**
 * Klassische Semaphore
 */
public class Semaphore {
	private final java.util.concurrent.Semaphore s;

	/**
	 * Initialisiert die Semaphore mit einem Wert
	 * @param initial Startwert
	 */
	public Semaphore(int initial) {
		s = new java.util.concurrent.Semaphore(initial, true);
	}

	/**
	 * Verringert die Semaphore um 1; blockiert solange bis dies erfolgreich durchgeführt wurde.
	 */
	public void P() {
		s.acquireUninterruptibly();
	}
	/**
	 * Erhöht die Semaphore um 1
	 */
	public void V() {
		s.release();
	}
}

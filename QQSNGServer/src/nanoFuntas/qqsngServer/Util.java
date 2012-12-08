package nanoFuntas.qqsngServer;

public class Util {

	/**
	 * Safe close all kinds of AutoCloseable, jsut name them.
	 * 
	 * @param cs a list of closeable objects.
	 */
	public static void safeClose(AutoCloseable... cs) {
		for (AutoCloseable c : cs) {
			try {
				if (c != null)
					c.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

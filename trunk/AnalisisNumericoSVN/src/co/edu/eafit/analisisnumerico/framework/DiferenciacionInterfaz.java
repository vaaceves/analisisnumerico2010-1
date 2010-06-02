package co.edu.eafit.analisisnumerico.framework;

public interface DiferenciacionInterfaz {
	
	/**
	 * Funcion que se debe implementar en TODAS las funciones de diferenciacion
	 * Es el metodo a ejecutar por la interfaz grafica. Dentro se debe implementar el flujo de la funcion.
	 * @param x valor a interpolar
	 * @param h el delta del mensaje 
	 * @return el mensaje de exito o fracaso
	 */
	public double metodoDiferenciacion(double x, double h) throws AnalisisException;
}

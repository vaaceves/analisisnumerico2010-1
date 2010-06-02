package co.edu.eafit.analisisnumerico.framework;

public interface InterpolacionInterfaz {
	
	/**
	 * Funcion que se debe implementar en TODAS las funciones de interpolacion
	 * Es el metodo a ejecutar por la interfaz grafica. Dentro se debe implementar el flujo de la funcion.
	 * @param valores valores iniciales. matriz de 2 columnas. la primera tiene las x, y la 2daa las fx
	 * @param valorAInterpolar valor a interpolar 
	 * @param entradas -> todas las entradas que necesite. Recibe n parametros
	 * @return el mensaje de exito o fracaso
	 */
	public String metodoInterpolacion(double[][] valores, double valorAInterpolar, double... entradas) throws AnalisisException;
}

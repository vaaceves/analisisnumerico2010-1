package co.edu.eafit.analisisnumerico.framework;

public interface IntegracionInterfaz {
	
	/**
	 * Funcion que se debe implementar en TODAS las funciones de integracion
	 * Es el metodo a ejecutar por la interfaz grafica. Dentro se debe implementar el flujo de la funcion.
	 * @param h el delta
	 * @param limiteInferior limite inferior de la integral
	 * @param limiteSuperior limite superior de la integral
	 * @param particiones si es necesario, numero de particiones 
	 * @return el mensaje de exito o fracaso
	 */
	public double metodoIntegracion(double h, double limiteInferior, double limiteSuperior, int... particiones) throws AnalisisException;
}

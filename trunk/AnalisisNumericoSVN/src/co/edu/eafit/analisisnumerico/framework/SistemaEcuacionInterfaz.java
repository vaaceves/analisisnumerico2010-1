package co.edu.eafit.analisisnumerico.framework;

public interface SistemaEcuacionInterfaz {

	/**
	 * Funcion que se debe implementar en TODAS las funciones de sistemas de ecuacion
	 * Es el metodo a ejecutar por la interfaz grafica. Dentro se debe implementar el flujo de la funcion.
	 * @param entradas -> todas las entradas que necesite. Recibe n parametros
	 * @return el mensaje de exito o fracaso
	 */
	public void metodoSistema() throws AnalisisException;
	
	
}

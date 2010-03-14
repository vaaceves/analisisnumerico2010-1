package co.edu.eafit.analisisnumerico.common;

/**
 * Interfaz que obliga a implementar el metodo con una serie de entradas 
 * @author Sebastian
 *
 */
public interface MetodoInterfaz {

	/**
	 * Funcion que se debe implementar en TODAS las funciones
	 * Es el metodo a ejecutar por la interfaz grafica. Dentro se debe implementar el flujo de la funcion.
	 * @param entradas -> todas las entradas que necesite. Recibe n parametros
	 * @return el mensaje de exito o fracaso
	 */
	public String metodo(double... entradas);
	
	/**
	 * Ejecuta la funcion en una serie de valores
	 * @param valores: Valores a tomar la funcion
	 * @return
	 */
	public double f(double... valores);
	
	
}

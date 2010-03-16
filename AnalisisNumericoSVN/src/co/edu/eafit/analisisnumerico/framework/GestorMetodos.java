package co.edu.eafit.analisisnumerico.framework;

import co.edu.eafit.analisisnumerico.metodos.Biseccion;
import co.edu.eafit.analisisnumerico.metodos.BusquedasIncrementales;
import co.edu.eafit.analisisnumerico.metodos.ReglaFalsa;

/**
 * Clase Gestor Metodos. Posee la fabrica de metodos y la ejecucion dinamica de los mismos
 * @author Sebastian
 *
 */
public class GestorMetodos {


	/**
	 * Ejecuta un metodo dinamicamente, de acuerdo a los parametros recibidos
	 * @param numMetodo: Constante del metodo a ejecutar
	 * @param modo: Indica si es modo consola o modo grafico
	 * @param valoresPredefinidos: Array con valores por defecto, o null, para pedirselos al usuario
	 * @param strings: Lista de valores a solicitarle al usuario
	 */
	public static void ejecutar(int numMetodo, int modo, double[] valoresPredefinidos, String...strings){
		double[] entradas=null;
		if(valoresPredefinidos==null){
			try {
				entradas = solicitarListaDoubleConsola(strings);
			} catch (AnalisisException e) {
				new AnalisisException(e.getMessage());
			}
		}
		else{
			entradas=valoresPredefinidos;
		}
		MetodoPadre mp = GestorMetodos.fabricaMetodos(numMetodo);
		String resultado = mp.metodo(entradas);
		System.out.println("-------------------------------");
		System.out.println(resultado);
		System.out.println("-------------------------------");
		Object[][] resul = mp.generarMatriz();
		UtilConsola.imprimir(resul);
	}

	/**
	 * Fabrica de metodos 
	 * @param metodo: Identificador del metodo a crear
	 * @return
	 */
	private static MetodoPadre fabricaMetodos(int metodo) {
		if(metodo==Constantes.BISECCION) return new Biseccion();
		if(metodo==Constantes.BUSQUEDASINCREMENTALES) return new BusquedasIncrementales();
		if(metodo==Constantes.REGLAFALSA) return new ReglaFalsa();
		return null;
	}

	/**
	 * Solicita por consola una serie de parametros Double
	 * @param strings: Lista de parametros a solicitar al usuario
	 * @return: La lista de valores que el usuario ingresa
	 * @throws AnalisisException: En caso de entrada invalida
	 */
	private static double[] solicitarListaDoubleConsola(String[] strings) throws AnalisisException {
		double[] entradas = new double[strings.length];
		for(int i=0;i<strings.length;i++){
			entradas[i]=UtilConsola.leerDouble("Ingrese "+strings[i]+": ");
		}
		return entradas;
	}

}

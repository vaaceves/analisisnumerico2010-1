package co.edu.eafit.analisisnumerico.framework;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/**
 * Utilidades de lectura y escritura por consola 
 * @author Sebastian
 *
 */
public class UtilConsola {

	/**
	 * imprime la matriz resultado en pantalla
	 * @param matriz
	 */
	public static void imprimir(Object[][] matriz) {
		for(int i=0;i<matriz.length;i++){
			for(int j=0;j<matriz[0].length;j++){
				System.out.print(matriz[i][j]+"\t\t");
			}
			System.out.println();
		}		
	}

	/**
	 * Imprime en pantalla el mensaje del usuario y devuelve el double
	 * @param string
	 * @return el double o una excepcion si la entrada es invalida
	 */
	public static double leerDouble(String mensaje) throws AnalisisException{
		String txt="";
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		double resul = -1;
		boolean deboSalir=false;
		while(!deboSalir){
			deboSalir=true;
			System.out.println(mensaje);
			try {
				txt=br.readLine();
				resul = Double.parseDouble(txt);
			} catch (Exception e) {
				new AnalisisException(e.getMessage());
				deboSalir=false;
			}
		}
		return resul;
	}

	/**
	 * Imprime en pantalla el mensaje del usuario y devuelve el string
	 * @param string
	 * @return el double o una excepcion si la entrada es invalida
	 */
	public static String leerString(String mensaje) throws AnalisisException{
		String txt="";
		System.out.println(mensaje);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			txt=br.readLine();
		} catch (IOException e) {
			throw new AnalisisException(e.getMessage());
		}
		return txt;
	}

	/**
	 * Permite, recibiendo como parametro las cifras significativas, obtener la tolerancia
	 * @param numeroCifrasSignificativas: Ejemplo (5)
	 * @return la tolerancia en double. Ejemplo (0.00001)
	 */
	public static double getTolerancia(double numeroCifrasSignificativas){
		return 1/Math.pow(10, numeroCifrasSignificativas);
	}
}

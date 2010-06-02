package co.edu.eafit.analisisnumerico.framework;

import java.text.DecimalFormat;
import java.util.Vector;

/**
 * Clase padre de todos los metodos de interpolacion.
 * Abstracta.
 * Maneja utilidades de interpolacion
 * @author Sebastian
 * @since 17/05/2010
 */

/**
 * @author Sebastian
 *
 */
public abstract class MetodoUnidad3  implements InterpolacionInterfaz{

	/**
	 * formato para las X (los resultados)
	 */
	static private final DecimalFormat xFormat = new DecimalFormat("###.#####");
	/**
	 * formato para el proceso 
	 */
	static private final DecimalFormat procesoFormat = new DecimalFormat("###.##");
	
	public Vector<Vector<String>> datos;
	
	public double[][] matrizResultado;
	public Object[][] generarMatrizString;
	
	/**
	 * Metodo que crea una matriz aumentada donde las 2 primeras columnas son la x y la f de x, el resto 0  
	 * @param valores la matriz con pares x,fx
	 * @return matriz aumentada
	 */
	protected double[][] crearMatrizDiferenciasDivididas(double[][] valores) {
		double[][] resul = new double[valores.length][valores.length+1];
		//inicializo
		for(int i=0;i<resul.length;i++){
			for(int j=0;j<resul.length;j++){
				resul[i][j]=0;
			}
		}
		for(int i=0;i<resul.length;i++){
			resul[i][0]= valores[i][0];
			resul[i][1]= valores[i][1];
		}
		return resul;
	}
	
	protected double[][] crearMatrizLagrange(double[][] valores) {
		double[][] resul = new double[valores.length][3];
		//inicializo
		for(int i=0;i<resul.length;i++){
			for(int j=0;j<resul[0].length;j++){
				resul[i][j]=0;
			}
		}
		for(int i=0;i<resul.length;i++){
			resul[i][0]= valores[i][0];
			resul[i][1]= valores[i][1];
		}
		return resul;
	}
	
	public void adicionarMatrizImpresion(double[][] mimatriz, String... titulo){
		if(datos==null){
			datos = new Vector<Vector<String>>();
			for(int i=0;i<mimatriz[0].length;i++){
				datos.add(new Vector<String>());
			}
		}
		try{
			int i=0;
			for(i=0;i<titulo.length;i++){
				datos.get(i).add(titulo[i]);
			}
			for(;i<datos.size();i++){
				datos.get(i).add("");
			}
			for(int k=0;k<mimatriz.length;k++){
				for(int j=0;j<mimatriz[0].length;j++){
					datos.get(j).add(String.valueOf(procesoFormat(mimatriz[k][j])));
				}
			}
		}catch(Exception e){
			new AnalisisException("Error de programacion. Numero invalido de filas en la tabla de resultados");
		}
	}
	
	
	public String resulFormat(double valor){
		return xFormat.format(valor);
	}
	
	public String procesoFormat(double valor){
		return procesoFormat.format(valor);
	}

	public Object[][] generarMatrizString() {
		Object[][] resul= new Object[matrizResultado.length][matrizResultado[0].length];
		for(int i=0;i<matrizResultado.length;i++){
			for(int j=0;j<matrizResultado[0].length;j++){
				resul[i][j]= xFormat(matrizResultado[i][j]);
			}
		}
		return resul;
	}
	
	public String xFormat(double v){
		return xFormat.format(v);
	}
	
	public void resetDatos() {
		datos=null;
		matrizResultado=null;
	}
}
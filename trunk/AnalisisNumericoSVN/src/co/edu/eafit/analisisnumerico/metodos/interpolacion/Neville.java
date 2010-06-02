package co.edu.eafit.analisisnumerico.metodos.interpolacion;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.MetodoUnidad3;

/**
 * Metodo de Neville

 */
public class Neville extends MetodoUnidad3{

	@Override
	public String metodoInterpolacion(double[][] valores, double valorAInterpolar, double... entradas)
	throws AnalisisException {
		double matriz[][]=crearMatrizDiferenciasDivididas(valores);
		int k=1;
		double a=valorAInterpolar;
		double numerador;
		double denominador;
		for (int i=2;i<matriz[0].length;i++){
			for (int j=i-1;j<matriz.length;j++){
				numerador=((a-matriz[j-k][0])*matriz[j][i-1])-((a-matriz[j][0])*matriz[j-1][i-1]);
				denominador=matriz[j][0]-matriz[j-k][0];
				matriz[j][i]=numerador/denominador;
			}
			k++;
		}
		matrizResultado=matriz;
		return "falta el resultado";
	}
}
package co.edu.eafit.analisisnumerico.metodos.interpolacion;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.MetodoUnidad3;

/**
 * Metodo de lagrange
 * @author dani, mario
 *
 */
public class Lagrange extends MetodoUnidad3{

	@Override
	public String metodoInterpolacion(double[][] valores, double valorAInterpolar,double... entradas)
			throws AnalisisException {
		double[][] matriz = crearMatrizLagrange(valores);
		double numerador=1;
		double denominador=1;
		for (int j=0;j<matriz.length;j++){
			for (int i=0;i<matriz.length;i++){
				if(i!=j){
					numerador=numerador*(valorAInterpolar-matriz[i][0]);
				}else{
					for (int k=0;k<matriz.length;k++){
						if(k!=i){
							denominador=denominador*(matriz[i][0]-matriz[k][0]);
						}
					}
				}
			}
			matriz[j][2]=numerador/denominador;
			numerador=1;
			denominador=1;
		}
		double result=0;
		for (int j=0;j<matriz.length;j++){
			result=result+(matriz[j][1]*matriz[j][2]);
		}
		matrizResultado=matriz;
		return "Resultado de la interpolación de "+valorAInterpolar+": "+resulFormat(result);
	}
}
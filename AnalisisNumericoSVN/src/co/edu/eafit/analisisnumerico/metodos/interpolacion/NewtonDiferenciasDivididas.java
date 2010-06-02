package co.edu.eafit.analisisnumerico.metodos.interpolacion;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.MetodoUnidad3;

/**
 * Metodo de dif divididas
 * @author Sebastian, dani, mario
 *
 */
public class NewtonDiferenciasDivididas extends MetodoUnidad3{

	@Override
	public String metodoInterpolacion(double[][] valores, double valorAInterpolar, double... entradas)
	throws AnalisisException {
		double[][] matriz = crearMatrizDiferenciasDivididas(valores);
		int k=1;
		for (int i=2;i<matriz[0].length;i++){
			for (int j=i-1;j<matriz.length;j++){
				matriz[j][i]=(matriz[j][i-1]-matriz[j-1][i-1])/(matriz[j][0]-matriz[j-k][0]);
			}
			k++;
		}
		double tmp=1;
		int r=1;
		double result=0;
		for (int i=0;i<matriz.length-1;i++){
			tmp= tmp*(valorAInterpolar-matriz[i][0]);
			result=result+matriz[r][r+1]*tmp;
			r++;
		}
		result=result+matriz[0][1];
		matrizResultado=matriz;
		return "Resultado de la interpolación de "+valorAInterpolar+": "+resulFormat(result);
	}
}

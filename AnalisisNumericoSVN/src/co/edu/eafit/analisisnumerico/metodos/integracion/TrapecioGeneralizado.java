package co.edu.eafit.analisisnumerico.metodos.integracion;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.IntegracionInterfaz;
import co.edu.eafit.analisisnumerico.framework.MetodoUnidad5;

public class TrapecioGeneralizado extends MetodoUnidad5 implements IntegracionInterfaz{

	@Override
	public double metodoIntegracion(double h, double limiteInferior,
			double limiteSuperior, int... particiones) throws AnalisisException {
		double sumatoria=0;
		 
		for (int i=1;i<particiones[0];i++){
			sumatoria+=f(limiteInferior+(h*i));
		}
		double resultado= (h/2)*(f(limiteInferior)+(2*sumatoria)+f(limiteSuperior));
		return resultado;
	}
}
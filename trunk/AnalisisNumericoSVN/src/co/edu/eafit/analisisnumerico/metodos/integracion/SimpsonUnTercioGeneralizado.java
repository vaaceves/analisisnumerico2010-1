package co.edu.eafit.analisisnumerico.metodos.integracion;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.IntegracionInterfaz;
import co.edu.eafit.analisisnumerico.framework.MetodoUnidad5;

public class SimpsonUnTercioGeneralizado extends MetodoUnidad5 implements IntegracionInterfaz{

	@Override
	public double metodoIntegracion(double h, double limiteInferior,
			double limiteSuperior, int... particiones) throws AnalisisException {
		double sumatoriaImpar=0;
		double sumatoriaPar=0;
		for (int i=1;i<particiones[0];i++){
			if((i%2)==0){
				sumatoriaPar+=f(limiteInferior+(h*i));
			}else{
				sumatoriaImpar+=f(limiteSuperior+(h*i));
			}
		}
		
		double resultado= (h/3)*(f(limiteInferior)+(4*sumatoriaImpar)+(2*sumatoriaPar)+f(limiteSuperior));
		return resultado;
	}

}

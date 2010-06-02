package co.edu.eafit.analisisnumerico.metodos.integracion;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.IntegracionInterfaz;
import co.edu.eafit.analisisnumerico.framework.MetodoUnidad5;

public class SimpsonTresOctavos extends MetodoUnidad5 implements IntegracionInterfaz{

	@Override
	public double metodoIntegracion(double delta, double x0,
			double x1, int... particiones) throws AnalisisException {
		double h= (x1-x0)/3;
		double resultado= (3*h/8)*(f(x0)+ 3*f(x0+h)+3*f(x0+(2*h))+f(x1));
		return resultado;

	}

}

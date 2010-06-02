package co.edu.eafit.analisisnumerico.metodos.integracion;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.IntegracionInterfaz;
import co.edu.eafit.analisisnumerico.framework.MetodoUnidad5;

public class TrapecioSencillo extends MetodoUnidad5 implements IntegracionInterfaz{

	@Override
	public double metodoIntegracion(double h, double x0,
			double x1, int... particiones) throws AnalisisException {
		double resultado= ((x1-x0)* (f(x0)+f(x1)))/2;
		return resultado;
	}

}

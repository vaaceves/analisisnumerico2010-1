package co.edu.eafit.analisisnumerico.metodos.diferenciacion.dospuntos;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.DiferenciacionInterfaz;
import co.edu.eafit.analisisnumerico.framework.MetodoUnidad4;

public class DiferenciacionDosPuntosRegresiva extends MetodoUnidad4 implements DiferenciacionInterfaz {

	@Override
	public double metodoDiferenciacion(double x, double h)
			throws AnalisisException {
		double resultado= (f(x-h)-f(x))/-h;
		return resultado;
		
	}

}

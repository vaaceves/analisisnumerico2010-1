package co.edu.eafit.analisisnumerico.metodos.diferenciacion.trespuntos;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.DiferenciacionInterfaz;
import co.edu.eafit.analisisnumerico.framework.MetodoUnidad4;

public class DiferenciacionTresPuntosProgresiva extends MetodoUnidad4 implements DiferenciacionInterfaz {

	@Override
	public double metodoDiferenciacion(double x, double h)
			throws AnalisisException {
		double resultado= (-3*f(x)+ 4*f(x+h)-f(x+2*h))/(2*h);
		return resultado;
	}

}

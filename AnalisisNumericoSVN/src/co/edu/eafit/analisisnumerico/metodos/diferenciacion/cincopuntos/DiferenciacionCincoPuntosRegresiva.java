package co.edu.eafit.analisisnumerico.metodos.diferenciacion.cincopuntos;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.DiferenciacionInterfaz;
import co.edu.eafit.analisisnumerico.framework.MetodoUnidad4;

public class DiferenciacionCincoPuntosRegresiva extends MetodoUnidad4 implements DiferenciacionInterfaz {

	@Override
	public double metodoDiferenciacion(double x, double h)
			throws AnalisisException {
		double resultado= (-25*f(x)+(48*f(x-h))-(36*f(x-2*h))+(16*f(x-3*h))-(3*f(x-4*h)))/(12*(h*-1));
		return resultado;
	}

}

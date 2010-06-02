package co.edu.eafit.analisisnumerico.metodos.diferenciacion.cincopuntos;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.DiferenciacionInterfaz;
import co.edu.eafit.analisisnumerico.framework.MetodoUnidad4;

public class DiferenciacionCincoPuntosCentrada extends MetodoUnidad4 implements DiferenciacionInterfaz {

	@Override
	public double metodoDiferenciacion(double x, double h)
			throws AnalisisException {
		double resultado= (f(x-2*h)-(8*f(x-h))+(8*f(x+h))-(f(x+2*h)))/(12*h);
		return resultado;
	}

}

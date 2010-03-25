package co.edu.eafit.analisisnumerico.metodos;

import co.edu.eafit.analisisnumerico.framework.*;

/**
 * Metodo de Raices Multiples.
 * Categoria: Ecuaciones Lineales
 * * @author Equipo analisis
 *
 */
public class RaicesMultiples extends MetodoPadre implements MetodoInterfaz {

	/**
	 * Funcion Main. Ejecuta Raices multiples
	 * 
	 */
	public static void main(String[] args){
		String[] fPred = {"3x^2-4", "6x", "0"};
		double[] valoresIniciales = {1,5,20};
		GestorMetodos.ejecutar(Constantes.RAICESMULTIPLES, Constantes.MODOGRAFICOINTERFAZ1, "Raices multiples", "f,fdev,fdd", fPred, valoresIniciales, "Xn", "Cifras significativas", "iteraciones");
	}

	@Override
	public String metodo(double... entradas) throws AnalisisException {
		this.adicionarFilaTitulos("iteracion","xn","f(xn)","f'(xn)","f''(x)","error");
		double x0 = entradas[0];
		double cifrasSignificativas = entradas[1];
		double iteraciones = entradas[2];
		//CONVIERTO LAS CIFRAS A TOLERANCIA (5->0,000001)
		double tolerancia = UtilConsola.getTolerancia(cifrasSignificativas);
		double y0 =f(x0);
		double dy0 = fd(x0);
		double ddy0 = fdd(x0);
		double error= tolerancia+1;
		double denominador = Math.pow(dy0, 2)-(y0*ddy0);
		double cont =0;
		double x1;
		adicionarFilaResultados(cont,x0,y0,dy0,ddy0,-1.0);
		while(y0!=0&&error>tolerancia&&cont<iteraciones){
			x1 = x0-((y0*dy0)/denominador);
			y0 = f(x1);
			dy0 = fd(x1);
			ddy0 = fdd(x1);
			error = Math.abs(x1-x0);
			denominador = Math.pow(dy0, 2)-(y0*ddy0);
			x0=x1;
			adicionarFilaResultados(cont,x0,y0,dy0,ddy0,error);
			cont++;
		}
		if (y0 == 0){
			return x0+" es raiz. Hallada en "+cont+" iteraciones";
		}
		else if (error < tolerancia){
			return x0+" es una raiz aproximada. Hallada en "+cont;
		}
		else if (denominador == 0){
			return "Denominador se hizo cero";
		}
		else return "fallo con "+cont+" iteraciones";
	}

}

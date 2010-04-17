package co.edu.eafit.analisisnumerico.metodos.iterativos;

import co.edu.eafit.analisisnumerico.framework.*;

/**
 * Metodo de Newton.
 * Categoria: Ecuaciones Lineales
 * * @author Equipo analisis
 *
 */
public class Newton extends MetodoPadre implements MetodoInterfaz {

	/**
	 * Funcion Main. Ejecuta Newton
	 * 
	 */
	public static void main(String[] args){
		GestorMetodos.ejecutar(Constantes.NEWTON, Constantes.MODOGRAFICOINTERFAZ1, "Metodo de Newton", "f,fdev",null,null,  "Xn", "Cifras significativas", "iteraciones");
	}

	@Override
	public String metodo(double... entradas) throws AnalisisException{
		this.adicionarFilaTitulos("iteracion","xn","f(xn)","f'(xn)","error");
		double x0 = entradas[0];
		double cifrasSignificativas = entradas[1];
		double iteraciones = entradas[2];
		//CONVIERTO LAS CIFRAS A TOLERANCIA (5->0,000001)
		double tolerancia = UtilConsola.getTolerancia(cifrasSignificativas);
		double y0 =f(x0);
		double dy0 = fd(x0);
		double error= tolerancia+1;
		double cont =0;
		double x1;
		adicionarFilaResultados(cont,x0,y0,dy0,-1.0);
		while(y0!=0&&error>tolerancia&&dy0!=0&&cont<iteraciones){
			x1 = x0-(y0/dy0);
			y0 = f(x1);
			dy0 = fd(x1);
			error = Math.abs(x1-x0);
			x0=x1;
			adicionarFilaResultados(cont,x0,y0,dy0,error);
			cont++;
		}
		if (y0 == 0){
			return xFormat(x0)+" es raiz. Hallada en "+cont+" iteraciones";
		}
		else if (error < tolerancia){
			return xFormat(x0)+" es una raiz aproximada con error: "+eFormat(error)+". Hallada en "+cont+" iteraciones";
		}
		else if (dy0 == 0){
			return "Derivada se hizo cero";
		}
		else return "fallo con "+cont+" iteraciones";
	}

}

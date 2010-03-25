package co.edu.eafit.analisisnumerico.metodos;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.Constantes;
import co.edu.eafit.analisisnumerico.framework.GestorMetodos;
import co.edu.eafit.analisisnumerico.framework.MetodoInterfaz;
import co.edu.eafit.analisisnumerico.framework.MetodoPadre;
import co.edu.eafit.analisisnumerico.framework.UtilConsola;

public class Secante extends MetodoPadre implements MetodoInterfaz{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double[] valoresIniciales = {4.9,4.8,5,40};
		String[] fi ={"ln(x+4)+x*cos(1+x^2)-5"};
		GestorMetodos.ejecutar(Constantes.SECANTE, Constantes.MODOCONSOLA, "Método de la secante", "f", fi, valoresIniciales, "Xi", "Xs", "Cifras significativas", "iteraciones");
	}
	
	@Override
	public String metodo(double... entradas) throws AnalisisException{
		this.adicionarFilaTitulos("iteracion","xi","xs","xm","f(xm)","error");
		double xi=entradas[0];
		double xs=entradas[1];
		double cifrasSignificativas = entradas[2];
		double iteraciones = entradas[3];
		double tolerancia = UtilConsola.getTolerancia(cifrasSignificativas);
		
		double y0 =f(xi);
		double y1 =f(xs);
		double denominador=y1-y0;
		double error= tolerancia+1;
		int cont =0;
		if(y0==0)return "Xi es raiz";
		else {
			double xAux;
			double yAux;
			while(y1!=0&&error>tolerancia&&denominador!=0&&cont<iteraciones){
				xAux=(xs-(y1*(xs-xi)));
				yAux=f(xAux);
				error = Math.abs((xAux-xs)/xAux);
				xi=xs;
				xs=xAux;
				y0=y1;
				y1=yAux;
				denominador=y1-y0;
				cont++;
			}
		}
		//se pone que la raiz es xs o xi????????????
		if(y1==0)return xs+"es raiz. Hallado en "+cont+" iteraciones.";
		else if(error<=tolerancia)return xs+" es raiz con un error relativo de "+error+". Hallado en "+cont+" iteraciones.";
		else if(denominador==0)return "el denominador se hizo cero.";
		else return "Se ha fracasado con "+cont+" iteraciones";
	}
		

}

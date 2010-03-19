package co.edu.eafit.analisisnumerico.metodos;

import co.edu.eafit.analisisnumerico.framework.Constantes;
import co.edu.eafit.analisisnumerico.framework.GestorMetodos;
import co.edu.eafit.analisisnumerico.framework.MetodoInterfaz;
import co.edu.eafit.analisisnumerico.framework.MetodoPadre;
import co.edu.eafit.analisisnumerico.framework.UtilConsola;

public class BusquedasIncrementales extends MetodoPadre implements MetodoInterfaz{

	public static void main(String[] args) {
		//double[] valoresIniciales = {3.5,4.0,5.0,20};
		GestorMetodos.ejecutar(Constantes.BUSQUEDASINCREMENTALES, Constantes.MODOCONSOLA, null, "Xi", "Xs", "delta", "iteraciones");
	}

	@Override
	public String metodo(double... entradas) {
		this.adicionarFilaTitulos("iteracion","xi","xs","error");
		double xi=entradas[0];
		double xs=entradas[1];
		double delta = entradas[2];
		double iteraciones = entradas[3];
		int cont=0;
		double y0;
		double y1 = f(xs) ;
		do{
			xi=xs;
			xs=xs+delta;
			cont++;
			y0=y1;
			y1=f(xs);
		}while(y0!=0&&y1!=0&&y0*y1>=0&&cont<iteraciones);
		if(y1==0) return xs + "es raiz";
		else if(y0==0) return xi + "es raiz";
		else if(cont<iteraciones)return "Intervalo: entre "+ xi +" y "+ xs + "hallado en "+ cont +"numero de iteraciones";
		else return "numero maximo de iteraciones";	
	}
}

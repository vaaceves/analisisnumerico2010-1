package co.edu.eafit.analisisnumerico.metodos;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.Constantes;
import co.edu.eafit.analisisnumerico.framework.GestorMetodos;
import co.edu.eafit.analisisnumerico.framework.MetodoInterfaz;
import co.edu.eafit.analisisnumerico.framework.MetodoPadre;

/**
 * metodo de búsquedas incrementales
 * @author Daniela
 *
 */
public class BusquedasIncrementales extends MetodoPadre implements MetodoInterfaz{

	public static void main(String[] args) {
		//double[] valoresIniciales = {3.5,4.0,5.0,20};
		GestorMetodos.ejecutar(Constantes.BUSQUEDASINCREMENTALES, Constantes.MODOGRAFICOINTERFAZ1, "Metodo de busquedas incrementales", "f",null, null,  "Xi", "delta", "iteraciones");
	}

	@Override
	public String metodo(double... entradas)  throws AnalisisException {
		this.adicionarFilaTitulos("iteracion","xi","xs");
		double xi=entradas[0];
		double delta = entradas[1];
		double iteraciones = entradas[2];	
		double y0=f(xi);
		
		if(y0==0){
			return xi + "es raiz";
		}else{
			double xs=xi+delta;
			double y1=f(xs);
			double cont=1;
			
			while(y0*y1>0&&cont<iteraciones){
				xi=xs;
				y0=y1;
				xs=xi+delta;
				y1=f(xs);
				cont++;
			}
			if(y1==0) return xs + "es raiz";
			else if(y0*y1<0) return "Intervalo: entre "+ xi +" y "+ xs + "hallado en "+ cont +"numero de iteraciones";
			else return "numero maximo de iteraciones";			
		}
		
			
	}
}

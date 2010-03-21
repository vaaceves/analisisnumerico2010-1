package co.edu.eafit.analisisnumerico.metodos;

import co.edu.eafit.analisisnumerico.framework.Constantes;
import co.edu.eafit.analisisnumerico.framework.GestorMetodos;
import co.edu.eafit.analisisnumerico.framework.MetodoInterfaz;
import co.edu.eafit.analisisnumerico.framework.MetodoPadre;
import co.edu.eafit.analisisnumerico.framework.UtilConsola;

public class PuntoFijo  extends MetodoPadre implements MetodoInterfaz{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//double[] valoresIniciales = {3.5,4.0,5.0,20};
		GestorMetodos.ejecutar(Constantes.PUNTOFIJO,Constantes.MODOCONSOLA, null, "X0", "Cifras significativas", "iteraciones");
	}
	
	@Override
	public String metodo(double... entradas) {
		this.adicionarFilaTitulos("iteracion","xi","xm","f(xm)","error");
		double x0 = entradas[0];
		double cifrasSignificativas = entradas[1];
		double iteraciones = entradas[2];
		double tolerancia = UtilConsola.getTolerancia(cifrasSignificativas);
		
		double y =f(x0);
		double error= tolerancia+1;
		int cont =0;
		double x1=0;
		while(y!=0&&error>tolerancia&&cont<iteraciones){
			x1=g(x0);
			y=f(x1);
			error=Math.abs(x1-x0)/x1;
			cont++;
			x0=x1;
		}
		if(y==0)return x1+"es raiz. Hallado en "+cont+" iteraciones.";
		else if(error<=tolerancia)return x1+" es raiz con un error relativo de "+error+". Hallado en "+cont+" iteraciones.";
		else return "Se ha fracasado con "+cont+" iteraciones";
	}

	
}
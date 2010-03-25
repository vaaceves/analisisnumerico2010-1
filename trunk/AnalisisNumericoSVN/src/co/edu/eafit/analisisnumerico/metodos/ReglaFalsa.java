package co.edu.eafit.analisisnumerico.metodos;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.Constantes;
import co.edu.eafit.analisisnumerico.framework.GestorMetodos;
import co.edu.eafit.analisisnumerico.framework.MetodoInterfaz;
import co.edu.eafit.analisisnumerico.framework.MetodoPadre;
import co.edu.eafit.analisisnumerico.framework.UtilConsola;

public class ReglaFalsa extends MetodoPadre implements MetodoInterfaz{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] fPre = {"3x^2-4"};
		double[] valPre = {-1.0,4.0,5.0,20.0};
		GestorMetodos.ejecutar(Constantes.REGLAFALSA, Constantes.MODOCONSOLA, "Método de regla falsa", "f", fPre, valPre, "Xi", "Xs", "Cifras significativas", "iteraciones");
	}

	@Override
	public String metodo(double... entradas) throws AnalisisException {
		// TODO Auto-generated method stub
		this.adicionarFilaTitulos("iteracion","xi","xs","xm","f(xm)","f(xi)","f(xs)","error");
		double xi=entradas[0];
		double xs=entradas[1];
		double cifrasSignificativas = entradas[2];
		double iteraciones = entradas[3];
		double tolerancia = UtilConsola.getTolerancia(cifrasSignificativas);
		double yi =f(xi);
		double ys =f(xs);
		if(yi==0)return "Xi es raiz";
		else if(ys==0)return "Xs es raiz";
		else if(ys*yi>0)return "Intervalo inadecuado";
		else{
			double xm=xi-((yi*(xi-xs))/(yi-ys)); //Xm de la regla falsa. 
			double xAux;
			double error= tolerancia+1;
			double cont =1;
			double ym=f(xm);
			adicionarFilaResultados(cont,xi,xs,xm,ym,yi,ys,error);
			while(ym!=0&&error>tolerancia&&cont<iteraciones){
				if(yi*ym<0){
					xs=xm;
					ys=ym;
				}
				else{
					xi=xm;
					yi=ym;
				}
				xAux=xm;
				xm=(xi+xs)/2;
				ym=f(xm);
				error = Math.abs(xm-xAux)/xm;
				cont++;
				adicionarFilaResultados(cont,xi,xs,xm,ym,yi,ys,error);
			}
			if(ym==0)return xm+"es raiz. Hallado en "+cont+" iteraciones.";
			else if(error<=tolerancia)return xm+" es raiz con un error relativo de "+error+". Hallado en "+cont+" iteraciones.";
			else return "Se ha fracasado con "+cont+" iteraciones";
		}
	}
}

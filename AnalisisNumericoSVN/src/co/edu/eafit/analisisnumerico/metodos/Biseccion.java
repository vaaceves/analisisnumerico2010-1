package co.edu.eafit.analisisnumerico.metodos;

import co.edu.eafit.analisisnumerico.common.AnalisisException;
import co.edu.eafit.analisisnumerico.common.MetodoInterfaz;
import co.edu.eafit.analisisnumerico.common.MetodoPadre;
import co.edu.eafit.analisisnumerico.common.UtilConsola;


/**
 * Metodo de la biseccion. Utiliza el punto medio para acercarse a la raiz
 * Categoria: Ecuaciones Lineales
 * * @author Sebastian
 *
 */
public class Biseccion extends MetodoPadre implements MetodoInterfaz {

	/*
	 * este metodo no debe aparecer una vez tengamos la interfaz grafica
	 * solo debe crear el objeto, ejecutar el metodo ejecutar, e imprimir los resultados
	 * no debe poseer logica del negocio
	 * una vez creemos la interfaz, lo vamos a eliminar
	 */
	
	public static void main(String[] args){
		Biseccion b = new Biseccion();
		String resul = b.ejecutar();
		System.out.println(resul);
		Object[][] matriz = b.generarMatriz();
		UtilConsola.imprimir(matriz);
	}


	/*
	 * este metodo no debe aparecer una vez tengamos la interfaz grafica
	 * solo debe leer los datos por consola y ejecutar la funcion. NADA MAS.
	 * no debe poseer logica del negocio
	 * una vez creemos la interfaz, lo vamos a eliminar
	 */
	private String ejecutar() {
		try{
			double xi = UtilConsola.leerDouble("Ingrese Xi: ");
			double xs = UtilConsola.leerDouble("Ingrese Xs: ");
			double cifrasSignificativas = UtilConsola.leerDouble("Ingrese el numero de cifras significativas: ");
			double tolerancia = UtilConsola.getTolerancia(cifrasSignificativas);
			double iteraciones = UtilConsola.leerDouble("Ingrese las iteraciones: ");
			String resultado = metodo(xi,xs, tolerancia,iteraciones);
			return resultado;
		}
		catch(AnalisisException e){ return e.getMessage();	}
		
	}


	@Override
	public String metodo(double... entradas) {
		this.adicionarFilaTitulos("iteracion","xi","xs","xm","f(xm)","error");
		double xi = entradas[0];
		double xs = entradas[1];
		double tolerancia = entradas[2];
		double iteraciones = entradas[3];
		double yi =f(xi);
		double ys =f(xs);
		if(yi==0)return "Xi es raiz";
		else if(ys==0)return "Xs es raiz";
		else if(ys*yi>0)return "Intervalo inadecuado";
		else{
			double xm= (xi+xs)/2;
			double xAux;
			double error= tolerancia+1;
			double cont =1;
			double ym=f(xm);
			adicionarFilaResultados(cont-1,xi,xs,xm,ym,-1.0);
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
				error = Math.abs(xm-xAux);
				adicionarFilaResultados(cont,xi,xs,xm,ym,error);
				cont++;
			}
			if(ym==0)return xm+"es raiz. Hallado en "+cont+" iteraciones.";
			else if(error<=tolerancia)return xm+" es raiz con un error relativo de "+error+". Hallado en "+cont+" iteraciones.";
			else return "Se ha fracasado con "+cont+" iteraciones";
		}
	}
}
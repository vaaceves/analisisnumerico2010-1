package co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones;


import co.edu.eafit.analisisnumerico.framework.*;

/**
 * Metodo de 
 * Categoria: Ecuaciones Lineales
 * * @author Sebastian
 *
 */
public class LUSimple extends MetodoUnidad2 implements SistemaEcuacionInterfaz {

	public LUSimple(Object[][] matriz) throws AnalisisException{
		super(matriz);
	}
	
	/**
	 * Funcion Main. Ejecuta Gauss Simple
	 * 
	 */
	public static void main(String[] args){

	}

	@Override
	public String metodoSistema(double... d) throws AnalisisException {
		recortarMatriz();
		String resultado="";
		for(int k=0;k<n-1;k++){
			boolean ok=gaussEnEtapa(k);
			if(!ok)return "division por cero en gauss";
		}
		DatoMatriz[][] 	u = getU();
		DatoMatriz[][] l = getL();
		adicionarMatrizImpresion(u, "Matriz U:");
		adicionarMatrizImpresion(l, "Matriz L:");
		l = concatenarTerminoIndependiente(l,b);
		Termino[] z = sustitucionProgresiva(l);
		adicionarVectorTerminos(z, "z", "Matriz Z");
		u = concatenarTerminoIndependiente(u, z);
		x = sustitucionRegresiva(u);
		if(x==null)resultado="Error: Division por cero hallando los multiplicadores";
		resultado=imprimirResultadosMatrizTermino();
		return resultado;
	}
}

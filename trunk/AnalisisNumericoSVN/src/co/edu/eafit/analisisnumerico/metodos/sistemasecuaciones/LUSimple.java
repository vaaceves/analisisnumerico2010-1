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
	public void metodoSistema() throws AnalisisException {
		recortarMatriz();
		for(int k=0;k<n-1;k++){
			gaussEnEtapa(k);
		}
		DatoMatriz[][] 	u = getU();
		DatoMatriz[][] l = getL();
		l = concatenarTerminoIndependiente(l,b);
		Termino[] z = sustitucionProgresiva(l);
		u = concatenarTerminoIndependiente(u, z);
		x = sustitucionRegresiva(u);
		
	}
	
	

}
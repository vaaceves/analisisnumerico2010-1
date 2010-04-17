package co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones;


import co.edu.eafit.analisisnumerico.framework.*;

/**
 * Metodo de la biseccion. Utiliza el punto medio para acercarse a la raiz
 * Categoria: Ecuaciones Lineales
 * * @author Sebastian
 *
 */
public class GaussSimple extends MetodoUnidad2 implements SistemaEcuacionInterfaz {

	public GaussSimple(Object[][] matriz) throws AnalisisException{
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
		for(int k=0;k<n-1;k++){
			gaussEnEtapa(k);
		}
		x=sustitucionRegresiva(matriz);
		
	}
	
	

}
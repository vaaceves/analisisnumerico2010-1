package co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones;


import co.edu.eafit.analisisnumerico.framework.*;

/**
 * Metodo de la biseccion. Utiliza el punto medio para acercarse a la raiz
 * Categoria: Ecuaciones Lineales
 * * @author Sebastian
 *
 */
public class MatrizBanda extends MetodoUnidad2 implements SistemaEcuacionInterfaz {

	public MatrizBanda(Object[][] matriz) throws AnalisisException{
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
		String resultado="";
		for(int k=0;k<n-1;k++){
			boolean ok= gaussEnEtapa(k);
			if(!ok)return "Error: Division por cero hallando los multiplicadores en etapa "+(k+1);
			adicionarMatrizImpresion(matriz, "Etapa "+(k+1));
		}
		x=sustitucionRegresiva(matriz);
		if(x==null)return "Division por cero en uno de los elementos de la diagonal";
		resultado=imprimirResultadosMatrizTermino();
		return resultado;
	}
	
	

}
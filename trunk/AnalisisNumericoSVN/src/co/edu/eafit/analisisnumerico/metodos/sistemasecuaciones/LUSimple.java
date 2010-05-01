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
		boolean validacionCero=true;
		String resultado="";
		for(int k=0;k<n-1;k++){
			validacionCero=gaussEnEtapa(k);
			adicionarMatrizImpresion(matriz, "Matriz A en etapa "+k);
		}
		DatoMatriz[][] 	u = getU();
		DatoMatriz[][] l = getL();
		l = concatenarTerminoIndependiente(l,b);
		Termino[] z = sustitucionProgresiva(l);
		u = concatenarTerminoIndependiente(u, z);
		x = sustitucionRegresiva(u);
		
		if(validacionCero==true){
			imprimirMatriz();
			resultado=imprimirResultadosMatrizTermino();
		}else{
			resultado="Error: Division por cero hallando los multiplicadores";
		}
		return resultado;
	}
}

package co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones;


import co.edu.eafit.analisisnumerico.framework.*;

/**
 * Metodo de 
 * Categoria: Ecuaciones Lineales
 * * @author Sebastian
 *
 */
public class PivoteoParcial extends MetodoUnidad2 implements SistemaEcuacionInterfaz {

	public PivoteoParcial(Object[][] matriz) throws AnalisisException{
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
			double mayor = Math.abs(matriz[k][k].getValor());
			int row = k;
			for(int i=k+1;i<n;i++){
				if(mayor<Math.abs(matriz[k][i].getValor())){
					mayor = Math.abs(matriz[k][i].getValor());
					row=i;
				}
			}
			if(mayor==0){
				throw new AnalisisException("NO SE PUEDE REALIZAR EL METODO PUES EN LA ETAPA "+k+" EL MAYOR FUE 0");
			}
			if(row!=k){
				intercambiarFilas(row,k);
			}
			gaussEnEtapa(k);
		}
		x=sustitucionRegresiva(matriz);
	}
	
	

}
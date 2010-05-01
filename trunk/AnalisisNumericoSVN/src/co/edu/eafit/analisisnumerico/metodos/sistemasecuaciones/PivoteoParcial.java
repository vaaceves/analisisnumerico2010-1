package co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones;


import co.edu.eafit.analisisnumerico.framework.*;

/**
 * Metodo de pivoteo parcial. 
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
	public String metodoSistema(double... d) throws AnalisisException {
		for(int k=0;k<n-1;k++){
			double mayor = Math.abs(matriz[k][k].getValor());
			int row = k;
			for(int i=k+1;i<n;i++){
				if(mayor<Math.abs(matriz[i][k].getValor())){
					mayor = Math.abs(matriz[i][k].getValor());
					row=i;
				}
			}
			if(mayor==0){
				return "NO SE PUEDE REALIZAR EL METODO PUES EN LA ETAPA "+k+" EL MAYOR FUE 0";
			}
			if(row!=k){
				intercambiarFilas(row,k);
			}
			boolean ok = gaussEnEtapa(k);
			if(!ok)return "division por cero en gauss. Etapa "+(k+1);
			if(row!=k){
				adicionarMatrizImpresion(matriz, "Etapa "+(k+1),"Intercambio filas "+(row+1)+","+(k+1));
			}
			else{
				adicionarMatrizImpresion(matriz, "Etapa "+(k+1));
			}
		}
		x=sustitucionRegresiva(matriz);
		if(x==null)return "Division por cero en uno de los elementos de la diagonal";
		String resultado=imprimirResultadosMatrizTermino();
		return resultado;
	}
}
package co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones;


import co.edu.eafit.analisisnumerico.framework.*;

/**
 * Metodo de 
 * Categoria: Ecuaciones Lineales
 * * @author Sebastian
 *
 */
public class PivoteoTotal extends MetodoUnidad2 implements SistemaEcuacionInterfaz {

	public PivoteoTotal(Object[][] matriz) throws AnalisisException{
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
			double mayor = 0;
			int row = k;
			int column=k;
			for(int i=k;i<n;i++){
				for(int j=k;j<n;j++){
					if(mayor<Math.abs(matriz[i][j].getValor())){
						mayor = Math.abs(matriz[i][j].getValor());
						row=i;
						column=j;
					}
				}
			}
			if(mayor==0){
				 return "NO SE PUEDE REALIZAR EL METODO PUES EN LA ETAPA "+k+" EL MAYOR FUE 0";
			}
			if(column!=k){
				System.out.println("INTERCAMBIANDO COLUMNAS: "+column+", "+k);
				intercambiarColumnas(column, k);
			}
			if(row!=k){
				System.out.println("INTERCAMBIANDO FILAS: "+row+", "+k);
				intercambiarFilas(row,k);
			}
			boolean ok = gaussEnEtapa(k);
			if(!ok) return "Division por cero en etapa "+(k+1);
			if(row!=k||column!=k){
				adicionarMatrizImpresion(matriz, "Etapa "+(k+1),"Nuevo pivote "+(row+1)+","+(column+1));
			}
			else{
				adicionarMatrizImpresion(matriz, "Etapa "+(k+1));
			}
		}
		x=sustitucionRegresiva(matriz);
		imprimirVector(x, "x");
		String resultado=imprimirResultadosMatrizTermino();
		return resultado;
	}
	
	

}
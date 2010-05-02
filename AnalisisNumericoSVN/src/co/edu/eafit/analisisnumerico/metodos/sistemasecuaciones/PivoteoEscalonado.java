package co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones;


import co.edu.eafit.analisisnumerico.framework.*;

/**
 * Metodo de pivoteo escalonado
 * Categoria: Ecuaciones Lineales
 * * @author Sebastian
 *
 */
public class PivoteoEscalonado extends MetodoUnidad2 implements SistemaEcuacionInterfaz {

	public PivoteoEscalonado(Object[][] matriz) throws AnalisisException{
		super(matriz);
	}
	
	/**
	 * Funcion Main. Ejecuta Gauss Simple
	 * 
	 */
	public static void main(String[] args){

	}

	double[] vm = null;
	
	@Override
	public String metodoSistema(double... d) throws AnalisisException {
		vm= new double[n];
		for(int i=0;i<n;i++)vm[i]=0;
		
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				if(vm[i]<Math.abs(matriz[i][j].getValor())){
					vm[i] = Math.abs(matriz[i][j].getValor());
				}
			}
		}
		for(int k=0;k<n-1;k++){
			double mayor = 0;
			int row = k;
			double[] vmm = new double[n];
			for(int i=k;i<n;i++){
				vmm[i] = Math.abs(matriz[i][k].getValor())/vm[i];
			}
			for(int i=k;i<n;i++){
				if(mayor<vmm[i]){
					mayor = vmm[i];
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
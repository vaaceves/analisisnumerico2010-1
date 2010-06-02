package co.edu.eafit.analisisnumerico.metodos.interpolacion;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.MetodoInterfaz;
import co.edu.eafit.analisisnumerico.framework.MetodoUnidad3;

public class SplainsCuadraticos extends MetodoUnidad3{

	public static void main(String[] args){
		double[][] valores = {{1,3},{3,1},{4,3.5},{5,2}};
		try {
			(new SplainsCuadraticos()).metodoInterpolacion(valores, 0);
		} catch (AnalisisException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public String metodoInterpolacion(double[][] valores,
			double valorAInterpolar, double... entradas)
			throws AnalisisException {
		int numVariables = (valores.length-1)*3;
		double[][] matrizGenerada= new double[numVariables][numVariables+1];
		//Primeras Condiciones: Los extremos
		//Extremo Inferior
		matrizGenerada[0][0]=Math.pow(valores[0][0],2);
		matrizGenerada[0][1]=valores[0][0];
		matrizGenerada[0][2]=1.0;
		matrizGenerada[0][matrizGenerada[0].length-1]=valores[0][1];
		//Extremo Superior
		matrizGenerada[1][matrizGenerada[0].length-4]=Math.pow(valores[valores.length-1][0],2);
		matrizGenerada[1][matrizGenerada[0].length-3]=valores[valores.length-1][0];
		matrizGenerada[1][matrizGenerada[0].length-2]=1.0;
		matrizGenerada[1][matrizGenerada[0].length-1]=valores[valores.length-1][1];
		//PUNTOS DE ENCUENTRO
		//puntos de encuentro de extremo inferior
		matrizGenerada[2][0]=Math.pow(valores[1][0],2);
		matrizGenerada[2][1]=valores[1][0];
		matrizGenerada[2][2]=1.0;
		matrizGenerada[2][matrizGenerada[0].length-1]=valores[1][1];
		//puntos de encuentro de extremo superior
		matrizGenerada[3][matrizGenerada[0].length-4]=Math.pow(valores[valores.length-2][0],2);
		matrizGenerada[3][matrizGenerada[0].length-3]=valores[valores.length-2][0];
		matrizGenerada[3][matrizGenerada[0].length-2]=1.0;
		matrizGenerada[3][matrizGenerada[0].length-1]=valores[valores.length-2][1];
		//CICLO QUE GENERA TODOS LOS PUNTOS DE ENCUENTRO DE LOS VALORES INTERMEDIOS
		int ecuacionEnProceso=4;
		for(int i=1;i<valores.length-2;i++){
			//por cada punto hay que hacer una valoracion en el mismo y en el siguiente
			//EN EL MISMO
			matrizGenerada[ecuacionEnProceso][i*3]=Math.pow(valores[i][0],2);
			matrizGenerada[ecuacionEnProceso][i*3+1]=valores[i][0];
			matrizGenerada[ecuacionEnProceso][i*3+2]=1.0;
			matrizGenerada[ecuacionEnProceso][matrizGenerada[0].length-1]=valores[i][1];
			ecuacionEnProceso++;
			//EN EL SIGUIENTE
			matrizGenerada[ecuacionEnProceso][i*3]=Math.pow(valores[i+1][0],2);
			matrizGenerada[ecuacionEnProceso][i*3+1]=valores[i+1][0];
			matrizGenerada[ecuacionEnProceso][i*3+2]=1.0;
			matrizGenerada[ecuacionEnProceso][matrizGenerada[0].length-1]=valores[i+1][1];
			ecuacionEnProceso++;
		}
		
		for(int i=1;i<valores.length-1;i++){
			double valorEcuacion=valores[i][0];
			matrizGenerada[ecuacionEnProceso][i*3-3]=valorEcuacion*2;
			matrizGenerada[ecuacionEnProceso][i*3+1-3]=1;
			matrizGenerada[ecuacionEnProceso][i*3+3-3]=valorEcuacion*2*-1;
			matrizGenerada[ecuacionEnProceso][i*3+4-3]=-1;
			matrizGenerada[ecuacionEnProceso][matrizGenerada[0].length-1]=0;
			ecuacionEnProceso++;
		}
		//La ultimaEcuacion
		matrizGenerada[ecuacionEnProceso][0]=1;
		
		for(int i=0;i<matrizGenerada.length;i++){
			for(int j=0;j<matrizGenerada[0].length;j++){
				System.out.print(matrizGenerada[i][j]+"\t");
			}
			System.out.println();
		}
		return "";
	}
}

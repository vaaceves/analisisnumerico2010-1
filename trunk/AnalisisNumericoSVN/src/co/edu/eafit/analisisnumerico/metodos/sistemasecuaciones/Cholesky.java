package co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.DatoMatriz;
import co.edu.eafit.analisisnumerico.framework.MetodoUnidad2;
import co.edu.eafit.analisisnumerico.framework.SistemaEcuacionInterfaz;

public class Cholesky extends MetodoUnidad2 implements SistemaEcuacionInterfaz {
	
    static boolean error = false;
	public Cholesky(Object[][] matriz) throws AnalisisException{
		super(matriz);
	}
	public String metodoSistema(double... d) throws AnalisisException {
		double[][] a = new double[matriz.length][matriz[0].length-1];
			double[] b = new double[matriz.length];
	        error = false;//booleano que permita controlar presencia de errores
	        llenarLU(n); //metodo que llena la matriz u y la matriz l con sus respectivos datos
	        //Llenar matriz a
	        for(int i=0;i<matriz.length;i++){
				for(int j=0;j<matriz[0].length-1;j++){
					a[i][j]=matriz[i][j].getValor();
				}
			}
	        //llenar vector b
	        for(int i=0;i<matriz.length;i++){
				b[i]=matriz[i][matriz.length].getValor();
			}

	        for (int k = 0; k < n; k++) {
	            // para hallar lkk y ukk
	            double suma = 0;
	            for (int p = 0; p < k; p++) {
	                suma = suma + (l[k][p] * u[p][k]);
	            }
	            if (a[k][k] - suma > 0) {//verificar que en la raiz no haya un numero negativo
	                l[k][k] = Math.sqrt(a[k][k] - suma);
	            } else {
	            	return "Error: Division por ceros";
	            }
	            u[k][k] = l[k][k];

	            //para hallar lik
	            for (int i = k + 1; i < n; i++) {
	                suma = 0;
	                for (int r = 0; r < k; r++) {
	                    suma = suma + (l[i][r] * u[r][k]);
	                }
	                if (l[k][k] != 0) {//verificar que no se divida por cero
	                    l[i][k] = (a[i][k] - suma) / l[k][k];
	                } else {
	                	return "Error: Division por ceros";
	                }
	            }

	            //para hallar ukj
	            for (int j = k + 1; j < n; j++) {
	                suma = 0;
	                for (int s = 0; s < k; s++) {
	                    suma = suma + (l[k][s] * u[s][j]);
	                }
	                if (l[k][k] != 0) {//verificar que no se divida por cero
	                    u[k][j] = (a[k][j] - suma) / l[k][k];
	                } else {
	                	return "Error: Division por ceros";
	                }
	            }
	        }


            
        	double[][] lnueva = aumentarMatriz(l,b);
        	double[] z=sustitucionProgresiva2(lnueva); // Lz=b
        	double[][] unueva = aumentarMatriz(u,z);
        	double[] x=sustitucionRegresiva2(unueva); //Ux=z
            //para mostrar resultados
            //para mostrar resultados
            String[] col = new String[n];
            Impresion.mostrarResultadoLU(lnueva, unueva, x, z, col, "Resultados Dolytle", n);
            String resultado=imprimirResultadosMatrizTermino();
            return resultado;
	    
	}
	
}

package co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
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
	        inicializarMatricesLU(n);
	        //Llenar matriz a
	        for(int i=0;i<matriz.length;i++){
				for(int j=0;j<matriz[0].length-1;j++){
					a[i][j]=matriz[i][j].getValor();
				}
			}
	        for(int i=0;i<matriz.length;i++){
				b[i]=matriz[i][matriz.length].getValor();
			}
	        recortarMatriz();
	        for (int k = 0; k < n; k++) {
	            double suma = 0;
	            for (int p = 0; p < k; p++) {
	                suma = suma + (l[k][p] * u[p][k]);
	            }
	            if (a[k][k] - suma > 0) {
	                l[k][k] = Math.sqrt(a[k][k] - suma);
	            } else {
	            	return "Error: Division por ceros";
	            }
	            u[k][k] = l[k][k];
	            for (int i = k + 1; i < n; i++) {
	                suma = 0;
	                for (int r = 0; r < k; r++) {
	                    suma = suma + (l[i][r] * u[r][k]);
	                }
	                if (l[k][k] != 0) {
	                    l[i][k] = (a[i][k] - suma) / l[k][k];
	                } else {
	                	return "Error: Division por ceros";
	                }
	            }
	            //ukj
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
        	double[][] lnueva = adicionarColumna(l,b);
        	double[] z=sustitucionProgresiva2(lnueva); // Lz=b
        	double[][] unueva = adicionarColumna(u,z);
        	double[] x=sustitucionRegresiva2(unueva); //Ux=z
        	adicionarMatrizImpresion(l, "Matriz L");
        	adicionarMatrizImpresion(u, "Matriz U");
        	adicionarVectorTerminos(z, "Z", "Vector Z");
            String resultado=imprimirResultadosMatrizTermino(x);
            return resultado;
	    
	}
	
}

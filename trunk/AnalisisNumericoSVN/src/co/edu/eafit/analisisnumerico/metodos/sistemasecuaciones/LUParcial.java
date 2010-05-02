package co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones;

import java.util.Scanner;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.DatoMatriz;
import co.edu.eafit.analisisnumerico.framework.MetodoUnidad2;
import co.edu.eafit.analisisnumerico.framework.SistemaEcuacionInterfaz;
import co.edu.eafit.analisisnumerico.framework.Termino;

public class LUParcial extends MetodoUnidad2 implements SistemaEcuacionInterfaz  {

	public LUParcial(Object[][] matriz) throws AnalisisException {
		super(matriz);
	}
	

	@Override
	public String metodoSistema(double... d) throws AnalisisException {
		m = new int[matriz.length];
		for(int i=0;i<m.length;i++){
			m[i]=i;
		}
		recortarMatriz();
		int pos;
		boolean validacionCero=true;
		String resultado="";
		for(int k=0;k<n-1;k++){
			double mayor = Math.abs(matriz[k][k].getValor());
			pos = k;
			for(int i=k+1;i<n;i++){
				if(mayor<Math.abs(matriz[i][k].getValor())){
					mayor = Math.abs(matriz[i][k].getValor());
					pos = i;
				}
			}
			if(mayor==0){
				return "NO SE PUEDE REALIZAR EL METODO PUES EN LA ETAPA "+k+" EL MAYOR FUE 0";
			}
			if (pos!=k){
				intercambiarFilas(pos,k);
				m[k]=pos;
			}
			boolean ok = gaussEnEtapa(k);
			if(!ok) return "Error. Division por cero en gauss";
			adicionarMatrizImpresion(matriz, "Matriz A en etapa "+k);
		}

		DatoMatriz[][] 	u = getU();
		DatoMatriz[][] l = getL();
		adicionarMatrizImpresion(u, "Matriz U:");
		adicionarMatrizImpresion(l, "Matriz L:");
		DatoMatriz [] b_new = b;
		for(int k=0;k<n-1;k++){
			if (m[k]!=k)
			{
				intercambiarFilasB(m[k],k);
			}
			gaussEnB(k);
		}
		DatoMatriz[]z=b;
		u = concatenarTerminoIndependiente(u, z);
		x = sustitucionRegresiva(u);
		if(x!=null){
			resultado=imprimirResultadosMatrizTermino();
		}else{
			resultado="Error: Division por cero hallando los multiplicadores";
		}
		return resultado;

	}
}

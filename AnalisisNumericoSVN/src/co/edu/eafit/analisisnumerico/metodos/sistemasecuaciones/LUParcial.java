package co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones;

import java.util.Scanner;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.DatoMatriz;
import co.edu.eafit.analisisnumerico.framework.MetodoUnidad2;
import co.edu.eafit.analisisnumerico.framework.SistemaEcuacionInterfaz;
import co.edu.eafit.analisisnumerico.framework.Termino;

public class LUParcial extends MetodoUnidad2 implements SistemaEcuacionInterfaz  {


	static int tamano=4;
	static Scanner scan = null;
	static LUParcial lup;
	
	public LUParcial(Object[][] matriz) throws AnalisisException {
		super(matriz);
	}
	public static void main (String [] args)
	{
		Object [][] val=new Object [tamano][tamano+1];
		val[0][0]=-7.0;
		val[0][1]=2.0;
		val[0][2]=-3.0;
		val[0][3]=4.0;
		val[0][4]=-12.0;
		val[1][0]=5.0;
		val[1][1]=-1.0;
		val[1][2]=14.0;
		val[1][3]=-1.0;
		val[1][4]=13.0;
		val[2][0]=1.0;
		val[2][1]=9.0;
		val[2][2]=-7.0;
		val[2][3]=5.0;
		val[2][4]=31.0;
		val[3][0]=-12.0;
		val[3][1]=13.0;
		val[3][2]=-8.0;
		val[3][3]=-4.0;
		val[3][4]=-32.0;
		try {
			lup = new LUParcial(val);
			lup.metodoSistema();
		} catch (AnalisisException e) {
			e.printStackTrace();
		}

	}
	

	@Override
	public void metodoSistema() throws AnalisisException {
		recortarMatriz();
		int pos;
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
				throw new AnalisisException("NO SE PUEDE REALIZAR EL METODO PUES EN LA ETAPA "+k+" EL MAYOR FUE 0");
			}
			if (pos!=k){
				intercambiarFilas(pos,k);
				m[k]=pos;
			}
			gaussEnEtapa(k);
		}

		DatoMatriz[][] 	u = getU();
		DatoMatriz[][] l = getL();
		DatoMatriz [] b_new = b;
		for(int k=0;k<n-1;k++){
			if (m[k]!=k)
			{
				intercambiarFilasB(m[k],k);
			}
			gaussEnB(k);
		}
		DatoMatriz[]z=b;
		
		for (int i=0;i<z.length;i++)
		{
			System.out.println("Z "+i+": "+z[i].getValor());
		}
		//l = concatenarTerminoIndependiente(l,b); 
		//Termino[] z = sustitucionProgresiva(l);
		u = concatenarTerminoIndependiente(u, z);
		x = sustitucionRegresiva(u);
		imprimir(x);

	}

	public static void imprimir(Termino[] x)
	{
		for(int i=0;i<x.length;i++)
		{
			System.out.println("-------------->x"+i+": "+x[i].getValor());
		}


	}

}

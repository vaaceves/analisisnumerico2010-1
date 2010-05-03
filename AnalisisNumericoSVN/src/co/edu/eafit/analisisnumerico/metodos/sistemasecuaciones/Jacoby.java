package co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones;

import java.util.Scanner;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.MetodoUnidad2;
import co.edu.eafit.analisisnumerico.framework.SistemaEcuacionInterfaz;
import co.edu.eafit.analisisnumerico.framework.UtilConsola;

public class Jacoby extends MetodoUnidad2 implements SistemaEcuacionInterfaz  {

	
	static int tamano=3;
	static Scanner scan = null;
	static Jacoby jac;
	
	public Jacoby(Object[][] matriz) throws AnalisisException {
		super(matriz);
	}
	
	public static void main (String [] args)
	{
		Object [][] val=new Object [tamano][tamano+1];
		val[0][0]=31.0;
		val[0][1]=-6.0;
		val[0][2]=4.0;
		val[0][3]=10.0;
		
		val[1][0]=5.0;
		val[1][1]=18.0;
		val[1][2]=-3.0;
		val[1][3]=21.0;
		
		val[2][0]=3.0;
		val[2][1]=-4.0;
		val[2][2]=31.0;
		val[2][3]=30.0;

		try {
			jac = new Jacoby(val);
			jac.metodoSistema();
		} catch (AnalisisException e) {
			e.printStackTrace();
		}
	}

	public void metodoSistema() throws AnalisisException {
		double tolerancia=UtilConsola.getTolerancia(5); //Tolerancia maxima
		double cont = 0;
		double error = tolerancia+1;
		double iter =20; //Numero de iteraciones maximas
		double suma; 
		double ini [] = {1,1,1}; //Vector de valores iniciales
		double respuesta[] = new double [ini.length];
		double divisor=-1;

		while (error>tolerancia&&cont<=iter&&divisor!=0)
		{
			for (int i=0;i<n;i++)
			{
				divisor=matriz[i][i].getValor();
				suma = 0;
				for (int j=0;j<n;j++)
				{
					if (i!=j)
					{
						suma+=(matriz[i][j].getValor()*ini[j]);
					}
				}
				respuesta[i]=((b[i].getValor()-suma)/matriz[i][i].getValor());
			}
			
			error=Math.max(Math.abs(respuesta[0]-ini[0]),Math.abs(respuesta[1]-ini[1])); //Error con la norma
			error=Math.max(error,Math.abs(respuesta[2]-ini[2])); //Error con la norma
			for (int i=0;i<n;i++)
			{
				System.out.println("-------------->x"+i+": "+respuesta[i]);
				ini[i]=respuesta[i];
			}
			cont++;

		}
		if(error<tolerancia)
		{
			System.out.println("Solución aproximada");
		}
		else if (cont>iter){
			System.out.println("sobrepaso iteraciones");
		}
		else if (divisor==0)
		{
			System.out.println("Division por cero");
		}
	}

}

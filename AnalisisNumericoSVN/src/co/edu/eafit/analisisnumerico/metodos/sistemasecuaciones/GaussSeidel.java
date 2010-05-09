package co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones;

import co.edu.eafit.analisisnumerico.framework.AnalisisException;
import co.edu.eafit.analisisnumerico.framework.MetodoUnidad2;
import co.edu.eafit.analisisnumerico.framework.SistemaEcuacionInterfaz;
import co.edu.eafit.analisisnumerico.framework.UtilConsola;

public class GaussSeidel extends MetodoUnidad2 implements SistemaEcuacionInterfaz  {

	public GaussSeidel(Object[][] matriz) throws AnalisisException {
		super(matriz);
	}

	@Override
	public String metodoSistema(double... d) throws AnalisisException {
		double tolerancia=UtilConsola.getTolerancia(d[0]); //Tolerancia maxima
		double cont = 0;
		double error = tolerancia+1;
		double iter =d[1]; //Numero de iteraciones maximas
		double suma; 
		double error_inmediato;
		double ini [] = new double[d.length-2];
		for(int i=2;i<d.length;i++){
			ini[i-2]=d[i];
		}
		double respuesta[] = new double [ini.length];
		String resultado="";
		while (error>tolerancia&&cont<=iter)
		{
			error=0;
			for (int i=0;i<n;i++)
			{
				suma = 0;
				for (int j=0;j<n;j++)
				{
					if (i!=j)
					{
						suma+=(matriz[i][j].getValor()*ini[j]);
					}
				}
				if(matriz[i][i].getValor()==0){
					return "Division por cero";
				}
				respuesta[i]=((b[i].getValor()-suma)/matriz[i][i].getValor());
				error_inmediato=Math.abs(respuesta[i]-ini[i]);
				error=Math.max(error, error_inmediato);
				//System.out.println("Error: "+error);
				ini[i]=respuesta[i];
			}
			if(cont==0){
				String[] titulos = new String[respuesta.length+1];
				for(int i=0;i<titulos.length-1;i++){
					titulos[i]="X"+(i+1);
				}
				titulos[respuesta.length]="Error";
				adicionarVectorImpresion(respuesta, error, titulos);
			}
			else{
				adicionarVectorImpresion(respuesta, error);
			}
			cont++;
		}
		if(error<tolerancia)
		{
			resultado=imprimirResultadosMatrizTermino(respuesta);
		}
		else if (cont>iter){
			resultado="sobrepaso iteraciones";
		}
		return resultado;
	}



}
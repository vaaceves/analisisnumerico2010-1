package co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones;


import co.edu.eafit.analisisnumerico.framework.*;


/**
 * Metodo de Matriz Banda. Solo para matrices banda tridiagonales
 * Categoria: Sistemas de ecuaciones
 * * @author Numerico
 *
 */
public class MatrizBanda extends MetodoUnidad2 implements SistemaEcuacionInterfaz {

	public MatrizBanda(Object[][] matriz) throws AnalisisException{
		super(matriz);
	}


	@Override
	public String metodoSistema(double... d) throws AnalisisException {
		double respuesta[] = new double [n];
		double a_banda[] = new double [n];
		double b_banda[] = new double [n-1];
		double c_banda[] = new double [n-1];

		double m_banda[] = new double [n];
		double x_banda[] = new double [n];

		for(int y=0;y<n;y++)
		{
			respuesta[y]=b[y].getValor();
			a_banda[y]=matriz[y][y].getValor();
		}

		for (int k=0;k<n-1;k++)
		{
			b_banda[k]=matriz[k][k+1].getValor();
			c_banda[k]=matriz[k+1][k].getValor();
			if(a_banda[k]==0)return "division por cero. Error";
			m_banda[k]=c_banda[k]/a_banda[k];
			a_banda[k+1]=a_banda[k+1]-(m_banda[k]*b_banda[k]);
			respuesta[k+1]=respuesta[k+1]-(m_banda[k]*respuesta[k]);
		}
		
		for(int i=n-1;i>=0;i--){
			if(a_banda[i]==0){
				return "Division por cero. Error";
			}
			if(i!=n-1){
				x_banda[i]=(respuesta[i]-b_banda[i]*x_banda[i+1])/a_banda[i];
			}
			else{
				x_banda[i]=respuesta[i]/a_banda[i];
			}
		}
		String resultado=imprimirResultadosMatrizTermino(x_banda);
		return resultado;
	}
}




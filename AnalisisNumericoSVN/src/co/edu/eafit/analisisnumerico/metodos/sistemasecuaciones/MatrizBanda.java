package co.edu.eafit.analisisnumerico.metodos.sistemasecuaciones;


import co.edu.eafit.analisisnumerico.framework.*;


/**
 * Metodo de Matriz Banda. Solo para matrices banda tridiagonales
 * Categoria: Sistemas de ecuaciones
 * * @author Numerico
 *
 */
public class MatrizBanda extends MetodoUnidad2 implements SistemaEcuacionInterfaz {

	static int tamano=4;
	static MatrizBanda mBanda;

	public MatrizBanda(Object[][] matriz) throws AnalisisException{
		super(matriz);
	}

	/**
	 * Funcion Main. Ejecuta Matriz Banda
	 * 
	 */
	public static void main(String[] args){
		Object [][] val=new Object [tamano][tamano+1];
		val[0][0]=4.0;
		val[0][1]=6.0;
		val[0][2]=0.0;
		val[0][3]=0.0;
		val[0][4]=7.0;

		val[1][0]=1.0;
		val[1][1]=2.0;
		val[1][2]=9.0;
		val[1][3]=0.0;
		val[1][4]=4.0;

		val[2][0]=0.0;
		val[2][1]=7.0;
		val[2][2]=4.0;
		val[2][3]=2.0;
		val[2][4]=6.0;

		val[3][0]=0.0;
		val[3][1]=0.0;
		val[3][2]=5.0;
		val[3][3]=3.0;
		val[3][4]=1.0;

		try {
			mBanda = new MatrizBanda(val);
			mBanda.metodoSistema();
		} catch (AnalisisException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String metodoSistema(double... d) throws AnalisisException {
		//		String resultado="";
		//		for(int k=0;k<n-1;k++){
		//			boolean ok= gaussEnEtapa(k);
		//			if(!ok)return "Error: Division por cero hallando los multiplicadores en etapa "+(k+1);
		//			adicionarMatrizImpresion(matriz, "Etapa "+(k+1));
		//		}
		//		x=sustitucionRegresiva(matriz);
		//		if(x==null)return "Division por cero en uno de los elementos de la diagonal";
		//		resultado=imprimirResultadosMatrizTermino();
		//		return resultado;
		String solucion=null;
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

			m_banda[k]=c_banda[k]/a_banda[k];
			a_banda[k+1]=a_banda[k+1]-(m_banda[k]*b_banda[k]);
			respuesta[k+1]=respuesta[k+1]-(m_banda[k]*respuesta[k]);
		}

		for(int i=n-1;i>=0;i--){
			if(i!=n-1){
				x_banda[i]=(respuesta[i]-b_banda[i]*x_banda[i+1])/a_banda[i];
			}
			else{
				x_banda[i]=respuesta[i]/a_banda[i];
			}
		}
		for (int i=0;i<n;i++)
		{
			System.out.println("-------------->x"+i+": "+x_banda[i]);
		}
	}

}




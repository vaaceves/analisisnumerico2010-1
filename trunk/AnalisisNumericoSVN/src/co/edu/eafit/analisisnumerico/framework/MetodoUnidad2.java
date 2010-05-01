package co.edu.eafit.analisisnumerico.framework;

import java.text.DecimalFormat;
import java.util.Vector;

public abstract class MetodoUnidad2 implements SistemaEcuacionInterfaz{

	/**
	 * formato para las X 
	 */
	static private final DecimalFormat xFormat = new DecimalFormat("000.#####");

	/**
	 * Formato para los errores y funciones
	 */
	static private final DecimalFormat eFormat = new DecimalFormat("0.##E00");

	double[][] matrizMultiplicadores;
	public Termino[] x;
	protected DatoMatriz[][] matriz;
	public int n;
	public DatoMatriz[] b;
	public int[] m; //Vector de marcas necesario para LUParcial.

	public Vector<Vector<String>> datos;

	public MetodoUnidad2(Object[][] valores) throws AnalisisException{
		matriz= new DatoMatriz[valores.length][valores[0].length];
		x= new Termino[valores.length];
		matrizMultiplicadores= new double[valores.length][valores.length];
		n= matriz.length;
		for(int i=0;i<matriz.length;i++){
			matrizMultiplicadores[i][i]=1.0;
			for(int j=0;j<matriz[0].length;j++){
				matriz[i][j] = new DatoMatriz();
				matriz[i][j].setFila(i);
				matriz[i][j].setColumna(j);
				matriz[i][j].setMarca(j);
				try{
					matriz[i][j].setValor(Double.parseDouble(valores[i][j].toString()));
				}catch(NumberFormatException ex){
					throw new AnalisisException("VALOR INVALIDO EN LA POSICION "+i+","+j);
				}

			}
		}
		b= new DatoMatriz[valores.length];
		for(int i=0;i<b.length;i++){
			b[i] = matriz[i][matriz[0].length-1];
		}
	}


	/**
	 * 
	 * @param fila: la fila de resultado a añadir a la tabla
	 */
	public void adicionarMatrizImpresion(DatoMatriz[][] matriz, String... titulo){
		if(datos==null){
			datos = new Vector<Vector<String>>();
			for(int i=0;i<matriz[0].length;i++){
				datos.add(new Vector<String>());
			}
		}
		try{
			int i=0;
			for(i=0;i<titulo.length;i++){
				datos.get(i).add(titulo[i]);
			}
			for(;i<datos.size();i++){
				datos.get(i).add("");
			}
			for(int k=0;k<matriz.length;k++){
				for(int j=0;j<matriz[0].length;j++){
					datos.get(j).add(String.valueOf(matriz[k][j].getValor()));
				}
			}
		}catch(Exception e){
			new AnalisisException("Error de programacion. Numero invalido de filas en la tabla de resultados");
		}
	}

	public void adicionarVectorImpresion(double[] resultado){
		if(datos==null){
			datos = new Vector<Vector<String>>();
			for(int i=0;i<matriz[0].length;i++){
				datos.add(new Vector<String>());
			}
		}
		try{
			for(int i=0;i<resultado.length;i++){
				datos.get(i).add(String.valueOf(resultado[i]));

			}
		}catch(Exception e){
			new AnalisisException("Error de programacion. Numero invalido de filas en la tabla de resultados");
		}
	}
	//	public void imprimirMatriz(){
	//		for(int i=0;i<datos.get(0).size();i++){
	//			for(int j=0;j<datos.size();j++){
	//				System.out.println(datos.get(i).get(j));
	//			}
	//			System.out.println();
	//		}
	//	}



	public boolean addMultiplicador(int fila, int columna){
		if(matriz[columna][columna].getValor()!=0){
			matrizMultiplicadores[fila][columna]= matriz[fila][columna].getValor()/
			matriz[columna][columna].getValor();
			return true;
		}else{
			return false;
		}


	}

	//hace gauss sobre el vector B
	public void gaussEnB(int k)
	{
		for(int i=k+1;i<n;i++){
			b[i].setValor(b[i].getValor()-getMultiplicador(i,k)*b[k].getValor());
		}
	}


	//intercambia las filas del vector B teniendo en cuenta el vector de marcas
	public void intercambiarFilasB(int row, int k)
	{
		DatoMatriz dAuxiliar = b[k];
		b[k] = b[row];
		b[row] = dAuxiliar;
	}


	public double getMultiplicador(int fila, int columna){
		return matrizMultiplicadores[fila][columna];
	}

	/**
	 * validar si los elementos de la diagonal son diferentes de 0. 
	 * Si es 0 retorna falso
	 */
	public boolean validarDiagonal(DatoMatriz[][] matriz){
		for(int i=0;i<matriz.length;i++){
			for(int j=0;j<matriz[0].length;j++){
				if(i==j && matriz[i][j].getValor()==0){
					return false;
				}
			}
		}
		return true;
	}

	public Termino[] sustitucionRegresiva(DatoMatriz[][] matriz){
		if(!validarDiagonal(matriz))return null;
		Termino[] x= new Termino[this.x.length];
		for(int i=n-1;i>=0;i--){
			double suma =0;
			for(int j=i+1;j<n;j++){
				suma+=matriz[i][j].getValor()*x[j].getValor();
			}
			x[i] = new Termino();
			x[i].setMarca(matriz[i][i].getMarca());
			x[i].setValor((matriz[i][n].getValor()-suma)
					/matriz[i][i].getValor());
		}
		return x;
	}


	public Termino[] sustitucionProgresiva(DatoMatriz[][] matriz){
		if(!validarDiagonal(matriz))return null;
		Termino[] x= new Termino[this.x.length];
		for(int i=0;i<n;i++){
			double suma =0;
			for(int j=i-1;j>=0;j--){
				suma+=matriz[i][j].getValor()*x[j].getValor();
			}
			x[matriz[i][i].getMarca()] = new Termino();
			x[matriz[i][i].getMarca()].setMarca(matriz[i][i].getMarca());
			x[matriz[i][i].getMarca()].setValor((matriz[i][n].getValor()-suma)
					/matriz[i][i].getValor());
		}
		return x;
	}

	public DatoMatriz[][] getU(){
		DatoMatriz[][] u = new DatoMatriz[matriz.length][matriz.length];
		for(int i=0;i<matriz.length;i++){
			for(int j=0;j<matriz.length;j++){
				if(i>j)continue;
				else u[i][j]=matriz[i][j];
			}	
		}
		return u;
	}

	public DatoMatriz[][] getL(){
		DatoMatriz[][] l = new DatoMatriz[matrizMultiplicadores.length][matrizMultiplicadores.length];
		for(int i=0;i<matrizMultiplicadores.length;i++){
			for(int j=0;j<matrizMultiplicadores.length;j++){
				l[i][j] = new DatoMatriz();
				l[i][j].setColumna(j);
				l[i][j].setFila(i);
				l[i][j].setMarca(j);
				l[i][j].setValor(matrizMultiplicadores[i][j]);
			}
		}
		return l;
	}

	public DatoMatriz[][] concatenarTerminoIndependiente(DatoMatriz[][] matriz, Object[] terminoIndependiente){
		DatoMatriz[][] resul = new DatoMatriz[matriz.length][matriz.length+1];
		for(int i=0;i<resul.length;i++){
			for(int j=0;j<resul.length;j++){
				resul[i][j] = matriz[i][j];
			}
		}
		for(int i=0;i<resul.length;i++){
			if(terminoIndependiente[i] instanceof DatoMatriz){
				resul[i][matriz.length]=(DatoMatriz)terminoIndependiente[i];
			}
			else{
				Termino t = (Termino)terminoIndependiente[i];
				DatoMatriz d = new DatoMatriz();
				d.setFila(i);
				d.setColumna(matriz.length);
				d.setMarca(t.getMarca());
				d.setValor(t.getValor());
				resul[i][matriz.length]=d;
			}


		}
		return resul;
	}

	public void recortarMatriz(){
		DatoMatriz[][] nuevaMatriz = new DatoMatriz[matriz.length][matriz.length];
		for(int i=0;i<nuevaMatriz.length;i++){
			for(int j=0;j<nuevaMatriz.length;j++){
				nuevaMatriz[i][j] = matriz[i][j];
			}
		}
		matriz=nuevaMatriz;
	}

	public boolean gaussEnEtapa(int k){
		boolean validacionCero=true;
		for(int i=k+1;i<n;i++){
			validacionCero=addMultiplicador(i, k);
			if(!validacionCero)return false;
			for(int j=k;j<matriz[0].length;j++){
				matriz[i][j].setValor(matriz[i][j].getValor()-(getMultiplicador(i,k)
						*matriz[k][j].getValor()));
			}
		}
		return true;
	}

	public void intercambiarFilas(int row, int k){
		for(int i=k;i<matriz[0].length;i++){
			DatoMatriz dAuxiliar = matriz[k][i];
			matriz[k][i] = matriz[row][i];
			matriz[row][i] = dAuxiliar;
		}
	}

	public void intercambiarColumnas(int column, int k){
		for(int i=0;i<matriz.length;i++){
			DatoMatriz dAuxiliar = matriz[i][k];
			matriz[i][k] = matriz[i][column];
			matriz[i][column] = dAuxiliar;
		}
	}

	public String xFormat(double valor){
		return xFormat.format(valor);
	}

	public String eFormat(double valor){
		return eFormat.format(valor);
	}

	public void imprimirReducido(int k, int metodo){
		System.out.println("Etapa: "+k);
		System.out.println("IMPRIMIENDO A:");
		for(int i=0;i<matriz[0].length;i++){
			System.out.print("X"+matriz[0][i].getMarca()+"\t");
		}
		System.out.println();
		for(int i=0;i<matriz.length;i++){
			for(int j=0;j<matriz[0].length;j++){
				System.out.print(eFormat(matriz[i][j].getValor())+"\t");
			}
			System.out.println("");
		}
		System.out.println();
		System.out.println("IMPRIMIENTO L");
		for(int i=0;i<matrizMultiplicadores.length;i++){
			for(int j=0;j<matrizMultiplicadores[0].length;j++){
				System.out.print(eFormat(matrizMultiplicadores[i][j])+"\t");
			}
			System.out.println("\n");
		}
		System.out.println();
	}

	public void imprimirCompleto(int k, int metodo){
		System.out.println("Etapa: "+k);
		System.out.println("IMPRIMIENDO A:");
		for(int i=0;i<matriz[0].length;i++){
			System.out.print("X"+matriz[0][i].getMarca()+"\t");
		}
		System.out.println();
		for(int i=0;i<matriz.length;i++){
			for(int j=0;j<matriz[0].length;j++){
				System.out.print(matriz[i][j].getValor()+"\t");
			}
			System.out.println("");
		}
		System.out.println();
		System.out.println("IMPRIMIENTO L");
		for(int i=0;i<matrizMultiplicadores.length;i++){
			for(int j=0;j<matrizMultiplicadores[0].length;j++){
				System.out.print(matrizMultiplicadores[i][j]+"\t");
			}
			System.out.println("\n");
		}
		System.out.println();
	}

	public void imprimirVector(Termino[] terminos, String variable){
		System.out.println("Valores "+variable);
		for(Termino t :  terminos){
			System.out.print(variable+t.getMarca()+"= "+t.getValor());
		}
		System.out.println();
	}

	public String imprimirResultadosMatrizTermino(){
		String resultado="";
		for(int i=0;i<x.length;i++){
			resultado+= "X"+x[i].getMarca()+"="+x[i].getValor()+", ";
		}
		return resultado;
	}

	public Object[][] generarMatrizSinTitulos(){
		if(datos==null||datos.size()==0)return new Object[0][0];
		Object[][] miMatriz = new Object[datos.get(0).size()][datos.size()];
		DecimalFormat decimalFormat = new DecimalFormat("0.####E00");
		//adiciono el resto
		for(int i=0;i<miMatriz.length;i++){
			for(int j=0;j<miMatriz[0].length;j++){
					miMatriz[i][j]=datos.get(j).get(i);
			}
		}
		return miMatriz;
	}
}

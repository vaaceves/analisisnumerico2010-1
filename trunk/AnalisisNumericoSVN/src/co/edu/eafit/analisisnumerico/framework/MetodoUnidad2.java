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


	public void addMultiplicador(int fila, int columna){
		matrizMultiplicadores[fila][columna]= matriz[fila][columna].getValor()/
		matriz[columna][columna].getValor();
	}

	public double getMultiplicador(int fila, int columna){
		return matrizMultiplicadores[fila][columna];
	}

	public Termino[] sustitucionRegresiva(DatoMatriz[][] matriz){
		Termino[] x= new Termino[this.x.length];
		for(int i=n-1;i>=0;i--){
			double suma =0;
			for(int j=i+1;j<n;j++){
				suma+=matriz[i][j].getValor()*x[j].getValor();
			}
			x[matriz[i][i].getMarca()] = new Termino();
			x[matriz[i][i].getMarca()].setMarca(matriz[i][i].getMarca());
			x[matriz[i][i].getMarca()].setValor((matriz[i][n].getValor()-suma)
					/matriz[i][i].getValor());
		}
		return x;
	}

	public Termino[] sustitucionProgresiva(DatoMatriz[][] matriz){
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
	
	public void gaussEnEtapa(int k){
		for(int i=k+1;i<n;i++){
			addMultiplicador(i, k);
			for(int j=k;j<matriz[0].length;j++){
				matriz[i][j].setValor(matriz[i][j].getValor()-(getMultiplicador(i,k)
						*matriz[k][j].getValor()));
			}
		}
	}

	public void intercambiarFilas(int row, int k){
		for(int i=k;i<matriz[0].length;i++){
			DatoMatriz dAuxiliar = matriz[k][i];
			matriz[k][i] = matriz[row][i];
			matriz[row][i] = dAuxiliar;
		}
	}
}

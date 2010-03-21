package co.edu.eafit.analisisnumerico.framework;

import java.util.Vector;


/**
 * Funcion que debe ser padre de todos los metodos a implementar
 * Gestiona los resultados, y posee la funcion a evaluar
 * @author Sebastian Velez
 * 
 */
public abstract class MetodoPadre implements MetodoInterfaz{


	private Vector<Vector<Double>> datos;
	private Vector<String> titulos;

	/**
	 * 
	 * @param fila: la fila de resultado a añadir a la tabla
	 */
	public void adicionarFilaResultados(Double... fila){
		if(datos==null){
			datos = new Vector<Vector<Double>>();
			for(int i=0;i<fila.length;i++){
				datos.add(new Vector<Double>());
			}
		}
		try{
			int cont=0;
			for(Double miDato : fila){
				(datos.get(cont)).add(miDato);
				cont++;
			}
		}catch(Exception e){
			new AnalisisException("Error de programacion. Numero invalido de filas en la tabla de resultados");
		}
	}
	/**
	 * Adiciona los titulos a mostrar
	 * @param titulos: lista de titulos
	 * @throws AnalisisException
	 */
	public void adicionarFilaTitulos(String... titulos){
		this.titulos = new Vector<String>();
		for(String s: titulos){
			this.titulos.add(s);
		}
	}

	/**
	 * Funcion que retorna una matriz de objetos para el objeto JTable.
	 * Debe ejecutarse solo al final
	 * @return matriz 
	 */
	public Object[][] generarMatriz(){
		Object[][] matriz = new Object[datos.get(0).size()+1][datos.size()];
		//adiciono titulos
		for(int i=0;i<titulos.size();i++){
			matriz[0][i]=titulos.get(i);
		}
		//adiciono el resto
		for(int i=1;i<matriz.length;i++){
			for(int j=0;j<matriz[0].length;j++){
				matriz[i][j]=datos.get(j).get(i-1);
			}
		}
		return matriz;
	}
	
	/**
	 * Funcion a evaluar en un valor
	 * @param declaracionFuncion -> cuando tengamos el parser, es un string con la funcion.
	 * @param valorVariables -> valor de las n variables en orden (x,y,z)
	 * @return la funcion evaluada en esos valores
	 */
	public double f(String declaracionFuncion, double... valorVariables){
		double x= valorVariables[0]; 
		//return Math.pow(Math.E, (Math.pow(x, 2)*-1)+1)-x*Math.sin(2*x+3)-3;
		//return Math.log(Math.pow(x, 2)+3)-6*Math.pow(x, 2)+x*Math.cos(x)-3; // Ln((x^2)+3)-6x^2+xcos(x)-3 FUNCION 2
		return Math.sin(x)-Math.exp(-x); //sen(x)-e^(-x)FUNCION 3
	}
	
	public double fdev(String declaracionFuncion, double... valorVariables){ //Derivada de la funcion
		double x= valorVariables[0]; 
		//return ((2*x)*-1)*Math.pow(Math.E, (Math.pow(x, 2)*-1)+1)-Math.sin(2*x+3)+(2*x)*Math.cos(2*x+3); // -2x*e^(-(x^2)+1)-sen(2x+3)+2xcos(2x+3) FUNCION 1
		//return ((2*x)/(Math.pow(x, 2)+3))-12*x+Math.cos(x)-x*Math.sin(x); // (2x/(x^2)+3) - 12x + cos(x) - xsen(x) FUNCION 2
		return Math.cos(x)+Math.exp(-x); //cos(x)+e^(-x) FUNCION 3
	}
	
	public double fdd(String declaracionFuncion, double... valorVariables){ //Segunda derivada de la funcion
		double x= valorVariables[0]; 
		//return (-2)*Math.pow(Math.E, (Math.pow(x, 2)*-1)+1)+(4*x)*Math.pow(Math.E, (Math.pow(x, 2)*-1)+1)-2*Math.sin(2*x+3)+(2)*Math.cos(2*x+3)+2*x+2*Math.sin(2*x+3); // -2*e^(-(x^2)+1)+4x*e^(-(x^2)+1)-2cos(2x+3)+(2*cos(2x+3)+2x+2(-sen(2x+3))) FUNCION 1
		return -Math.sin(x)-Math.exp(-x); // -sen(x)-e^(-x) FUNCION 3
	}
	
	
	/**
	 * Evalua con la funcion predeterminada, declarada en f(String, double[])
	 * No recibe otra funcion
	 * @param valorVariables -> las n variables a evaluar
	 * @return el valor evaluado en esa funcion
	 */
	public double f(double... valorVariables){
		return f("", valorVariables);
	}
	
	public double fdev(double... valorVariables){ //Derivada de la funcion
		return fdev("", valorVariables);
	}
	
	public double fdd(double... valorVariables){ //Segunda derivada de la funcion
		return fdd("", valorVariables);
	}
	
	/**
	 * Evalua con la funcion G predeterminada, declarada en f(String, double[])
	 * No recibe otra funcion
	 * @param valorVariables -> las n variables a evaluar
	 * @return el valor evaluado en esa funcion
	 */	
	public double g(double... valorVariables){
		return f("", valorVariables);
	}
	



}

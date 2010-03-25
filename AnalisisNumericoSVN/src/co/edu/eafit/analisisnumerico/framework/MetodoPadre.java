package co.edu.eafit.analisisnumerico.framework;

import java.util.Vector;

import org.nfunk.jep.JEP;


/**
 * Funcion que debe ser padre de todos los metodos a implementar
 * Gestiona los resultados, y posee la funcion a evaluar
 * @author Sebastian Velez
 * 
 */
public abstract class MetodoPadre implements MetodoInterfaz{

	private JEP parserF;
	private JEP parserG;
	private JEP parserFdev;
	private JEP parserFdd;
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
		if(datos==null||datos.size()==0)return new Object[0][titulos.size()];
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
	
	public Object[][] generarMatrizSinTitulos(){
		if(datos==null||datos.size()==0)return new Object[0][titulos.size()];
		Object[][] matriz = new Object[datos.get(0).size()][datos.size()];
		//adiciono el resto
		for(int i=0;i<matriz.length;i++){
			for(int j=0;j<matriz[0].length;j++){
				matriz[i][j]=datos.get(j).get(i);
			}
		}
		return matriz;
	}
	
	public Object[] getTitulos(){return titulos.toArray();}
	
	/**
	 * Evalua la funcion ya asignada en los valores dados
	 * No recibe otra funcion
	 * @param valorVariables -> las n variables a evaluar
	 * @return el valor evaluado en esa funcion
	 */
	public double f(double... valorVariables) throws AnalisisException{
		if(parserF==null){
			throw new AnalisisException("no se ha inicializado el parser");
		}
		parserF.setVarValue("x", valorVariables[0]);
		if(parserF.hasError()){
			throw new AnalisisException("Error asignando el valor al parser");
		}
		return parserF.getValue();
	}
	
	/**
	 * Evalua la funcion ya asignada en los valores dados
	 * No recibe otra funcion
	 * @param valorVariables -> las n variables a evaluar
	 * @return el valor evaluado en esa funcion
	 */
	public double fd(double... valorVariables) throws AnalisisException{
		if(parserFdev==null){
			throw new AnalisisException("no se ha inicializado el parser");
		}
		parserFdev.setVarValue("x", valorVariables[0]);
		if(parserFdev.hasError()){
			throw new AnalisisException("Error asignando el valor al parser");
		}
		return parserFdev.getValue();
	}
	
	/**
	 * Evalua la funcion ya asignada en los valores dados
	 * No recibe otra funcion
	 * @param valorVariables -> las n variables a evaluar
	 * @return el valor evaluado en esa funcion
	 */
	public double fdd(double... valorVariables) throws AnalisisException{
		if(parserFdd==null){
			throw new AnalisisException("no se ha inicializado el parser");
		}
		parserFdd.setVarValue("x", valorVariables[0]);
		if(parserFdd.hasError()){
			throw new AnalisisException("Error asignando el valor al parser");
		}
		return parserFdd.getValue();
	}
	
	
	/**
	 * Evalua la funcion ya asignada en los valores dados
	 * No recibe otra funcion
	 * @param valorVariables -> las n variables a evaluar
	 * @return el valor evaluado en esa funcion
	 */
	public double g(double... valorVariables) throws AnalisisException{
		if(parserG==null){
			throw new AnalisisException("no se ha inicializado el parser");
		}
		parserG.setVarValue("x", valorVariables[0]);
		if(parserG.hasError()){
			throw new AnalisisException("Error asignando el valor al parser");
		}
		return parserG.getValue();
	}
	
	/**
	 * @param parserF the parserF to set
	 */
	public void setParserF(JEP parserF) {
		this.parserF = parserF;
	}
	/**
	 * @param parserG the parserG to set
	 */
	public void setParserG(JEP parserG) {
		this.parserG = parserG;
	}
	/**
	 * @return the parserFdev
	 */
	public JEP getParserFdev() {
		return parserFdev;
	}
	/**
	 * @param parserFdev the parserFdev to set
	 */
	public void setParserFdev(JEP parserFdev) {
		this.parserFdev = parserFdev;
	}
	/**
	 * @return the parserFdd
	 */
	public JEP getParserFdd() {
		return parserFdd;
	}
	/**
	 * @param parserFdd the parserFdd to set
	 */
	public void setParserFdd(JEP parserFdd) {
		this.parserFdd = parserFdd;
	}

	
}

package co.edu.eafit.analisisnumerico.common;

import java.util.Vector;

/**
 * 
 * @author Sebastian Velez
 * 
 */
public abstract class FuncionPadre {


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
	
	public void adicionarFilaTitulos(String... titulos) throws AnalisisException{
		if(titulos.length!=datos.size()){
			throw new AnalisisException("Numero invalido de columnas");
		}
		for(String s: titulos){
			this.titulos.add(s);
		}
	}
	



}

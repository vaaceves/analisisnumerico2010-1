package co.edu.eafit.analisisnumerico.framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

import org.nfunk.jep.JEP;

import co.edu.eafit.analisisnumerico.GUI.GUIInterpolacion;

/**
 * Maneja la lógica de la interfaz de interpolacion. crea eventos. valida
 * @author Sebastian
 * @since 17/05/2010
 *
 */
public class GestorInterfazPrincipalInterpolacion {

	/**Objeto interfaz*/
	GUIInterpolacion interfaz;

	/**Objeto de Negocio*/
	private static GestorInterfazPrincipalInterpolacion gestor;

	/**Número de valores a interpolar*/
	private int numeroValores;

	/**Parser de la funcion*/
	JEP parser;

	private double valorAInterpolar;

	public static GestorInterfazPrincipalInterpolacion getInstance(){
		if(gestor==null){
			gestor = new GestorInterfazPrincipalInterpolacion();
			gestor.interfaz = new GUIInterpolacion();
			gestor.crearEventos();	
		}
		return gestor;	
	}

	public void iniciar(){
		interfaz.setVisible(true);
	}

	private void crearEventos() {
		crearEventoRegresar();
		crearEventoAyuda();
		crearEventoTeclaApretada();
		crearEvento(interfaz.btnLagrange, Constantes.LAGRANGE);
		crearEvento(interfaz.btnNewton, Constantes.NEWTONDIFERENCIASDIVIDIDAS);
		crearEvento(interfaz.btnNeville, Constantes.NEVILLE);
	}

	private void crearEvento(JButton btn, final int metodo) {
		btn.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){
				double[][] matriz = generarDatosyValidar();
				if(matriz==null){
					return;
				}
				switch(metodo){
				case Constantes.LAGRANGE:
					GestorMetodos.ejecutarInterpolacion(Constantes.LAGRANGE, "Lagrange", matriz,valorAInterpolar);
					break;
				case Constantes.NEVILLE:
					GestorMetodos.ejecutarInterpolacion(Constantes.NEVILLE, "Neville", matriz,valorAInterpolar);
					break;
				case Constantes.NEWTONDIFERENCIASDIVIDIDAS:
					GestorMetodos.ejecutarInterpolacion(Constantes.NEWTONDIFERENCIASDIVIDIDAS, "Newton con Diferencias Divididas", matriz,valorAInterpolar);
					break;
				default:
					new AnalisisException("Error de programacion. Debe agregar el metodo en GestorInterfazPrincipalInterpolacion.java, crearEvento");
				}
			}
		});
	}

	private double[][] generarDatosyValidar() {
		//PRIMERO VALIDO VALOR A INTERPOLAR
		if(interfaz.txtValorInterpolar==null||interfaz.txtValorInterpolar.getText()==null||interfaz.txtValorInterpolar.getText().equals("")){
			new AnalisisException("El valor a interpolar es un campo obligatorio");
			return null;
		}
		else{
			try{
				valorAInterpolar = Double.parseDouble(interfaz.txtValorInterpolar.getText().trim());
			}
			catch(Exception e){
				new AnalisisException("Número inválido en el valor a interpolar");
				return null;
			}
		}
		Vector<Double> x = new Vector<Double>();
		Vector<Double> fx = new Vector<Double>();
		//Si entra aqui significa que hay mas de 20 valores
		if(interfaz.txtFuncion.getText()!=null&&!interfaz.txtFuncion.getText().equals("")){
			boolean resul = crearF(interfaz.txtFuncion.getText());
			if(!resul)return null;
		}
		//Manejo la primera tabla
		int tam=numeroValores;
		if(numeroValores>20){
			tam=20;
		}
		for(int i=0;i<tam;i++){
			double unaX = 0;
			try{
				unaX= Double.parseDouble(interfaz.tblValores1.getValueAt(i, 0).toString());
			}
			catch(Exception e){
				new AnalisisException("El valor número "+(i+1)+" es incorrecto");
				return null;
			}
			//valido si hay valor en f(x). si no lo hay, aplico el parser. si tampoco, error
			if(interfaz.tblValores1.getValueAt(i, 1)!=null&&!interfaz.tblValores1.getValueAt(i, 1).toString().equals("")){
				double unaY = 0;
				try{
					unaY= Double.parseDouble(interfaz.tblValores1.getValueAt(i, 1).toString());
				}
				catch(Exception e){
					new AnalisisException("La fx del valor "+(i+1)+" es incorrecta");
					return null;
				}
				x.add(unaX);
				fx.add(unaY);
			}
			else{
				if(parser==null){
					new AnalisisException("La fx del valor "+(i+1)+" es vacia, y no se ha declarado una funcion");
					return null;
				}
				else{
					parser.setVarValue("x", unaX);
					if(parser.hasError()){
						new AnalisisException("Error hallando con el parser la posición "+(i+1));
					}
					else{
						double unaY = parser.getValue();
						x.add(unaX);
						fx.add(unaY);
					}
				}
			}
		}
		//Manejo la segunda tabla en caso que sea necesario
		if(numeroValores<=20){
			return generarTablaInterpolacion(x,fx);
		}
		tam=numeroValores-20;
		for(int i=0;i<tam;i++){
			double unaX = 0;
			try{
				unaX= Double.parseDouble(interfaz.tblValores2.getValueAt(i, 0).toString());
			}
			catch(Exception e){
				new AnalisisException("El valor número "+(i+1)+" en la segunda tabla es incorrecto");
				return null;
			}
			//valido si hay valor en f(x). si no lo hay, aplico el parser. si tampoco, error
			if(interfaz.tblValores2.getValueAt(i, 1).toString()!=null&&!interfaz.tblValores2.getValueAt(i, 1).toString().equals("")){
				double unaY = 0;
				try{
					unaY= Double.parseDouble(interfaz.tblValores2.getValueAt(i, 1).toString());
				}
				catch(Exception e){
					new AnalisisException("La fx del valor "+(i+1)+" en la segunda tabla es incorrecta");
					return null;
				}
				x.add(unaX);
				fx.add(unaY);
			}
			else{
				if(parser==null){
					new AnalisisException("La fx del valor "+(i+1)+" en la segunda tabla es vacia, y no se ha declarado una funcion");
					return null;
				}
				else{
					parser.setVarValue("x", unaX);
					if(parser.hasError()){
						new AnalisisException("Error hallando con el parser la posición "+(i+1));
					}
					else{
						double unaY = parser.getValue();
						x.add(unaX);
						fx.add(unaY);
					}
				}
			}
		}
		return generarTablaInterpolacion(x, fx);
	}

	private double[][] generarTablaInterpolacion(Vector<Double> x,
			Vector<Double> fx) {
		if(x.size()!=fx.size())return null;
		double[][] resul = new double[x.size()][2];
		for(int i=0;i<x.size();i++){
			resul[i][0]=x.get(i);
			resul[i][1]=fx.get(i);
		}
		return resul;
	}

	private boolean crearF(String funcion) {
		if(funcion==null)return false;
		parser = new JEP();
		parser.setImplicitMul(true);
		parser.addStandardConstants();
		parser.addStandardFunctions();
		parser.addVariable("x", 0);
		parser.parseExpression(funcion);
		if(parser.hasError()){
			new AnalisisException("Funcion mal formada");
			return false;
		}
		return true;
	}



	private void crearEventoTeclaApretada() {
		interfaz.txtNumValores.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {}

			@Override
			public void keyReleased(KeyEvent arg0) {
				try{
					int num = Integer.parseInt(interfaz.txtNumValores.getText().trim());
					if(num<=40){
						String[] titulos = new String[2];
						titulos[0]="x";
						titulos[1]="f(x) (Opcional)";
						numeroValores=num;
						if(num<21){
							String[][] matriz = new String[num][2];
							interfaz.tblValores1.setModel(new DefaultTableModel(matriz, titulos));
							interfaz.tblValores1.setVisible(true);
							interfaz.tblValores2.setVisible(false);
						}
						else{
							int celdasSegundoValor = num-20;
							String[][] matriz = new String[20][2];
							interfaz.tblValores1.setModel(new DefaultTableModel(matriz, titulos));
							String[][] matriz2 = new String[celdasSegundoValor][2];
							interfaz.tblValores2.setModel(new DefaultTableModel(matriz2, titulos));
							interfaz.tblValores1.setVisible(true);
							interfaz.tblValores2.setVisible(true);
						}
					}
				}
				catch(Exception e){}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {}
		});	
	}

	private void crearEventoAyuda() {
		// TODO AQUI ES LA LOGICA PARA DESPLEGAR EL VIDEO
	}

	private void crearEventoRegresar() {
		interfaz.btnRegresar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				interfaz.setVisible(false);
			}
		});
	}

	//main temporal para pruebas
	public static void main(String[] args){
		GestorInterfazPrincipalInterpolacion.getInstance().iniciar();
	}

}

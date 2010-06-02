package co.edu.eafit.analisisnumerico.framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.nfunk.jep.JEP;

import co.edu.eafit.analisisnumerico.GUI.GUIDiferenciacion;
import co.edu.eafit.analisisnumerico.GUI.GUIInterpolacion;

public class GestorInterfazPrincipalDiferenciacion {

	/**Objeto interfaz*/
	GUIDiferenciacion interfaz;

	/**Objeto de Negocio*/
	private static GestorInterfazPrincipalDiferenciacion gestor;

	/**delta a usar*/
	private double delta;

	/**Parser de la funcion*/
	JEP parser;

	private double valor;

	
	public static GestorInterfazPrincipalDiferenciacion getInstance() {
		if(gestor==null){
			gestor = new GestorInterfazPrincipalDiferenciacion();
			gestor.interfaz = new GUIDiferenciacion();
			gestor.crearEventos();	
		}
		return gestor;
	}
	
	public void iniciar(){
		interfaz.setVisible(true);
	}
	
	public void crearEventos(){
		crearEventoRegresar();
		crearEventoAyuda();
		crearEvento(interfaz.btnDosPtosProgresivo, Constantes.DIFERENCIACION2PTOSPROGRESIVA);
		crearEvento(interfaz.btnDosPtosRegresivo, Constantes.DIFERENCIACION2PTOSREGRESIVA);
		crearEvento(interfaz.btnTresPuntosCentral, Constantes.DIFERENCIACION3PTOSCENTRADA);
		crearEvento(interfaz.btnTresPuntosProgresivo, Constantes.DIFERENCIACION3PTOSPROGRESIVA);
		crearEvento(interfaz.btnTresPuntosRegresivo, Constantes.DIFERENCIACION3PTOSREGRESIVA);
		crearEvento(interfaz.btnCincoPuntosCentral, Constantes.DIFERENCIACION5PTOSCENTRADA);
		crearEvento(interfaz.btnCincoPuntosProgresivo, Constantes.DIFERENCIACION5PTOSPROGRESIVA);
		crearEvento(interfaz.btnCincoPuntosRegresivo, Constantes.DIFERENCIACION5PTOSREGRESIVA);
	}
	
	private void crearEventoAyuda() {
		// TODO AQUI ES LA LOGICA PARA DESPLEGAR EL VIDEO
	}
	
	private void crearEvento(JButton btn, final int metodo) {
		btn.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e){
				boolean datosValidos= validar();
				if(!datosValidos){
					return;
				}
				else{
					GestorMetodos.ejecutarDiferenciacion(metodo, parser, delta, valor, interfaz.textArea1);
				}
				
			}
		});
		
		
	}

	private boolean validar() {
		//PRIMERO VALIDO VALOR A INTERPOLAR
		if(interfaz.txtValor==null||interfaz.txtValor.getText()==null||interfaz.txtValor.getText().equals("")){
			new AnalisisException("El valor es un campo obligatorio");
			return false;
		}
		else{
			try{
				valor = Double.parseDouble(interfaz.txtValor.getText().trim());
			}
			catch(Exception e){
				new AnalisisException("Número inválido en el valor a interpolar");
				return false;
			}
		}
		//VALIDO delta
		if(interfaz.txtDelta==null||interfaz.txtDelta.getText()==null||interfaz.txtDelta.getText().equals("")){
			new AnalisisException("El delta es un campo obligatorio");
			return false;
		}
		else{
			try{
				delta = Double.parseDouble(interfaz.txtDelta.getText().trim());
			}
			catch(Exception e){
				new AnalisisException("Número inválido en el delta");
				return false;
			}
		}
		if(delta==0){
			new AnalisisException("Delta no puede ser 0");
			return false;
		}
		boolean resul = crearF(interfaz.txtFuncion.getText());
		if(!resul)return false;
		return true;
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

	private void crearEventoRegresar() {
		interfaz.btnRegresar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				interfaz.setVisible(false);
			}
		});
	}

}

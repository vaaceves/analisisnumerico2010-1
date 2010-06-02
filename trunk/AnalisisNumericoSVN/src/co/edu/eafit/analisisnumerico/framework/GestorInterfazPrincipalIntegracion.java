package co.edu.eafit.analisisnumerico.framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.nfunk.jep.JEP;

import co.edu.eafit.analisisnumerico.GUI.GUIDiferenciacion;
import co.edu.eafit.analisisnumerico.GUI.GUIIntegracion;
import co.edu.eafit.analisisnumerico.GUI.GUIInterpolacion;

public class GestorInterfazPrincipalIntegracion {

	/**Objeto interfaz*/
	GUIIntegracion interfaz;

	/**Objeto de Negocio*/
	private static GestorInterfazPrincipalIntegracion gestor;

	/**delta a usar*/
	private double delta;

	/**numero de particiones*/
	private int particiones;

	/**Parser de la funcion*/
	JEP parser;

	private double limiteInferior;
	private double limiteSuperior;


	public static GestorInterfazPrincipalIntegracion getInstance() {
		if(gestor==null){
			gestor = new GestorInterfazPrincipalIntegracion();
			gestor.interfaz = new GUIIntegracion();
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
		crearEvento(interfaz.btnSimpsonTresOctavos, Constantes.SIMPSONTRESOCTAVOS);
		crearEvento(interfaz.btnSimpsonuntercio, Constantes.SIMPSONUNTERCIO);
		crearEvento(interfaz.btnSimpsonuntercioGeneralizado, Constantes.SIMPSONUNTERCIOGENERALIZADO);
		crearEvento(interfaz.btnTrapecioGeneralizado, Constantes.TRAPECIOGENERALIZADO);
		crearEvento(interfaz.btnTrapecioSencillo, Constantes.TRAPECIOSENCILLO);
	}

	private void crearEventoAyuda() {
		// TODO AQUI ES LA LOGICA PARA DESPLEGAR EL VIDEO
	}

	private void crearEvento(JButton btn, final int metodo) {
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				boolean datosValidos= validar(metodo);
				if(!datosValidos){
					return;
				}
				else{
					GestorMetodos.ejecutarIntegracion(metodo, parser, delta, limiteInferior, limiteSuperior, particiones, interfaz.textArea1);
				}

			}
		});


	}

	private boolean validar(int metodo) {
		//PRIMERO VALIDO VALOR LIMITES
		if(interfaz.txtLimiteInferior==null||interfaz.txtLimiteInferior.getText()==null||interfaz.txtLimiteInferior.getText().equals("")){
			new AnalisisException("El Limite inferior es un campo obligatorio");
			return false;
		}
		else{
			try{
				limiteInferior= Double.parseDouble(interfaz.txtLimiteInferior.getText().trim());
			}
			catch(Exception e){
				new AnalisisException("Número inválido en el limite inferior");
				return false;
			}
		}
		if(interfaz.txtLimiteSuperior==null||interfaz.txtLimiteSuperior.getText()==null||interfaz.txtLimiteSuperior.getText().equals("")){
			new AnalisisException("El Limite superior es un campo obligatorio");
			return false;
		}
		else{
			try{
				limiteSuperior= Double.parseDouble(interfaz.txtLimiteSuperior.getText().trim());
			}
			catch(Exception e){
				new AnalisisException("Número inválido en el limite Superior");
				return false;
			}
		}
		//valido el numero de particiones si es del caso
		if(metodo==Constantes.TRAPECIOGENERALIZADO||metodo==Constantes.SIMPSONUNTERCIOGENERALIZADO){
			if(interfaz.txtParticiones==null||interfaz.txtParticiones.getText()==null||interfaz.txtParticiones.getText().equals("")){
				new AnalisisException("El número de particiones es un campo obligatorio");
				return false;
			}
			else{
				try{
					particiones= Integer.parseInt(interfaz.txtParticiones.getText().trim());
				}
				catch(Exception e){
					new AnalisisException("Número inválido en el número de particiones");
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

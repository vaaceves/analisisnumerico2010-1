package co.edu.eafit.analisisnumerico.framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream.GetField;
import java.net.InterfaceAddress;
import java.util.Vector;

import javax.swing.JButton;

import co.edu.eafit.analisisnumerico.GUI.MetodoGUI1;
import co.edu.eafit.analisisnumerico.GUI.MetodoGUI2;
import co.edu.eafit.analisisnumerico.GUI.SistemasEcuacionesGUI;

/**
 * Esta clase controla la interfaz dinamica numero 1
 * @author Sebastian
 *
 */
public class GestorInterfazPrincipalSistemasEcuaciones {
	SistemasEcuacionesGUI interfaz;
	/**objeto de negocio*/
	private static GestorInterfazPrincipalSistemasEcuaciones gestor;
	private static final String TEXTOINICIO="";
	private static final String TEXTOFIN=":";
	int numVariables=0;
	int numFunciones=0;

	public static GestorInterfazPrincipalSistemasEcuaciones getInstance(){
		if(gestor==null){
			gestor = new GestorInterfazPrincipalSistemasEcuaciones();
			gestor.interfaz = new SistemasEcuacionesGUI();
			gestor.crearEventos();	
		}
		return gestor;	
	}

	public void iniciar(){
		interfaz.setVisible(true);
	}
	
	private void crearEventos() {
		crearEvento(interfaz.getBtnCholesky(), Constantes.CHOLESKY);
		crearEvento(interfaz.getBtnCroult(), Constantes.CROULT);
		crearEvento(interfaz.getBtnDolytle(), Constantes.DOOLITTLE);
		crearEvento(interfaz.getBtnEliminacionGauss(), Constantes.GAUSSSIMPLE);
		crearEvento(interfaz.getBtnGaussSeidel(), Constantes.GAUSSSEIDEL);
		crearEvento(interfaz.getBtnJacobi(), Constantes.JACOBI);
		crearEvento(interfaz.getBtnLUGaussianaParcial(), Constantes.LUPIVOTEO);
		crearEvento(interfaz.getBtnLUGaussianaSimple(), Constantes.LUSIMPLE);
		crearEvento(interfaz.getBtnMatrizBanda(), Constantes.MATRIZBANDA);
		crearEvento(interfaz.getBtnPivoteoParcial(), Constantes.PIVOTEOPARCIAL);
		crearEvento(interfaz.getBtnPivoteoTotal(), Constantes.PIVOTEOTOTAL);
		crearEvento(interfaz.getBtnRelajacion(), Constantes.RELAJACION);
		interfaz.getBtnRegresar().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				gestor.interfaz.setVisible(false);
			}
		});
	}



	private void crearEvento(JButton button, final int metodo) {
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ejecutarMetodo(metodo);
			}
		});
	}

	public void ejecutarMetodo(int metodo) {
		switch(metodo){
			case Constantes.GAUSSSIMPLE:
				GestorMetodos.ejecutarSistemaEcuacion(Constantes.GAUSSSIMPLE, "Eliminación Gaussiana Simple", interfaz.getTabla());
				break;
			case Constantes.PIVOTEOPARCIAL:
				GestorMetodos.ejecutarSistemaEcuacion(Constantes.PIVOTEOPARCIAL, "Pivoteo Parcial", interfaz.getTabla());
				break;
			case Constantes.PIVOTEOTOTAL:
				GestorMetodos.ejecutarSistemaEcuacion(Constantes.PIVOTEOTOTAL, "Pivoteo Total", interfaz.getTabla());
				break;				
			case Constantes.LUSIMPLE:
				GestorMetodos.ejecutarSistemaEcuacion(Constantes.LUSIMPLE, "LU Simple", interfaz.getTabla());
				break;				
			case Constantes.PIVOTEOESCALONADO:
				GestorMetodos.ejecutarSistemaEcuacion(Constantes.PIVOTEOESCALONADO, "Pivoteo Escalonado", interfaz.getTabla());
				break;				
		}
	}

}


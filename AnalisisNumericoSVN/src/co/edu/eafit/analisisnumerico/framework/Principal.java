package co.edu.eafit.analisisnumerico.framework;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.nfunk.jep.JEP;

import co.edu.eafit.analisisnumerico.GUI.GUIPrincipal;

/**
 * Clase principal. Inicia la aplicacion por modo grafico. gestiona eventos y validaciones
 * @author Sebastian
 *
 */
public class Principal {
	
	/**Objeto principal para garantizar singleton*/
	static Principal p;
	
	
	/** Objeto Interfaz Grafica*/
	public GUIPrincipal gui;
	
	/**
	 * devuelve el objeto unico principal
	 * @return Principal
	 */
	public static Principal getInstance(){
		if(p==null){
			p = new Principal();
		}
		return p;
	}
	
	
	/**
	 * Inicia la interfaz y genera los eventos
	 */
	public void iniciar(){
		gui = new GUIPrincipal();
		crearEventos();
		gui.setVisible(true);
	}
	
	/**
	 * Crea los eventos de la interfaz principal	
	 */
	private void crearEventos() {
		crearEventoBotonMetodos();
		crearEventoFuncion(gui.getFnBiseccion(), Constantes.BISECCION);
		crearEventoFuncion(gui.getFnBusquedas(), Constantes.BUSQUEDASINCREMENTALES);
		crearEventoFuncion(gui.getFnNewton(), Constantes.NEWTON);
		crearEventoFuncion(gui.getFnPuntoFijo(), Constantes.PUNTOFIJO);
		crearEventoFuncion(gui.getFnRaicesMultiples(), Constantes.RAICESMULTIPLES);
		crearEventoFuncion(gui.getFnReglaFalsa(), Constantes.REGLAFALSA);
		crearEventoFuncion(gui.getFnSecante(), Constantes.SECANTE);
	}

	/**
	 * Crea los eventos para el boton metodo (Valida y si todo ok, muestra los metodos disponibles)
	 */
	private void crearEventoBotonMetodos() {
		gui.getBtnMetodos().addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean resultado = crearFunciones();
				if(resultado){
					gui.getjTabbedPane1().setVisible(true);
	                gui.getjLabel13().setVisible(true);
				}
				
			}
		});
	}

	/**
	 * Crea los parsers de las funciones
	 * @return booleano si funciones exitosas o no
	 */
	protected boolean crearFunciones() {
		String fx = gui.getTxtFx().getText();
		String gx = gui.getTxtG().getText();
		String fdevx = gui.getTxtFdev().getText();
		String fddx = gui.getFdd().getText();
		boolean fxOk= crearF(fx, "f");
		boolean gxOk= crearF(gx, "g");
		boolean fdevOk= crearF(fdevx, "fdev");
		boolean fddOk= crearF(fddx, "fdd");
		return fxOk&&gxOk&&fdevOk&&fddOk;
	}
	
	/**
	 * Crea los parsers de las funciones nuevas
	 * @return booleano si funciones exitosas o no
	 */
	protected boolean crearFuncionesNuevas() {
		boolean fxOk = true;
		boolean gxOk=true;
		boolean fdevOk = true;
		boolean fddOk = true;
		if(MetodoPadre.getParserF()==null){
			String fx = gui.getTxtFx().getText();
			fxOk= crearF(fx, "f");
		}
		if(MetodoPadre.getParserG()==null){
			String gx = gui.getTxtG().getText();
			gxOk= crearF(gx, "g");
		}
		if(MetodoPadre.getParserFdev()==null){
			String fdevx = gui.getTxtFdev().getText();
			fdevOk= crearF(fdevx, "fdev");
		}
		if(MetodoPadre.getParserFdd()==null){
			String fddx = gui.getFdd().getText();
			fddOk= crearF(fddx, "fdd");
		}
		return fxOk&&gxOk&&fdevOk&&fddOk;
	}
	
	/**
	 * Crea una funcion
	 */
	private boolean crearF(String fx, String funcion) {
		if(fx.equals("")){
			return true;
		}
		JEP parser = new JEP();
		parser.setImplicitMul(true);
		parser.addStandardConstants();
		parser.addStandardFunctions();
		parser.addVariable("x", 0);
		parser.parseExpression(fx);
		if(funcion.equals("f")){
			if(parser.hasError()){
				new AnalisisException("f(x) esta mal formado");
				return false;
			}
			else{
				MetodoPadre.setParserF(parser);
				return true;
			}
		}
		else if(funcion.equals("g")){
			if(parser.hasError()){
				new AnalisisException("g(x) esta mal formado");
				return false;
			}
			else{
				MetodoPadre.setParserG(parser);
				return true;
			}
		}
		else if(funcion.equals("fdev")){
			if(parser.hasError()){
				new AnalisisException("La primera derivada de f(x) esta mal formada");
				return false;
			}
			else{
				MetodoPadre.setParserFdev(parser);
				return true;
			}
		}
		else if(funcion.equals("fdd")){
			if(parser.hasError()){
				new AnalisisException("La segunda derivada de f(x) esta mal formada");
				return false;
			}
			else{
				MetodoPadre.setParserFdd(parser);
				return true;
			}
		}
		return false;
	}

	/**
	 * Crea eventos para un boton, de acuerdo al metodo que activa
	 * @param boton boton para añadir evento
	 * @param metodo metodo que lanzará el botón
	 * TODO METODO NUEVO, PARA QUE FUNCIONE EN PRINCIPAL, DEBE ESTAR DEFINIDO AQUI
	 */
	public void crearEventoFuncion(JButton boton, final int metodo){
		boton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				switch(metodo){
					case Constantes.BISECCION:
						GestorMetodos.ejecutar(Constantes.BISECCION, Constantes.MODOGRAFICOINTERFAZ1, "Metodo de Bisección", "f", null, null, "Xi", "Xs", "Cifras significativas", "iteraciones");
						break;
					case Constantes.BUSQUEDASINCREMENTALES:
						GestorMetodos.ejecutar(Constantes.BUSQUEDASINCREMENTALES, Constantes.MODOGRAFICOINTERFAZ1, "Metodo de busquedas incrementales", "f",null, null,  "Xi", "delta", "iteraciones");
						break;
					case Constantes.NEWTON:
						GestorMetodos.ejecutar(Constantes.NEWTON, Constantes.MODOGRAFICOINTERFAZ1, "Metodo de Newton", "f,fdev",null,null,  "Xn", "Cifras significativas", "iteraciones");
						break;
					case Constantes.PUNTOFIJO:
						GestorMetodos.ejecutar(Constantes.PUNTOFIJO,Constantes.MODOGRAFICOINTERFAZ1, "Método de Punto Fijo","f,g", null , null, "X0", "Cifras significativas", "iteraciones");
						break;
					case Constantes.RAICESMULTIPLES:
						GestorMetodos.ejecutar(Constantes.RAICESMULTIPLES, Constantes.MODOGRAFICOINTERFAZ1, "Raices multiples", "f,fdev,fdd", null, null, "Xn", "Cifras significativas", "iteraciones");
						break;
					case Constantes.REGLAFALSA:
						GestorMetodos.ejecutar(Constantes.REGLAFALSA, Constantes.MODOGRAFICOINTERFAZ1, "Método de regla falsa", "f", null, null, "Xi", "Xs", "Cifras significativas", "iteraciones");						
						break;
					case Constantes.SECANTE:
						GestorMetodos.ejecutar(Constantes.SECANTE, Constantes.MODOGRAFICOINTERFAZ1, "Método de la secante", "f", null, null, "Xi", "Xs", "Cifras significativas", "iteraciones");
						break;
				}
			}
		});
	}

	public static void main(String[] args){
		Principal.getInstance().iniciar();
	}
}

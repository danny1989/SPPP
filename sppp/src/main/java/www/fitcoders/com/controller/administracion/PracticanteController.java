/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package www.fitcoders.com.controller.administracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import www.fitcoders.com.model.Docente;
import www.fitcoders.com.model.Estado;
import www.fitcoders.com.model.ObservacionPracticante;
import www.fitcoders.com.model.Practicante;
import www.fitcoders.com.model.PracticanteFuncion;
import www.fitcoders.com.service.GeneralService;
import www.fitcoders.com.util.Faces;
import www.fitcoders.com.util.SPPPID;

/**
 *
 * @author Max
 */
@ManagedBean
@SessionScoped
public class PracticanteController implements Serializable {
    
    @ManagedProperty("#{generalService}")
    GeneralService generalService;
   
    private List<Practicante> listaPracticantes;
    private Practicante practicante;
    private PracticanteFuncion practicanteFuncion;
    private ObservacionPracticante observacionPracticante;
    private Docente docente;
    private List<Docente> listaDocentes;
    
    public void iniciarValoresLista() throws Exception {
        listaPracticantes = generalService.listHQL("from Practicante p", null, null);
    }

    public void iniciarValorInstancia() {
        practicante = new Practicante();
        practicante.setPracticanteFuncionList(new ArrayList<PracticanteFuncion>());
    }
    
    public void iniciarInstanciaPracticanteFuncion() {
        practicanteFuncion = new PracticanteFuncion();
    }
    
    public void instanciarNuevaObservacion() {
        observacionPracticante = new ObservacionPracticante();
    }
    
    //datos para el asesor
    public void cargarDatosParaAsesor(Integer idPracticante) throws Exception {
        practicante = generalService.getByHQL("from Practicante p where p.id=" + idPracticante, null, null, false);
        docente = new Docente();
        listaDocentes = generalService.listHQL("from Docente d", null, null);
    }
    
    public void eliminarPracticanteFuncion(int index) throws Exception {
        PracticanteFuncion pf = practicante.getPracticanteFuncionList().remove(index);
        if(pf.getId() != null) {
            generalService.delete(pf);
        }
        Faces.addMessage("La función ha sido eliminada!", "", FacesMessage.SEVERITY_WARN);
    }
    
    public void cargarParaEdicionPracticante(Integer idPracticante) throws Exception {
        System.out.println(idPracticante);
        
        practicante = generalService.getByHQL("from Practicante p where p.id=" + idPracticante, null, null, false);
        
        List<PracticanteFuncion> listaPF = generalService.listHQL("from PracticanteFuncion p where p.idPracticante.id=" + practicante.getId(), null, null);
        List<ObservacionPracticante> listaOP = generalService.listHQL("from ObservacionPracticante o where o.idPracticante.id=" + practicante.getId(), null, null);
        practicante.setPracticanteFuncionList(listaPF);
        practicante.setObservacionPracticanteList(listaOP);
    }
    
    public void guardarObservacionPracticante() throws Exception {
        observacionPracticante.setIdPracticante(practicante);
        practicante.getObservacionPracticanteList().add(observacionPracticante);
        observacionPracticante.setActivo(true);
        
        if(practicante.getId() != null) {
            Integer idPF = (Integer)generalService.save(observacionPracticante);
            observacionPracticante.setId(idPF);
        }
        Faces.addMessage("NUEVA OBSERVACIÓN AÑADIDA", "", FacesMessage.SEVERITY_INFO);
    }
    
    public void guardarPracticaAlumno() throws Exception {
        generalService.update(practicante);
        Faces.addMessage("LAS PRÁCTICAS DEL ALUMNO FUERON ACTUALIZADAS...", "", FacesMessage.SEVERITY_INFO);
        
        iniciarValoresLista();
    }
   
    
    public void calcularFinPracticas() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(practicante.getFechaInicio());
        
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 3);
        
        practicante.setFechaFin(cal.getTime());
        System.out.println(practicante.getFechaFin());
    }
    
    public void buscarPracticanteWS() {
        System.out.println("Buscando prancitcante en WS");
        practicante.setNombreCompleto("NOMBRE DEL PRACTICANTE");
        practicante.setCreditosAprobados(160);
        practicante.setFechaRegistro(new Date());
    }
   
    public void setGeneralService(GeneralService generalService) {
        this.generalService = generalService;
    }

    public List<Practicante> getListaPracticantes() {
        return listaPracticantes;
    }

    public void setListaPracticantes(List<Practicante> listaPracticantes) {
        this.listaPracticantes = listaPracticantes;
    }

    public Practicante getPracticante() {
        return practicante;
    }

    public void setPracticante(Practicante practicante) {
        this.practicante = practicante;
    }

    public PracticanteFuncion getPracticanteFuncion() {
        return practicanteFuncion;
    }

    public void setPracticanteFuncion(PracticanteFuncion practicanteFuncion) {
        this.practicanteFuncion = practicanteFuncion;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public List<Docente> getListaDocentes() {
        return listaDocentes;
    }

    public void setListaDocentes(List<Docente> listaDocentes) {
        this.listaDocentes = listaDocentes;
    }

    public ObservacionPracticante getObservacionPracticante() {
        return observacionPracticante;
    }

    public void setObservacionPracticante(ObservacionPracticante observacionPracticante) {
        this.observacionPracticante = observacionPracticante;
    }
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package www.fitcoders.com.controller.administracion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import www.fitcoders.com.model.ComisionSppp;
import www.fitcoders.com.model.Docente;
import www.fitcoders.com.model.Estado;
import www.fitcoders.com.model.MiembroComisionSppp;
import www.fitcoders.com.model.Usuario;
import www.fitcoders.com.service.GeneralService;
import www.fitcoders.com.util.Faces;
import www.fitcoders.com.util.SPPPID;

/**
 *
 * @author Max
 */
@ManagedBean
@SessionScoped
public class ComisionController implements Serializable {

    @ManagedProperty("#{generalService}")
    GeneralService generalService;

    private List<ComisionSppp> listaComisionPeriodos;
    private List<Docente> listaDocentes;
    private ComisionSppp comisionSppp;
    private MiembroComisionSppp miembroComisionSppp;

    public void inicializarListaComisionPeriodo() throws Exception {
        listaComisionPeriodos = generalService.listHQL("from ComisionSppp c", null, null);
    }

    public void inicializarInstanciaComision() {
        comisionSppp = new ComisionSppp();
        comisionSppp.setMiembroComisionSpppList(new ArrayList<MiembroComisionSppp>());
    }

    public void getComisiongetById(Integer idComision) throws Exception {
        comisionSppp = generalService.getByHQL("from ComisionSppp c where c.id=" + idComision, null, null, false);
        List<MiembroComisionSppp> lista = generalService.listHQL("from MiembroComisionSppp m where m.idComisionSppp.id=" + idComision, null, null);
        comisionSppp.setMiembroComisionSpppList(lista);
    }

    public void eliminarMiembroComision(int posicion) throws Exception {
        MiembroComisionSppp em = comisionSppp.getMiembroComisionSpppList().remove(posicion);
        if (comisionSppp.getId() != null) {
            generalService.delete(em);
            generalService.delete(em.getIdUsuario());
        }
        Faces.addMessage("SE HA ELIMINADO A UN MIEMBRO DE LA COMISIÓN", "", FacesMessage.SEVERITY_INFO);
    }

    public void guardarMiembroEnLista() throws Exception {
        comisionSppp.getMiembroComisionSpppList().add(miembroComisionSppp);
        if(comisionSppp.getId() != null) {
            Integer idUs = (Integer)generalService.save(miembroComisionSppp.getIdUsuario());
            miembroComisionSppp.getIdUsuario().setId(idUs);
            ////////////////////
            generalService.save(miembroComisionSppp);
        }
        Faces.getRequestContext().update("idConvenio:tableComiId");
    }
    
    public List<Docente> autoCompletarDocente(String query) throws Exception {
        List<Docente> listaPersonas = generalService.
                listHQL("from Docente p where p.nombreCompleto like '%" + query.toUpperCase() + "%'", null, null);
         
        return listaPersonas;
    }
    
    public void guardarComisionSPPP() throws Exception {
        if (comisionSppp.getId() == null) {
            comisionSppp.setFechaRegistro(new Date());
            Integer idCom = (Integer)generalService.save(comisionSppp);
            comisionSppp.setId(idCom);
            //////////////////
            for (MiembroComisionSppp mi : comisionSppp.getMiembroComisionSpppList()) {
                mi.getIdUsuario().setIdEstado(new Estado(SPPPID.Usuario.ACTIVO));
                Integer idUs = (Integer)generalService.save(mi.getIdUsuario());
                mi.getIdUsuario().setId(idUs);
                ///////////////////7
                mi.setIdComisionSppp(comisionSppp);
                generalService.save(mi);
            }
                        
            Faces.addMessage("COMISIÓN GUARDADA CON ÉXITO...!", "", FacesMessage.SEVERITY_INFO);
        } else {
            generalService.update(comisionSppp);
            Faces.addMessage("comisión actualizada con éxito", "", FacesMessage.SEVERITY_INFO);
        } 
        inicializarListaComisionPeriodo();
    }
    
    public void instanciarMiembroComisionSppp() throws Exception {
        miembroComisionSppp = new MiembroComisionSppp();
        miembroComisionSppp.setIdComisionSppp(comisionSppp);
        miembroComisionSppp.setIdUsuario(new Usuario());
        
        listaDocentes = generalService.listHQL("from Docente d", null, null);
    }

    public List<ComisionSppp> getListaComisionPeriodos() {
        return listaComisionPeriodos;
    }

    public void setListaComisionPeriodos(List<ComisionSppp> listaComisionPeriodos) {
        this.listaComisionPeriodos = listaComisionPeriodos;
    }

    public void setGeneralService(GeneralService generalService) {
        this.generalService = generalService;
    }

    public ComisionSppp getComisionSppp() {
        return comisionSppp;
    }

    public void setComisionSppp(ComisionSppp comisionSppp) {
        this.comisionSppp = comisionSppp;
    }

    public MiembroComisionSppp getMiembroComisionSppp() {
        return miembroComisionSppp;
    }

    public void setMiembroComisionSppp(MiembroComisionSppp miembroComisionSppp) {
        this.miembroComisionSppp = miembroComisionSppp;
    }

    public List<Docente> getListaDocentes() {
        return listaDocentes;
    }

    public void setListaDocentes(List<Docente> listaDocentes) {
        this.listaDocentes = listaDocentes;
    }

}

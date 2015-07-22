/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fiis.sppp.controller.administracion;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import com.fiis.sppp.model.Docente;
import com.fiis.sppp.service.GeneralService;
import com.fiis.sppp.util.Faces;

/**
 *
 * @author Danny
 */
@ManagedBean
@SessionScoped
public class DocenteController implements Serializable {

    @ManagedProperty("#{generalService}")
    GeneralService generalService;

    private List<Docente> listaDocentes;
    private Docente docente;

    public void inicializarListaDocentes() throws Exception {
        listaDocentes = generalService.listHQL("from Docente d", null, null);
    }

    public void inicializarInstanciaDocente() {
        docente = new Docente();
    }

    public void getDocenteById(Integer idDocente) throws Exception {
        docente = generalService.getByHQL("from Docente d where d.id=" + idDocente, null, null, false);
    }

    public void guardarDocente() throws Exception {
        docente.setNombreCompleto(docente.getPaterno() + " " + docente.getMaterno() + " " + docente.getNombre());
        if (docente.getId() == null) {
            generalService.save(docente);
            Faces.addMessage("DOCENTE CREADO CON ÉXITO...!", "", FacesMessage.SEVERITY_INFO);
        } else {
            generalService.update(docente);
            Faces.addMessage("DOCENTE ACTUALIZADO CON ÉXITO...!", "", FacesMessage.SEVERITY_INFO);
        }
        inicializarListaDocentes();
    }

    public List<Docente> getListaDocentes() {
        return listaDocentes;
    }

    public void setListaDocentes(List<Docente> listaDocentes) {
        this.listaDocentes = listaDocentes;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public void setGeneralService(GeneralService generalService) {
        this.generalService = generalService;
    }

}

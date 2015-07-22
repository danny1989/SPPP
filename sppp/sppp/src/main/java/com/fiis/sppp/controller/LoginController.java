/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fiis.sppp.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.servlet.ServletException;
import com.fiis.sppp.model.Practicante;
import com.fiis.sppp.model.Usuario;
import com.fiis.sppp.service.GeneralService;
import com.fiis.sppp.util.Faces;
import com.fiis.sppp.util.SPPPID;

/**
 *
 * @author Danny
 */
@ManagedBean
@SessionScoped
public class LoginController implements Serializable {

    @ManagedProperty("#{generalService}")
    GeneralService generalService;
    //////////////////////////////////////////
    private String usuario;
    private String contrasena;
    private Integer tipoUsuario;
    private Practicante practicante;
    private Usuario usuarioAdmin;

    public void logOut() throws ServletException, IOException {
        Faces.removeSessionAttribute(Faces.ATTRIBUTE_USER_ADMIN);
        Faces.logout();
        Faces.invalidateSession();
        Faces.redirect("login.xhtml");
    }

    public void logIn() throws Exception {
        Map<String, Object> param = new HashMap<>();
        param.put("usuario", usuario);

        if (usuario.startsWith("00")) {
            try {
                String sql = "from Practicante p where p.codigoAlumno=:usuario";
                Practicante p = generalService.getByHQL(sql, param, null, false);
                if (p == null) {
                    p = wsEstudiante();
                    if (p == null) {
                        Faces.addMessage("NO SE HA ENCONTRADO EL ALUMNO....", "", FacesMessage.SEVERITY_ERROR);
                    } else {
                        p.setId((Integer) generalService.save(p));
                    }
                }
                if (p != null) {
                    practicante = p;
                    Faces.addMessage("BIENVENIDO USUARIO...!", "", FacesMessage.SEVERITY_INFO);
                    Faces.setSessionAttribute(Faces.ATTRIBUTE_PRACTICA, p);
                    Faces.redirect("index.xhtml");
                }
            } catch (Exception e) {
                e.printStackTrace();
                Faces.addMessage("ERROR EN EL SERVICIO WEB DE OCDA.!", "", FacesMessage.SEVERITY_FATAL);
            }
        } else {
            param.clear();
            param.put("nombre", usuario);
            param.put("pass", contrasena);
            String hqlAdmin = "from Usuario us where us.nombre=:nombre and us.contrasena=:pass";
            usuarioAdmin = generalService.getByHQL(hqlAdmin, param, null, false);
            if (usuarioAdmin != null) {
                Faces.setSessionAttribute(Faces.ATTRIBUTE_USER_ADMIN, usuarioAdmin);
                Faces.redirect("index.xhtml");
            } else {
                Faces.addMessage("NO EXISTE USUARIO ADMINISTRADOR...!", "", FacesMessage.SEVERITY_INFO);
            }
        }
//        if (Objects.equals(tipoUsuario, SPPPID.TipoUsuario.PRACTICANTE)) {
//
//        } else if (Objects.equals(tipoUsuario, SPPPID.TipoUsuario.COMISION)) {
//
//        } else if (Objects.equals(tipoUsuario, SPPPID.TipoUsuario.ADMINISTRADOR)) {
//            
//
//        }

    }

    public Practicante wsEstudiante() {
        Practicante p = null;
        try {
            String cod = usuario + "";
            System.out.println(cod);
            com.fitcode.webservice.SOAPEndPoint so = new com.fitcode.webservice.SOAPEndPoint("http://www.unas.edu.pe/ocda-academico/service/Servicio.php");
            String name = so.getOperationMethod("getAlumnoNombre")
                    .addParam("nombre", "" + cod)
                    .callOperationToString();
            p = new Practicante();
            p.setNombreCompleto(name + "");
            String credito = so.getOperationMethod("getAlumnoTCA")
                    .addParam("credito", "" + cod)
                    .callOperationToString();
            p.setCreditosAprobados(Integer.parseInt(credito + ""));
            p.setCodigoAlumno(cod);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return p;
    }

    public Integer getTipoUsuario() {
        if (tipoUsuario == null) {
            tipoUsuario = 2;
        }
        return tipoUsuario;
    }

    public void setTipoUsuario(Integer tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    // <editor-fold defaultstate="collapsed" desc="GETTER and SETTER">
    public String getUsuario() {
        return usuario;
    }

    public Usuario getUsuarioAdmin() {
        return usuarioAdmin;
    }

    public void setUsuarioAdmin(Usuario usuarioAdmin) {
        this.usuarioAdmin = usuarioAdmin;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Practicante getPracticante() {
        return practicante;
    }

    public void setPracticante(Practicante practicante) {
        this.practicante = practicante;
    }

    public void setGeneralService(GeneralService generalService) {
        this.generalService = generalService;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    // </editor-fold>
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fiis.sppp.util;

/**
 *
 * @author Danny
 */
public class SPPPID {
    
    public static class TipoUsuario {
        public static Integer PRACTICANTE = 1;
        public static Integer COMISION = 2;
        public static Integer ADMINISTRADOR = 3;
    }
    
    public static class Practicate {
        /**
         * 1
         */
        public static Integer A_PRACTICA_REGISTRADA = 1;
        /**
         * 2
         */
        public static Integer B_PRACTICA_APROBADA = 2;
        
        public static Integer C_PRACTICA_RECHAZADA = 3;
        public static Integer D_PRACTICA_OBSERVACION = 4;
        public static Integer E_ASESOR_ASIGNADO = 5;
        public static Integer F_INFORME_ENTREGADO = 6;
        public static Integer G_PRACTICA_ANULADA = 7;
        public static Integer H_JURADOS_ASIGNADOS = 8;
        public static Integer J_PRACTICA_CULMINADA = 10;
        
    }
    
    public static class Usuario {
        public static Integer ACTIVO = 5;
        public static Integer INACTIVO = 6;
    }
    
    public static class IdArea {
        public static Integer SISTEMAS = 1;
        public static Integer INFORMATICA = 2;
        public static Integer CIENCIAS_BASICAS = 3;
    }
    
}

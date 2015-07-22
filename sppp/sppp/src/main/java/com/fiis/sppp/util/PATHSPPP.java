/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fiis.sppp.util;

import java.io.File;

/**
 *
 * @author Danny
 */
public class PATHSPPP {
    
    private static final String PATH = File.separator + "resources" + File.separator + "report" + File.separator;
    
    public static final String RP_FORMATO_F1 = PATH + "rp_sppp_formato_f1.jasper";
    public static final String RP_AUTORIZACION_Y_ASIGNACION_ASESOR = PATH + "rp_sppp_autorizacion_y_asignacion_asesor.jasper";
    public static final String RP_ACTA_SUSTENTACION = PATH + "rp_acta_sustentacion.jasper";
    public static final String RP_ACUERDO_SUSTENTACION = PATH + "rp_acuerdo_sustentacion.jasper";
    public static final String RP_REMISION_PRACTICAS_PPP = PATH + "rp_remision_de_informe_final.jasper";
}

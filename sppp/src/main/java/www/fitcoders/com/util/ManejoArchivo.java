/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package www.fitcoders.com.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import javax.faces.context.FacesContext;
import www.fitcoders.com.util.Faces;

/**
 *
 * @author CJAVAPERU
 */
public class ManejoArchivo implements Serializable {

    /**
     *
     * @param <T>
     * @param type
     * @param path "/resources/configuracion/marketing.json"
     * @return
     * @throws MalformedURLException
     */
    public static <T> T setFileJsonToClass(Class<T> type, String path) throws Exception {
        Reader reader = new InputStreamReader(Faces.getResourceAsStream(path), "UTF-8");
        Gson gson = new GsonBuilder().create();
        T pee = gson.fromJson(reader, type);

        return pee;
    }

    /**
     * reemplaza el contenido del archivo.
     * @param ob
     * @param path
     * @throws Exception 
     */
    public static void setClassToFileJson(Object ob, String path) throws Exception {
        Gson gson = new Gson();
        String gSonString = gson.toJson(ob);
        
        URL url = FacesContext.getCurrentInstance().getExternalContext().getResource(path);
        File file = new File(url.getPath());
        FileWriter writer = new FileWriter(file);
        writer.write(gSonString);
        
        writer.close();
    }

}

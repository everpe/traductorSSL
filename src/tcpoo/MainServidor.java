package tcpoo;

import bd.BaseDatos;
import bd.Conexion;
import logica.Sistema;

/**
 *
 * @author ever
 */
public class MainServidor {
    public static void main(String[] args) 
    {
        System.setProperty("javax.net.ssl.keyStore", "myKeystore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "llavero");
        System.setProperty("javax.net.debug", "ssl");
        
        Conexion conexion= new Conexion(1);
        BaseDatos baseDatos= new BaseDatos(conexion);
        Sistema sistema = new Sistema(baseDatos);       
        
        Servidor servidor = new Servidor(9090, sistema);
        servidor.hablar();
        //servidor.start();
    }
}

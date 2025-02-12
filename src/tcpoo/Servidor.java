/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpoo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import logica.Sistema;

public class Servidor extends Thread
{
    private Sistema sistema;
    private Protocolo protocolo;
    private int serverPort;
    private SSLServerSocket socketServidor;    
    private SSLSocket socketCliente;
    private DataInputStream in;
    private DataOutputStream out;

    public Servidor(int serverPort, Sistema sistema) 
    {
        this.sistema = sistema;
        this.protocolo = new Protocolo(sistema);
        this.serverPort = serverPort;
        try 
        {
            SSLServerSocketFactory sslServerSocketFactory = (SSLServerSocketFactory)
                                                SSLServerSocketFactory.getDefault();
            this.socketServidor = (SSLServerSocket) sslServerSocketFactory.
                                                    createServerSocket(serverPort);
            System.out.println("Servidor Creado");
        } catch (IOException e) 
        {
            System.out.println("Error creando el socket del servidor");
        }
    }
    
    public void hablar()
    {
        try {
            while(true)
            {                
                this.socketCliente = (SSLSocket)this.socketServidor.accept();
                in = new DataInputStream(this.socketCliente.getInputStream());
                out = new DataOutputStream(this.socketCliente.getOutputStream());
                
                while(true)
                {
                    System.out.println("Se conecto cliente");
                    String peticion = in.readUTF();
                    System.out.println("peticion recibida: " + peticion);
                    String resp = this.protocolo.comprobarComunicacion(peticion);
                    out.writeUTF(resp);
                    out.flush();
                    System.out.println("Respuesta enviada: " + resp);
                }  
            }
        } catch (IOException e) 
        {
            System.out.println("Error en la comunicación con el servidor");
        }
        
    }
    
    @Override
    public void run()
    {
        this.hablar();
//        while(true)
//        {
//            try 
//            {
//                System.out.println("Se conecto cliente");
//                String peticion = in.readUTF();
//                System.out.println("peticion recibida: " + peticion);
//                out.writeUTF("Hola evercito");
//                out.flush();
//                System.out.println("Respuesta enviada");
//            } catch (IOException e) 
//            {
//                System.out.println("Error en la comunicación");
//            }            
//        }
    }
    
    
    
}

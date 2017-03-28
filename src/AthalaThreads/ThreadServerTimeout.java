/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AthalaThreads;

import AthalaPayload.ConsoleServeur;
import AthalaPayload.RequeteAndroid;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mathieu
 */
public class ThreadServerTimeout extends Thread {
    private final int port = 4462;
    private ConsoleServeur gui;
    private ServerSocket SSocket=null;
    
    public ThreadServerTimeout(ConsoleServeur g)
    {        
        gui=g;
    }
    public void run()
    {
         try
        {
            SSocket=new ServerSocket(port);
        }
        catch(IOException ex)
        {
            System.out.println("An error occured : port is already in use ["+ex+"]");
        }
        
        Socket CSocket=null;
        //Corps du serveur
        while(!isInterrupted())
        {
            try
            {                
                CSocket=SSocket.accept();
                //Send socket to the thread client which will manage the client
                ObjectInputStream ois = new ObjectInputStream(CSocket.getInputStream());
                RequeteAndroid req = (RequeteAndroid) ois.readObject();
                if(req.getType() == RequeteAndroid.REQUEST_BEACON)
                {
                    gui.AddClient(req.getU());
                }
            }
            catch(IOException e)
            {
                System.out.println("An error occured : accept error ["+e+"]");                
            } catch (ClassNotFoundException ex) {
                 System.out.println("An error occured : read object failed ["+ex+"]");
                 System.exit(1);
             }
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AthalaThreads;

import AthalaPayload.ConsoleServeur;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 *
 * @author Mathieu
 */
public class ThreadServer extends Thread{
    private ConsoleServeur gui;
    private ServerSocket SSocket=null;
    public ThreadServer(ConsoleServeur cs)
    {
        gui=cs;
    }    
    public void run()
    {
        try
        {
            SSocket=new ServerSocket(4461);
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
                ThreadClient thC = new ThreadClient(CSocket,gui);
                thC.start();                         
            }
            catch(IOException e)
            {
                System.out.println("An error occured : accept error ["+e+"]");
                System.exit(1);
            }
        }
    }
}

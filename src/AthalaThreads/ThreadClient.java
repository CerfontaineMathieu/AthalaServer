/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AthalaThreads;

import AthalaPayload.ConsoleServeur;
import AthalaPayload.Requete;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mathieu
 */
public class ThreadClient extends Thread{
    private Socket Sclient;
    private String ClientName;
    private boolean finish= true;
    private ConsoleServeur gui;
    public ThreadClient(Socket cli,ConsoleServeur g)
    {
        Sclient = cli;
        gui=g;
    }
    
    public void run()
    {
                
        ObjectInputStream ois=null;
        Requete req=null;
        try {
            ois=new ObjectInputStream(Sclient.getInputStream());
        } catch (IOException ex) {
            System.out.println("An error occured : getInputStream failed ["+ex+"]");
        }
            while(finish)
            {
                try
                {                           
                    req=(Requete)ois.readObject();
                    Runnable work=req.createRunnable(Sclient, gui);
                    Thread th = new Thread(work);
                    th.start();                    
                } catch (IOException ex) {
                    System.out.println("An error occured : read object failed ["+ex+"]");
                    finish=false;
                } catch (ClassNotFoundException ex) {
                    System.out.println("An error occured : canno't found class ["+ex+"]");
                    finish=false;
                }
            }
        try { 
            ois.close();
            Sclient.close();            
        } catch (IOException ex) {
            System.out.println("Erreur de fermeture de la socket cliente : "+ex);
        }
    }
}

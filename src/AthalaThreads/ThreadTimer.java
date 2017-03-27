/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AthalaThreads;

import AthalaData.User;
import AthalaPayload.ConsoleServeur;
import AthalaPayload.Requete;
import AthalaPayload.RequeteAndroid;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Mathieu
 */
public class ThreadTimer extends Thread{
    private boolean finish= true;
    private ConsoleServeur gui;
    private HashMap<String,Long> cooldowns;
    private final int seconds = 60;
    public ThreadTimer(ConsoleServeur g,HashMap<String,Long> cd)
    {
        gui=g;
        cooldowns = cd;
    }
    
    public void run()
    {
                
        while(finish)
        {
           try
            {                                                                                       
                Thread.sleep(1500);
                for ( String username : cooldowns.keySet() ) {                   
                    if(hasCooldown(username)){
                       cooldowns.remove(username);
                    }
                }                                  
            } catch (InterruptedException ex) {
                System.out.println("An error occured : interrupted exception ["+ex+"]");
                finish=false;
            }
        }
    }
  

    public boolean hasCooldown(String u){
        if(cooldowns.get(u)+(seconds*1000)>System.currentTimeMillis())
        {
            return false;
        }
        else return true;
    }

    public void activateCooldown(String u){
     cooldowns.put(u, System.currentTimeMillis());
    }
}

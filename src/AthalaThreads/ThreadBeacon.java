/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AthalaThreads;

import AthalaPayload.ConsoleServeur;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mathieu
 */
public class ThreadBeacon extends Thread{
    private InetAddress ip;
    private String beaconMsg;
    private ConsoleServeur cs;
    private MulticastSocket socket;
    private boolean finish=true;
    public ThreadBeacon(ConsoleServeur _cs)
    {
        try {
            ip = InetAddress.getLocalHost();
            beaconMsg = ip.getHostAddress() + "/4461/";
            cs = _cs;
            socket = new MulticastSocket(4446);
        } catch (UnknownHostException ex) {
            _cs.Trace("Adresse ip de l'hôte introuvable. Vérifier votre connexion au réseau local et réessayer.");
        } catch (IOException ex) {
            _cs.Trace("Impossible d'écouter sur le port 4446 en multicast.");
        }
    }
    public void run()
    {     
       
       while(finish)
       {      
           byte[] beacon = beaconMsg.getBytes();
           try {
               InetAddress group = InetAddress.getByName("225.4.5.6");
               DatagramPacket packet;
               packet = new DatagramPacket(beacon,beacon.length,group,4446);               
               socket.send(packet);
               
               try
               {
                   Thread.sleep(3000);
               }
               catch(InterruptedException ex)
               {
                   cs.Trace("Le thread de beacon a été interrompu inopinément. Il se peut que cela génère des problèmes de synchronisation avec les clients.");
                   finish=false;
               }
           } catch (UnknownHostException ex) {
               cs.Trace("Adresse ip multicast introuvable. Vérifier votre connexion au réseau local et réessayer.");
               finish=false;
           } catch (IOException ex) {
               cs.Trace("Echec de l'envoi du beacon en multicast sur l'adresse 225.4.5.6 port 4446. Vérifier votre connexion au réseau local et réessayer.");
               finish=false;
           }           
       }
       socket.close();
    }
}

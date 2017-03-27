/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AthalaPayload;

import java.net.*;
import javax.net.ssl.SSLSocket;

/**
 *
 * @author Mathieu
 */
public interface Requete {
    public Runnable createRunnable(Socket s,ConsoleServeur cs);    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AthalaThreads;

import java.util.HashMap;
import java.util.Iterator;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author Mathieu
 */
public class ThreadRefresh extends Thread{
    
    private JList list;
    private HashMap<String,Long> ConnectedUsers;
    public ThreadRefresh(JList toRefresh,HashMap<String,Long> l)
    {
        list = toRefresh;
        ConnectedUsers =l;
    }
    public void run()
    {
        while(true)
        {
            try {
                Thread.sleep(1500);
                DefaultListModel dlm = new DefaultListModel();
                for ( String key : ConnectedUsers.keySet() ) {
                    dlm.addElement(key);
                }       
                list.setModel(dlm);
            } catch (InterruptedException ex) {
                System.out.println("An error occured : interrupted exception ["+ex+"]");
            }
        }
    }
}

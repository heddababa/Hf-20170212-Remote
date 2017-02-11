/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hrremote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
//import hrremote.*;

/**
 *
 * @author User
 */
public interface HrRemote extends Remote{
  ArrayList<Munkakor> getMunkakorok() throws RemoteException;
  ArrayList <Dolgozo> getDolgozokListajaAdottReszleghez (int reszlegID)throws RemoteException;
  ArrayList <Dolgozo> getDolgozokListajaOsszesReszleghez (int reszlegID)throws RemoteException;
  ArrayList<Reszleg> getReszlegList ()throws RemoteException;
  int getMinfizetes(Dolgozo dolgozo)throws RemoteException; 
  int getMaxFizetss(Dolgozo dolgozo)throws RemoteException;    
  boolean setFizetes(Dolgozo dolgozo, int fizetes)throws RemoteException;  
  boolean setDolgozo(String firstName, String lastName, String email,
          String phoneNumber, String jobId, int salary, double commissionPCT, 
          int managerID, int departmentID) throws RemoteException, SQLException;
  int[] getOsszFizetesReszlegben(int reszlegId)throws RemoteException;
  ArrayList<String> getEmailList()throws RemoteException;
  ArrayList<Dolgozo> getFonokList(int reszlegId)throws RemoteException;
}

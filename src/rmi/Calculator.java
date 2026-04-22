package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {

    CalculationResult add(double a, double b) throws  RemoteException ;
    CalculationResult sub(double a, double b) throws  RemoteException ;
    CalculationResult mul(double a, double b) throws  RemoteException;
    CalculationResult div(double a, double b) throws  RemoteException, ArithmeticException ;

}

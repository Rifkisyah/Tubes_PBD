/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.data;

/**
 *
 * @author rifki
 */
public class SessionAccount {
    private static EmployeeAccount currentAccount;
    
    public static void setSessionAccount(EmployeeAccount employeeAccount){
        currentAccount = employeeAccount;
    }
    
    public static EmployeeAccount getSessionAccount(){
        return currentAccount;
    }
}

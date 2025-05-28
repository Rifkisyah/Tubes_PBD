/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.data;

import com.bookstore.model.AkunPegawai;

/**
 *
 * @author rifki
 */
public class SesiAkunPegawai {
    private static AkunPegawai akunSaatini;
    
    public static void setSesiAkunPegawai(AkunPegawai akun){
        akunSaatini = akun;
    }
    
    public static AkunPegawai getSesiAkunPegawai(){
        return akunSaatini;
    }
}

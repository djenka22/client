/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package nit;

import frm.FrmLogin;
import frm.GlavnaForma;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import komunikacija.Komunikacija;
import komunikacija.Odgovor;
import komunikacija.Operacije;

/**
 *
 * @author Rusimovic
 */
public class NitZaCitanje extends Thread {

    Komunikacija komunikacija;
    FrmLogin frmLogin;
    GlavnaForma glavnaForma;

    public NitZaCitanje(Komunikacija komunikacija, FrmLogin frmLogin) {
        this.frmLogin = frmLogin;
        this.komunikacija = komunikacija;
    }

    @Override
    public void run() {
        System.out.println("Pokrenuta nit klijenta za citanje");
        try {
            while (true) {

                Odgovor odgovor = (Odgovor) komunikacija.procitaj();
                obradiOdgovor(odgovor);
            }

        } catch (IOException ex) {
            Logger.getLogger(NitZaCitanje.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NitZaCitanje.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obradiOdgovor(Odgovor odgovor) throws IOException {
        switch (odgovor.getOperacija()) {
            case Operacije.LOGIN:
                frmLogin.login(odgovor);
                break;
            case Operacije.NOVI_KORISNIK_SE_PRIJAVIO:
                glavnaForma.azurirajKorisnike(odgovor);
                break;
            case Operacije.SLANJE_OPSTE_PORUKE:
                glavnaForma.azurirajPoruke(odgovor);
                break;
            case Operacije.SLANJE_PORUKA_ODREDJENOM_KORISNIKU:
                glavnaForma.azurirajPoruke(odgovor);
                break;
            case Operacije.ODJAVA_KLIJENTA:
                glavnaForma.azurirajPoruke(odgovor);
                break;
        }
    }

    public void setGlavnaForma(GlavnaForma glavnaForma) {
        this.glavnaForma = glavnaForma;
    }

}

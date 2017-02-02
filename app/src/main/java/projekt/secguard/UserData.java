package projekt.secguard;


import java.io.Serializable;

/** Przechowywanie danych o zalogowanym userze */
public class UserData implements Serializable{

    private int id;
    private String login; //nazwa usera
    private String haslo; //haslo usera
    private String imie; //imie pracownika
    private String nazwisko; //nazwisko pracownika
    private String pozycja; //stanowisko
    private Boolean status; //czy konto jest aktywne?
 //test
    public UserData(int id, String login, String haslo, String imie, String nazwisko, String pozycja, Boolean status) {
        this.id = id;
        this.login = login;
        this.haslo = haslo;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pozycja = pozycja;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public String getLogin() {
        return login;
    }
    public String getHaslo() {
        return haslo;
    }
    public String getImie() {
        return imie;
    }
    public String getNazwisko() {
        return nazwisko;
    }
    public String getPozycja() {
        return pozycja;
    }
    public Boolean getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public void setHalo(String haslo) {
        this.haslo = haslo;
    }
    public void setImie(String imie) {
        this.imie = imie;
    }
    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    public void setPozycja(String pozycja) {
        this.pozycja = pozycja;
    }
    public void setStatus(Boolean status) {
        this.status = status;
    }
}

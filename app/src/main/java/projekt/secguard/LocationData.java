package projekt.secguard;

import java.io.Serializable;

/**
 * Created by Dominik on 11.02.2017.
 */

public class LocationData implements Serializable{

    private int id;
    private String nazwa;
    private int nr_ulicy;
    private String ulica;
    private String miasto;
    private int idTyp;
    private int idZlec;
    private long startData;
    private long stopData;
    private long startGodziny;
    private long stopGodziny;
    private int ilOchroniarzy;
    private int gps_x;
    private int gps_y;
    private int gps_r;

    LocationData()
    {

    }

    public int getIdZlec() {
        return idZlec;
    }

    public long getStartData() {
        return startData;
    }

    public void setStartData(long startData) {
        this.startData = startData;
    }

    public long getStopData() {
        return stopData;
    }

    public void setStopData(long stopData) {
        this.stopData = stopData;
    }

    public long getStartGodziny() {
        return startGodziny;
    }

    public void setStartGodziny(long startGodziny) {
        this.startGodziny = startGodziny;
    }

    public long getStopGodziny() {
        return stopGodziny;
    }

    public void setStopGodziny(long stopGodziny) {
        this.stopGodziny = stopGodziny;
    }

    public int getGps_r() {
        return gps_r;
    }

    public void setGps_r(int gps_r) {
        this.gps_r = gps_r;
    }

    public int getGps_y() {
        return gps_y;
    }

    public void setGps_y(int gps_y) {
        this.gps_y = gps_y;
    }

    public int getNr_ulicy() {

        return nr_ulicy;
    }

    public void setNr_ulicy(int nr_ulicy) {
        this.nr_ulicy = nr_ulicy;
    }

    public int getGps_x() {

        return gps_x;
    }

    public void setGps_x(int gps_x) {
        this.gps_x = gps_x;
    }








    //GETTERS&SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public int getIlOchroniarzy() {
        return ilOchroniarzy;
    }

    public void setIlOchroniarzy(int il_ochroniarzy) {
        this.ilOchroniarzy = il_ochroniarzy;
    }






    public void setIdZlec(int id_zlec) {
        this.idZlec = id_zlec;
    }

    public int getIdTyp() {

        return idTyp;
    }

    public void setIdTyp(int id_typ) {
        this.idTyp = id_typ;
    }

    public String getMiasto() {

        return miasto;
    }

    public void setMiasto(String miasto) {
        this.miasto = miasto;
    }

    public String getUlica() {

        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public int getNrUlicy() {

        return nr_ulicy;
    }

    public void setNrUlicy(int nr_ulicy) {
        this.nr_ulicy = nr_ulicy;
    }

    public String getNazwa() {

        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}

package projekt.secguard;

import java.io.Serializable;

/**
 * Created by Dominik on 11.02.2017.
 */

public class LocationData implements Serializable{

    private int id;
    private String nazwa;
    private String nr_ulicy;
    private String ulica;
    private String miasto;
    private int idTyp;
    private int idZlec;
    private int[] startData = new int[3]; //[dd][mm][yy]
    private int[] stopData = new int[3];  //jw.
    private Boolean[] godziny = new Boolean[104]; //104 kafelki doby po 15 minut od 00:00
    private int ilOchroniarzy;

    void addGodzina(int h, int m)
    {
        int x = (h*60+m)/15;
        godziny[x] = true;
    }

    void delGodzina(int h, int m)
    {
        int x = (h*60+m)/15;
        godziny[x] = false;
    }

    void addGodzinaRange(int h_start, int m_start, int h_stop, int m_stop)
    {
        int x = (h_start*60+m_start)/15;
        int y = (h_stop*60+m_stop)/15;

        for(int i = x; i <= y ; i++)
        {
            godziny[i] = true;
        }
    }

    void delGodzinaRange(int h_start, int m_start, int h_stop, int m_stop)
    {
        int x = (h_start*60+m_start)/15;
        int y = (h_stop*60+m_stop)/15;

        for(int i = x; i <= y ; i++)
        {
            godziny[i] = true;
        }
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

    public Boolean[] getGodziny() {

        return godziny;
    }

    public void setGodziny(Boolean[] godziny) {
        this.godziny = godziny;
    }

    public int[] getStopData() {

        return stopData;
    }

    public void setStopData(int[] stop_data) {
        this.stopData = stop_data;
    }

    public int[] getStartData() {

        return startData;
    }

    public int[] setStartData(int[] start_data) {
        this.startData = start_data;
    }

    public int getIdZlec() {

        return idZlec;
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

    public String getNrUlicy() {

        return nr_ulicy;
    }

    public void setNrUlicy(String nr_ulicy) {
        this.nr_ulicy = nr_ulicy;
    }

    public String getNazwa() {

        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}

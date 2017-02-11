package projekt.secguard;

import java.io.Serializable;

/**
 * Created by Dominik on 11.02.2017.
 */

public class LocationData implements Serializable{

    private String nazwa;
    private String nr_ulicy;
    private String ulica;
    private String miasto;
    private int idTyp;
    private int idZlec;
    private int[] startData; //[dd][mm][yy]
    private int[] stopData;  //jw.
    private int[][] godziny;
    private int ilOchroniarzy;

    void addGodzina(int h, int m)
    {
        Boolean flag = true;
        for(int i = 0;i<godziny[0].length;i++)
        {
            if(godziny[i][0] == h)
            {
                if(godziny[i][1] == m)
                    flag = false;
            }
        }

        if(flag == true)
        {
            godziny[godziny[0].length][0] = h;
            godziny[godziny[0].length][1] = m;
        }
    }


    //GETTERS&SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public int getIlOchroniarzy() {
        return ilOchroniarzy;
    }

    public void setIlOchroniarzy(int il_ochroniarzy) {
        this.ilOchroniarzy = il_ochroniarzy;
    }

    public int[][] getGodziny() {

        return godziny;
    }

    public void setGodziny(int[][] godziny) {
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

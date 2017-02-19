package projekt.secguard;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private long startData;
    private long stopData;
    private long startGodziny;
    private long stopGodziny;
    private int ilOchroniarzy;
    private double gps_x;
    private double gps_y;
    private double gps_r;

    LocationData()
    {

    }

    void setStartDateFromString(String a)
    {
        try {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date d = f.parse(a);
        long milliseconds = d.getTime();
            startData = milliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    void setStopDateFromString(String a)
    {
        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
            Date d = f.parse(a);
            long milliseconds = d.getTime();
            stopData = milliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    void setStartTimeFromString(String a)
    {
        try {
            SimpleDateFormat f = new SimpleDateFormat("hh:mm:ss");
            Date d = f.parse(a);
            long milliseconds = d.getTime();
            startGodziny = milliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    void setStopTimeFromString(String a)
    {
        try {
            SimpleDateFormat f = new SimpleDateFormat("hh:mm:ss");
            Date d = f.parse(a);
            long milliseconds = d.getTime();
            stopGodziny = milliseconds;
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    String getStringStartDate()
    {
        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date(startData));
        return dateString;
    }

    String getStringStopDate()
    {
        String dateString = new SimpleDateFormat("yyyy-MM-dd").format(new Date(stopData));
        return dateString;
    }

    String getStringStartTime()
    {
        String timeString = new SimpleDateFormat("hh:mm").format(new Date(startGodziny));
        return timeString;
    }

    String getStringStopTime()
    {
        String timeString = new SimpleDateFormat("hh:mm").format(new Date(stopGodziny));
        return timeString;
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

    public double getGps_r() {
        return gps_r;
    }

    public void setGps_r(double gps_r) {
        this.gps_r = gps_r;
    }

    public double getGps_y() {
        return gps_y;
    }

    public void setGps_y(double gps_y) {
        this.gps_y = gps_y;
    }

    public String getNr_ulicy() {

        return nr_ulicy;
    }

    public void setNr_ulicy(String nr_ulicy) {
        this.nr_ulicy = nr_ulicy;
    }

    public double getGps_x() {

        return gps_x;
    }

    public void setGps_x(double gps_x) {
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

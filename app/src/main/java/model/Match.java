package model;

import java.io.Serializable;

public class Match implements Serializable {

    private int match_id;
    private String match_clubname_1;
    private String match_clubname_2;
    private byte[] match_clublogo_1;
    private byte[] match_clublogo_2;
    private String match_tanggal;
    private String match_waktu;
    private String match_deskripsi;

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public String getMatch_clubname_1() {
        return match_clubname_1;
    }

    public void setMatch_clubname_1(String match_clubname_1) {
        this.match_clubname_1 = match_clubname_1;
    }

    public String getMatch_clubname_2() {
        return match_clubname_2;
    }

    public void setMatch_clubname_2(String match_clubname_2) {
        this.match_clubname_2 = match_clubname_2;
    }

    public byte[] getMatch_clublogo_1() {
        return match_clublogo_1;
    }

    public void setMatch_clublogo_1(byte[] match_clublogo_1) {
        this.match_clublogo_1 = match_clublogo_1;
    }

    public byte[] getMatch_clublogo_2() {
        return match_clublogo_2;
    }

    public void setMatch_clublogo_2(byte[] match_clublogo_2) {
        this.match_clublogo_2 = match_clublogo_2;
    }

    public String getMatch_tanggal() {
        return match_tanggal;
    }

    public void setMatch_tanggal(String match_tanggal) {
        this.match_tanggal = match_tanggal;
    }

    public String getMatch_waktu() {
        return match_waktu;
    }

    public void setMatch_waktu(String match_waktu) {
        this.match_waktu = match_waktu;
    }

    public String getMatch_deskripsi() {
        return match_deskripsi;
    }

    public void setMatch_deskripsi(String match_deskripsi) {
        this.match_deskripsi = match_deskripsi;
    }
}

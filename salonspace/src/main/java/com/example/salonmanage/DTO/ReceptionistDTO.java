package com.example.salonmanage.DTO;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class ReceptionistDTO {

    @NotNull
    private String phone;
    @NotNull
    private ArrayList<Integer> serviceId;
    @NotNull
    private int idReceptionist;
    @NotNull
    private String date;
    @NotNull
    private long total;
    @NotNull
    private int time;

    public ReceptionistDTO(String phone, ArrayList<Integer> serviceId, int idReceptionist, String date, long total, int time) {
        this.phone = phone;
        this.serviceId = serviceId;
        this.idReceptionist = idReceptionist;
        this.date = date;
        this.total = total;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ReceptionistDTO() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<Integer> getServiceId() {
        return serviceId;
    }

    public void setServiceId(ArrayList<Integer> serviceId) {
        this.serviceId = serviceId;
    }

    public int getIdReceptionist() {
        return idReceptionist;
    }

    public void setIdReceptionist(int idReceptionist) {
        this.idReceptionist = idReceptionist;
    }
}

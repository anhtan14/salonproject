package com.example.salonmanage.DTO;

import com.example.salonmanage.Entities.Role;

import java.util.List;

public class UpdateUserDTO {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String birthday;

    private int branch;
    private List<Role> role;

    public UpdateUserDTO() {
    }

    public UpdateUserDTO(Integer id, String name, String email, String phone, String birthday, int branch, List<Role> role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birthday = birthday;
        this.branch = branch;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getBranch() {
        return branch;
    }

    public void setBranch(int branch) {
        this.branch = branch;
    }

    public List<Role> getRole() {
        return role;
    }

    public void setRole(List<Role> role) {
        this.role = role;
    }
}

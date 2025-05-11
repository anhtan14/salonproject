package com.example.salonmanage.service;

import com.example.salonmanage.Entities.User;
import com.example.salonmanage.DTO.registerDTO;
import java.util.List;

public interface UserService {
    public List<User> getAll();

    public User save(User user);

    public void sendmail();

    public String OTP(String mail);

    public void register(registerDTO registerDTO);


    public User findByPhone(String phone);

    public User update(User user);

}

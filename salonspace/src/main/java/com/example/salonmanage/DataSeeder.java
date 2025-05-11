package com.example.salonmanage;

import com.example.salonmanage.Entities.Role;
import com.example.salonmanage.Entities.Times;
import com.example.salonmanage.repository.RoleRepository;
import com.example.salonmanage.repository.TimesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private  RoleRepository roleRepository;

    @Autowired
    private  TimesRepository timesRepository;

    @Override
    public void run(String... args) throws Exception {
        // Kiểm tra xem dữ liệu đã tồn tại hay chưa
        if (roleRepository.count() == 0) {
            Role admin = new Role("ROLE_ADMIN");
            Role editor = new Role("ROLE_RECEPTIONIST");
            Role customer = new Role("ROLE_CUSTOMER");
            Role staff = new Role("ROLE_STAFF");
            roleRepository.saveAll(List.of(admin, editor, customer, staff));
        }
        if (timesRepository.count() == 0){
            Times seven = new Times("7:00");
            Times nine = new Times("9:00");
            Times eleven = new Times("11:00");
            Times threety = new Times("13:00");
            Times fivety = new Times("15:00");
            Times seventy = new Times("17:00");
            Times ninety = new Times("19:00");
            Times twentyone = new Times("21:00");
            timesRepository.saveAll(List.of(seven,nine,eleven,threety,fivety,seventy,ninety,twentyone));
        }
    }
}

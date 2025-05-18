package com.example.salonmanage.service;

import com.example.salonmanage.DTO.registerDTO;
import com.example.salonmanage.Entities.Role;
import com.example.salonmanage.Entities.User;
import com.example.salonmanage.repository.RoleRepository;
import com.example.salonmanage.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private userRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll() {
        System.out.println(userRepository.findAll());
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User save(User user) {
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);

        return userRepository.save(user);
    }

    @Override
    public void sendmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("salonspaseteam@gmail.com");
        message.setTo("hoangphucqh1234@gmail.com");
        message.setText("hellll000");
        message.setSubject("hêhehehe");
        mailSender.send(message);
    }



    @Override
    public User findByPhone(String phone) {
        User user = userRepository.findByPhone(phone).get();
        return user;
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public String OTP(String mail) {
        int randomPIN = (int) (Math.random() * 9000) + 1000;
        String stringRandomPIN = String.valueOf(randomPIN);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("salonspaseteam@gmail.com");
        message.setTo(mail);
        message.setText("Hello \n\n" + "Your Register OTP :" + stringRandomPIN + ".Please Verify. \n\n" + "Regards \n" + "SalonSpace");
        message.setSubject("Regiter OTP");
        mailSender.send(message);

        return stringRandomPIN;
    }

    @Override
    public void register(registerDTO registerDTO) {
        Role role = roleRepository.findByName("ROLE_CUSTOMER");
        User user = new User();
        user.setEmail(registerDTO.getEmail());
        user.setName(registerDTO.getName());
        user.setPassword(passwordEncoder.encode(registerDTO.getPass()));
        user.setRoles(Set.of(role));
        user.setBirthday(registerDTO.getBirthday());
        user.setStatus(1);
        user.setPhone(registerDTO.getPhone());
        userRepository.save(user);
    }
}





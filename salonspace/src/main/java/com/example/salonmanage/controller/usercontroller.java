package com.example.salonmanage.controller;

import com.example.salonmanage.DTO.ResetPassDTO;
import com.example.salonmanage.DTO.forgotDTO;
import com.example.salonmanage.DTO.registerDTO;
import com.example.salonmanage.DTO.userDTO;
import com.example.salonmanage.Entities.User;
import com.example.salonmanage.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.salonmanage.service.UserService;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class usercontroller {
    @Autowired
    private UserService userService;

    @Autowired
    private userRepository userRepository;
    private final Map<String, String> otpStore = new HashMap<>();
    private final Map<String, LocalDateTime> otpTime = new HashMap<>();
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PutMapping()
    public ResponseEntity<?> createUser(@RequestBody @Valid User user) {
        User createdUser = userService.save(user);
        URI uri = URI.create("/users/" + createdUser.getId());
        userDTO userDto = new userDTO(createdUser.getId(), createdUser.getPhone());
        return ResponseEntity.created(uri).body(userDto);
    }

    @PostMapping("/mail")
    public ResponseEntity<String> Otp(@RequestBody registerDTO registerDTO) {
        boolean phoneExist = userRepository.existsByPhone(registerDTO.getPhone());
        boolean emailExist = userRepository.existsByEmail(registerDTO.getEmail());
        String email = registerDTO.getEmail();
        if (phoneExist == false) {
            if (emailExist == false) {
                String otp = userService.OTP(registerDTO.getEmail());
                LocalDateTime localDateTime = LocalDateTime.now();
                otpTime.put(otp, localDateTime);
                otpStore.put(email, otp);

                return ResponseEntity.ok("Generate OTP successfully");
            } else {
                return ResponseEntity.ok("Email is already existed");
            }
        } else {
            return ResponseEntity.ok("Phone is already existed");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody registerDTO registerDTO) {
        String email = registerDTO.getEmail();
        String otp = registerDTO.getOtp();
        double ttlMinutes = 10;
        LocalDateTime creationTime = otpTime.get(otpStore.get(email));
        LocalDateTime currentTime = LocalDateTime.now();
        long minutesElapsed = creationTime.until(currentTime, ChronoUnit.MINUTES);

        if (minutesElapsed <= ttlMinutes) {
            if (otpStore.containsKey(email) && otpStore.get(email).equals(otp)) {
                userService.register(registerDTO);
                otpStore.remove(email);
                return ResponseEntity.ok("sucessfully");
            } else return ResponseEntity.ok("OTP is not correct");
        } else {
            otpTime.remove(otpStore.get(email));
            otpStore.remove(email);
            return ResponseEntity.ok("OTP het han");
        }
    }

    @PostMapping("/mail2")
    public ResponseEntity<String> mail(@RequestBody forgotDTO forgotDTO) {
        String email = forgotDTO.getEmail();
        boolean existMail = userRepository.existsByEmail(email);
        if (existMail == true) {
            String otp = userService.OTP(email);
            LocalDateTime localDateTime = LocalDateTime.now();
            otpTime.put(otp, localDateTime);
            otpStore.put(email, otp);
            return ResponseEntity.ok("suscess");
        } else return ResponseEntity.ok("email not exist");
    }

    @PostMapping("/checkotp")
    public ResponseEntity<String> checkOTP(@RequestBody forgotDTO forgotDTO) {
        String email = forgotDTO.getEmail();
        String otp = forgotDTO.getOtp();
        double ttlMinutes = 0.4;
        LocalDateTime creationTime = otpTime.get(otpStore.get(email));
        LocalDateTime currentTime = LocalDateTime.now();
        long minutesElapsed = creationTime.until(currentTime, ChronoUnit.MINUTES);
        if (minutesElapsed <= ttlMinutes) {

            if (otpStore.containsKey(email) && otpStore.get(email).equals(otp)) {
                otpStore.remove(email);
                return ResponseEntity.ok("sucessfully");
            } else return ResponseEntity.ok("OTP is not correct");
        } else {
            otpTime.remove(otpStore.get(email));
            otpStore.remove(email);
            return ResponseEntity.ok("OTP het han");
        }

    }

    @PostMapping("/resetpass")
    public ResponseEntity<String> resetPass(@RequestBody forgotDTO forgotDTO) {
        User user = userRepository.findByEmail(forgotDTO.getEmail());
        user.setPassword(passwordEncoder.encode(forgotDTO.getOtp()));
        userRepository.save(user);
        return ResponseEntity.ok("suscess");
    }

    @GetMapping("/staff")
    public List<Map<String, String>> getUsersWithRoleStaff() {
        List<User> staffUsers = userRepository.findByRolesName("ROLE_STAFF");
        List<Map<String, String>> result = new ArrayList<>();
        for (User user : staffUsers) {
            Map<String, String> userMap = new HashMap<>();
            userMap.put("name", user.getName());
            userMap.put("role", "ROLE_STAFF");
            userMap.put("img", user.getImg());
            result.add(userMap);
        }

        return result;
    }

    @GetMapping("/top-users")
    public List<Object[]> getTopUsersWithMostBookings() {
        List<Object[]> topUsersInfo = userRepository.findTop4UsersWithMostBookings();
        return topUsersInfo;
    }

    @PostMapping("/newpass")
    public  ResponseEntity<String> resetPass(@RequestBody ResetPassDTO resetPassDTO)
    {
        User user = userRepository.getById(resetPassDTO.getId());
        boolean isMatch = passwordEncoder.matches(resetPassDTO.getOldPass(),user.getPassword());
        if(isMatch==true){
         user.setPassword(passwordEncoder.encode(resetPassDTO.getNewPass()));
         userRepository.save(user);
         return ResponseEntity.ok("suscess");
        }
        else return ResponseEntity.ok("wrong");

    }
}

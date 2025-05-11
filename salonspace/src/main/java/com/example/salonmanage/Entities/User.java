package com.example.salonmanage.Entities;



import javax.persistence.*;
import javax.validation.constraints.Email;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Nationalized;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;
    @Column(nullable = false)
    @Nationalized
    private String name;
    @Column(nullable = false)
    private String birthday;
    @Column(nullable = false)
    private String phone;
    @Column()
    private String img;
    @Column(nullable = false, length = 255)

    private String password;
    @Column(nullable = false)
    @Email
    private String email;

    @Column
    private int status;
    @ManyToOne()
    @JoinColumn(name="branch_id")
    private Branch branch;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private List<Comment> comments =new ArrayList();
    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();


    @OneToMany(mappedBy = "user")
    private  List<Booking> bookings = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private  List<BookingDetail> bookingDetails = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Collection<Notification> notifications ;

    public Collection<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Collection<Notification> notifications) {
        this.notifications = notifications;
    }

    public User(){

    }
    public User(Integer id, String name, String birthday, String phone, String img, String password, String email, int status, Branch branch, List<Comment> comments, Set<Role> roles, List<Booking> bookings, List<BookingDetail> bookingDetails) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
        this.img = img;
        this.password = password;
        this.email = email;
        this.status = status;
        this.branch = branch;
        this.comments = comments;
        this.roles = roles;
        this.bookings = bookings;
        this.bookingDetails = bookingDetails;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<BookingDetail> getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(List<BookingDetail> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

    public Branch getBranch() {
        return branch;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.phone;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}

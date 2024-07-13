package cac.administracionusuarios.modelo;

/**
 *
 * @author Crixo
 */

import java.util.Date;

public class Usuario {
    private int id;
    private String name;
    private String email;
    private String userName;
    private String password;
    private String contactNo;
    private String city;
    private String address;
    private String role;
    private Date created_at;
    private Date updated_at;

    public Usuario() {
    }

    public Usuario(int id, String name, String email, String userName, String password, String contactNo, String city,
            String address, String role, Date created_at, Date updated_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.contactNo = contactNo;
        this.city = city;
        this.address = address;
        this.role = role;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Usuario(String name, String email, String userName, String password, String contactNo, String city,
            String address, String role) {
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.contactNo = contactNo;
        this.city = city;
        this.address = address;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", name=" + name + ", email=" + email + ", userName=" + userName
                + ", password=" + password + ", contactNo=" + contactNo + ", city=" + city + ", address=" + address
                + ", role=" + role + ", created_at=" + created_at + ", updated_at=" + updated_at + '}';
    }

}

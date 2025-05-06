package com.blogmasterpro;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Objects;

/**
 * Entity representing a user in the system.
 */
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false, length = 100)
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false, length = 150)
    private String email;

    @NotBlank(message = "Password is required")
    @Column(nullable = false, length = 255)
    private String passwordHash;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    @Min(value = 1, message = "Age must be positive")
    @Max(value = 120, message = "Age must be less than 120")
    private Integer age;

    @Size(max = 100, message = "Country name too long")
    private String country;

    @Size(max = 100, message = "City name too long")
    private String city;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Size(max = 100, message = "Twitter handle too long")
    private String twitter;

    @Size(max = 100, message = "LinkedIn handle too long")
    private String linkedin;

    @Size(max = 100, message = "GitHub handle too long")
    private String github;

    @Min(value = 0, message = "Blog count cannot be negative")
    private Integer blogCount = 0;

    @Min(value = 0, message = "Comments count cannot be negative")
    private Integer comments = 0;

    private LocalDateTime lastLogin;

    // Constructors
    public User() {
        this.registrationDate = LocalDateTime.now();
        this.blogCount = 0;
        this.comments = 0;
    }

    public User(String name, String email, String passwordHash) {
        this();
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    // Getters and setters for all fields

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }

    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public LocalDateTime getRegistrationDate() { return registrationDate; }
    public void setRegistrationDate(LocalDateTime registrationDate) { this.registrationDate = registrationDate; }

    public String getTwitter() { return twitter; }
    public void setTwitter(String twitter) { this.twitter = twitter; }

    public String getLinkedin() { return linkedin; }
    public void setLinkedin(String linkedin) { this.linkedin = linkedin; }

    public String getGithub() { return github; }
    public void setGithub(String github) { this.github = github; }

    public Integer getBlogCount() { return blogCount; }
    public void setBlogCount(Integer blogCount) { this.blogCount = blogCount; }

    public Integer getComments() { return comments; }
    public void setComments(Integer comments) { this.comments = comments; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", dob=" + dob +
            ", age=" + age +
            ", country='" + country + '\'' +
            ", city='" + city + '\'' +
            ", registrationDate=" + registrationDate +
            ", twitter='" + twitter + '\'' +
            ", linkedin='" + linkedin + '\'' +
            ", github='" + github + '\'' +
            ", blogCount=" + blogCount +
            ", comments=" + comments +
            ", lastLogin=" + lastLogin +
            '}';
    }
}
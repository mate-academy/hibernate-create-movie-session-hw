package mate.academy.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    private String email;
    private String salt;

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            SecureRandom random = new SecureRandom();
            byte[] saltBytes = new byte[16];
            random.nextBytes(saltBytes);
            this.salt = Base64.getEncoder().encodeToString(saltBytes);

            this.password = hashPassword(password, saltBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }

    public boolean checkPassword(String passwordToCheck) {
        try {
            byte[] saltBytes = Base64.getDecoder().decode(this.salt);
            String hashedInput = hashPassword(passwordToCheck, saltBytes);
            return this.password.equals(hashedInput);
        } catch (Exception e) {
            throw new RuntimeException("Error while verifying password", e);
        }
    }

    private String hashPassword(String password, byte[] saltBytes) throws Exception {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), saltBytes, 65536, 128);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}

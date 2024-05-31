package encapsulaciones;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;

@Entity
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String password;
    private String nombre;
    private String email;
    private boolean admin;
    private boolean superAdmin;
    @OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Url> misUrl;

    public Usuario(String username, String password, String nombre, String email, boolean admin, boolean superAdmin) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.email = email;
        this.admin = admin;
        this.superAdmin = superAdmin;
    }

    public Usuario() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
    }

    public long getId() {return id;}

    public List<Url> getMisUrl() {
        return misUrl;
    }
    public void setMisUrl(List<Url> misUrl) {
        this.misUrl = misUrl;
    }
}

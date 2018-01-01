package hdcz.com.app.greenland1.bean;

/**
 * Created by guyuqiang on 2017/12/29.14:00
 */

public class PersonBean {
    private Integer id;
    private String name;
    private String password;

    public PersonBean(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public PersonBean() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "PersonBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

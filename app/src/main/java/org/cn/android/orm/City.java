package org.cn.android.orm;

import org.cn.orm.annotation.Column;
import org.cn.orm.annotation.Entity;
import org.cn.orm.annotation.Id;

@Entity(name = "city")
public class City {
    @Id
    @Column(name = "id")
    public String id;
    @Column(name = "name")
    public String name;
    @Column(name = "code")
    public String code;

    public City() {
    }

    public City(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "City{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }
}

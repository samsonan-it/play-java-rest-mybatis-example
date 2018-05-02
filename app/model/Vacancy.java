package model;

import com.google.common.base.Objects;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Vacancy {

    @NotNull
    private Long id;

    @NotNull
    private String name;

    @Min(value = 0L)
    private Long salary;

    @Size(max = 255)
    private String experience;

    @Size(max = 50)
    private String city;

    public Vacancy() {
    }

    public Vacancy(Long id, String name, String salary, String experience, String city) {
        this.id = id;
        this.name = name;
        this.salary = salary == null? null : Long.parseLong(salary);
        this.experience = experience;
        this.city = city;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Vacancy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", experience='" + experience + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vacancy vacancy = (Vacancy) o;
        return Objects.equal(id, vacancy.id) &&
                Objects.equal(name, vacancy.name) &&
                Objects.equal(salary, vacancy.salary) &&
                Objects.equal(experience, vacancy.experience) &&
                Objects.equal(city, vacancy.city);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name, salary, experience, city);
    }
}

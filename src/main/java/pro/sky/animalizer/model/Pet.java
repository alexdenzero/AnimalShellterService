package pro.sky.animalizer.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Класс-модель, описывающая питомца.
 */
@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String petType;
    private String petBreed;

    @ManyToOne
    @JoinColumn(name = "pet_owner_id")
    private User petOwner;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public Pet(Long id, String petType, String petBreed, User petOwner) {
        this.id = id;
        this.petType = petType;
        this.petBreed = petBreed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id) && Objects.equals(petType, pet.petType) && Objects.equals(petBreed, pet.petBreed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, petType, petBreed);
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", petType='" + petType + '\'' +
                ", petBreed='" + petBreed + '\'' +
                '}';
    }
}

package pro.sky.animalizer.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.animalizer.model.Pet;

/**
 * Интерфейс - репозиторий для работы с сущностью Pet.
 */
public interface PetRepository extends JpaRepository<Pet, Long> {
}
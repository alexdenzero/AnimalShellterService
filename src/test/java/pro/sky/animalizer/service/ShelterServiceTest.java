package pro.sky.animalizer.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pro.sky.animalizer.exceptions.ShelterNotFoundException;
import pro.sky.animalizer.model.Shelter;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import pro.sky.animalizer.repositories.ShelterRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ShelterServiceTest {

    @InjectMocks
    private ShelterService shelterService;

    @Mock
    private ShelterRepository shelterRepository;

    @Test
    public void testGetShelterById_ShouldReturnShelter() {
        long shelterId = 1L;
        Shelter mockShelter = new Shelter();
        mockShelter.setId(shelterId);
        given(shelterRepository.findById(shelterId)).willReturn(Optional.of(mockShelter));

        Shelter result = shelterService.getShelterById(shelterId);

        assertThat(result).isEqualTo(mockShelter);
    }

    @Test
    public void testGetShelterById_ShouldThrowNotFoundException() {
        long shelterId = 1L;
        given(shelterRepository.findById(shelterId)).willReturn(Optional.empty());

        assertThatThrownBy(() -> shelterService.getShelterById(shelterId))
                .isInstanceOf(ShelterNotFoundException.class);
    }

    @Test
    public void testGetAllShelters_ShouldReturnListOfShelters() {
        List<Shelter> mockShelters = List.of(new Shelter(), new Shelter());
        given(shelterRepository.findAll()).willReturn(mockShelters);

        List<Shelter> result = shelterService.getAllShelters();

        assertThat(result).isEqualTo(mockShelters);
    }

    @Test
    public void testCreateShelter_ShouldReturnCreatedShelter() {
        Shelter inputShelter = new Shelter();
        given(shelterRepository.save(any(Shelter.class))).willReturn(inputShelter);

        Shelter result = shelterService.createShelter(inputShelter);

        assertThat(result).isEqualTo(inputShelter);
    }

    @Test
    public void testEditShelter_ShouldReturnEditedShelter() {
        long shelterId = 1L;
        Shelter existingShelter = new Shelter();
        existingShelter.setId(shelterId);
        Shelter inputShelter = new Shelter();
        inputShelter.setAddress("New Address");
        given(shelterRepository.findById(shelterId)).willReturn(Optional.of(existingShelter));
        given(shelterRepository.save(any(Shelter.class))).willAnswer(invocation -> invocation.getArgument(0));

        Shelter result = shelterService.editShelter(shelterId, inputShelter);

        assertThat(result.getId()).isEqualTo(shelterId);
        assertThat(result.getAddress()).isEqualTo("New Address");
    }

    @Test
    public void testDeleteShelter_ShouldReturnDeletedShelter() {
        long shelterId = 1L;
        Shelter existingShelter = new Shelter();
        existingShelter.setId(shelterId);
        given(shelterRepository.findById(shelterId)).willReturn(Optional.of(existingShelter));

        Shelter result = shelterService.deleteShelter(shelterId);

        assertThat(result.getId()).isEqualTo(shelterId);
        verify(shelterRepository).delete(existingShelter);
    }
}

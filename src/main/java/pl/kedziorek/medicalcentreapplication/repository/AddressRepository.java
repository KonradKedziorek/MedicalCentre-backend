package pl.kedziorek.medicalcentreapplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kedziorek.medicalcentreapplication.domain.Address;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findFirstByCityAndPostcodeAndStreetAndLocalNumberAndHouseNumber(
            String city, String postcode, String street, String localNumber, String houseNumber
    );
}

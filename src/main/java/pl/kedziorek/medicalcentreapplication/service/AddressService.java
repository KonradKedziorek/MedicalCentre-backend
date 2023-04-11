package pl.kedziorek.medicalcentreapplication.service;

import pl.kedziorek.medicalcentreapplication.domain.Address;

public interface AddressService {
    Address findFirstByCityAndPostcodeAndStreetAndLocalNumberAndHouseNumber(
            String city, String postcode, String street, String localNumber, String houseNumber
    );
}

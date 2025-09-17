package org.example.company;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    @Test
    void shouldCreateAddressWithValidParameters() {
        // given
        String street = "123 Main St";
        String city = "Beijing";
        String state = "Beijing";
        String zipCode = "100000";
        String country = "China";

        // when
        Address address = new Address(street, city, state, zipCode, country);

        // then
        assertEquals(street, address.getStreet());
        assertEquals(city, address.getCity());
        assertEquals(state, address.getState());
        assertEquals(zipCode, address.getZipCode());
        assertEquals(country, address.getCountry());
    }

    @Test
    void shouldThrowExceptionWhenStreetIsNull() {
        // given
        String street = null;
        String city = "Beijing";
        String state = "Beijing";
        String zipCode = "100000";
        String country = "China";

        // when & then
        assertThrows(IllegalArgumentException.class, () -> 
            new Address(street, city, state, zipCode, country));
    }

    @Test
    void shouldThrowExceptionWhenStreetIsEmpty() {
        // given
        String street = "";
        String city = "Beijing";
        String state = "Beijing";
        String zipCode = "100000";
        String country = "China";

        // when & then
        assertThrows(IllegalArgumentException.class, () -> 
            new Address(street, city, state, zipCode, country));
    }

    @Test
    void shouldThrowExceptionWhenStreetIsBlank() {
        // given
        String street = "   ";
        String city = "Beijing";
        String state = "Beijing";
        String zipCode = "100000";
        String country = "China";

        // when & then
        assertThrows(IllegalArgumentException.class, () -> 
            new Address(street, city, state, zipCode, country));
    }

    @Test
    void shouldReturnFullAddress() {
        // given
        Address address = new Address("123 Main St", "Beijing", "Beijing", "100000", "China");

        // when
        String fullAddress = address.getFullAddress();

        // then
        assertEquals("123 Main St, Beijing, Beijing 100000, China", fullAddress);
    }
}
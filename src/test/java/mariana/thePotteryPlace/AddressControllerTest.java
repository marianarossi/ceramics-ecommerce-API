package mariana.thePotteryPlace;

import mariana.thePotteryPlace.model.Address;
import mariana.thePotteryPlace.model.User;
import mariana.thePotteryPlace.repository.AddressRepository;
import mariana.thePotteryPlace.repository.OrderItemRepository;
import mariana.thePotteryPlace.repository.UserRepository;
import mariana.thePotteryPlace.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AddressControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderItemRepository orderItemRepository;


    @BeforeEach
    public void cleanup() {
        orderItemRepository.deleteAll();
        orderRepository.deleteAll();
        addressRepository.deleteAll();  // Clean up addresses
        userRepository.deleteAll();  // Clean up users
        testRestTemplate.getRestTemplate().getInterceptors().clear();  // Clear RestTemplate interceptors

    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void postAddress_whenAddressIsValid_receiveOk() {
        User user = createValidUser();  // Ensure user exists
        userRepository.save(user);

        Address address = createValidAddress(user);
        String token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZTIzQGdtYWlsLmNvbSIsImV4cCI6MTczMTk5MTUyMH0.O769HXFm3ioS2IeBZXSDPtldlQoxK20T4WzKdpi5Snj3sVAVEqAO_Tb7TKM1UFZMi5f1_Epg9U7Jz0I-vTpH6g";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Address> entity = new HttpEntity<>(address, headers);

        ResponseEntity<Object> response = testRestTemplate.exchange("/addresses", HttpMethod.POST, entity, Object.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void postAddress_whenAddressIsValid_addressSavedToDatabase() {
        User user = createValidUser();
        userRepository.save(user);

        Address address = createValidAddress(user);
        testRestTemplate.postForEntity("/addresses", address, Object.class);

        assertThat(addressRepository.count()).isEqualTo(1);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void postAddress_whenAddressIsInvalid_receiveBadRequest() {
        User user = createValidUser();
        userRepository.save(user);

        // Create an invalid address with missing required field (e.g., no city)
        Address invalidAddress = Address.builder()
                .street("123 Pottery Lane")
                .number(100)
                .complement("Apt 101")
                .neighborhood("Downtown")
                .state("DF")
                .country("Brazil")
                .zip("70000000")
                .user(user)
                .build();

        // Set token header
        String token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJ0ZXN0ZTIzQGdtYWlsLmNvbSIsImV4cCI6MTczMTk5MTUyMH0.O769HXFm3ioS2IeBZXSDPtldlQoxK20T4WzKdpi5Snj3sVAVEqAO_Tb7TKM1UFZMi5f1_Epg9U7Jz0I-vTpH6g";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<Address> entity = new HttpEntity<>(invalidAddress, headers);

        // Send request and verify response
        ResponseEntity<Object> response = testRestTemplate.exchange("/addresses", HttpMethod.POST, entity, Object.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER") // Mock user with the role "USER"
    public void getAddress_whenAddressDoesNotExist_receiveNotFound() {
        // Try to retrieve an address with a non-existing ID
        Long nonExistingAddressId = 999L; // Non-existent ID
        ResponseEntity<Address> response = testRestTemplate.exchange("/addresses/" + nonExistingAddressId, HttpMethod.GET, null, Address.class);

        // Assert that the response status is NOT FOUND
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void getAddress_whenAddressExists_receiveOk() {
        User user = createValidUser();
        userRepository.save(user);

        // Create and save a valid address
        Address savedAddress = createValidAddress(user);
        addressRepository.save(savedAddress);



        // Send GET request to fetch the saved address by ID
        ResponseEntity<Address> response = testRestTemplate.getForEntity("/addresses/" + savedAddress.getId(), Address.class);

        // Assert that response is OK and the address is correct
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getStreet()).isEqualTo("123 Pottery Lane");
    }

    private User createValidUser() {
        return User.builder()
                .email("teste23@gmail.com")
                .displayName("test-display")
                .password("P4ssword")
                .gender("female")
                .ssn("123456789")
                .phone("123456789")
                .birthDate(LocalDate.now())
                .build();
    }

    private Address createValidAddress(User user) {
        return Address.builder()
                .street("123 Pottery Lane")
                .number(100)
                .complement("Apt 101")
                .neighborhood("Downtown")
                .city("Brasília")
                .state("DF")
                .country("Brazil")
                .zip("70000000")
                .user(user)  // Associate with user
                .build();
    }
}


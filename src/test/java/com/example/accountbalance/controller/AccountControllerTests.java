package com.example.accountbalance.controller;

import reactor.core.publisher.Mono;

import com.example.accountbalance.controller.AccountController;
import com.example.accountbalance.convertor.AccountMapper;
import com.example.accountbalance.dto.AccountDto;
import com.example.accountbalance.model.Account;
import com.example.accountbalance.repository.AccountRepository;
import com.example.accountbalance.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@AutoConfigureTestDatabase
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AccountControllerTests {

    private WebTestClient webTestClient;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    private ObjectMapper objectMapper;

    private List<Account> accounts;

    @Autowired
    private AccountMapper accountMapper;

    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();

        String restTemplateURI = "http://localhost:8080";
        webTestClient = WebTestClient
                .bindToController(new AccountController(accountService))
                .configureClient()
                .baseUrl(restTemplateURI)
                .build();

        accountRepository.deleteAll();
        accounts = accountRepository.saveAll(
                List.of(Account.builder().name("Ivan Ivanov").build(), Account.builder().name("Martin Matev").build()));
    }

    @Test
    public void givenNewAccount_whenCreate_thenStatusCreated() throws JsonProcessingException {
        AccountDto account = AccountDto.builder().name("Ivelina Hristova").build();
        WebTestClient.BodyContentSpec bodyContentSpec = webTestClient
                .post()
                .uri("/accounts")
                .body(Mono.just(account), AccountDto.class)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody();
        String responseBody = castBodyToString(bodyContentSpec);

        AccountDto accountDto = objectMapper.readValue(responseBody, AccountDto.class);
        assertNotNull(accountDto);
        assertNotNull(accountDto.getUid());
        assertEquals("Ivelina Hristova", accountDto.getName());

    }

    @Test
    public void givenAccountId_whenGetById_thenStatusOk() throws JsonProcessingException {
        String uid = this.accounts.get(0).getUid();

        WebTestClient.BodyContentSpec bodyContentSpec = webTestClient
                .get()
                .uri("/accounts/{accountId}", uid)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody();

        String responseBody = castBodyToString(bodyContentSpec);
        AccountDto accountDto = objectMapper.readValue(responseBody, AccountDto.class);

        assertNotNull(accountDto);
        assertEquals(uid, accountDto.getUid());
        assertEquals("Ivan Ivanov", accountDto.getName());
    }

    @Test
    public void givenAccounts_whenGetPage_thenStatusOk() throws JsonProcessingException, JSONException {
        WebTestClient.BodyContentSpec bodyContentSpec = webTestClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("localhost")
                        .port("8080")
                        .path("/accounts")
                        .queryParam("page", 0)
                        .queryParam("size", 10)
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.numberOfElements")
                .isEqualTo(2)
                .jsonPath("$.content[0]")
                .isNotEmpty();

        JSONObject jsonObject = new JSONObject(castBodyToString(bodyContentSpec));
        List<AccountDto> accountsDto = objectMapper.readValue(jsonObject.get("content").toString(),
                new TypeReference<>() {});

        assertNotNull(accountsDto);
        assertEquals(this.accounts.stream().map(account -> accountMapper.sourceToDestination(account)).toList(),
                accountsDto);
    }

    private String castBodyToString(final WebTestClient.BodyContentSpec bodyContentSpec) {
        return new String(Objects.requireNonNull(bodyContentSpec.returnResult().getResponseBody()));
    }

}

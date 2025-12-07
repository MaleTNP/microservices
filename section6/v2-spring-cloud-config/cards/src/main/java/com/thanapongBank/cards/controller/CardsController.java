package com.thanapongBank.cards.controller;

import com.thanapongBank.cards.constants.CardsConstant;
import com.thanapongBank.cards.dto.CardsContactInfoDto;
import com.thanapongBank.cards.dto.CardsDto;
import com.thanapongBank.cards.dto.ResponseDto;
import com.thanapongBank.cards.service.ICardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CardsController {

    private final ICardService iCardService;

    public CardsController(ICardService iCardService) {
        this.iCardService = iCardService;
    }

    @Value("${build.version}")
    private String buildVersion;

    @Autowired
    private Environment environment;

    @Autowired
    private CardsContactInfoDto cardsContactInfoDto;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createCard(@RequestParam
                                                      @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
                                                      String mobileNumber) {
        iCardService.createCard(mobileNumber);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(CardsConstant.STATUS_201, CardsConstant.MESSAGE_200));
    }

    @GetMapping("/fetch")
    public ResponseEntity<CardsDto> fetchCard(@RequestParam
                                                  @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
                                                  String mobileNumber) {
        CardsDto cardsDto = iCardService.fetchCard(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK).body(cardsDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateCard(@Valid @RequestBody CardsDto cardsDto) {
        boolean isUpdated = iCardService.updateCard(cardsDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstant.STATUS_200, CardsConstant.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstant.STATUS_417, CardsConstant.MESSAGE_417_UPDATED));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteCard(@RequestParam
                                                      @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile Number must be 10 digits")
                                                      String mobileNumber) {
        boolean isDeleted = iCardService.deleteCard(mobileNumber);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(CardsConstant.STATUS_200, CardsConstant.MESSAGE_200));
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(CardsConstant.STATUS_417, CardsConstant.MESSAGE_417_DELETED));
        }
    }

    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(buildVersion);
    }

    @GetMapping("java-version")
    public ResponseEntity<String> getJavaVersion() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(environment.getProperty("java.home"));
    }


    @GetMapping("/contact-info")
    public ResponseEntity<CardsContactInfoDto> getContactInfo() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(cardsContactInfoDto);
    }
}

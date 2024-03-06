package com.mandacarubroker.controller;


import com.mandacarubroker.domain.realestatesfunds.GetResponseDataTransferObject;
import com.mandacarubroker.domain.realestatesfunds.RealEstatesFunds;
import com.mandacarubroker.domain.realestatesfunds.RegisterDataTransferObject;
import com.mandacarubroker.domain.realestatesfunds.ResponseDataTransferObject;
import com.mandacarubroker.repository.RealEstatesFundsRepository;
import com.mandacarubroker.service.RealEstatesFundsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("checkstyle:MissingJavadocType")
@RestController
@RequestMapping("ref")
public class RealEstatesFundsController {
  private final RealEstatesFundsService service;
  private final RealEstatesFundsRepository repository;

  public RealEstatesFundsController(
      RealEstatesFundsService realEstatesFundsService,
      RealEstatesFundsRepository realEstatesFundsRepository
  ) {
    this.service = realEstatesFundsService;
    this.repository = realEstatesFundsRepository;
  }

  @Operation(summary = "Get all real estate funds", method = "POST")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success"),
  })
  @GetMapping
  public ResponseEntity<GetResponseDataTransferObject> getAll() {
    List<RealEstatesFunds> response = service.getAll();

    return ResponseEntity
        .ok()
        .body(new GetResponseDataTransferObject(
            true,
            null,
            response
        ));
  }
  @Operation(summary = "Get a specific real estate funds", method = "POST")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success"),
  })
  @GetMapping("/{id}")
  public ResponseEntity<ResponseDataTransferObject> get(@PathVariable String id) {
    var response = service.get(id);
    if (response.isEmpty()) {
      return ResponseEntity
          .badRequest()
          .body(new ResponseDataTransferObject(
              false,
              "register not found",
              null
          ));
    }

    return ResponseEntity
        .ok()
        .body(new ResponseDataTransferObject(
            true,
            null,
            response
        ));
  }
  @Operation(summary = "Register a specific real estate funds", method = "POST")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success"),
  })
  @PostMapping("/register")
  public ResponseEntity<ResponseDataTransferObject> register(
      @RequestBody
      @Valid
      RegisterDataTransferObject data
  ) {
    var isRegistered = repository.getBySymbol(data.symbol());
    if (isRegistered != null) {
      return ResponseEntity
          .badRequest()
          .body(new ResponseDataTransferObject(
              false,
              "Already registered",
              null
          ));
    }
    RealEstatesFunds response = service.create(data);

    return ResponseEntity
        .ok()
        .body(new ResponseDataTransferObject(
            true,
            null,
            response
        ));
  }
  @Operation(summary = "Update a specific real estate funds", method = "POST")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success"),
  })
  @PutMapping("/{id}")
  public ResponseEntity<ResponseDataTransferObject> update(
      @PathVariable String id,
      @RequestBody @Valid RegisterDataTransferObject data
  ) {
    var response = service.update(id, data).orElse(null);
    if (response == null) {
      return ResponseEntity
          .status(500)
          .body(new ResponseDataTransferObject(
              false,
              "There was an error",
              null
          ));
    }

    return ResponseEntity
        .ok()
        .body(new ResponseDataTransferObject(
            true,
            null,
            response
        ));
  }
  @Operation(summary = "Delete a specific real estate funds", method = "POST")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success"),
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDataTransferObject> delete(
      @PathVariable String id
  ) {
    service.delete(id);

    return ResponseEntity
        .ok()
        .body(new ResponseDataTransferObject(
            true,
            "successfully deleted",
            null
        ));
  }
}

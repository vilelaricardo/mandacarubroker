package com.mandacarubroker.controller;

import com.mandacarubroker.domain.typesinvestiments.GetResponseDataTransferObject;
import com.mandacarubroker.domain.typesinvestiments.RegisterTypeInvestment;
import com.mandacarubroker.domain.typesinvestiments.ResponseDataTransferObject;
import com.mandacarubroker.domain.typesinvestiments.TypesInvestments;
import com.mandacarubroker.service.TypesInvestmentsService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("checkstyle:MissingJavadocType")
@RestController
@RequestMapping("assets")
public class InvestmentsController {
  private final TypesInvestmentsService investmentsService;

  public InvestmentsController(
      TypesInvestmentsService typesInvestmentsService
  ) {
    this.investmentsService = typesInvestmentsService;
  }
  @Operation(summary = "Get all Investments", method = "POST")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success"),
  })
  @GetMapping
  public ResponseEntity<GetResponseDataTransferObject> getAll() {
    List<TypesInvestments> response = investmentsService.getAllInvestments();
    return ResponseEntity
        .ok()
        .body(new GetResponseDataTransferObject(
            true,
            null,
            response
        ));
  }
  @Operation(summary = "Register a investment", method = "POST")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success"),
  })
  @PostMapping("/register")
  public ResponseEntity<ResponseDataTransferObject> create(
      @RequestBody @Valid RegisterTypeInvestment data
  ) {
    TypesInvestments response = this.investmentsService.create(data);

    return ResponseEntity
        .ok()
        .body(new ResponseDataTransferObject(
          true,
          "successfully registered",
          response
        ));
  }
  @Operation(summary = "Delete a specific investment", method = "POST")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "success"),
  })
  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseDataTransferObject> delete(@PathVariable String id) {
    investmentsService.delete(id);

    return ResponseEntity
        .ok()
        .body(new ResponseDataTransferObject(
            true,
            "successfully deleted",
            null
        ));
  }
}

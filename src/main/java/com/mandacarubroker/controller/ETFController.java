package com.mandacarubroker.controller;

import com.mandacarubroker.domain.etf.ETF;
import com.mandacarubroker.domain.etf.GetResponseDataTransferObject;
import com.mandacarubroker.domain.etf.RegisterDataTransferObject;
import com.mandacarubroker.domain.etf.ResponseDataTransferObject;
import com.mandacarubroker.repository.ETFRepository;
import com.mandacarubroker.service.ETFService;
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
@RequestMapping("etf")
public class ETFController {
  private final ETFService service;
  private final ETFRepository repository;

  public ETFController(
      ETFRepository etfRepository,
      ETFService etfService
  ) {
    this.service = etfService;
    this.repository = etfRepository;
  }

  @GetMapping
  public ResponseEntity<GetResponseDataTransferObject> getAll() {
    List<ETF> response = service.getAll();

    return ResponseEntity
        .ok()
        .body(new GetResponseDataTransferObject(
            true,
            null,
            response
        ));
  }

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
    ETF response = service.create(data);

    return ResponseEntity
        .ok()
        .body(new ResponseDataTransferObject(
            true,
            null,
            response
        ));
  }

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

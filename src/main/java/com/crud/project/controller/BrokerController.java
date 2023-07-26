package com.crud.project.controller;

import com.crud.project.controller.util.DataControllerUtil;
import com.crud.project.model.Broker;
import com.crud.project.request.BrokerRequest;
import com.crud.project.response.BrokerResponse;
import com.crud.project.service.DataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/broker")
@Tag(name = "Broker Controller")
@PreAuthorize("hasAuthority('Broker')")
public class BrokerController {

    @Autowired
    DataService dataService;

    @Autowired
    DataControllerUtil util;

    @Operation(summary = "Get All Broker", description = "Get All Brokers Data")
    @GetMapping
    public ResponseEntity<List<BrokerResponse>> getAllBroker() {
        List<Broker> brokers = dataService.getBrokers();
        return new ResponseEntity<>(util.convertToResponseList(brokers), HttpStatus.OK);
    }

    @Operation(summary = "Get Broker By Id", description = "Get Broker Data by Id")
    @GetMapping("/{id}")
    public ResponseEntity<BrokerResponse> getBrokerById(@PathVariable("id") long id) {
        Broker broker = dataService.getBrokerById(id);
        return new ResponseEntity<>(util.convertToBrokerResponse(broker), HttpStatus.OK);
    }

    @Operation(summary = "Add Broker", description = "Add New Broker Data")
    @PostMapping
    public ResponseEntity<?> addBroker(@RequestBody BrokerRequest broker) {
        dataService.addBroker(broker);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Update Broker", description = "Update Broker Data by Id")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateBroker(
            @PathVariable("id") long id,
            @RequestBody BrokerRequest broker) throws IOException {
        dataService.updateBroker(id, broker);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Delete Broker", description = "Delete Broker Data by Id")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBroker(@PathVariable("id") long id) {
        dataService.deleteBroker(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

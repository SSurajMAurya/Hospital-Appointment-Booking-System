package com.hospital.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.main.dto.AppointmentDto;
import com.hospital.main.requestHelper.AppointmentRequest;
import com.hospital.main.service.AppointmentService;
import com.hospital.main.utility.ApiResponseMessage;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    //create appointments

    @PostMapping
    public ResponseEntity<AppointmentDto> createAppointmentHandler(@RequestBody AppointmentRequest appointmentRequest){
      return new ResponseEntity<>( appointmentService.createAppointment(appointmentRequest) , HttpStatus.CREATED);
   
    }

    //update

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> updateAppointmentHandler(@RequestBody AppointmentRequest appointmentRequest ,@PathVariable Long id){
        return ResponseEntity.ok( appointmentService.updateAppointment(appointmentRequest, id));
     
      }

    //get all

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAllAppointment(){
        return ResponseEntity.ok(appointmentService.getAllAppointment());
    }

    //delete

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseMessage> deleteAppointment(@PathVariable Long id){
        
        appointmentService.deleteAppointment(id);

        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Appointment deleted successfully").success(true).build();

        return ResponseEntity.ok(responseMessage);
    }

}

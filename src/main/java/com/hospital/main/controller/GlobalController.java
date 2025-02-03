// package com.hospital.main.controller;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.ControllerAdvice;
// import org.springframework.web.bind.annotation.ExceptionHandler;

// import com.hospital.main.exceptions.ResourceNotFound;

// @ControllerAdvice
// public class GlobalController {

//     @ExceptionHandler(ResourceNotFound.class)
//     public ResponseEntity<String> ResouceNotFoundHandler(ResourceNotFound ex){

//         return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
//     }

// }

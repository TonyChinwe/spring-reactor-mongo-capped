package com.phisoft.springreactormongocapped.controller;


import com.phisoft.springreactormongocapped.dto.EmployeeDto;
import com.phisoft.springreactormongocapped.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.time.Duration;

/**
 * The Api Layer that interfaces with the clients
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    /**
     * Fetches all the saved Employee in mongodb database
     * @return Flux<EmployeeDto></EmployeeDto>
     */
    @GetMapping
    public Flux<EmployeeDto> getEmployees(){
        return service.getEmployees();
    }

    /**
     * Streams all the saved Employee in mongodb database
     * @return them as Flux<EmployeeDto></EmployeeDto>
     */
    @GetMapping(value = "/stream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EmployeeDto> streamEmployees(){
        return service.streamEmployees().delayElements(Duration.ofSeconds(1));
    }

    /**
     * Fetches an Employee using employee id
     * @param id employee id
     * @return Mono<EmployeeDto></EmployeeDto>
     */
    @GetMapping("/{id}")
    public Mono<EmployeeDto> getEmployee(@PathVariable("id") String id){
        return service.getEmployee(id);
    }

    /**
     * Fetches list of Employee using employee based on salary range
     * @param min minimum salary range
     * @param max maximum salary range
     * @return Flux<EmployeeDto></EmployeeDto>
     */
    @GetMapping("/salary-range")
    public Flux<EmployeeDto> getSalaryRange(@RequestParam("min") Long min,@RequestParam("max") Long max){
        return service.getSalaryInRange(min,max);
    }

    /**
     * Saves an Employee to our mongodb database
     * @param employeeDto Mono of employee dto to be saved
     * @return Mono<EmployeeDto></EmployeeDto>
     */
    @PostMapping("/save")
    public Mono<EmployeeDto> save(@RequestBody Mono<EmployeeDto> employeeDto){
     return service.saveEmployee(employeeDto);
    }


    /**
     * Update an already saved Employee in our mongodb database using the Employee id
     * @param employeeDto Mono of employee dto to be saved
     * @param id id of the Employee to be updated
     * @return Mono<EmployeeDto></EmployeeDto>
     */
    @PutMapping("/update/{id}")
    public Mono<EmployeeDto> update(@RequestBody Mono<EmployeeDto> employeeDto,@PathVariable("id") String id){
        return service.updateEmployee(employeeDto,id);
    }

    /**
     * Delete already saved Employee from our mongodb database using the Employee id
     * @param id id of the Employee to be deleted
     * @return Mono<Void></EmployeeDto>
     */
    @DeleteMapping("/delete/{id}")
    public Mono<Void> delete(@PathVariable("id") String id){
        return service.deleteEmployee(id);
    }
}

package com.example.timesheet.ctrl;

import com.example.common.config.enums.SortOrderEnum;
import com.example.common.dto.response.InsertSuccessResponse;
import com.example.common.dto.response.PageResponse;
import com.example.common.dto.response.SuccessResponse;
import com.example.common.util.SearchUtil;
import com.example.timesheet.dto.AddSalaryRequest;
import com.example.timesheet.dto.UpdateSalaryRequest;
import com.example.timesheet.entity.Salary;
import com.example.timesheet.service.SalaryService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.webjars.NotFoundException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin/salary")
public class SalaryMgtCtrl {

    @Autowired
    private SalaryService salaryService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public InsertSuccessResponse create(@Valid @RequestBody AddSalaryRequest salary) {
        Salary s = salaryService.save(salary);
        return new InsertSuccessResponse(s.getId());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse updateAllFields(@PathVariable String id, @Valid @RequestBody UpdateSalaryRequest salary)
            throws NotFoundException {
        Salary s = salaryService.updateAllFields(id, salary);
        return new InsertSuccessResponse(s.getId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse deleteByIdIn(@RequestBody List<String> ids) {
        salaryService.deleteByIdIn(ids);
        return new SuccessResponse();
    }

    @PostMapping("/active/{id}/{active}")
    @ResponseStatus(HttpStatus.OK)
    public SuccessResponse setActive(@PathVariable("id") String id, @PathVariable("active") boolean isActive)
            throws NotFoundException {
        Salary s = salaryService.setActive(id, isActive);
        return new InsertSuccessResponse(s.getId());
    }

    @GetMapping("/{id}")
    public Optional<Salary> findById(@PathVariable("id") String id) throws NotFoundException {
        return salaryService.findById(id);
    }

    @GetMapping
    public PageResponse<Salary> findAll(@PositiveOrZero @RequestParam(required = false, defaultValue = "0") Integer page,
                                        @Positive @RequestParam(required = false) Integer size, @RequestParam(required = false) String sort,
                                        @RequestParam(required = false) SortOrderEnum order) {
        Pageable pageable = SearchUtil.getPageableFromParam(page, size, sort, order);
        Page<Salary> pageData = salaryService.findAll(pageable);
        return new PageResponse<>(pageData);
    }
}

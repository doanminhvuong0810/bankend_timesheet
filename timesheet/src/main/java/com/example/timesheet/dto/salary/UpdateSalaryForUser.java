
package com.example.timesheet.dto.salary;

import javax.validation.constraints.NotNull;

public class UpdateSalaryForUser {
    @NotNull
    private String id;
    @NotNull
    private String userId;
    @NotNull
    private Double salary;

    public UpdateSalaryForUser() {
    }

    public String getId() {
        return this.id;
    }

    public String getUserId() {
        return this.userId;
    }

    public Double getSalary() {
        return this.salary;
    }

    public void setSalary(final Double salary) {
        this.salary = salary;
    }
}

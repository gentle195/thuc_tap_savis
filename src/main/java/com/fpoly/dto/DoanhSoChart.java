package com.fpoly.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoanhSoChart {
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;
    private Double value;

    public DoanhSoChart(LocalDate date){
        this.date = date;
        this.value = 0D;
    }
}

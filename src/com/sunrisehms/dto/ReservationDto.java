/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sunrisehms.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author User
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private Long Id;
    private CustomerDto customerDto;
    private Date from;
    private Date to;
    private Integer status;
    private List<Long> roomIds;
    private Integer packageId;
    private BigDecimal price;
    private Date checkIn;
    private Date checkOut;
}

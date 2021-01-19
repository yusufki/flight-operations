package com.yusuf.finartz.model;

import com.yusuf.finartz.bean.IdObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TicketDTO extends IdObject {

    private long id;
    private Flight flight;
    private long customerId;
    private float price;
    private String currency;
    private String paymentCardNumber;
    private String status;
}

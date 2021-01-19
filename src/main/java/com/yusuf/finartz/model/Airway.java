package com.yusuf.finartz.model;

import com.yusuf.finartz.bean.IdObject;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity

@Getter
@Setter
@ToString
public class Airway extends IdObject {

    private String name;

    @OneToMany(mappedBy = "airway", fetch = FetchType.LAZY)
    private List<Flight> flight;
}

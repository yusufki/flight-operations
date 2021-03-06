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
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"name"})
})
public class Airport extends IdObject {

    private String name;



}

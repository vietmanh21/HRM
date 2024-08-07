package com.example.hrm.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class ResetRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private Date expiredDate;

}

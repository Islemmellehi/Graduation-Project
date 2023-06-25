package com.example.vic.Client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLIENT")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CLIENT", nullable = false)
    private Integer id;

    @Column(name = "FIRST_NAME")

    private String name;

    @Column(name = "LAST_NAME")
    private String lastname;

    @Column(name = "PASSWORD")


    private String password;

    @Column(name = "MAIL_CLIENT")

    private String email;

    @Column(name = "SF_CLIENT")

    private String status;

    @Column(name = "TEL_CLIENT")
    private String tel;

    @Column(name = "NB_ENF")

    private Integer nbenf;

    @Column(name = "NB_BEBE")

    private Integer nbbeb;



}

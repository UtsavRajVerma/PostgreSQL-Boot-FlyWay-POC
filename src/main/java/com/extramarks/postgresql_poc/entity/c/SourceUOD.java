package com.extramarks.postgresql_poc.entity.c;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "user_other_details",schema = "Source")
public class SourceUOD {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int user_id_uod;

    private String key_name;

    private String value;

    private String created_date;
}

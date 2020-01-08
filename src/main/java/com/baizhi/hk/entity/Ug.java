package com.baizhi.hk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ug implements Serializable {
    @Id
    private String id;
    private String userId;
    private String guruId;
}

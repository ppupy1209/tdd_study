package com.study.dayonetest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "student_pass")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class StudentPass {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_pass_id")
    private Long id;

    @Column(name = "exam")
    private String exam;

    @Column(name = "studentName")
    private String studentName;

    @Column(name = "avgScore")
    private Double avgScore;

}

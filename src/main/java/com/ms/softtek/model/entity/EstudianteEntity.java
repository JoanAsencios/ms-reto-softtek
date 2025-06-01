package com.ms.softtek.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "estudiante")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstudianteEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "nombre", nullable = false, length = 100)
  private String nombre;

  @Column(name = "apellido", nullable = false, length = 100)
  private String apellido;

  @Column(name = "email", nullable = false, length = 150, unique = true)
  private String email;

  @Column(name = "creditos")
  private int creditos;

  @Column(name = "semestre")
  private int semestre;

  @Column(name = "promedio")
  private int promedio;

}

package com.ms.softtek.service;

import com.ms.softtek.model.Estudiante;
import com.ms.softtek.model.EstudianteRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EstudianteService {

  Flux<Estudiante> getAllEstudiantes();
  Mono<Estudiante> getEstudianteById(int id);
  Mono<Estudiante> createEstudiante(EstudianteRequest request);
  Mono<Estudiante> updateEstudiante(int id, EstudianteRequest request);
  Mono<Void> deleteEstudiante(int id);

}

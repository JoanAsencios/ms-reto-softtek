package com.ms.softtek.service.impl;

import com.ms.softtek.model.Estudiante;
import com.ms.softtek.model.EstudianteRequest;
import com.ms.softtek.model.entity.EstudianteEntity;
import com.ms.softtek.repository.EstudianteRepository;
import com.ms.softtek.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class EstudianteServiceImpl implements EstudianteService {

  private final EstudianteRepository repository;

  @Override
  public Flux<Estudiante> getAllEstudiantes() {
    return Mono.fromCallable(repository::findAll)
        .flatMapMany(Flux::fromIterable)
        .map(this::toEstudiante);
  }

  @Override
  public Mono<Estudiante> getEstudianteById(int id) {
    return Mono.fromCallable(() -> repository.findById(id))
        .flatMap(optional -> optional.map(Mono::just).orElse(Mono.empty()))
        .map(this::toEstudiante);
  }

  @Override
  public Mono<Estudiante> createEstudiante(EstudianteRequest request) {
    return Mono.fromCallable(() -> repository.save(toEntity(request)))
        .map(this::toEstudiante);
  }

  @Override
  public Mono<Estudiante> updateEstudiante(int id, EstudianteRequest request) {
    return Mono.fromCallable(() -> repository.findById(id))
        .flatMap(optional -> optional.map(existing -> {
          existing.setNombre(request.getNombre());
          existing.setApellido(request.getApellido());
          existing.setEmail(request.getEmail());
          existing.setCreditos(request.getCreditos());
          existing.setSemestre(request.getSemestre());
          existing.setPromedio(request.getPromedio());
          return repository.save(existing);
        }).map(Mono::just).orElse(Mono.empty()))
        .map(this::toEstudiante);
  }

  @Override
  public Mono<Void> deleteEstudiante(int id) {
    return Mono.fromRunnable(() -> repository.deleteById(id));
  }

  // Mapping
  private Estudiante toEstudiante(EstudianteEntity entity) {
    return new Estudiante()
        .id(entity.getId())
        .nombre(entity.getNombre())
        .apellido(entity.getApellido())
        .email(entity.getEmail())
        .creditos(entity.getCreditos())
        .semestre(entity.getSemestre())
        .promedio(entity.getPromedio());
  }

  private EstudianteEntity toEntity(EstudianteRequest request) {
    return EstudianteEntity.builder()
        .nombre(request.getNombre())
        .apellido(request.getApellido())
        .email(request.getEmail())
        .creditos(request.getCreditos())
        .semestre(request.getSemestre())
        .promedio(request.getPromedio())
        .build();
  }
}

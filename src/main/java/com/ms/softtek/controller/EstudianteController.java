package com.ms.softtek.controller;

import com.ms.softtek.model.Estudiante;
import com.ms.softtek.model.EstudianteRequest;
import com.ms.softtek.service.EstudianteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class EstudianteController implements EstudianteControllerApi{

  private final EstudianteService service;

  @Override
  public Mono<ResponseEntity<Estudiante>> getEstudiante(Integer idEstudiante,
      ServerWebExchange exchange) {

    return service.getEstudianteById(idEstudiante)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @Override
  public Mono<ResponseEntity<Flux<Estudiante>>> getAllEstudiantes(
      ServerWebExchange exchange) {

    Flux<Estudiante> estudiantesFlux = service.getAllEstudiantes();
    return Mono.just(ResponseEntity.ok(estudiantesFlux));
  }

  @Override
  public Mono<ResponseEntity<Estudiante>> createEstudiante(
      Mono<EstudianteRequest> estudianteRequest, ServerWebExchange exchange) {

    return estudianteRequest.flatMap(request ->
        service.createEstudiante(request)
            .map(ResponseEntity::ok)
    );
  }

  @Override
  public Mono<ResponseEntity<Estudiante>> updateEstudiante(Integer idEstudiante,
      Mono<EstudianteRequest> estudianteRequest, ServerWebExchange exchange) {

    return estudianteRequest.flatMap(req ->
            service.updateEstudiante(idEstudiante, req)
        ).map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @Override
  public Mono<ResponseEntity<Void>> deleteEstudiante(Integer idEstudiante,
      ServerWebExchange exchange) {

    return service.deleteEstudiante(idEstudiante)
        .then(Mono.just(ResponseEntity.noContent().build()));
  }
}

package br.com.vollmed.Api.model.consulta;

import java.time.LocalDateTime;

import br.com.vollmed.Api.model.medico.Medico;
import br.com.vollmed.Api.model.paciente.Paciente;
import jakarta.persistence.*;
import lombok.Data;

@Table(name = "consultas")
@Entity
@Data //traz os métodos getters, setters, equals, hashcode e toString
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String observacao;

    private Medico medico;
    private Paciente paciente;

    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    private Status status;
}

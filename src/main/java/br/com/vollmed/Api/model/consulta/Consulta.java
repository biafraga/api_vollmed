package br.com.vollmed.Api.model.consulta;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.Data;

import br.com.vollmed.Api.model.medico.Medico;
import br.com.vollmed.Api.model.paciente.Paciente;

@Table(name = "consultas")
@Entity
@Data //traz os métodos getters, setters, equals, hashcode e toString
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String observacao;

    @JoinColumn(name = "medicoId")
    @ManyToOne
    private Medico medico;

    @JoinColumn(name = "pacienteId")
    @ManyToOne
    private Paciente paciente;

    private LocalDateTime data;

    @Enumerated(EnumType.STRING)
    private Status status;
}

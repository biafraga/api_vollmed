package br.com.vollmed.Api.model.paciente;

import jakarta.persistence.*;
import lombok.*;

@Entity 
@Table(name = "pacientes") 
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Paciente {

    private String nome;
    private String email;
    private String telefone;
    private String cpf;

    
}

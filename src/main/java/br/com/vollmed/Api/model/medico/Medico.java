package br.com.vollmed.Api.model.medico;

import jakarta.persistence.*;
import lombok.*;

@Entity // SPRING JPA - Informa que a classe é uma entidade, ou seja, será uma tabela no BD.
@Table(name = "medicos") // SPRING JPA - *Opcional, altera o nome da tabela de medico para medicos
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Medico {
    // atributos -> características -> variáveis
    private String nome;
    private String email;
    private String telefone;
    private String crm;
    private Especialidade especialidade;
}

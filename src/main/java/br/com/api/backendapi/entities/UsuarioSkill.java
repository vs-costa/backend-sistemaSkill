package br.com.api.backendapi.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import br.com.api.backendapi.enums.LevelSkillEnum;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_usuario_skill")
@Getter
@Setter
public class UsuarioSkill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_usuario_skill")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @NotBlank(message = "O usuário não pode ser nulo ou vazio")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    @NotBlank(message = "A skill não pode ser nula ou vazia")
    private Skill skill;

    @NotBlank(message = "O nível de habilidade não pode ser nulo ou vazio")
    private LevelSkillEnum level;

}

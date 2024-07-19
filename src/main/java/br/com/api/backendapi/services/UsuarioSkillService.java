package br.com.api.backendapi.services;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.api.backendapi.dto.UsuarioSkillAtualizarDTO;
import br.com.api.backendapi.dto.UsuarioSkillDTO;
import br.com.api.backendapi.dto.paginacao.PageResponse;
import br.com.api.backendapi.entities.Skill;
import br.com.api.backendapi.entities.Usuario;
import br.com.api.backendapi.entities.UsuarioSkill;
import br.com.api.backendapi.enums.LevelSkillEnum;
import br.com.api.backendapi.exceptions.ResourceNotFoundException;
import br.com.api.backendapi.mapper.UsuarioSkillMapper;
import br.com.api.backendapi.repositories.SkillRepository;
import br.com.api.backendapi.repositories.UsuarioRepository;
import br.com.api.backendapi.repositories.UsuarioSkillRepository;

@Service
public class UsuarioSkillService {

    @Autowired
    UsuarioSkillRepository usuarioSkillRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    UsuarioSkillMapper usuarioSkillMapper;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioSkillService.class);

    // GET ID
    public UsuarioSkillDTO buscarPorId(Long id) {
        UsuarioSkill usuarioSkill = usuarioSkillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário Skill não encontrado com ID: " + id));
        return usuarioSkillMapper.converterUsuarioSkillDTO(usuarioSkill);
    }

    // GET Listar por usuário com paginação
    public PageResponse<UsuarioSkillDTO> listarPorUsuario(Long usuarioId, Pageable pageable) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + usuarioId));

        Page<UsuarioSkill> usuarioSkills = usuarioSkillRepository.findByUsuario(usuario, pageable);
        List<UsuarioSkillDTO> usuarioSkillDTOs = usuarioSkills.map(usuarioSkillMapper::converterUsuarioSkillDTO).getContent();

        return new PageResponse<>(
                usuarioSkillDTOs,
                usuarioSkills.getNumber(),
                usuarioSkills.getSize(),
                usuarioSkills.getTotalElements(),
                usuarioSkills.getTotalPages(),
                usuarioSkills.isLast(),
                usuarioSkills.isFirst()
        );
    }

    // GET Listar todos
    public PageResponse<UsuarioSkillDTO> listarTodos(int pagina, int tamanhoPagina) {
        Page<UsuarioSkill> page = usuarioSkillRepository.findAll(PageRequest.of(pagina, tamanhoPagina));
        List<UsuarioSkillDTO> conteudo = page.getContent().stream()
                .map(usuarioSkillMapper::converterUsuarioSkillDTO)
                .collect(Collectors.toList());

        return new PageResponse<>(conteudo, pagina, tamanhoPagina, page.getTotalElements(), page.getTotalPages(),
                page.isLast(), page.isFirst());
    }

    // POST
    public UsuarioSkillDTO salvar(UsuarioSkillDTO usuarioSkillDTO) {
        try {
            UsuarioSkill novoUsuarioSkill = new UsuarioSkill();

            // Buscar o usuário correspondente ao ID fornecido
            Optional<Usuario> optionalUsuario = usuarioRepository.findById(usuarioSkillDTO.getUsuarioId());
            if (!optionalUsuario.isPresent()) {
                throw new RuntimeException("Usuário não encontrado com o ID: " + usuarioSkillDTO.getUsuarioId());
            }
            Usuario usuario = optionalUsuario.get();
            novoUsuarioSkill.setUsuario(usuario);

            // Buscar a skill correspondente ao ID fornecido
            Optional<Skill> optionalSkill = skillRepository.findById(usuarioSkillDTO.getSkillId());
            if (!optionalSkill.isPresent()) {
                throw new RuntimeException("Skill não encontrada com o ID: " + usuarioSkillDTO.getSkillId());
            }
            Skill skill = optionalSkill.get();
            novoUsuarioSkill.setSkill(skill);

            novoUsuarioSkill.setLevel(usuarioSkillDTO.getLevel());

            UsuarioSkill usuarioSkillSalvo = usuarioSkillRepository.save(novoUsuarioSkill);
            return usuarioSkillMapper.converterUsuarioSkillDTO(usuarioSkillSalvo);
        } catch (DataIntegrityViolationException | PersistenceException e) {
            logger.error("Erro de integridade de dados ao salvar UsuarioSkill", e);
            throw e;
        } catch (RuntimeException e) {
            logger.error("Erro genérico ao salvar UsuarioSkill", e);
            throw e;
        }
    }

    // PUT
    public UsuarioSkillAtualizarDTO atualizar(long id, UsuarioSkillAtualizarDTO usuarioSkillDTO) {
        UsuarioSkill registroAntigo = usuarioSkillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário Skill não encontrado com ID: " + id));

        if (usuarioSkillDTO.getLevel() != null) {
            registroAntigo.setLevel(usuarioSkillDTO.getLevel());
        }
        UsuarioSkillAtualizarDTO usuarioSkillConvertido = usuarioSkillMapper
                .converterUsuarioSkillAtualizarDTO(registroAntigo);
        usuarioSkillRepository.save(registroAntigo);
        return usuarioSkillConvertido;
    }

    // DELETE
    public void excluir(Long id) {
        UsuarioSkill usuarioSkill = usuarioSkillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário Skill não encontrado com ID: " + id));
        usuarioSkillRepository.delete(usuarioSkill);
    }

    // Filtros
    public List<UsuarioSkillDTO> buscarPorLevel(LevelSkillEnum level) {
        List<UsuarioSkill> usuarioSkills = usuarioSkillRepository.findByLevel(level);
        return converterParaDTO(usuarioSkills);
    }

    public List<UsuarioSkillDTO> buscarPorSkillNome(String skillNome) {
        List<UsuarioSkill> usuarioSkills = usuarioSkillRepository.findBySkillNome(skillNome);
        return converterParaDTO(usuarioSkills);
    }

    public List<UsuarioSkillDTO> buscarPorUsuarioIdESkillNome(Long usuarioId, String skillNome) {
        List<UsuarioSkill> usuarioSkills = usuarioSkillRepository.findByUsuarioIdAndSkillNome(usuarioId, skillNome);
        return converterParaDTO(usuarioSkills);
    }
    
 // Listar todas as UsuarioSkills ordenadas por nome da Skill em ordem alfabética (ascendente)
    public List<UsuarioSkillDTO> listarTodasOrdenadasPorNomeSkillAsc() {
        List<UsuarioSkill> usuarioSkills = usuarioSkillRepository.findAllByOrderBySkillNomeAsc();
        return usuarioSkills.stream().map(usuarioSkillMapper::converterUsuarioSkillDTO).collect(Collectors.toList());
    }

    // Listar todas as UsuarioSkills ordenadas por nome da Skill em ordem alfabética (descendente)
    public List<UsuarioSkillDTO> listarTodasOrdenadasPorNomeSkillDesc() {
        List<UsuarioSkill> usuarioSkills = usuarioSkillRepository.findAllByOrderBySkillNomeDesc();
        return usuarioSkills.stream().map(usuarioSkillMapper::converterUsuarioSkillDTO).collect(Collectors.toList());
    }
    
 // GET Listar por nome da skill
    public List<UsuarioSkillDTO> listarPorNomeSkill(String skillNome) {
        List<UsuarioSkill> usuarioSkills = usuarioSkillRepository.findBySkillNome(skillNome);
        return converterParaDTO(usuarioSkills);
    }

    private List<UsuarioSkillDTO> converterParaDTO(List<UsuarioSkill> usuarioSkills) {
        List<UsuarioSkillDTO> dtos = new ArrayList<>();
        for (UsuarioSkill usuarioSkill : usuarioSkills) {
            UsuarioSkillDTO dto = usuarioSkillMapper.converterUsuarioSkillDTO(usuarioSkill);
            Skill skill = usuarioSkill.getSkill();
            if (skill != null) {
                dto.setSkillNome(skill.getNome());
                dto.setSkillDescricao(skill.getDescricao());
                dto.setSkillImagem(skill.getImagem());
            }
            dto.setUsuarioId(usuarioSkill.getUsuario().getId());
            dtos.add(dto);
        }
        return dtos;
    }

    // Ordenar por Level
    public List<UsuarioSkillDTO> listarTodasOrdenadasPorLevel() {
        List<UsuarioSkill> usuarioSkills = usuarioSkillRepository.findAll(Sort.by(Sort.Direction.ASC, "level"));
        return usuarioSkills.stream().map(usuarioSkillMapper::converterUsuarioSkillDTO).collect(Collectors.toList());
    }
}

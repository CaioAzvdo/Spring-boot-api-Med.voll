package med.voll.api.controller;

import jakarta.validation.Valid;
import lombok.Getter;

import med.voll.api.endereco.Endereco;
import med.voll.api.medico.*;
import org.aspectj.weaver.ast.Var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.List;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    @Autowired
    private MedicoRepository repository;


    @PostMapping
    @Transactional
    public ResponseEntity Cadastrar(@RequestBody @Valid DadosCadastrosMedicos dados, UriComponentsBuilder uriBuilder){
        var medico = new Medico(dados);
        repository.save(medico);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));

    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedico>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao){
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity Atualizar(@RequestBody @Valid DadosAtualizarMedicos dados){
        var medico = repository.getReferenceById(dados.id());
        medico.AtualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));

    }
    @DeleteMapping("/deletar/{id}")
    @Transactional
    public ResponseEntity Deletar(@PathVariable Long id){
        //repository.deleteById(id);
        var medico = repository.getReferenceById(id);
        medico.excluir();
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/perfil/{id}")
    public ResponseEntity Detalhar(@PathVariable Long id){
        var medico = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }
}

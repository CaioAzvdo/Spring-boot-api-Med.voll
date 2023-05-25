package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.paciente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity<DadosDetalhamentoPaciente> Cadastrar(@RequestBody @Valid DadosCadastrosPacientes dados, UriComponentsBuilder uriBuilder){
        var paciente = new Paciente(dados);
        repository.save(paciente);
        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping("/listar")
    public ResponseEntity<Page<DadosListagemPaciente>> listar(@PageableDefault(size = 10, sort = {"nome"})Pageable paginacao){
            var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemPaciente::new);
            return ResponseEntity.ok(page);
    }

    @PutMapping("/atualizar")
    @Transactional
    public ResponseEntity Atualizar(@RequestBody @Valid DadosAtualizarPaciente dados){
        var paciente = repository.getReferenceById(dados.id());
        paciente.AtualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity deletar(@PathVariable Long id){
//        var paciente = repository.getReferenceById(id);
//        paciente.excluir();
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("ativar/{id}")
    @Transactional
    public ResponseEntity ativarConta(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);
        paciente.ativar();
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/detalhar/{id}")
    public ResponseEntity<DadosDetalhamentoPaciente> detalhar(@PathVariable Long id){
        var paciente = repository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }




}

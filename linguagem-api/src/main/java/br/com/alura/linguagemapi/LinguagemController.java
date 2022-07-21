package br.com.alura.linguagemapi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LinguagemController {

    @Autowired
    private LinguagemRepository repositorio;
            
    @GetMapping("/linguagens")
    public List<Linguagem> obterLinguagens(){
        List<Linguagem> linguagens = repositorio.findAll((Sort.by(Sort.Direction.ASC, "ranking")));
        return linguagens;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/linguagens/cadastrar")
    public Linguagem cadastrarLinguagem(@RequestBody Linguagem linguagem){
        Linguagem linguagemSalva = repositorio.save(linguagem);
        return linguagemSalva;
    }
    
    @PutMapping("/linguagens/atualizar/{id}")
    public ResponseEntity<Linguagem> atualizarLinguagem(@PathVariable String id,@RequestBody Linguagem linguagem) {
        Linguagem linguagemAtualizada = repositorio.findById(id).orElseThrow();

        linguagemAtualizada.setTitle(linguagem.getTitle());
        linguagemAtualizada.setImage(linguagem.getImage());
        linguagemAtualizada.setRanking(linguagem.getRanking());
        

        repositorio.save(linguagemAtualizada);

        return ResponseEntity.ok(linguagemAtualizada);
    }

    @DeleteMapping("linguagens/apagar/{id}")
    public String apagarLinguagem(@PathVariable String id){
        repositorio.deleteById(id);
        
        return "Apagado com sucesso!";
    }    
    
}

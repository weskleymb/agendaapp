package br.senac.rn.agendaescolar.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.senac.rn.agendaescolar.models.Aluno;

public class AlunoService {

    public List<Aluno> buscar() {
//        try {
            String endereco = "http://10.1.1.21:8080/agendaweb/resources/aluno/todos";
            Gson gson = new Gson();
            AcessoWS ws = new AcessoWS();
            String json = ws.httpGet(endereco);
            Type type = new TypeToken<List<Aluno>>(){}.getType();
            List<Aluno> alunos = new ArrayList<>();
            alunos = gson.fromJson(json, type);
            return alunos;
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
    }

}

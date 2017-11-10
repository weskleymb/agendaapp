package br.senac.rn.agendaescolar.views;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.senac.rn.agendaescolar.models.Aluno;

public class AlunoListaActivity extends AppCompatActivity {

    private ListView lvAlunos;
    private Button btCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno_lista);
        inicializarComponentes();
        definirEventos();
    }

    private void inicializarComponentes() {
        lvAlunos = (ListView) findViewById(R.id.lista_alunos);
        btCadastrar = (Button) findViewById(R.id.cadastrar);

        List<Aluno> alunos = new ArrayList<Aluno>();

        alunos.add(new Aluno(
                "Lucio Flávio",
                "Av Alexandrino de Alencar, 1592",
                "996360721",
                "http://www.lemavorum.com.br",
                10.0)
        );

        alunos.add(new Aluno(
                "Carlos Bandeira",
                "R da Diatomita, 357",
                "996073082",
                "http://www.facebook.com/doalceycarlos",
                10.0)
        );

        alunos.add(new Aluno(
                "Jalielson Andrade",
                "R Baraúna, 408",
                "988698986",
                "http://www.facebook.com/jalielson.andrade",
                10.0)
        );

        alunos.add(new Aluno(
                "Janna Barbosa",
                "R Sebastião Barreto, 4449",
                "988881402",
                "http://www.facebook.com/jannabarbosa",
                10.0)
        );

        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(
                this,
                android.R.layout.simple_list_item_1,
                alunos);

        lvAlunos.setAdapter(adapter);
    }

    private void definirEventos() {
//        btCadastrar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Context contexto = AlunoListaActivity.this;
//                String mensagem = "Clicou no botão";
//                int duracao = Toast.LENGTH_LONG;
//                Toast.makeText(contexto, mensagem, duracao).show();
//                tvResultado.setText(etNome.getText().toString());
//            }
//        });
    }

}

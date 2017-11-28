package br.senac.rn.agendaescolar.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import br.senac.rn.agendaescolar.daos.AlunoDao;
import br.senac.rn.agendaescolar.models.Aluno;

public class AlunoFormularioActivity extends AppCompatActivity {

    private EditText etNome, etEndereco, etFone, etSite;
    private RatingBar rbNota;
    private Button btCadastrar;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno_formulario);
        inicializarComponentes();
        definirEventos();
    }

    private void definirEventos() {
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
    }

    private void inicializarComponentes() {
        etNome = (EditText) findViewById(R.id.formulario_nome);
        etEndereco = (EditText) findViewById(R.id.formulario_endereco);
        etFone = (EditText) findViewById(R.id.formulario_fone);
        etSite = (EditText) findViewById(R.id.formulario_site);
        rbNota = (RatingBar) findViewById(R.id.formulario_nota);
        btCadastrar = (Button) findViewById(R.id.formulario_cadastrar);

        Intent intent = getIntent();
        aluno = (Aluno) intent.getSerializableExtra("aluno");
        if (aluno != null) {
            preencherCampos(aluno);
        } else {
            aluno = new Aluno();
        }

    }

    private void cadastrar() {
        AlunoDao dao = new AlunoDao(this);

        aluno.setNome(etNome.getText().toString());
        aluno.setEndereco(etEndereco.getText().toString());
        aluno.setFone(etFone.getText().toString());
        aluno.setSite(etSite.getText().toString());
        aluno.setNota(Double.valueOf(rbNota.getProgress()));

        if (aluno.getId() == null) {
            dao.inserir(aluno);
        } else {
            dao.editar(aluno);
        }
        finish();
    }

    private void limparCampos() {
        etNome.setText("");
        etEndereco.setText("");
        etFone.setText("");
        etSite.setText("");
        rbNota.setProgress(0);
    }

    private void preencherCampos(Aluno aluno) {
        etNome.setText(aluno.getNome());
        etEndereco.setText(aluno.getEndereco());
        etFone.setText(aluno.getFone());
        etSite.setText(aluno.getSite());
        rbNota.setProgress(aluno.getNota().intValue());
    }
}

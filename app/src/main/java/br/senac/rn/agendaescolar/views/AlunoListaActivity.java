package br.senac.rn.agendaescolar.views;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.senac.rn.agendaescolar.adapter.AlunoAdapter;
import br.senac.rn.agendaescolar.daos.AlunoDao;
import br.senac.rn.agendaescolar.models.Aluno;

public class AlunoListaActivity extends AppCompatActivity {

    private ListView lvAlunos;
    private Button btCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno_lista);
    }

    @Override
    protected void onResume() {
        super.onResume();
        inicializarComponentes();
        definirEventos();
    }

    private void inicializarComponentes() {
        lvAlunos = (ListView) findViewById(R.id.lista_alunos);
        btCadastrar = (Button) findViewById(R.id.cadastrar);
        carregaLista();
        registerForContextMenu(lvAlunos);
    }

    private void carregaLista() {
        List<Aluno> alunos = new AlunoDao(this).buscarTodos();
        AlunoAdapter adapter = new AlunoAdapter(this, alunos);
        lvAlunos.setAdapter(adapter);
    }

    private void definirEventos() {
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentChamaFormulario = new Intent(AlunoListaActivity.this, AlunoFormularioActivity.class);
                startActivity(intentChamaFormulario);
            }
        });
        lvAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int posicao, long id) {
                Aluno aluno = (Aluno) lvAlunos.getItemAtPosition(posicao);
                Intent intentChamaFormulario = new Intent(AlunoListaActivity.this, AlunoFormularioActivity.class);
                intentChamaFormulario.putExtra("aluno", aluno);
                startActivity(intentChamaFormulario);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_lista_opcoes, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo alunoEscolhido;
        alunoEscolhido = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Aluno aluno = (Aluno) lvAlunos.getItemAtPosition(alunoEscolhido.position);
        switch (item.getItemId()) {
            case R.id.item_sms:
                Intent intentSms = new Intent(Intent.ACTION_VIEW);
                intentSms.setData(Uri.parse("sms:" + aluno.getFone()));
                item.setIntent(intentSms);
                break;
            case R.id.item_site:
                Intent intentSite = new Intent(Intent.ACTION_VIEW);
                intentSite.setData(Uri.parse(aluno.getSite()));
                item.setIntent(intentSite);
                break;
            case R.id.item_mapa:
                Intent intentMapa = new Intent(Intent.ACTION_VIEW);
                intentMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
                item.setIntent(intentMapa);
                break;
            case R.id.item_ligar:
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 9);
                } else {
                    Intent intentLigar = new Intent(Intent.ACTION_CALL);
                    intentLigar.setData(Uri.parse("tel:" + aluno.getFone()));
                    item.setIntent(intentLigar);
                }
                break;
            case R.id.item_deletar:
                AlunoDao dao = new AlunoDao(this);
                dao.deletar(aluno);
                carregaLista();
                break;
        }
        return super.onContextItemSelected(item);
    }
}

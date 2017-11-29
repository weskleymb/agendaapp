package br.senac.rn.agendaescolar.service;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import br.senac.rn.agendaescolar.models.Aluno;

public class Teste extends AsyncTask<Void, Void, List<Aluno>> {

    private Context context;

    public Teste(Context context) {
        this.context = context;
    }

    @Override
    protected List<Aluno> doInBackground(Void[] objects) {
        AlunoService service = new AlunoService();
        return service.buscar();
    }

    @Override
    protected void onPostExecute(List<Aluno> alunos) {
        super.onPostExecute(alunos);
    }
}

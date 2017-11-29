package br.senac.rn.agendaescolar.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.senac.rn.agendaescolar.daos.AlunoDao;
import br.senac.rn.agendaescolar.models.Aluno;

public class AlunoFormularioActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 123;
    private ImageView ivFoto;
    private EditText etNome, etEndereco, etFone, etSite;
    private RatingBar rbNota;
    private Button btCadastrar, btFoto;
    private Aluno aluno;
    private String caminhoFoto;

    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aluno_formulario);
        inicializarComponentes();
        definirEventos();
    }

    private void inicializarComponentes() {
        ivFoto = (ImageView) findViewById(R.id.formulario_foto);
        etNome = (EditText) findViewById(R.id.formulario_nome);
        etEndereco = (EditText) findViewById(R.id.formulario_endereco);
        etFone = (EditText) findViewById(R.id.formulario_fone);
        etSite = (EditText) findViewById(R.id.formulario_site);
        rbNota = (RatingBar) findViewById(R.id.formulario_nota);
        btCadastrar = (Button) findViewById(R.id.formulario_cadastrar);
        btFoto = (Button) findViewById(R.id.formulario_camera);
        Intent intent = getIntent();
        aluno = (Aluno) intent.getSerializableExtra("aluno");
        if (aluno != null) {
            preencherCampos(aluno);
        } else {
            aluno = new Aluno();
        }

    }

    private void definirEventos() {
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastrar();
            }
        });
        btFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                tirarFoto();
                dispatchTakePictureIntent();
            }
        });
    }

    private void tirarFoto() {
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String autoridade = "com.example.android.fileprovider";
        caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";
        File arquivoFoto = new File(caminhoFoto);
        Uri fileUri = FileProvider.getUriForFile(this,autoridade,arquivoFoto);
//        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intentCamera, CODIGO_CAMERA);
//        startActivity(intentCamera);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CODIGO_CAMERA && requestCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            bitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            ivFoto.setScaleType(ImageView.ScaleType.FIT_XY);
            ivFoto.setImageBitmap(bitmap);
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

    private File criarArquivoImagem() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = criarArquivoImagem();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                String autoridade = "br.senac.rn.agendaescolar.views";
                Uri photoURI = FileProvider.getUriForFile(this,autoridade,photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

}

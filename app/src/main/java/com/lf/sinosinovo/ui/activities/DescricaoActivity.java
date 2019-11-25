package com.lf.sinosinovo.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.lf.sinosinovo.R;
import com.lf.sinosinovo.model.Denuncia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DescricaoActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_TAKE_PHOTO = 1;
    private static String caminhoDaFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descricao);

        setTitle("Mais alguns dados");
        defineOpcao();
    }

    private void configuraBotaoFoto() {
        Button botaoImagem = findViewById(R.id.activity_descricao_botao_tirar_foto);
        botaoImagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPermissions();
            }
        });
    }

    private void defineOpcao() {
        configuraBotaoAvancar();
        configuraBotaoFoto();
        configuraBotaoVoltar();
    }

    private void configuraBotaoVoltar() {

        Button botaoVoltar = findViewById(R.id.activity_descricao_botao_voltar);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private Denuncia pegarDenuncia() {

        Intent intent = getIntent();
        Denuncia denuncia = (Denuncia) intent.getSerializableExtra("denuncia");

        System.out.println(denuncia.getCategoria());

        EditText descricaoEdit = findViewById(R.id.activity_descricao_desc);
        descricaoEdit.setHorizontallyScrolling(false);
        descricaoEdit.setMaxLines(10);
        String descricaoString = descricaoEdit.getText().toString();
        denuncia.setDescricao(descricaoString);
        denuncia.setCaminhoDaFoto(caminhoDaFoto);

        return denuncia;
    }

    private void configuraBotaoAvancar() {

        Button botaoAvancar = findViewById(R.id.activity_descricao_botao_avancar);

        botaoAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vaiParaResumo(pegarDenuncia());
            }
        });
    }

    private void vaiParaResumo(Denuncia denuncia) {

        Intent intent = new Intent(this, ResumoActivity.class);
        intent.putExtra("denuncia", denuncia);
        startActivity(intent);
    }


    // MÉTODOS PARA SEPARAR DEPOIS, TESTE DE CAMERA
    // MÉTODOS PARA SEPARAR DEPOIS, TESTE DE CAMERA

    private void getPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else
            dispatchTakePictureIntent();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else {
                    Toast.makeText(this, "Não vai funcionar!!!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                photoFile = File.createTempFile("foto_sinosi" + System.currentTimeMillis(), ".jpg", storageDir);
                caminhoDaFoto = "file:" + photoFile.getAbsolutePath();
            } catch (IOException ex) {
                Toast.makeText(getApplicationContext(), "Erro ao tirar a foto", Toast.LENGTH_SHORT).show();
            }

            if (photoFile != null) {
                Uri uri = FileProvider.getUriForFile(getBaseContext(), getBaseContext().getApplicationContext().getPackageName() + ".provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try {
                ImageView imagem = findViewById(R.id.activity_descricao_imagem);
                Bitmap bm1 = BitmapFactory.decodeStream(getContentResolver().openInputStream(Uri.parse(caminhoDaFoto)));
                imagem.setImageBitmap(bm1);
            } catch (FileNotFoundException fnex) {
                Toast.makeText(getApplicationContext(), "Foto não encontrada!", Toast.LENGTH_LONG).show();
            }
        }
    }



}
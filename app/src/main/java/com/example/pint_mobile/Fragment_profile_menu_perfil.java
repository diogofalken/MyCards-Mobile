package com.example.pint_mobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class Fragment_profile_menu_perfil extends Fragment implements Dialog_lista_distritos.OnInputSelectedDistrict, Dialog_registo_data_nascimento.OnInputSelectedDate{

    @Override
    public void sendInputDate(String input) {
        data_nasc.setText(input);
    }

    @Override
    public void sendInputDistrict(String input) {
        distrito.setText(input);
    }

    private TextView cancelar, guardar;
    private Button alterar_foto, alterar_senha, distrito, data_nasc;
    private EditText email, senha, nome, nr_tele;
    private ImageView see_password, foto_user;
    private static int GALLERY = 1;
    private static int CAMERA = 2;
    private static int RESULT_CANCELED = 1;
    private static String IMAGE_DIRECTORY = "/images";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menu_profile_perfil, container, false);

        cancelar = view.findViewById(R.id.cancel);
        guardar = view.findViewById(R.id.save);
        alterar_foto = view.findViewById(R.id.alterar_foto);
        alterar_senha = view.findViewById(R.id.alterar_senha);
        distrito = view.findViewById(R.id.id_email);
        data_nasc = view.findViewById(R.id.data_nasc);
        email = view.findViewById(R.id.id_nome);
        senha = view.findViewById(R.id.senha);
        nome = view.findViewById(R.id.id_nome);
        nr_tele = view.findViewById(R.id.telemovel);
        see_password = view.findViewById(R.id.see_pass);
        foto_user = view.findViewById(R.id.user_pic);


        cancelar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dialog_save_changes dialog = new Dialog_save_changes();
                dialog.setTargetFragment(Fragment_profile_menu_perfil.this, 1);
                dialog.show(getFragmentManager(), "Dialog_save_changes");
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //guardar
            }
        });

        see_password.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                see_not_see_password(view);
            }
        });

        data_nasc.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dialog_registo_data_nascimento dialog = new Dialog_registo_data_nascimento();
                dialog.setTargetFragment(Fragment_profile_menu_perfil.this, 1);
                dialog.show(getFragmentManager(), "Dialog_registo_data_nascimento");
            }
        });

        distrito.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dialog_lista_distritos dialog = new Dialog_lista_distritos();
                dialog.setTargetFragment(Fragment_profile_menu_perfil.this, 1);
                dialog.show(getFragmentManager(), "Dialog_lista_distritos");
            }
        });

        alterar_senha.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Dialog_change_password dialog = new Dialog_change_password();
                dialog.setTargetFragment(Fragment_profile_menu_perfil.this, 1);
                dialog.show(getFragmentManager(), "Dialog_change_password");
            }
        });

        alterar_foto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
                pictureDialog.setTitle("Escolha uma opção");
                String[] pictureDialogItems = {
                        "Escolher uma fotografia da galeria",
                        "Tirar uma fotografia com a câmera" };
                pictureDialog.setItems(pictureDialogItems,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        choosePhotoFromGallary();
                                        break;
                                    case 1:
                                        takePhotoFromCamera();
                                        break;
                                }
                            }
                        });
                pictureDialog.show();
            }
        });



        return view;
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);
                    foto_user.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            foto_user.setImageBitmap(thumbnail);
            saveImage(thumbnail);
        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(getActivity(),
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }


    public void see_not_see_password(View v){
        TextView password = v.findViewById(R.id.senha);
        ImageView img_see = v.findViewById(R.id.see_pass);
        Context mContext = getContext();
        Drawable drawable_see = ContextCompat.getDrawable(
                mContext,
                R.drawable.ic_see_password
        );
        Drawable drawable_not_see = ContextCompat.getDrawable(
                mContext,
                R.drawable.ic_not_see_password
        );
        if(password.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD){ //password visivel
            password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            img_see.setImageDrawable(drawable_not_see);

        }
        else{ //password nao visivel
            password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            img_see.setImageDrawable(drawable_see);
        }
    }
}

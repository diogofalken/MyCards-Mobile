package com.example.pint_mobile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Fragment_profile_menu_perfil extends Fragment implements Dialog_lista_distritos.OnInputSelectedDistrict, Dialog_registo_data_nascimento.OnInputSelectedDate {

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
    private EditText email, senha, primeiroNome,segundoNome;
    private ImageView foto_user;
    SharedPreferences sharedPreferences;
    static final String MyPREFERENCES = "MyPrefs" ;
    private static int Gallery = 2;
    private static int RESULT_CANCELED = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_menu_profile_perfil, container, false);

        Bitmap bitmap = new ImageSaver(this.getContext()).
                setFileName("myImage.png").
                setDirectoryName("images").
                load();


        sharedPreferences = getContext().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        cancelar = view.findViewById(R.id.cancel);
        guardar = view.findViewById(R.id.save);
        alterar_foto = view.findViewById(R.id.alterar_foto);
        alterar_senha = view.findViewById(R.id.alterar_senha);
        distrito = view.findViewById(R.id.nome);
        data_nasc = view.findViewById(R.id.data_nasc);
        email = view.findViewById(R.id.nome);
        senha = view.findViewById(R.id.senha);
        primeiroNome = view.findViewById(R.id.primeiro_nome);
        segundoNome = view.findViewById(R.id.segundo_nome);
        foto_user = view.findViewById(R.id.user_pic);
        foto_user.setImageBitmap(bitmap);

        if(bitmap == null) {
            Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
            foto_user.setImageBitmap(largeIcon);
        }

        email.setText(sharedPreferences.getString("Email", ""));
        primeiroNome.setText(sharedPreferences.getString("PrimeiroNome", ""));
        segundoNome.setText(sharedPreferences.getString("UltimoNome", ""));
        data_nasc.setText(sharedPreferences.getString("DataNascimento", ""));
        distrito.setText(sharedPreferences.getString("Localizacao",""));

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
                pictureDialog.setTitle("Selecione a opção");
                String[] pictureDialogItems = {
                        "Selecione Foto da Galeria" };
                pictureDialog.setItems(pictureDialogItems,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                takePhotoFromGalery();
                            }
                        });
                pictureDialog.show();
            }
        });

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.mycards.dsprojects.pt/api/cliente/" + sharedPreferences.getString("Id", "");
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("status").equals("true")) {
                                        Toast.makeText(getActivity().getApplicationContext(), "Dados pessoais alterados com sucesso!", Toast.LENGTH_SHORT).show();

                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("PrimeiroNome", primeiroNome.getText().toString());
                                        editor.putString("UltimoNome", segundoNome.getText().toString());
                                        editor.putString("DataNascimento", data_nasc.getText().toString());
                                        editor.putString("Localizacao", distrito.getText().toString());
                                        editor.commit();

                                        // Create new fragment and transaction
                                        Fragment newFragment = new Fragment_feed_perfil();
                                        // consider using Java coding conventions (upper first char class names!!!)
                                        FragmentTransaction transaction = getFragmentManager().beginTransaction();

                                        // Replace whatever is in the fragment_container view with this fragment,
                                        // and add the transaction to the back stack
                                        transaction.replace(R.id.fragment_container, newFragment);
                                        transaction.addToBackStack(null);

                                        // Commit the transaction
                                        transaction.commit();

                                    } else {
                                        Toast.makeText(getActivity().getApplicationContext(), "Dados pessoais não alterados!", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ConnectivityManager conMgr = (ConnectivityManager) getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
                            Toast.makeText(getContext(), "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
                        } else if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            //This indicates that the request has either time out or there is no connection
                            Log.i("VolleyError::", error.toString());
                        } else if (error instanceof AuthFailureError) {
                            //Error indicating that there was an Authentication Failure while performing the request
                            Log.i("AuthFailureError::", error.toString());
                        } else if (error instanceof ServerError) {
                            //Indicates that the server responded with a error response
                            Log.i("ServerError::", error.toString());
                            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                        } else if (error instanceof NetworkError) {
                            //Indicates that there was network error while performing the request
                            Log.i("NetworkError::", error.toString());
                            Toast.makeText(getContext(), "Sem ligação à Internet!", Toast.LENGTH_LONG).show();
                        } else if (error instanceof ParseError) {
                            //Indicates that the server response could not be parsed
                            Log.i("ParseError::", error.toString());
                        }
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("PrimeiroNome", primeiroNome.getText().toString());
                        params.put("UltimoNome", segundoNome.getText().toString());
                        params.put("DataNascimento", data_nasc.getText().toString());
                        params.put("Localizacao", distrito.getText().toString());

                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(postRequest);
            }
        });

        return view;
    }

    private void takePhotoFromGalery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        this.startActivityForResult(intent, Gallery);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

        if(requestCode == Gallery) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), contentURI);

                    foto_user.setImageBitmap(bitmap);

                    new ImageSaver(this.getContext()).
                            setFileName("myImage.png").
                            setDirectoryName("images").
                            save(bitmap);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}

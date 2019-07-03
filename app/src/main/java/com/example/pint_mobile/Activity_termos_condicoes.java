package com.example.pint_mobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Activity_termos_condicoes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termos_condicoes);

        //remover action bar
        getSupportActionBar().hide();

        //voltar para a activity conta_criada_sucesso
        final ImageView button = findViewById(R.id.voltar_conta_criada_sucesso);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Activity_termos_condicoes.this, Activity_concluir_registo.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });

        // inserir termos e condiçoes
        TextView termos = findViewById(R.id.termos_condicoes);
        termos.setText((Html.fromHtml("<h4 class=\"py-3\">1. IDENTIFICAÇÃO DO RESPONSÁVEL PELO TRATAMENTO </h4>\n" +
                "      <p>Identificação da Empresa:</p>\n" +
                "      <p>123456789</p>\n" +
                "      <p>Viseu</p>\n" +
                "      <p>912345678</p>\n" +
                "\n" +
                "      <h4 class=\"py-3\">2. INFORMAÇÃO E CONSENTIMENTO</h4>\n" +
                "      <p>A Lei da Proteção de Dados Pessoais (em diante “LPD”) e o Regulamento Geral de\n" +
                "        Proteção de Dados (Regulamento (UE) 2016/679 do Parlamento Europeu e do Conselho\n" +
                "        de 27 de abril de 2016, em diante “RGPD”) asseguram a proteção das pessoas singulares\n" +
                "        no que diz respeito ao tratamento de dados pessoais e à livre circulação desses dados. </p>\n" +
                "      <p>Nos termos legais são considerados \"dados pessoais\" qualquer informação, de qualquer\n" +
                "        natureza e independentemente do respetivo suporte, incluindo a imagem, relativa a\n" +
                "        uma pessoa singular identificada ou identificável, pelo que a proteção não abrange os\n" +
                "        dados de pessoas coletivas. </p>\n" +
                "      <p>Mediante a aceitação da presente Política de Privacidade o utilizador presta o seu\n" +
                "        consentimento informado, expresso, livre e inequívoco para que os dados pessoais\n" +
                "        fornecidos através do site http://dsprojects.pt/MyCards sejam incluídos num ficheiro da\n" +
                "        responsabilidade da MyCards, cujo tratamento nos termos da LPD e do RGPD cumpre as\n" +
                "        medidas de segurança técnicas e organizativas adequadas. </p>\n" +
                "      <p> Os dados presentes nesta base são unicamente os dados prestados pelos próprios na\n" +
                "        altura do seu registo, sendo recolhidos e processados automaticamente, nos termos do\n" +
                "        Regulamento Geral de Proteção de Dados. </p>\n" +
                "      <p>Em caso algum será solicitada informação sobre convicções filosóficas ou políticas,\n" +
                "        filiação partidária ou sindical, fé religiosa, vida privada e origem racial ou étnica bem\n" +
                "        como os dados relativos à saúde e à vida sexual, incluindo os dados genéticos.\n" +
                "        Em caso algum levaremos a cabo qualquer das seguintes atividades com os dados\n" +
                "        pessoais que nos sejam facultados através deste site:</p>\n" +
                "      <ul>\n" +
                "        <li>Ceder a outras pessoas ou outras entidades, sem o consentimento prévio do\n" +
                "          titular dos dados; </li>\n" +
                "        <li>Transferir para fora do Espaço Económico Europeu (EEE), sem o consentimento\n" +
                "          prévio do titular dos dados. </li>\n" +
                "      </ul>\n" +
                "\n" +
                "      <h4 class=\"py-3\">3. FINALIDADES DO TRATAMENTO DE DADOS PESSOAIS</h4>\n" +
                "      <p>Os dados pessoais que tratamos através desta página serão unicamente utilizados para\n" +
                "        as seguintes finalidades: </p>\n" +
                "      <ul>\n" +
                "        <li>(i) Processamento de encomendas;</li>\n" +
                "        <li>(ii) Comunicação com clientes e esclarecimento de dúvidas; </li>\n" +
                "        <li>(iii) Processamento de pedidos de informação; </li>\n" +
                "        <li>(iv) Processamento de reclamações;</li>\n" +
                "        <li>(viii) Prevenção e combate à fraude; </li>\n" +
                "        <li>(ix) Solicitação de comentários a produtos ou serviços adquiridos; </li>\n" +
                "        <li>(x) Realização de inquéritos de satisfação.</li>\n" +
                "        <li>(xi) Comunicações de marketing direto (caso tenha consentido no tratamento\n" +
                "          dos seus dados pessoais para esta finalidade); </li>\n" +
                "      </ul>\n" +
                "      <p>A MyCards garante a confidencialidade de todos os dados fornecidos pelos seus\n" +
                "        clientes. Não obstante a MyCards proceder à recolha e ao tratamento de dados de\n" +
                "        forma segura e que impede a sua perda ou manipulação, utilizando as técnicas mais\n" +
                "        aperfeiçoadas para o efeito, informamos que a recolha em rede aberta permite a\n" +
                "        circulação dos dados pessoais sem condições de segurança, correndo o risco de ser\n" +
                "        vistos e utilizados por terceiros não autorizados. </p>\n" +
                "      <h4 class=\"py-3\">4. MEDIDAS DE SEGURANÇA</h4>\n" +
                "      <p>A MyCards declara que implementou e continuará a implementar as medidas de\n" +
                "        segurança de natureza técnica e organizativa necessárias para garantir a segurança dos\n" +
                "        dados de carácter pessoal que lhe sejam fornecidos visando evitar a sua alteração,\n" +
                "        perda, tratamento e/ou acesso não autorizado, tendo em conta o estado atual da\n" +
                "        tecnologia, a natureza dos dados armazenados e os riscos a que estão expostos. </p>\n" +
                "      <p>A MyCards garante a confidencialidade de todos os dados fornecidos pelos\n" +
                "        seus clientes quer no registo, quer no processo de compra/encomenda de produtos ou\n" +
                "        serviços. A recolha e tratamento de dados realiza-se de forma segura e que impede a\n" +
                "        sua perda ou manipulação. Todos os dados serão inseridos num Servidor Seguro que os\n" +
                "        encripta. O utilizador poderá verificar que o seu browser é seguro se o símbolo do\n" +
                "        cadeado aparecer ou se o endereço começar com https em vez de http.</p>\n" +
                "      <p>Os dados pessoais são tratados com o nível de proteção legalmente exigível para\n" +
                "        garantir a segurança dos mesmos e evitar a sua alteração, perda, tratamento ou acesso\n" +
                "        não autorizado, tendo em conta o estado da tecnologia, sendo o utilizador consciente e\n" +
                "        aceitando que as medidas de segurança em Internet não são inexpugnáveis. </p>\n" +
                "      <h4 class=\"py-3\">5. EXERCÍCIO DOS DIREITOS</h4>\n" +
                "      <p>De acordo com as disposições da LDPD e do RGPD, o utilizador pode exercer a todo o\n" +
                "        tempo os seus direitos de acesso, retificação, apagamento, limitação, oposição e\n" +
                "        portabilidade, através de solicitação por qualquer dos seguintes meios: </p>\n" +
                "      <h4 class=\"py-3\">6. AUTORIDADE DE CONTROLO</h4>\n" +
                "      <p>Nos termos legais, o titular dos dados tem o direito de apresentar uma reclamação em\n" +
                "        matéria de proteção de dados pessoais à autoridade de controlo competente, a\n" +
                "        Comissão Nacional de Proteção de Dados (CNPD). </p>")));

    }

    public void onclick_aceito_termos(View v){
        //mudar para a activity Activity_registo ao clicar no botao "aceito"
        Intent intent = new Intent(getApplicationContext(), Activity_concluir_registo.class);
        Bundle parametro = new Bundle();

        parametro.putBoolean("resposta_termos", Boolean.TRUE);
        intent.putExtras(parametro);

        startActivity(intent);
    }

}

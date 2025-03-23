package com.example.heapsortanimado;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private HBox vetor;
    @FXML
    private Label label0;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;
    @FXML
    private Label label4;
    @FXML
    private Label label5;
    @FXML
    private Label label6;
    @FXML
    private Label label7;
    @FXML
    private Label label8;
    @FXML
    private Label label9;
    @FXML
    private Label label10;
    @FXML
    private Label label11;
    @FXML
    private Label label12;
    @FXML
    private Label label13;
    @FXML
    private Label label14;
    @FXML
    private Label no0;
    @FXML
    private Label no1;
    @FXML
    private Label no2;
    @FXML
    private Label no3;
    @FXML
    private Label no4;
    @FXML
    private Label no5;
    @FXML
    private Label no6;
    @FXML
    private Label no7;
    @FXML
    private Label no8;
    @FXML
    private Label no9;
    @FXML
    private Label no10;
    @FXML
    private Label no11;
    @FXML
    private Label no12;
    @FXML
    private Label no13;
    @FXML
    private Label no14;
    @FXML
    private Circle c0;
    @FXML
    private Circle c1;
    @FXML
    private Circle c2;
    @FXML
    private Circle c3;
    @FXML
    private Circle c4;
    @FXML
    private Circle c5;
    @FXML
    private Circle c6;
    @FXML
    private Circle c7;
    @FXML
    private Circle c8;
    @FXML
    private Circle c9;
    @FXML
    private Circle c10;
    @FXML
    private Circle c11;
    @FXML
    private Circle c12;
    @FXML
    private Circle c13;
    @FXML
    private Circle c14;
    @FXML
    private Label[] labels;
    @FXML
    private Label[] nos;
    @FXML
    private Circle[] circles;
    @FXML
    private TextFlow codigo;

    @FXML
    public void init(){
        labels = new Label[]{label0, label1, label2, label3, label4, label5, label6, label7, label8, label9, label10, label11, label12, label13, label14};
        circles = new Circle[]{c0, c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14};
        nos = new Label[]{no0, no1, no2, no3, no4, no5, no6, no7, no8, no9, no10, no11, no12, no13, no14};
        for(int j=0; j<circles.length;j++){
            CorBranca(j);
        }
    }

    @FXML
    public void AlterarTexto(int elemento, String texto){
        if (elemento >= 0 && elemento < labels.length) {
            labels[elemento].setText(texto);
            nos[elemento].setText(texto);
        } else {
            System.out.println("Tentativa de acessar nó inválido");
        }
    }

    @FXML
    public void CorAmarela(int elemento){
        if (elemento >= 0 && elemento < labels.length) {
            labels[elemento].setStyle("-fx-background-color: yellow;");
            circles[elemento].setFill(Color.YELLOW);
        } else {
            System.out.println("Tentativa de acessar nó inválido");
        }
    }

    @FXML
    public void CorBranca(int elemento) {
        if (elemento >= 0 && elemento < labels.length) {
            labels[elemento].setStyle("-fx-background-color: white;");
            circles[elemento].setFill(Color.WHITE);
        } else {
            System.out.println("Tentativa de acessar nó inválido");
        }
    }

    @FXML
    public void CorCinzaClaro(int elemento) {
        if (elemento >= 0 && elemento < labels.length) {
            labels[elemento].setStyle("-fx-background-color: lightgray;");
            circles[elemento].setFill(Color.LIGHTGRAY);
        } else {
            System.out.println("Tentativa de acessar nó inválido");
        }
    }

    @FXML
    public void CorCinzaEscuro(int elemento) {
        if (elemento >= 0 && elemento < labels.length) {
            labels[elemento].setStyle("-fx-background-color: darkgray;");
            circles[elemento].setFill(Color.DARKGRAY);
        } else {
            System.out.println("Tentativa de acessar nó inválido");
        }
    }

    @FXML
    public void CorVerde(int elemento) {
        if (elemento >= 0 && elemento < labels.length) {
            labels[elemento].setStyle("-fx-background-color: green;");
            circles[elemento].setFill(Color.GREEN);
        } else {
            System.out.println("Tentativa de acessar nó inválido");
        }
    }
    @FXML
    public void CorVermelha(int elemento) {
        if (elemento >= 0 && elemento < labels.length) {
            labels[elemento].setStyle("-fx-background-color: darksalmon;");
            circles[elemento].setFill(Color.DARKSALMON);
        } else {
            System.out.println("Tentativa de acessar nó inválido");
        }
    }


    @FXML
    private VBox codigoContainer; // VBox para armazenar as linhas de código

    // Salva a referência da linha atual destacada
    private Label linhaAtual;

    /*
    @FXML
    public void criaTextFlow(){
        codigo.getChildren().clear();

        codigo.getChildren().add(new Text("public void heapSort(int[] vet) {\n"));

        codigo.getChildren().add(new Text("    int pai, FE, FD, TL2 = vet.length, aux, maiorF;\n"));
        codigo.getChildren().add(new Text("    while (TL2 > 1) {\n"));
        codigo.getChildren().add(new Text("        pai = TL2 / 2 - 1;\n"));
        codigo.getChildren().add(new Text("        while (pai >= 0) {\n"));
        codigo.getChildren().add(new Text("            FE = 2 * pai + 1;\n"));
        codigo.getChildren().add(new Text("            FD = 2 * pai + 2;\n"));
        codigo.getChildren().add(new Text("            maiorF = FE;\n"));
        codigo.getChildren().add(new Text("            if (FD < TL2 && vet[FD] > vet[FE])\n"));
        codigo.getChildren().add(new Text("                maiorF = FD;\n"));
        codigo.getChildren().add(new Text("            if (vet[maiorF] > vet[pai]) {\n"));
        codigo.getChildren().add(new Text("                aux = vet[pai];\n"));
        codigo.getChildren().add(new Text("                vet[pai] = vet[maiorF];\n"));
        codigo.getChildren().add(new Text("                vet[maiorF] = aux;\n"));
        codigo.getChildren().add(new Text("            }\n"));
        codigo.getChildren().add(new Text("            pai--;\n"));
        codigo.getChildren().add(new Text("        }\n"));
        codigo.getChildren().add(new Text("        aux = vet[0];\n"));
        codigo.getChildren().add(new Text("        vet[0] = vet[TL2 - 1];\n"));
        codigo.getChildren().add(new Text("        vet[TL2 - 1] = aux;\n"));
        codigo.getChildren().add(new Text("        TL2--;\n"));
        codigo.getChildren().add(new Text("    }\n"));
        codigo.getChildren().add(new Text("}\n"));
    }
    */

    // Salva a referência das linhas atuais destacadas
    private List<Label> linhasAtuais = new ArrayList<>();

    // Adiciona o código no VBox
    public void criaCodigo() {
        // Limpa qualquer conteúdo anterior
        codigoContainer.getChildren().clear();

        // Código simulado (exatamente como no TextFlow)
        String[] codigo = {
                "public void heapSort(int[] vet) {",
                "    int pai, FE, FD, TL2 = vet.length, aux, maiorF;",
                "    while (TL2 > 1) {",
                "        pai = TL2 / 2 - 1;",
                "        while (pai >= 0) {",
                "            FE = 2 * pai + 1;",
                "            FD = 2 * pai + 2;",
                "            maiorF = FE;",
                "            if (FD < TL2 && vet[FD] > vet[FE])",
                "                maiorF = FD;",
                "            if (vet[maiorF] > vet[pai]) {",
                "                aux = vet[pai];",
                "                vet[pai] = vet[maiorF];",
                "                vet[maiorF] = aux;",
                "            }",
                "            pai--;",
                "        }",
                "        aux = vet[0];",
                "        vet[0] = vet[TL2 - 1];",
                "        vet[TL2 - 1] = aux;",
                "        TL2--;",
                "    }",
                "}"
        };

        // Cria cada linha como um Label
        for (String linha : codigo) {
            Label label = new Label(linha);
            label.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px; -fx-padding: 2;");
            codigoContainer.getChildren().add(label);
        }
    }

    // Método para destacar múltiplas linhas
    public void destacarLinhas(int... indices) {
        // Remove o destaque anterior
        limparDestaques();

        for (int indice : indices) {
            if (indice >= 0 && indice < codigoContainer.getChildren().size()) {
                Label novaLinha = (Label) codigoContainer.getChildren().get(indice);
                novaLinha.setStyle("-fx-background-color: yellow; -fx-font-family: 'Consolas'; -fx-font-size: 14px; -fx-padding: 2;");

                // Adiciona à lista de linhas ativas
                linhasAtuais.add(novaLinha);
            }
        }
    }

    // Método para limpar os destaques das linhas
    public void limparDestaques() {
        for (Label linha : linhasAtuais) {
            linha.setStyle("-fx-font-family: 'Consolas'; -fx-font-size: 14px; -fx-padding: 2;");
        }
        linhasAtuais.clear();
    }

    // Método para simular execução linha por linha
    public void simularExecucao() {
        int[] passoAtual = {0}; // Para capturar a referência no lambda

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(700), e -> {
            if (passoAtual[0] < codigoContainer.getChildren().size()) {
                destacarLinhas(passoAtual[0]);
                passoAtual[0]++;
            }
        }));

        timeline.setCycleCount(codigoContainer.getChildren().size());
        timeline.play();
    }
}
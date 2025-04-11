package org.fipp.gabryelborges.animacaoradixsort;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class HelloApplication extends Application {
    AnchorPane pane;
    Button botao_inicio;
    private Button[] vetor, vetorCounting, vetorAux;
    private int larguraBotao = 61;
    private Label[] labels, labelsCounting, labelsAux;
    private int yVetor = 100, xVetor = 20, yVetorCounting = 400, yVetorAux = 600;
    private int tempoIntervalo = 500;
    private Label indiceLabel, posicaoLabel, maiorLabel, passoLabel, changeLabel;
    private List<String> posicoesNumericas = Arrays.asList("UNIDADE", "DEZENA", "CENTENA", "UNIDADE MILHAR", "DEZENA MILHAR", "CENTENA MILHAR");
    private ImageView setaVetor, setaCounting, setaAux;
    private int tamanhoVetor = 8;
    private VBox pseudoCodeBox; // Container para o pseudocódigo
    private Label[] pseudoCodeLines; // Linhas do pseudocódigo
    private VBox variablesBox; // Novo container para as variáveis
    private Label maiorVarLabel, expVarLabel, iVarLabel, digitoVarLabel, posVarLabel, vetorCountingVarLabel, vetorAuxVarLabel; // Labels para variáveis

    @Override
    public void start(Stage stage) {
        stage.setTitle("ANIMATION OF RADIX SORT");
        pane = new AnchorPane();
        pane.getStyleClass().add("pane");

        botao_inicio = new Button("Executar Radix");
        botao_inicio.setLayoutX(10);
        botao_inicio.setLayoutY(1);
        botao_inicio.setOnAction(e -> {
            botao_inicio.setDisable(true); // Desativa o botão para evitar múltiplas execuções
            new Thread(() -> {
                try {
                    radix();
                    Platform.runLater(() -> {
                        // Cria uma nova janela modal
                        Stage modalStage = new Stage();
                        modalStage.initModality(Modality.APPLICATION_MODAL); // Torna a janela modal
                        modalStage.initOwner(pane.getScene().getWindow()); // Define a janela principal como proprietária
                        modalStage.setTitle("Radix Sort Concluído");
                        modalStage.setResizable(false);

                        // Layout da janela modal
                        VBox modalContent = new VBox(20);
                        modalContent.setStyle("-fx-padding: 20; -fx-alignment: center;");
                        Label messageLabel = new Label("Radix Sort concluído!\nO vetor está ordenado.");
                        messageLabel.setFont(new Font(16));
                        Button closeButton = new Button("Fechar");
                        closeButton.setOnAction(event -> modalStage.close());

                        modalContent.getChildren().addAll(messageLabel, closeButton);

                        // Configura a cena
                        Scene modalScene = new Scene(modalContent, 300, 150);
                        modalStage.setScene(modalScene);

                        // Reativa o botão quando a janela modal for fechada
                        modalStage.setOnCloseRequest(event -> botao_inicio.setDisable(false));

                        // Exibe a janela modal
                        modalStage.showAndWait();
                    });
                } finally {
                    Platform.runLater(() -> botao_inicio.setDisable(false)); // Garante que o botão seja reativado mesmo em caso de erro
                }
            }).start();
        });
        pane.getChildren().add(botao_inicio);

        indiceLabel = new Label("i = -");
        indiceLabel.setFont(new Font(16));
        indiceLabel.setLayoutX(120);
        indiceLabel.setLayoutY(1);
        pane.getChildren().add(indiceLabel);

        posicaoLabel = new Label("Olhando para:");
        posicaoLabel.setFont(new Font(16));
        posicaoLabel.setLayoutX(20);
        posicaoLabel.setLayoutY(30);
        pane.getChildren().add(posicaoLabel);

        maiorLabel = new Label("Maior: -");
        maiorLabel.setFont(new Font(16));
        maiorLabel.setLayoutX(400);
        maiorLabel.setLayoutY(1);
        pane.getChildren().add(maiorLabel);

        passoLabel = new Label("Passo: -");
        passoLabel.setFont(new Font(16));
        passoLabel.setLayoutX(500);
        passoLabel.setLayoutY(1);
        pane.getChildren().add(passoLabel);

        changeLabel = new Label("");
        changeLabel.setFont(new Font(16));
        changeLabel.setVisible(false);
        pane.getChildren().add(changeLabel);

        java.io.InputStream inputStream = getClass().getResourceAsStream("/arrow-down.png");
        if (inputStream == null) {
            throw new IllegalStateException("Não foi possível encontrar o arquivo 'arrow-down.png' em resources");
        }
        Image arrowImage = new Image(inputStream);

        setaVetor = criarSeta(arrowImage, xVetor, yVetor - 30);
        setaCounting = criarSeta(arrowImage, xVetor, yVetorCounting - 50);
        setaAux = criarSeta(arrowImage, xVetor, yVetorAux - 30);
        pane.getChildren().addAll(setaVetor, setaCounting, setaAux);

        // Criar o painel do pseudocódigo
        pseudoCodeBox = new VBox(5);
        pseudoCodeBox.setStyle("-fx-background-color: white; -fx-padding: 10;");
        pseudoCodeBox.setLayoutX(700);
        pseudoCodeBox.setLayoutY(50);
        pseudoCodeBox.setPrefWidth(530);
        pseudoCodeBox.setPrefHeight(400);

        String[] pseudoCodeText = {
                "RADIX-SORT(VETOR, TAMANHO)",
                "1.  MAIOR = PEGAR-MAIOR(VETOR)",
                "2.  PARA EXP = 1 ATÉ MAIOR/EXP > 0 FAÇA",
                "3.      ZERAR VETOR_COUNTING E VETOR_AUX",
                "4.      // Passo 1: Contar ocorrências",
                "5.      PARA I = 0 ATÉ TAMANHO-1 FAÇA",
                "6.          DÍGITO = (VETOR[I] / EXP) % 10",
                "7.          VETOR_COUNTING[DÍGITO] = VETOR_COUNTING[DÍGITO] + 1",
                "8.      FIM-PARA",
                "9.      // Passo 2: Acumular valores",
                "10.     PARA I = 1 ATÉ 9 FAÇA",
                "11.         VETOR_COUNTING[I] = VETOR_COUNTING[I] + VETOR_COUNTING[I-1]",
                "12.     FIM-PARA",
                "13.     // Passo 3: Construir vetor auxiliar",
                "14.     PARA I = TAMANHO-1 ATÉ 0 FAÇA",
                "15.         DÍGITO = (VETOR[I] / EXP) % 10",
                "16.         POS = VETOR_COUNTING[DÍGITO] - 1",
                "17.         VETOR_AUX[POS] = VETOR[I]",
                "18.         VETOR_COUNTING[DÍGITO] = VETOR_COUNTING[DÍGITO] - 1",
                "19.     FIM-PARA",
                "20.     // Copiar vetor auxiliar",
                "21.     PARA I = 0 ATÉ TAMANHO-1 FAÇA",
                "22.         VETOR[I] = VETOR_AUX[I]",
                "23.     FIM-PARA",
                "24. FIM-PARA",
                "25. FIM"
        };

        pseudoCodeLines = new Label[pseudoCodeText.length];
        for (int i = 0; i < pseudoCodeText.length; i++) {
            pseudoCodeLines[i] = new Label(pseudoCodeText[i]);
            pseudoCodeLines[i].setFont(new Font(14));
            pseudoCodeLines[i].setStyle("-fx-text-fill: black;");
            pseudoCodeBox.getChildren().add(pseudoCodeLines[i]);
        }
        pane.getChildren().add(pseudoCodeBox);

        // Criar o painel de variáveis
        variablesBox = new VBox(5);
        variablesBox.setStyle("-fx-background-color: white; -fx-padding: 10; -fx-border-color: black; -fx-border-width: 1;");
        variablesBox.setLayoutX(700);
        variablesBox.setLayoutY(710); // Abaixo do pseudoCodeBox
        variablesBox.setPrefWidth(530);
        variablesBox.setPrefHeight(300);

        maiorVarLabel = new Label("maior = -");
        expVarLabel = new Label("exp = -");
        iVarLabel = new Label("i = -");
        digitoVarLabel = new Label("dígito = -");
        posVarLabel = new Label("pos = -");
        vetorCountingVarLabel = new Label("vetorCounting = []");
        vetorAuxVarLabel = new Label("vetorAux = []");

        Label titleLabel = new Label("Variáveis:");
        titleLabel.setFont(new Font(16));
        titleLabel.setStyle("-fx-font-weight: bold;");

        for (Label label : new Label[]{titleLabel, maiorVarLabel, expVarLabel, iVarLabel, digitoVarLabel, posVarLabel, vetorCountingVarLabel, vetorAuxVarLabel}) {
            label.setFont(new Font(14));
            variablesBox.getChildren().add(label);
        }
        pane.getChildren().add(variablesBox);

        criaVetor();
        criaLabels();
        criaVetorCounting();
        criaLabelsCounting();
        criaVetorAux();
        criaLabelsAux();

        Scene scene = new Scene(pane, 1300, 950);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setResizable(false);
        //stage.setMaximized(true);
        stage.show();
    }

    private void highlightPseudoCodeLine(int lineIndex) {
        Platform.runLater(() -> {
            for (int i = 0; i < pseudoCodeLines.length; i++) {
                if (i == lineIndex) {
                    pseudoCodeLines[i].setStyle("-fx-background-color: blue; -fx-text-fill: white;");
                } else {
                    pseudoCodeLines[i].setStyle("-fx-background-color: transparent; -fx-text-fill: black;");
                }
            }
        });
    }

    private ImageView criarSeta(Image image, double x, double y) {
        ImageView seta = new ImageView(image);
        seta.setFitWidth(30);
        seta.setFitHeight(30);
        seta.setLayoutX(x);
        seta.setLayoutY(y);
        seta.setVisible(false);
        return seta;
    }

    private void criaLabelsCounting() {
        labelsCounting = new Label[10];
        for (int i = 0; i < labelsCounting.length; i++) {
            labelsCounting[i] = new Label(String.valueOf(i));
            labelsCounting[i].setFont(new Font(16));
            labelsCounting[i].setLayoutX(xVetor + i * larguraBotao + (larguraBotao / 2) - 10);
            labelsCounting[i].setLayoutY(yVetorCounting + 60);
            pane.getChildren().add(labelsCounting[i]);
        }
    }

    private void criaVetorCounting() {
        vetorCounting = new Button[10];
        int i;
        for (i = 0; i < vetorCounting.length; i++) {
            vetorCounting[i] = botaoConfigurado();
            vetorCounting[i].setText("0");
            vetorCounting[i].setLayoutX(xVetor + i * larguraBotao);
            vetorCounting[i].setLayoutY(yVetorCounting);
            vetorCounting[i].setFont(new Font(16));
            pane.getChildren().add(vetorCounting[i]);
        }
        Label lbVetCont = new Label("VetCont");
        lbVetCont.setFont(new Font(16));
        lbVetCont.setStyle("-fx-font-weight: bold;");
        pane.getChildren().add(lbVetCont);
        lbVetCont.setLayoutX(xVetor + i * larguraBotao);
        lbVetCont.setLayoutY(yVetorCounting + 15);
    }

    private void criaVetorAux() {
        vetorAux = new Button[tamanhoVetor];
        int i;
        for (i = 0; i < vetorAux.length; i++) {
            vetorAux[i] = botaoConfigurado();
            vetorAux[i].setText("");
            vetorAux[i].setLayoutX(xVetor + i * larguraBotao);
            vetorAux[i].setLayoutY(yVetorAux);
            vetorAux[i].setFont(new Font(16));
            pane.getChildren().add(vetorAux[i]);
        }
        Label lbVetAux = new Label("VetAux");
        lbVetAux.setFont(new Font(16));
        lbVetAux.setStyle("-fx-font-weight: bold;");
        pane.getChildren().add(lbVetAux);
        lbVetAux.setLayoutX(xVetor + i * larguraBotao);
        lbVetAux.setLayoutY(yVetorAux + 15);
    }

    private void criaLabelsAux() {
        labelsAux = new Label[tamanhoVetor];
        for (int i = 0; i < labelsAux.length; i++) {
            labelsAux[i] = new Label(String.valueOf(i + 1));
            labelsAux[i].setFont(new Font(16));
            labelsAux[i].setLayoutX(xVetor + i * larguraBotao + (larguraBotao / 2) - 10);
            labelsAux[i].setLayoutY(yVetorAux + 60);
            pane.getChildren().add(labelsAux[i]);
        }
    }

    private void criaVetor() {
        vetor = new Button[tamanhoVetor];
        int i;
        for (i = 0; i < vetor.length; i++) {
            vetor[i] = botaoConfigurado();
            vetor[i].setLayoutX(xVetor + i * larguraBotao);
            vetor[i].setLayoutY(yVetor);
            vetor[i].setFont(new Font(16));
            pane.getChildren().add(vetor[i]);
        }
        Label lbVet = new Label("Vet");
        lbVet.setFont(new Font(16));
        lbVet.setStyle("-fx-font-weight: bold;");
        pane.getChildren().add(lbVet);
        lbVet.setLayoutX(xVetor + i * larguraBotao);
        lbVet.setLayoutY(yVetor + 15);
    }

    private Button botaoConfigurado() {
        Random r = new Random();
        Button b = new Button(String.valueOf(r.nextInt(2000)));
        b.getStyleClass().add("botao-vetor");
        b.setMinHeight(60);
        b.setMinWidth(larguraBotao);
        return b;
    }

    private void criaLabels() {
        labels = new Label[tamanhoVetor];
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new Label(String.valueOf(i));
            labels[i].setFont(new Font(16));
            labels[i].setLayoutX(xVetor + i * larguraBotao + (larguraBotao / 2) - 10);
            labels[i].setLayoutY(yVetor + 60);
            pane.getChildren().add(labels[i]);
        }
    }

    public void moverBotoesAuxParaVetor(Button[] aux) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws InterruptedException {
                highlightPseudoCodeLine(21);
                for (int i = 0; i < aux.length; i++) {
                    if (aux[i] != null) {
                        int indexAux = i;
                        int indexVetor = i;

                        double initialXAux = vetorAux[indexAux].getLayoutX();
                        double initialYAux = vetorAux[indexAux].getLayoutY();
                        double finalXAux = vetor[indexVetor].getLayoutX();
                        double finalYAux = vetor[indexVetor].getLayoutY();

                        int steps = 20;
                        for (int j = 0; j < steps; j++) {
                            final double deltaX = (finalXAux - initialXAux) / steps;
                            final double deltaY = (finalYAux - initialYAux) / steps;
                            Platform.runLater(() -> {
                                vetorAux[indexAux].setLayoutX(vetorAux[indexAux].getLayoutX() + deltaX);
                                vetorAux[indexAux].setLayoutY(vetorAux[indexAux].getLayoutY() + deltaY);
                            });
                            Thread.sleep(50);
                        }

                        Platform.runLater(() -> {
                            vetor[indexVetor].setText(vetorAux[indexAux].getText());
                            vetor[indexVetor].setStyle("-fx-background-color: #FF9999;");
                            vetorAux[indexAux].setText("");
                            iVarLabel.setText("i = " + indexAux);
                        });

                        Thread.sleep(tempoIntervalo / 2);

                        double originalXAux = xVetor + indexAux * larguraBotao;
                        double originalYAux = yVetorAux;
                        for (int j = 0; j < steps; j++) {
                            final double deltaX = (originalXAux - vetorAux[indexAux].getLayoutX()) / steps;
                            final double deltaY = (originalYAux - vetorAux[indexAux].getLayoutY()) / steps;
                            Platform.runLater(() -> {
                                vetorAux[indexAux].setLayoutX(vetorAux[indexAux].getLayoutX() + deltaX);
                                vetorAux[indexAux].setLayoutY(vetorAux[indexAux].getLayoutY() + deltaY);
                            });
                            Thread.sleep(50);
                        }

                        Platform.runLater(() -> {
                            vetorAux[indexAux].setLayoutX(originalXAux);
                            vetorAux[indexAux].setLayoutY(originalYAux);
                            vetor[indexVetor].setStyle("");
                            vetorAux[indexAux].setStyle("");
                            vetorAuxVarLabel.setText("vetorAux = [" + getVetorAuxText() + "]");
                        });

                        Thread.sleep(tempoIntervalo / 2);
                    }
                }
                highlightPseudoCodeLine(23);
                Platform.runLater(() -> iVarLabel.setText("i = -"));
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void animarSeta(ImageView seta, double targetX) throws InterruptedException {
        double initialX = seta.getLayoutX();
        int animationSteps = 20;
        long animationDuration = 500;
        long sleepPerStep = animationDuration / animationSteps;

        for (int step = 0; step <= animationSteps; step++) {
            double fraction = (double) step / animationSteps;
            double currentX = initialX + fraction * (targetX - initialX);
            Platform.runLater(() -> {
                seta.setLayoutX(currentX);
                seta.setVisible(true);
            });
            Thread.sleep(sleepPerStep);
        }

        Platform.runLater(() -> seta.setLayoutX(targetX));
    }

    private void animarMudanca(int change, int buttonIndex) throws InterruptedException {
        double buttonCenterX = xVetor + buttonIndex * larguraBotao + (larguraBotao / 2.0);
        double initialY = yVetorCounting;
        double targetY = yVetorCounting - 90;

        Platform.runLater(() -> {
            changeLabel.setText((change >= 0 ? "+" : "") + change);
            changeLabel.setLayoutX(buttonCenterX - (changeLabel.getWidth() / 2.0));
            changeLabel.setLayoutY(initialY);
            changeLabel.setVisible(true);
        });

        int animationSteps = 20;
        long animationDuration = 500;
        long sleepPerStep = animationDuration / animationSteps;

        for (int step = 0; step <= animationSteps; step++) {
            double fraction = (double) step / animationSteps;
            double currentY = initialY + fraction * (targetY - initialY);
            Platform.runLater(() -> changeLabel.setLayoutY(currentY));
            Thread.sleep(sleepPerStep);
        }

        Platform.runLater(() -> changeLabel.setVisible(false));
    }

    private String getVetorCountingText() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vetorCounting.length; i++) {
            sb.append(vetorCounting[i].getText());
            if (i < vetorCounting.length - 1) sb.append(", ");
        }
        return sb.toString();
    }

    private String getVetorAuxText() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < vetorAux.length; i++) {
            String text = vetorAux[i].getText().isEmpty() ? "-" : vetorAux[i].getText();
            sb.append(text);
            if (i < vetorAux.length - 1) sb.append(", ");
        }
        return sb.toString();
    }

    public void counting(int exp) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws InterruptedException {
                highlightPseudoCodeLine(3);
                Platform.runLater(() -> {
                    for (Button b : vetorCounting) {
                        b.setText("0");
                        b.setStyle("");
                    }
                    for (Button b : vetorAux) {
                        b.setText("");
                        b.setStyle("");
                    }
                    indiceLabel.setText("i = -");
                    setaVetor.setVisible(false);
                    setaCounting.setVisible(false);
                    setaAux.setVisible(false);
                    changeLabel.setVisible(false);
                    vetorCountingVarLabel.setText("vetorCounting = [" + getVetorCountingText() + "]");
                    vetorAuxVarLabel.setText("vetorAux = [" + getVetorAuxText() + "]");
                    digitoVarLabel.setText("dígito = -");
                    posVarLabel.setText("pos = -");
                });

                Thread.sleep(tempoIntervalo);

                Button[] aux = new Button[tamanhoVetor];

                // Passo 1: Conta as ocorrências de cada dígito
                Platform.runLater(() -> passoLabel.setText("Passo: 1"));
                highlightPseudoCodeLine(5);
                for (int i = 0; i < vetor.length; i++) {
                    Button b = vetor[i];
                    int numero = Integer.parseInt(b.getText());
                    int digito = (numero / exp) % 10;
                    int novoValor = Integer.parseInt(vetorCounting[digito].getText()) + 1;

                    int finalI1 = i;
                    double targetX = xVetor + finalI1 * larguraBotao + (larguraBotao - 30) / 2;
                    animarSeta(setaVetor, targetX);

                    highlightPseudoCodeLine(6);
                    Platform.runLater(() -> digitoVarLabel.setText("dígito = " + digito));
                    Thread.sleep(tempoIntervalo / 2);
                    highlightPseudoCodeLine(7);
                    Platform.runLater(() -> {
                        vetorCounting[digito].setText(String.valueOf(novoValor));
                        vetorCounting[digito].setStyle("-fx-background-color: #FF9999;");
                        vetor[finalI1].setStyle("-fx-background-color: #FF9999;");
                        indiceLabel.setText("i = " + finalI1);
                        iVarLabel.setText("i = " + finalI1);
                        posicaoLabel.setText("Olhando para: " + posicoesNumericas.get((int) Math.log10(exp)) + " - (" + digito + ") " + numero);
                        vetorCountingVarLabel.setText("vetorCounting = [" + getVetorCountingText() + "]");
                    });

                    animarMudanca(+1, digito);

                    Thread.sleep(tempoIntervalo - 500);

                    Platform.runLater(() -> {
                        vetorCounting[digito].setStyle("");
                        vetor[finalI1].setStyle("");
                    });
                }
                highlightPseudoCodeLine(8);

                Platform.runLater(() -> {
                    indiceLabel.setText("i = -");
                    iVarLabel.setText("i = -");
                    setaVetor.setVisible(false);
                    digitoVarLabel.setText("dígito = -");
                });

                // Passo 2: Acumula os valores no vetorCounting
                Platform.runLater(() -> passoLabel.setText("Passo: 2"));
                highlightPseudoCodeLine(10);
                java.io.InputStream sumInputStream = getClass().getResourceAsStream("/arrow-sum.png");
                if (sumInputStream == null) {
                    throw new IllegalStateException("Não foi possível encontrar o arquivo 'arrow-sum.png' em resources");
                }
                Image arrowSumImage = new Image(sumInputStream);

                Platform.runLater(() -> {
                    setaCounting.setImage(arrowSumImage);
                    setaCounting.setFitWidth(80);
                    setaCounting.setFitHeight(50);
                });

                for (int i = 1; i < vetorCounting.length; i++) {
                    int valorAnterior = Integer.parseInt(vetorCounting[i - 1].getText());
                    int soma = Integer.parseInt(vetorCounting[i].getText()) + valorAnterior;
                    int finalI = i;

                    double targetX = xVetor + finalI * larguraBotao + (larguraBotao - 120) / 2;
                    animarSeta(setaCounting, targetX);

                    highlightPseudoCodeLine(11);
                    Platform.runLater(() -> {
                        vetorCounting[finalI].setText(String.valueOf(soma));
                        vetorCounting[finalI].setStyle("-fx-background-color: #FF9999;");
                        indiceLabel.setText("i = " + finalI);
                        iVarLabel.setText("i = " + finalI);
                        vetorCountingVarLabel.setText("vetorCounting = [" + getVetorCountingText() + "]");
                    });

                    animarMudanca(valorAnterior, finalI);

                    Thread.sleep(tempoIntervalo - 500);

                    Platform.runLater(() -> {
                        vetorCounting[finalI].setStyle("");
                    });
                }
                highlightPseudoCodeLine(12);

                java.io.InputStream downInputStream = getClass().getResourceAsStream("/arrow-down.png");
                if (downInputStream == null) {
                    throw new IllegalStateException("Não foi possível encontrar o arquivo 'arrow-down.png' em resources");
                }
                Image arrowDownImage = new Image(downInputStream);
                Platform.runLater(() -> {
                    setaCounting.setImage(arrowDownImage);
                    indiceLabel.setText("i = -");
                    iVarLabel.setText("i = -");
                    setaCounting.setVisible(false);
                });

                // Passo 3: Constrói o vetor auxiliar
                Platform.runLater(() -> passoLabel.setText("Passo: 3"));
                highlightPseudoCodeLine(14);
                for (int i = vetor.length - 1; i >= 0; i--) {
                    int pos = (Integer.parseInt(vetor[i].getText()) / exp) % 10;
                    int novaPosicao = Integer.parseInt(vetorCounting[pos].getText()) - 1;

                    aux[novaPosicao] = vetor[i];

                    int finalI = i;
                    double targetXVetor = xVetor + finalI * larguraBotao + (larguraBotao - 30) / 2;
                    double targetXAux = xVetor + novaPosicao * larguraBotao + (larguraBotao - 30) / 2;

                    animarSeta(setaVetor, targetXVetor);
                    animarSeta(setaAux, targetXAux);

                    highlightPseudoCodeLine(15);
                    Platform.runLater(() -> digitoVarLabel.setText("dígito = " + pos));
                    Thread.sleep(tempoIntervalo / 2);
                    highlightPseudoCodeLine(16);
                    Platform.runLater(() -> posVarLabel.setText("pos = " + novaPosicao));
                    Thread.sleep(tempoIntervalo / 2);
                    highlightPseudoCodeLine(17);
                    Thread.sleep(tempoIntervalo / 2);
                    highlightPseudoCodeLine(18);
                    Platform.runLater(() -> {
                        vetorCounting[pos].setText(String.valueOf(novaPosicao));
                        vetorCounting[pos].setStyle("-fx-background-color: #FF9999;");
                        vetorAux[novaPosicao].setText(vetor[finalI].getText());
                        vetorAux[novaPosicao].setStyle("-fx-background-color: #FF9999;");
                        indiceLabel.setText("i = " + finalI);
                        iVarLabel.setText("i = " + finalI);
                        vetorCountingVarLabel.setText("vetorCounting = [" + getVetorCountingText() + "]");
                        vetorAuxVarLabel.setText("vetorAux = [" + getVetorAuxText() + "]");
                    });

                    animarMudanca(-1, pos);

                    Thread.sleep(tempoIntervalo - 500);

                    Platform.runLater(() -> {
                        vetorCounting[pos].setStyle("");
                        vetorAux[novaPosicao].setStyle("");
                    });
                }
                highlightPseudoCodeLine(19);

                Platform.runLater(() -> {
                    indiceLabel.setText("i = -");
                    iVarLabel.setText("i = -");
                    setaVetor.setVisible(false);
                    setaAux.setVisible(false);
                    digitoVarLabel.setText("dígito = -");
                    posVarLabel.setText("pos = -");
                });

                moverBotoesAuxParaVetor(aux);

                highlightPseudoCodeLine(24);
                return null;
            }
        };
        task.setOnSucceeded(event -> System.out.println("Counting terminou para exp = " + exp));
        Thread thread = new Thread(task);
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void radix() {
        highlightPseudoCodeLine(1);
        int maior = pegaMaior();
        Platform.runLater(() -> {
            maiorLabel.setText("Maior: " + maior);
            maiorVarLabel.setText("maior = " + maior);
        });
        highlightPseudoCodeLine(2);
        for (int exp = 1; maior / exp > 0; exp *= 10) {
            int posicaoIndex = (int) Math.log10(exp);
            int finalExp = exp;
            Platform.runLater(() -> {
                posicaoLabel.setText("Olhando para: " + posicoesNumericas.get(posicaoIndex));
                expVarLabel.setText("exp = " + finalExp);
            });
            counting(exp);
        }
        Platform.runLater(() -> {
            posicaoLabel.setText("Olhando para: -");
            passoLabel.setText("Passo: Concluído");
            setaVetor.setVisible(false);
            setaCounting.setVisible(false);
            setaAux.setVisible(false);
            expVarLabel.setText("exp = -");
        });
        highlightPseudoCodeLine(25);
        System.out.println("Ordenado");
    }

    private int pegaMaior() {
        int maior = Integer.parseInt(vetor[0].getText());
        for (int i = 1; i < vetor.length; i++) {
            if (maior < Integer.parseInt(vetor[i].getText())) {
                maior = Integer.parseInt(vetor[i].getText());
            }
        }
        return maior;
    }

    public static void main(String[] args) {
        launch();
    }
}
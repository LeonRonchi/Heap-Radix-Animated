package com.example.heapsortanimado;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HelloApplication extends Application {
    private int[] vet = new int[15];
    private static int confirm=1;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setWidth(1000);
        stage.setHeight(672);
        stage.setResizable(false);
        stage.setTitle("Animação Heap Sort!");
        stage.setScene(scene);
        stage.show();

        HelloController controller = fxmlLoader.getController();
        controller.init();
        controller.criaCodigo();

        povoaVetor(controller, vet);
        for (int j : vet) {
            System.out.printf("%d ", j);
        }
        System.out.println("\nGabarito: ");
        int[] vet2 = Arrays.stream(vet).sorted().toArray();
        for (int j : vet2) {
            System.out.printf("%d ", j);
        }
        heapSort(vet, controller);
    }

    public static void main(String[] args) {
        launch();
    }

    public static void povoaVetor(HelloController controller, int[] vet) {
        Random random = new Random();
        Set<Integer> numerosGerados = new HashSet<>();

        int i = 0;
        while (i < vet.length) {
            int randomInt = random.nextInt(100);
            if (numerosGerados.add(randomInt)) {
                vet[i] = randomInt;
                controller.AlterarTexto(i, String.valueOf(randomInt));
                i++;
            }
        }
    }

    public static void heapSort(int[] vet, HelloController controller) {
        int n = vet.length;

        PauseTransition pause = new PauseTransition(Duration.millis(2000));
        pause.setOnFinished(event -> {
            sortStep(vet, n, controller);
        });
        pause.playFromStart();
    }

    private static void sortStep(int[] vet, int TL2, HelloController controller) {
        if(confirm==1)
            confirm = JOptionPane.showConfirmDialog(null,"Deseja iniciar o algoritmo?","Confirmação",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(confirm == JOptionPane.YES_OPTION) {
            int pai = TL2 / 2 - 1;

            heapifyStep(vet, pai, TL2, controller, () -> {
                controller.CorVermelha(0);
                controller.CorVermelha(TL2 - 1);

                PauseTransition pause = new PauseTransition(Duration.millis(2000));
                pause.setOnFinished(e -> {
                    int aux = vet[0];
                    vet[0] = vet[TL2 - 1];
                    vet[TL2 - 1] = aux;

                    controller.animarTrocaNos(0, TL2 - 1, () -> {
                        controller.CorVerde(TL2 - 1);
                        controller.CorBranca(0);

                        // Atualiza vetor horizontal
                        controller.AlterarTextoVet(0, String.valueOf(vet[0]));
                        controller.AlterarTextoVet(TL2 - 1, String.valueOf(vet[TL2 - 1]));

                        if (TL2 - 1 > 1) {
                            sortStep(vet, TL2 - 1, controller);
                        } else {
                            controller.CorVerde(0);
                        }
                    });
                });
                pause.play();
            });
        }

    }

    private static void heapifyStep(int[] vet, int pai, int TL2, HelloController controller, Runnable onFinish) {
        if (pai < 0) {
            onFinish.run();
            return;
        }

        int[] paiWrapper = {pai};
        int[] maiorFWrapper = {2 * pai + 1};

        int FE = 2 * pai + 1;
        int FD = 2 * pai + 2;

        for (int i = 0; i < TL2; i++) {
            controller.CorBranca(i);
        }

        controller.CorAmarela(paiWrapper[0]);

        if (FE < TL2)
            controller.CorCinzaEscuro(FE);
        if (FD < TL2)
            controller.CorCinzaClaro(FD);

        if (FD < TL2 && vet[FD] > vet[FE]) {
            maiorFWrapper[0] = FD;
        }

        if (vet[maiorFWrapper[0]] > vet[paiWrapper[0]]) {
            boolean maiorFIsLeft = (maiorFWrapper[0] == FE);

            PauseTransition pauseBeforeSwap = new PauseTransition(Duration.millis(1000));
            pauseBeforeSwap.setOnFinished(e -> {
                controller.CorVermelha(paiWrapper[0]);
                controller.CorVermelha(maiorFWrapper[0]);

                PauseTransition pauseSwap = new PauseTransition(Duration.millis(1000));
                pauseSwap.setOnFinished(ev -> {
                    int aux = vet[paiWrapper[0]];
                    vet[paiWrapper[0]] = vet[maiorFWrapper[0]];
                    vet[maiorFWrapper[0]] = aux;

                    controller.animarTrocaNos(paiWrapper[0], maiorFWrapper[0], () -> {

                        controller.CorAmarela(paiWrapper[0]);
                        if (maiorFIsLeft) {
                            controller.CorCinzaEscuro(maiorFWrapper[0]);
                        } else {
                            controller.CorCinzaClaro(maiorFWrapper[0]);
                        }

                        PauseTransition pauseNext = new PauseTransition(Duration.millis(1000));
                        pauseNext.setOnFinished(ev2 -> {
                            if(TL2 != 3)
                                controller.CorBranca(2);
                            controller.CorBranca(1);

                            heapifyStep(vet, paiWrapper[0] - 1, TL2, controller, onFinish);
                        });
                        pauseNext.play();
                    });
                });
                pauseSwap.play();
            });

            pauseBeforeSwap.play();

        } else {
            PauseTransition pause = new PauseTransition(Duration.millis(1000));
            pause.setOnFinished(e -> {
                controller.limparDestaques();
                heapifyStep(vet, paiWrapper[0] - 1, TL2, controller, onFinish);
            });
            pause.play();
        }
    }
}

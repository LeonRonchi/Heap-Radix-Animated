package com.example.heapsortanimado;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class HelloApplication extends Application {
    private int[] vet = new int[15];
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

        povoaVetor(controller,vet);
        for (int j : vet) {
            System.out.printf("%d ", j);
        }
        System.out.println();
        heapSort(vet, controller);
    }

    public static void main(String[] args) {
        launch();
    }

    public static void povoaVetor(HelloController controller, int[] vet){
        Random random = new Random();
        Set<Integer> numerosGerados = new HashSet<>();

        int i = 0;
        while (i < vet.length) {
            int randomInt = random.nextInt(100); // Gera entre 0 e 99

            // Tenta adicionar o número no set
            if (numerosGerados.add(randomInt)) {
                vet[i] = randomInt;
                controller.AlterarTexto(i, String.valueOf(randomInt));
                i++;
            }
        }
    }

    /*
    public static void heapSort(int[] vet, HelloController controller){

        PauseTransition pause = new PauseTransition(Duration.millis(2000));

        int pai, FE, FD, TL2=vet.length, aux, maiorF;
        while (TL2 > 1) {
            pai=TL2/2-1;
            while(pai>=0){
                FE=2*pai+1;
                FD=2*pai+2;
                maiorF=FE;
                if(FD<TL2 && vet[FD]>vet[FE])
                    maiorF=FD;
                if(vet[maiorF]>vet[pai]){
                    aux=vet[pai];
                    vet[pai]=vet[maiorF];
                    vet[maiorF]=aux;
                }
                pai--;
            }
            aux=vet[0];
            vet[0]=vet[TL2-1];
            vet[TL2-1]=aux;
            pause.play();
            controller.AlterarTexto(TL2-1,""+aux);
            controller.CorVerde(TL2-1);
            TL2--;
        }
        pause.play();
        controller.AlterarTexto(0,""+vet[0]);
        controller.CorVerde(0);
        for (int j : vet) {
            System.out.printf("%d ", j);
        }
    }
    */

    public static void heapSort(int[] vet, HelloController controller) {
        int n = vet.length;
        sortStep(vet, n, controller);
    }

    private static void sortStep(int[] vet, int TL2, HelloController controller) {
        if (TL2 <= 1) return;

        int pai = TL2 / 2 - 1;

        heapifyStep(vet, pai, TL2, controller, () -> {
            // ❗ Seleciona o primeiro e o último elemento em vermelho
            controller.CorVermelha(0);
            controller.CorVermelha(TL2 - 1);

            // 🔥 Pausa antes de fazer a troca
            PauseTransition pause = new PauseTransition(Duration.millis(2000));
            pause.setOnFinished(e -> {
                // Após o heapify, faz a troca e continua a ordenação
                int aux = vet[0];
                vet[0] = vet[TL2 - 1];
                vet[TL2 - 1] = aux;

                // Mostra na interface
                controller.AlterarTexto(TL2 - 1, String.valueOf(aux));
                controller.AlterarTexto(0, String.valueOf(vet[0]));

                // ❗ Após a troca, coloca o último elemento como ordenado (Verde)
                controller.CorVerde(TL2 - 1);

                // ❗ Reseta a cor do primeiro elemento para branco
                controller.CorBranca(0);

                // 🔥 Continua a ordenação após a troca
                if (TL2 - 1 > 1) {
                    sortStep(vet, TL2 - 1, controller);
                } else {
                    // Última atualização
                    controller.AlterarTexto(0, String.valueOf(vet[0]));
                    controller.CorVerde(0);
                }
            });
            pause.play();
        });
    }

    private static void heapifyStep(int[] vet, int pai, int TL2, HelloController controller, Runnable onFinish) {
        if (pai < 0) {
            onFinish.run(); // Quando o heapify terminar, executa o próximo passo
            return;
        }

        int[] paiWrapper = {pai};
        int[] maiorFWrapper = {2 * pai + 1};

        int FE = 2 * pai + 1;
        int FD = 2 * pai + 2;

        // 🔥 Torna todos os nós brancos antes de processar
        for (int i = 0; i < TL2; i++) {
            controller.CorBranca(i);
        }

        // ✅ Destacar linha de atribuição do pai e FE/FD
        controller.destacarLinhas(5, 6, 7); // Linhas de atribuição do pai, FE e FD

        // 🔥 Seleciona o pai em amarelo
        controller.CorAmarela(paiWrapper[0]);

        // 🔥 Marca o filho esquerdo como Cinza Escuro (se existir)
        if (FE < TL2) {
            controller.CorCinzaEscuro(FE);
        }

        // 🔥 Marca o filho direito como Cinza Claro (se existir)
        if (FD < TL2) {
            controller.CorCinzaClaro(FD);
        }

        // ✅ Destacar linha da comparação entre FE e FD
        controller.destacarLinhas(8);

        // 🔥 Lógica para definir o maior filho
        if (FD < TL2 && vet[FD] > vet[FE]) {
            maiorFWrapper[0] = FD;
        }

        if (vet[maiorFWrapper[0]] > vet[paiWrapper[0]]) {
            boolean maiorFIsLeft = (maiorFWrapper[0] == FE);

            // ✅ Destacar a linha da troca
            controller.destacarLinhas(10, 11);

            // 🔥 Agora a troca de cor para vermelho está DENTRO da pausa!
            PauseTransition pauseBeforeSwap = new PauseTransition(Duration.millis(1000));
            pauseBeforeSwap.setOnFinished(e -> {
                // ❗ SOMENTE AQUI os elementos ficam vermelhos para troca
                controller.CorVermelha(paiWrapper[0]);
                controller.CorVermelha(maiorFWrapper[0]);

                // 🔥 Pausa para visualizar o vermelho antes de trocar
                PauseTransition pauseSwap = new PauseTransition(Duration.millis(1000));
                pauseSwap.setOnFinished(ev -> {
                    // 🔥 Faz a troca entre pai e maior filho
                    int aux = vet[paiWrapper[0]];
                    vet[paiWrapper[0]] = vet[maiorFWrapper[0]];
                    vet[maiorFWrapper[0]] = aux;

                    // ✅ Destacar linha da atualização do valor
                    controller.destacarLinhas(12, 13);

                    // Atualiza visualmente após a troca
                    controller.AlterarTexto(paiWrapper[0], String.valueOf(vet[paiWrapper[0]]));
                    controller.AlterarTexto(maiorFWrapper[0], String.valueOf(vet[maiorFWrapper[0]]));

                    // ❗ Após a troca, restaura as cores:
                    controller.CorBranca(paiWrapper[0]);
                    if (maiorFIsLeft) {
                        controller.CorCinzaEscuro(maiorFWrapper[0]); // Retorna para cinza escuro se era o filho esquerdo
                    } else {
                        controller.CorCinzaClaro(maiorFWrapper[0]);  // Retorna para cinza claro se era o filho direito
                    }

                    // 🔥 Pausa antes de continuar para o próximo pai
                    PauseTransition pauseNext = new PauseTransition(Duration.millis(1000));
                    pauseNext.setOnFinished(ev2 -> {
                        controller.limparDestaques(); // ✅ Limpa os destaques após cada execução
                        heapifyStep(vet, paiWrapper[0] - 1, TL2, controller, onFinish);
                    });
                    pauseNext.play();
                });
                pauseSwap.play();
            });
            pauseBeforeSwap.play();

        } else {
            // ✅ Destacar a linha de decremento do pai
            controller.destacarLinhas(15);

            // 🔥 Pausa antes de continuar o heapify sem troca
            PauseTransition pause = new PauseTransition(Duration.millis(1000));
            pause.setOnFinished(e -> {
                controller.limparDestaques(); // ✅ Limpa os destaques após cada execução
                heapifyStep(vet, paiWrapper[0] - 1, TL2, controller, onFinish);
            });
            pause.play();
        }
    }

}
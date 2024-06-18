package servico;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ManipulaTarefa {
	private final Node node;
    private final ExecutorService executorService;
    private final Set<String> processedMessages = new HashSet<>();

    public ManipulaTarefa(Node node) {
        this.node = node;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public void processaMensagemTarefa(String mensagem) {
        if (node.isLider()) {
            int idTarefa = Integer.parseInt(mensagem.split(" ")[1]);
            executorService.submit(new Tarefa(idTarefa));
        } else {
            if (!processedMessages.contains(mensagem)) {
                node.enviarMensagem(mensagem);
                processedMessages.add(mensagem);
            }
        }
    }
}

package servico;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ManipulaCliente implements Runnable {
	private final Socket socket;
    private final Node node;

    public ManipulaCliente(Socket socket, Node node) {
        this.socket = socket;
        this.node = node;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            System.out.println("Cliente conectado: " + socket.getInetAddress().getHostAddress());
            String mensagem = in.readLine();
            System.out.println("Mensagem recebida: " + mensagem);
            processarMensagem(mensagem, out);

        } catch (IOException e) {
            System.err.println("Erro ao processar a conexão: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void processarMensagem(String mensagem, PrintWriter out) {
        if (mensagem == null) {
            return;
        }

        if (mensagem.startsWith("ELEICAO")) {
            Eleicao eleicao = new Eleicao(node);
            eleicao.processaMensagemEleicao(mensagem);
        } else if (mensagem.startsWith("LIDER")) {
            Lider lider = new Lider(node);
            lider.processaMensagemLider(mensagem);
        } else if (mensagem.startsWith("TAREFA")) {
            ManipulaTarefa manipuladorTarefa = new ManipulaTarefa(node);
            manipuladorTarefa.processaMensagemTarefa(mensagem);
        } else if (mensagem.startsWith("PING")) {
            out.println("PONG");
        }
    }
}

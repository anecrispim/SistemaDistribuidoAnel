package servico;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorNode implements Runnable {
	private final Node node;

    public ServidorNode(Node node) {
        this.node = node;
    }
    
    @Override
    public void run() {
    	try (ServerSocket serverSocket = new ServerSocket(node.getPorta())) {
            System.out.println("Servidor iniciado na porta " + node.getPorta());

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    new Thread(new ManipulaCliente(socket, node)).start();
                } catch (IOException e) {
                    System.err.println("Erro ao aceitar a conexão: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

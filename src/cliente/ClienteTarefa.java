package cliente;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class ClienteTarefa {
	private static final int MAX_TENTATIVAS = 5;
    private static final List<ServidorInfo> SERVIDORES_CONHECIDOS = List.of(
            new ServidorInfo("10.15.120.88", 6000),
            new ServidorInfo("10.15.120.180", 6001),
            new ServidorInfo("10.15.120.128", 6002),
            new ServidorInfo("10.15.120.244", 6003),
            new ServidorInfo("10.15.121.0", 6004)
    );
	
    public void iniciar() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Digite o ID da tarefa (ou 'sair' para encerrar): ");
            String input = scanner.nextLine();

            if ("sair".equalsIgnoreCase(input)) {
                break;
            }

            try {
                int idTarefa = Integer.parseInt(input);
                enviarTarefa(idTarefa);
            } catch (NumberFormatException e) {
                System.out.println("ID da tarefa inválido. Por favor, digite um número inteiro.");
            }
        }

        scanner.close();
    }

    public void enviarTarefa(int idTarefa) {
        int tentativas = 0;

        while (tentativas < MAX_TENTATIVAS) {
            try {
                ServidorInfo lider = descobrirLider();
                try (Socket socket = new Socket(lider.getIp(), lider.getPorta());
                     PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                    out.println("TAREFA " + idTarefa);
                    System.out.println("Tarefa " + idTarefa + " enviada ao líder em " + lider.getIp() + ":" + lider.getPorta());
                    return;
                }
            } catch (IOException e) {
                System.err.println("Falha ao enviar a tarefa ao líder.");
                tentativas++;
            }
        }

        System.err.println("Falha ao enviar a tarefa após várias tentativas.");
    }

    private ServidorInfo descobrirLider() throws IOException {
        for (ServidorInfo servidor : SERVIDORES_CONHECIDOS) {
            try (Socket socket = new Socket(servidor.getIp(), servidor.getPorta());
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 Scanner in = new Scanner(socket.getInputStream())) {
                out.println("PING");
                String resposta = in.nextLine();
                if ("PONG".equalsIgnoreCase(resposta)) {
                    return servidor;
                }
            } catch (IOException e) {
                System.err.println("Falha ao pingar servidor " + servidor.getIp() + ":" + servidor.getPorta());
            }
        }

        throw new IOException("Nenhum líder disponível.");
    }

}

package servico;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Node {
	private final int id;
    private final String ip;
    private final int porta;
    private final String proximoIp;
    private final int proximaPorta;
    private boolean lider;
    
    public Node(int id, String ip, int porta, String proximoIp, int proximaPorta) {
        this.id = id;
        this.ip = ip;
        this.porta = porta;
        this.proximoIp = proximoIp;
        this.proximaPorta = proximaPorta;
        this.lider = false;
    }

	public boolean isLider() {
		return lider;
	}

	public void setLider(boolean lider) {
		this.lider = lider;
	}

	public int getId() {
		return id;
	}

	public String getIp() {
		return ip;
	}

	public int getPorta() {
		return porta;
	}

	public String getProximoIp() {
		return proximoIp;
	}

	public int getProximaPorta() {
		return proximaPorta;
	}
	
    
	public void iniciar() {
		System.out.println("Iniciando nó " + id + " na porta " + porta);
        new Thread(new ServidorNode(this)).start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (id == 0) {
        	Eleicao eleicao = new Eleicao(this);
        	eleicao.iniciarEleicao();
        }
        //iniciarMonitoramento();
    }
	
	public void enviarMensagem(String mensagem) {
		System.out.println("Enviando mensagem: " + mensagem + " para " + proximoIp + ":" + proximaPorta);
        try (Socket socket = new Socket(proximoIp, proximaPorta);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println(mensagem);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private void iniciarMonitoramento() {
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000); // Verifica a cada 5 segundos
                    verificarProximoNo();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    private void verificarProximoNo() {
        try (Socket socket = new Socket(proximoIp, proximaPorta);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("PING");
        } catch (IOException e) {
            System.out.println("O próximo nó " + proximoIp + ":" + proximaPorta + " não está respondendo. Iniciando eleição...");
            Eleicao eleicao = new Eleicao(this);
            eleicao.iniciarEleicao();
        }
    }
}

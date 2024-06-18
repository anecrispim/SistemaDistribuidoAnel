package servico;

public class Lider {
	private final Node node;

    public Lider(Node node) {
        this.node = node;
    }

    public void processaMensagemLider(String mensagem) {
        int idLider = Integer.parseInt(mensagem.split(" ")[1]);
        System.out.println("Node " + node.getId() + ": Novo líder é " + idLider);
        node.setLider(idLider == node.getId());
    }
}

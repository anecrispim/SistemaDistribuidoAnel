package servico;

public class Eleicao {
	private final Node node;

    public Eleicao(Node node) {
        this.node = node;
    }

    public void iniciarEleicao() {
        node.enviarMensagem("ELEICAO " + node.getId());
    }

    public void processaMensagemEleicao(String mensagem) {
        int eleicaoId = Integer.parseInt(mensagem.split(" ")[1]);
        if (eleicaoId > node.getId()) {
            node.enviarMensagem(mensagem);
        } else if (eleicaoId < node.getId()) {
            iniciarEleicao();
        } else {
        	declararLider();
        }
    }

    private void declararLider() {
        node.enviarMensagem("LIDER " + node.getId());
        node.setLider(true);
    }
}

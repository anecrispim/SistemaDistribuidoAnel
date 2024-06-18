package servico;

public class Servico {
	
	public static void main(String[] args) {
        if (args.length < 5) {
            System.out.println("Uso: java servico.Servico <nodeId> <nodeIp> <nodePort> <nextNodeIp> <nextNodePort>");
            return;
        }

        int nodeId;
        String nodeIp;
        int nodePort;
        String nextNodeIp;
        int nextNodePort;

        try {
            nodeId = Integer.parseInt(args[0]);
            nodeIp = args[1];
            nodePort = Integer.parseInt(args[2]);
            nextNodeIp = args[3];
            nextNodePort = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            System.out.println("Erro: o nodeId e as portas devem ser inteiros.");
            return;
        }

        Node node = new Node(nodeId, nodeIp, nodePort, nextNodeIp, nextNodePort);
        node.iniciar();
    }
}

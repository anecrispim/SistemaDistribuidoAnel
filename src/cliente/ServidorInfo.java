package cliente;

public class ServidorInfo {
	private final String ip;
    private final int porta;

    public ServidorInfo(String ip, int porta) {
        this.ip = ip;
        this.porta = porta;
    }

    public String getIp() {
        return ip;
    }

    public int getPorta() {
        return porta;
    }
}

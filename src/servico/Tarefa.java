package servico;

public class Tarefa implements Runnable {
	private final int idTarefa;

    public Tarefa(int idTarefa) {
        this.idTarefa = idTarefa;
    }

    @Override
    public void run() {
        System.out.println("Executando tarefa " + idTarefa + " na thread " + Thread.currentThread().getId());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

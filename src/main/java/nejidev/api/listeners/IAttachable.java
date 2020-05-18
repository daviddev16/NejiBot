package nejidev.api.listeners;

/*interface responsavel por linkar algum tipo de gerenciador ao Listener.*/
public interface IAttachable<Listener> {

    void attachListener(Listener listener);


}


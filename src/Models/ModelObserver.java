package Models;

public interface ModelObserver {
    void updateView(Model model);
    void updateViewRechercher(Model model);
    void updateViewMessage(Model model);
    void updateViewPayer(Model model);
    void updateViewLogout(Model model);
    void updateViewTable(Model model);


}


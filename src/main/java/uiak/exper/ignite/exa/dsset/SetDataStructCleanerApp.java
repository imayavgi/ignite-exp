package uiak.exper.ignite.exa.dsset;


import org.apache.ignite.IgniteException;

public class SetDataStructCleanerApp {
    public static void main(String[] args) throws IgniteException {
        SetDataStructSvc svc = new SetDataStructSvcImpl();
        svc.initialize();
        svc.clearAndRemoveSet();
    }
}
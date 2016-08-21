package uiak.exper.ignite.exa.dsset;

import org.apache.ignite.IgniteException;

/**
 * Created by imaya on 8/19/16.
 */
public interface SetDataStructSvc {
    void initialize() throws IgniteException;

    void writeToSet() throws IgniteException;

    void readAndTestSet() throws IgniteException;

    void clearAndRemoveSet() throws IgniteException;
}

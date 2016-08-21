package uiak.exper.ignite.exa;


import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.Ignition;

import java.util.Arrays;
import java.util.Collection;

public class ClosureDist {
    public static void main(String[] args) throws IgniteException {
        try (Ignite ignite = Ignition.start("examples/config/example-ignite.xml")) {
            dc(ignite);
        }
    }

    private static void dc(Ignite ignite) {
        Collection<Integer> res = ignite.compute().apply(
                (String word) -> {
                    System.out.println();
                    System.out.println(">>> Printing '" + word + "' on this node from ignite job.");

                    // Return number of letters in the word.
                    return word.length();
                },
                // Job parameters. Ignite will create as many jobs as there are parameters.
                Arrays.asList("Count characters using closure".split(" "))
        );

        int sum = res.stream().mapToInt(i -> i).sum();

        System.out.println();
        System.out.println(">>> Total number of characters in the phrase is '" + sum + "'.");
        System.out.println(">>> Check all nodes for output (this node is also part of the cluster).");
    }

}
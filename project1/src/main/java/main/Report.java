package main;
/*
 * Report Sorts
 * Terrence Jackson
 * UMGC CMSC 451
 * Project 1
 * 11.11.24
 * 
 * The driver for reporting the data collected on the two sorts
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Report {
    public static void main(String[] args) throws FileNotFoundException {
        String file;
        String line;
        int fileSelection = -1;
        String[][] tableData = new String[12][5];
        JFileChooser fileSelect = new JFileChooser(".");
        JFrame frame = new JFrame();
        frame.setTitle("Benchmark Report");
        JTable table;
        String[] columnNames = { "Size", "Avg Count", "Coef Count", "Avg Time", "Coef Time" };

        // open file explorer and ask user to pick a file
        while (fileSelection != JFileChooser.APPROVE_OPTION) {
            fileSelection = fileSelect.showOpenDialog(null);
        }
        // get the path of the selected file
        file = fileSelect.getSelectedFile().getAbsolutePath();

        // Creating an instance of Inputstream
        InputStream is = new FileInputStream(file);

        // loop through file
        try (Scanner sc = new Scanner(is)) {
            int row = 0; // init loop variable
            while (sc.hasNextLine()) {
                // init variables
                int n = -1;
                ArrayList<Integer> time = new ArrayList<>();
                ArrayList<Integer> count = new ArrayList<>();

                line = sc.nextLine(); // read in next line
                String[] stringParts = line.split(" "); // split it on spaces

                // loop over this line, pull out each part
                for (int i = 0; i < stringParts.length; i++) {
                    if (i == 0) {
                        // first part is the size of the input
                        n = Integer.parseInt(stringParts[i]);
                    } else if (i % 2 == 0) {
                        // even elements are time integers
                        time.add(Integer.parseInt(stringParts[i]));
                    } else {
                        // odd elements are count integers
                        count.add(Integer.parseInt(stringParts[i]));
                    }
                }

                // build the array for this row of data
                String[] thisRow = { String.format("%d", n),
                        String.format("%.2f", calculateAverage(count)),
                        String.format("%.2f%%", calculateCoeffVariation(count)),
                        String.format("%.2f", calculateAverage(time)),
                        String.format("%.2f%%", calculateCoeffVariation(time)) };
                tableData[row] = thisRow; // fill in the table array with this row
                row++; // increment to next row
            }
        }

        // finish up Swing to show table
        table = new JTable(tableData, columnNames); // build table element

        // right align all cols except the first
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        for (int i = 1; i < 5; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(rightRenderer);
        }

        JScrollPane sp = new JScrollPane(table); // add it to a scroll pane
        frame.add(sp); // add table+scroll pane to the frame
        frame.setSize(500, 265);
        frame.setVisible(true);
    }

    /*
     * Calculate the average of the values in a given array and return it
     */
    private static double calculateAverage(ArrayList<Integer> array) {
        double sum = 0;

        // add up all the values
        for (double num : array) {
            sum += num;
        }

        // divide by the number of values
        return sum / array.size();
    }

    /*
     * Calculate the Coefficient of Variation
     * References:
     * https://en.wikipedia.org/wiki/Coefficient_of_variation
     * 
     * https://www.khanacademy.org/math/statistics-probability/summarizing-
     * quantitative-data/variance-standard-deviation-population/a/calculating-
     * standard-deviation-step-by-step
     */
    private static double calculateCoeffVariation(ArrayList<Integer> array) {
        // calculate the average or mean
        double average = calculateAverage(array);

        // Calculate the standard deviation
        double sumSquaredDifferences = 0.0;
        for (int num : array) {
            sumSquaredDifferences += Math.pow(num - average, 2);
        }
        double standardDeviation = Math.sqrt(sumSquaredDifferences / array.size());

        // Calculate coefficient of variation (standard deviation / mean)
        return (standardDeviation / average) * 100; // expressed as a percentage
    }
}

package utils;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ScoreBoardTableModel extends AbstractTableModel {

    /**
     * Column headers for the scoreboard table
     */
    private final String[] columnNames = {"Player Name", "Wins"};


    /**
     * The object that stores the data put into the table
     */
    private final Object[][] data;


    /**
     * Creates a custom TableModel with custom columnNames
     * @param scores The List of the Score objects we want to display in the table
     */
    public ScoreBoardTableModel(List<Score> scores){
        data = new Object[scores.size()][2];
        int count = 0;
        for (Score score : scores) {
            data[count][0] = score.playerName;
            data[count++][1] = score.score;
        }
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}

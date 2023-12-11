package utils;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class ScoreBoardTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Player Name", "Wins"}; //same as before...
    private final Object[][] data;

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

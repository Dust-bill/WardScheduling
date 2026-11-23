import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;

// 网格显示层。每个格子的标题和正文可以分别更新。
public class Grid extends JPanel {
    private final JTextArea[][] cells;

    public Grid(int rows, int cols) {
        if (rows < 1 || cols < 1) throw new IllegalArgumentException("Grid dimensions must be positive");
        setLayout(new GridLayout(rows, cols, 6, 6));
        cells = new JTextArea[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                JTextArea cell = new JTextArea();
                cell.setEditable(false);
                cell.setLineWrap(true);
                cell.setWrapStyleWord(true);
                cell.setBackground(Color.WHITE);
                cell.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                cell.setPreferredSize(new Dimension(180, 95));
                cells[row][col] = cell;
                add(cell);
            }
        }
    }

    public void setHeading(int row, int col, String heading) {
        JTextArea cell = cells[row][col];
        onUiThread(() -> cell.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), heading)));
    }

    public void setCell(int row, int col, String text) {
        setCell(row, col, text, Color.WHITE);
    }

    public void setCell(int row, int col, String text, Color backgroundColour) {
        JTextArea cell = cells[row][col];
        onUiThread(() -> {
            cell.setText(text);
            cell.setBackground(backgroundColour);
        });
    }

    private void onUiThread(Runnable update) {
        if (SwingUtilities.isEventDispatchThread()) update.run();
        else SwingUtilities.invokeLater(update);
    }
}

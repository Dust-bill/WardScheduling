import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Main {
    // TODO 1：填窗口标题、行数、列数、检查间隔（毫秒）。
    private static final String TITLE = "Medical bys";
    private static final int ROWS = 2, COLS = 4;
    private static final long INTERVAL_MS = 1000;

    private final Grid grid = new Grid(ROWS, COLS);
    private final Monitor monitor = new Monitor(INTERVAL_MS, this::check);
    // TODO 2：需要跨次检查保留的对象或状态字段，声明在这里。

    private void initialise() {
        // TODO 3：填写格子标题和初始文字。行、列均从 0 开始。
        for (int i = 0 ; i <=1 ; i++){
            for (int j =0 ; j<=3;j++){
                grid.setHeading(i,j, HospitalInformation.bays[i*4+j]);
            }
        }

        //grid.setCell(row, col, text);
    }

    private void check() {
        // TODO 4：读取本轮数据 → 按题意判断/执行操作 → 更新对应格子。
        // grid.setCell(row, col, text);
        // grid.setCell(row, col, text, backgroundColour);
        // 可把多个格子的更新写在同一个循环里。多行文字用 "\n" 连接。
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().open());
    }

    private void open() {
        JFrame frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(grid);
        initialise();
        frame.addWindowListener(new WindowAdapter() {
            @Override public void windowClosed(WindowEvent event) {
                monitor.stop();
            }
        });
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        monitor.start();
    }
}

package cz.mendelu.xkozak.pjj.project.sudoku;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ListSelectionModel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Component;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Gui for SudokuResolver
 *
 * @author Tom� Koz�k
 */
public class Sudoku extends JFrame {

    private JPanel contentPane;
    private JTable table;
    private JButton btnDopotat;
    private JTabbedPane tabbedPane;
    private JButton reset;
    private JLabel label;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {

        cz.mendelu.xkozak.pjj.project.sudoku.resolver.Resolver r = new cz.mendelu.xkozak.pjj.project.sudoku.resolver.Resolver();
        r.setNumber(0, 1, 8);
        r.setNumber(0, 7, 6);
        r.setNumber(1, 0, 9);
        r.setNumber(1, 8, 8);
        r.setNumber(2, 4, 3);
        r.setNumber(2, 5, 4);
        r.setNumber(2, 6, 9);
        r.setNumber(3, 0, 3);
        r.setNumber(3, 1, 6);
        r.setNumber(3, 2, 9);
        r.setNumber(3, 4, 8);
        r.setNumber(3, 7, 5);
        r.setNumber(3, 8, 4);
        r.setNumber(4, 0, 2);
        r.setNumber(4, 2, 8);
        r.setNumber(4, 3, 7);
        r.setNumber(5, 0, 1);
        r.setNumber(5, 1, 4);
        r.setNumber(5, 2, 7);
        r.setNumber(5, 4, 6);
        r.setNumber(5, 7, 8);
        r.setNumber(5, 8, 2);
        r.setNumber(6, 4, 2);
        r.setNumber(6, 5, 1);
        r.setNumber(6, 6, 5);
        r.setNumber(7, 0, 6);
        r.setNumber(7, 8, 1);
        r.setNumber(8, 1, 3);
        r.setNumber(8, 7, 4);
        r.solve();

        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 9; y++) {
                System.out.print(r.getNumber(x, y) + " ");
            }
            System.out.println("");
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Sudoku frame = new Sudoku();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Sudoku() {
        setTitle("Sudoku by xkozak");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 303, 300);
        contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        btnDopotat = new JButton("Dopo\u010D\u00EDtat");
        contentPane.add(btnDopotat, BorderLayout.SOUTH);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setToolTipText("");
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        tabbedPane.setAutoscrolls(true);
        contentPane.add(tabbedPane, BorderLayout.NORTH);

        table = new JTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent arg0) {
                label.setText("Vkl�d�n� hodnot");
                label.setForeground(new Color(65, 86, 197));
            }
        });
        tabbedPane.addTab("Sudoku", null, table, null);
        table.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        table.setRowMargin(3);
        table.setFont(new Font("Calibri", Font.BOLD, 15));
        table.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        table.setRowHeight(20);
        table.setSurrendersFocusOnKeystroke(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setAutoCreateRowSorter(true);
        table.setColumnSelectionAllowed(true);
        table.setCellSelectionEnabled(true);
        table.setToolTipText("Vlo\u017Ete hodnotu 1-9");
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setPreferredScrollableViewportSize(new Dimension(200, 200));
        table.setSelectionBackground(new Color(215, 215, 215));
        reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                reset();
            }
        });
        contentPane.add(reset, BorderLayout.WEST);

        label = new JLabel("Muzete vkladat hodnoty ze zadani");
        contentPane.add(label, BorderLayout.EAST);
        btnDopotat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                calculate();
            }
        });
        setTable();
        test();					// dosazeni vychozich hodnot -> abych to vzdy nemusel vypisovat
    }

    private void setTable() {
        String data[][] = {{"", "", "", "", "", "", "", "", ""}, {"", "", "", "", "", "", "", "", ""}, {"", "", "", "", "", "", "", "", ""}, {"", "", "", "", "", "", "", "", ""}, {"", "", "", "", "", "", "", "", ""}, {"", "", "", "", "", "", "", "", ""}, {"", "", "", "", "", "", "", "", ""}, {"", "", "", "", "", "", "", "", ""}, {"", "", "", "", "", "", "", "", ""}};
        String col[] = {"", "", "", "", "", "", "", "", ""};
        DefaultTableModel model = new DefaultTableModel(data, col);

        table.setModel(model);

    }

    private void calculate() {
        label.setText("");
        check();								// kontrola, jestli me tam nekdo nenapsal blbosti
        int[][] array = new int[9][9];
        for (int i = 0; i < 9; i++) { 			// dost�v�m do pole ty hodnoty z tabulky
            for (int j = 0; j < 9; j++) {
                int pom = convert(table.getValueAt(i, j).toString());
                array[i][j] = pom;
            }
        }

        Resolver s = new Resolver();			// zalozeni sudoku a dosazeni tech hodnot
        s.setSudoku(array);
        if (s.isFinishable()) {					// je dokoncitelne
            updateTable(s.getSudoku());
            label.setText("Hotovka");
            label.setForeground(new Color(0, 100, 0));
        } else {								// neni dokoncitelne
            label.setText("Nelze dopocitat");
            label.setForeground(new Color(139, 35, 35));
        }
    }

    private void updateTable(Integer[][] x) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j <= 8; j++) {
                table.setValueAt(x[i][j], i, j);
            }
        }
    }

    private void check() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!(convert(table.getValueAt(i, j).toString().trim()) > 0 && convert(table.getValueAt(i, j).toString().trim()) < 10)) {
                    table.setValueAt("", i, j);
                } else {
                    table.setValueAt(table.getValueAt(i, j).toString().trim(), i, j);
                }
            }
        }
    }

    /**
     *
     * @param String, that we want convert to Integer. Just number between 1-9.
     * @return String to integer
     */
    private int convert(String num) {
        switch (num) {
            case "1":
                return 1;
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
        }
        return 0;
    }

    private void reset() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j <= 8; j++) {
                table.setValueAt("", i, j);
            }
        }
        label.setText("Tabulka byla vyresetov�na");
        label.setForeground(new Color(255, 127, 36));

    }

    private void test() {			// testovani -> abych to vzdy nemusel vypisovat!!!
        label.setText("Ukazka");
        setNumber(0, 1, 8);
        setNumber(0, 7, 6);
        setNumber(1, 0, 9);
        setNumber(1, 8, 8);
        setNumber(2, 4, 3);
        setNumber(2, 5, 4);
        setNumber(2, 6, 9);
        setNumber(3, 0, 3);
        setNumber(3, 1, 6);
        setNumber(3, 2, 9);
        setNumber(3, 4, 8);
        setNumber(3, 7, 5);
        setNumber(3, 8, 4);
        setNumber(4, 0, 2);
        setNumber(4, 2, 8);
        setNumber(4, 3, 7);
        setNumber(5, 0, 1);
        setNumber(5, 1, 4);
        setNumber(5, 2, 7);
        setNumber(5, 4, 6);
        setNumber(5, 7, 8);
        setNumber(5, 8, 2);
        setNumber(6, 4, 2);
        setNumber(6, 5, 1);
        setNumber(6, 6, 5);
        setNumber(7, 0, 6);
        setNumber(7, 8, 1);
        setNumber(8, 1, 3);
        setNumber(8, 7, 4);
    }

    public void setNumber(int x, int y, int num) {
        table.setValueAt(num, x, y);
    }

}

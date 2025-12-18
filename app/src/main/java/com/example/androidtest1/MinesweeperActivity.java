package com.example.androidtest1;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MinesweeperActivity extends AppCompatActivity {
    private static final int GRID_SIZE = 9;
    private boolean isFirstClick = true;
    private Minesweeper minesweeper;
    private GridLayout gl;

    View.OnClickListener onGridBtnClick = button -> {
        String index = (String) button.getTag();
        int x = Integer.parseInt(index.charAt(0) + "");
        int y = Integer.parseInt(index.charAt(1) + "");

        if (isFirstClick) {
            minesweeper.initGame(x, y);
            isFirstClick = false;
        }

        checkCell((Button) button, x, y);
    };

    View.OnClickListener onResetBtnClick = v -> {
        minesweeper = new Minesweeper();
        setGridEnabled(true);
        isFirstClick = true;
        clearUI();
    };

    public void setGridEnabled(boolean enabled) {
        for (int i = 0; i < gl.getChildCount(); i++) {
            View child = gl.getChildAt(i);
            child.setEnabled(enabled);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minesweeper);

        minesweeper = new Minesweeper();
        gl = findViewById(R.id.minesweeper);

        generateGrid();
        setupListeners();
        findViewById(R.id.resetbtn).setOnClickListener(onResetBtnClick);
    }

    private void setupListeners() {
        for (int i = 0; i < gl.getChildCount(); i++) {
            gl.getChildAt(i).setOnClickListener(onGridBtnClick);
        }
        findViewById(R.id.resetbtn).setOnClickListener(onResetBtnClick);
    }

    private void clearUI() {
        for (int i = 0; i < gl.getChildCount(); i++) {
            Button btn = (Button) gl.getChildAt(i);
            btn.setText("");
            setBtnColor(btn, "#f542e6");
        }
    }

    private void generateGrid() {
        gl.setRowCount(GRID_SIZE);
        gl.setColumnCount(GRID_SIZE);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        int w = dm.widthPixels;

        int buttonSize = (int) getResources().getDisplayMetrics().density * (w / 9);

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = buttonSize;
                params.height = buttonSize;
                Button btn = new Button(this);
                btn.setTextSize(10);
                btn.setTag(i + "" + j);
                setBtnColor(btn, "#f542e6");
                gl.addView(btn, params);
            }
        }
    }

    private void checkCell(Button button, int x, int y) {
        Cell cell = minesweeper.getCell(x, y);

        if (cell.isBomb) {
            showGrid();
            setGridEnabled(false);
            isFirstClick = true;
            return;
        }

        if (cell.bombsAround == 0) {
            minesweeper.dfs(x, y);
            showRevealedCells();
        } else {
            revealCell(button, cell);
            setBtnColor(button, "#00ff00");
        }
    }

    private void setBtnColor(Button button, String color) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setStroke(4, Color.parseColor(color));
        drawable.setCornerRadius(16f);
        drawable.setColor(Color.TRANSPARENT);
        button.setBackground(drawable);
    }

    private void revealCell(Button button, Cell cell) {
        button.setText(cell.bombsAround > 0 ? String.valueOf(cell.bombsAround) : "");
    }

    private void showRevealedCells() {
        for (int i = 0; i < gl.getChildCount(); i++) {
            Button v = (Button) gl.getChildAt(i);

            String index = (String) v.getTag();
            int x = Integer.parseInt(index.charAt(0) + "");
            int y = Integer.parseInt(index.charAt(1) + "");

            Cell cell = minesweeper.getCell(x, y);
            if (cell.isRevealed) {
                setBtnColor(v, "#555555");

                if (cell.bombsAround > 0) {
                    v.setText(String.valueOf(cell.bombsAround));
                }
            }
        }
    }

    private void showGrid() {
        for (int i = 0; i < gl.getChildCount(); i++) {
            Button v = (Button) gl.getChildAt(i);

            String index = (String) v.getTag();
            int x = Integer.parseInt(index.charAt(0) + "");
            int y = Integer.parseInt(index.charAt(1) + "");

            updateButton(v, x, y);
            v.setOnClickListener(onGridBtnClick);
        }
    }

    private void updateButton(Button btn, int x, int y) {
        Cell cell = minesweeper.getCell(x, y);

        if (cell.isBomb) {
            setBtnColor(btn, "#FF0000");
            btn.setText("💣");
        }

        setBtnColor(btn, "#555555");
        if (cell.bombsAround > 0)
            btn.setText(String.valueOf(cell.bombsAround));
    }
}

class Minesweeper {
    private final int GRID_SIZE = 9;
    private Grid grid;

    public Minesweeper() {
        grid = new Grid();
    }

    public void generateBombs(int firstX, int firstY) {
        int total = GRID_SIZE * GRID_SIZE;

        List<Integer> cells = new ArrayList<>();
        for (int i = 0; i < total; i++)
            cells.add(i);

        int firstIndex = firstY * GRID_SIZE + firstX;
        cells.remove(Integer.valueOf(firstIndex));
        Collections.shuffle(cells);
        Set<Integer> bombs = new HashSet<>(cells.subList(0, 10));

        for (int index : bombs) {
            int x = index % GRID_SIZE;
            int y = index / GRID_SIZE;
            grid.grid.get(y).get(x).isBomb = true;
        }
    }

    public void generateNumbers() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {

                if (grid.grid.get(i).get(j).isBomb) {
                    grid.grid.get(i).get(j).bombsAround = -1;
                    continue;
                }

                int count = 0;
                for (int x = i - 1; x <= i + 1; x++) {
                    for (int y = j - 1; y <= j + 1; y++) {
                        if (x < 0 || x >= GRID_SIZE || y < 0 || y >= GRID_SIZE)
                            continue;
                        if (x == i && y == j)
                            continue;
                        if (grid.grid.get(x).get(y).isBomb)
                            count++;
                    }
                }

                grid.grid.get(i).get(j).bombsAround = count;
            }
        }
    }

    public void dfs(int x, int y) {
        if (x < 0 || x >= GRID_SIZE || y < 0 || y >= GRID_SIZE) return;
        Cell cell = getCell(x, y);
        if (cell.isRevealed || cell.isBomb) return;
        cell.isRevealed = true;
        if (cell.bombsAround > 0) return;

        dfs(x + 1, y);
        dfs(x - 1, y);
        dfs(x, y + 1);
        dfs(x, y - 1);
    }


    public void initGame(int x, int y) {
        generateBombs(x, y);
        generateNumbers();
    }

    public Cell getCell(int x, int y) {
        return grid.get(x, y);
    }
}

class Grid {
    public ArrayList<ArrayList<Cell>> grid;

    public Grid() {
        grid = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            ArrayList<Cell> row = new ArrayList<>();

            for (int j = 0; j < 9; j++) {
                row.add(new Cell(i, j));
            }

            grid.add(row);
        }
    }

    public Cell get(int x, int y) {
        return this.grid.get(x).get(y);
    }
}

class Cell {
    public int x;
    public int y;
    public boolean isBomb;
    public boolean isRevealed;
    public int bombsAround;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
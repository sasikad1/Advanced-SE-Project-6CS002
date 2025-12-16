package base;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class PictureFrame {
  public int[] rerollValues = null;
  public GameEngine gameEngine = null;

  // Inner interface for rendering strategies - ඉහලට ගෙනියන්න
  interface RenderStrategy {
    void render(Graphics g);
  }

  class DominoPanel extends JPanel {
    private static final long serialVersionUID = 4190229282411119364L;
    private Map<Integer, RenderStrategy> renderStrategies;

    // Constructor එකක් එකතු කරන්න
    public DominoPanel() {
      initializeRenderStrategies();
    }

    private void initializeRenderStrategies() {
      renderStrategies = new HashMap<>();

      renderStrategies.put(0, new RenderStrategy() {
        public void render(Graphics g) {
          Location l = new Location(1, 2);
          l.drawGridLines(g);
          drawHeadings(g);
          drawGrid(g);
          gameEngine.drawDominoes(g);
        }
      });

      renderStrategies.put(1, new RenderStrategy() {
        public void render(Graphics g) {
          Location l = new Location(1, 2);
          l.drawGridLines(g);
          drawHeadings(g);
          drawGrid(g);
          gameEngine.drawGuesses(g);
        }
      });
    }

    public void drawGrid(Graphics g) {
      for (int row = 0; row < 7; row++) {
        for (int column = 0; column < 8; column++) {
          drawDigitGivenCentre(g, 30 + column * 20, 30 + row * 20, 20,
                  gameEngine.getGameState().getGrid()[row][column]);
        }
      }
    }

    public void drawHeadings(Graphics g) {
      for (int row = 0; row < 7; row++) {
        fillDigitGivenCentre(g, 10, 30 + row * 20, 20, row + 1);
      }

      for (int column = 0; column < 8; column++) {
        fillDigitGivenCentre(g, 30 + column * 20, 10, 20, column + 1);
      }
    }

    public void drawDomino(Graphics g, Domino d) {
      if (d.placed) {
        int y = Math.min(d.lowValueY, d.highValueY);
        int x = Math.min(d.lowValueX, d.highValueX);
        int w = Math.abs(d.lowValueX - d.highValueX) + 1;
        int h = Math.abs(d.lowValueY - d.highValueY) + 1;
        g.setColor(Color.WHITE);
        g.fillRect(20 + x * 20, 20 + y * 20, w * 20, h * 20);
        g.setColor(Color.RED);
        g.drawRect(20 + x * 20, 20 + y * 20, w * 20, h * 20);
        drawDigitGivenCentre(g, 30 + d.highValueX * 20, 30 + d.highValueY * 20, 20, d.highValue,
                Color.BLUE);
        drawDigitGivenCentre(g, 30 + d.lowValueX * 20, 30 + d.lowValueY * 20, 20, d.lowValue,
                Color.BLUE);
      }
    }

    void drawDigitGivenCentre(Graphics g, int x, int y, int diameter, int n) {
      int radius = diameter / 2;
      g.setColor(Color.BLACK);
      FontMetrics fm = g.getFontMetrics();
      String txt = Integer.toString(n);
      g.drawString(txt, x - fm.stringWidth(txt) / 2, y + fm.getMaxAscent() / 2);
    }

    void drawDigitGivenCentre(Graphics g, int x, int y, int diameter, int n,
                              Color c) {
      int radius = diameter / 2;
      g.setColor(c);
      FontMetrics fm = g.getFontMetrics();
      String txt = Integer.toString(n);
      g.drawString(txt, x - fm.stringWidth(txt) / 2, y + fm.getMaxAscent() / 2);
    }

    void fillDigitGivenCentre(Graphics g, int x, int y, int diameter, int n) {
      int radius = diameter / 2;
      g.setColor(Color.GREEN);
      g.fillOval(x - radius, y - radius, diameter, diameter);
      g.setColor(Color.BLACK);
      g.drawOval(x - radius, y - radius, diameter, diameter);
      FontMetrics fm = g.getFontMetrics();
      String txt = Integer.toString(n);
      g.drawString(txt, x - fm.stringWidth(txt) / 2, y + fm.getMaxAscent() / 2);
    }

    protected void paintComponent(Graphics g) {
      g.setColor(Color.YELLOW);
      g.fillRect(0, 0, getWidth(), getHeight());

      if (gameEngine != null) {
        RenderStrategy strategy = renderStrategies.get(gameEngine.getGameState().getMode());
        if (strategy != null) {
          strategy.render(g);
        } else {
          // Default rendering එකක් (අවශ්‍ය නම්)
          Location l = new Location(1, 2);
          l.drawGridLines(g);
          drawHeadings(g);
          drawGrid(g);
        }
      }
    }

    public Dimension getPreferredSize() {
      return new Dimension(202, 182);
    }
  }

  public DominoPanel dp;

  public void PictureFrame(GameEngine gameEngine) {
    this.gameEngine = gameEngine;
    if (dp == null) {
      JFrame f = new JFrame("Abominodo");
      dp = new DominoPanel();
      f.setContentPane(dp);
      f.pack();
      f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
      f.setVisible(true);
    }
  }

  public void reset() {
    // TODO: Implement if needed
  }
}
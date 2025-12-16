package base;
import java.awt.Color;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Kevan Buckley, maintained by __student
 * @version 2.0, 2014
 */

//public class Location extends SpacePlace {
public class Location {
  public int column;
  public int row;
  public DIRECTION direction;
  public int temporaryValue;
  public enum DIRECTION {VERTICAL, HORIZONTAL};
  
  public Location(int row, int column) {
    this.row = row;
    this.column = column;
  }

  public Location(int row, int column, DIRECTION direction) {
    this(row, column);
    this.direction = direction;
  }
  
  public String toString() {
    if(direction ==null){
      temporaryValue = column + 1;
      return "(" + (temporaryValue) + "," + (row +1) + ")";
    } else {
      temporaryValue = column + 1;
      return "(" + (temporaryValue) + "," + (row +1) + "," + direction + ")";
    }
  }
  
  public void drawGridLines(Graphics graphics) {
    graphics.setColor(Color.LIGHT_GRAY);
    for (temporaryValue = 0; temporaryValue <= 7; temporaryValue++) {
      graphics.drawLine(20, 20 + temporaryValue * 20, 180, 20 + temporaryValue * 20);
    }
    for (int see = 0; see <= 8; see++) {
      graphics.drawLine(20 + see * 20, 20, 20 + see * 20, 160);
    }
  }
  
  public static int readIntegerFromInput() {
    BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
    do {
      try {
        return Integer.parseInt(r.readLine());
      } catch (Exception e) {
      }
    } while (true);
  }
}
package base;
/**
 * @author Kevan Buckley, maintained by __student
 * @version 2.0, 2014
 */

public class Domino implements Comparable<Domino> {
  public int highValue;
  public int lowValue;
  public int highValueX;
  public int highValueY;
  public int lowValueX;
  public int lowValueY;
  public boolean placed = false;

  public Domino(int highValue, int lowValue) {
    super();
    this.highValue = highValue;
    this.lowValue = lowValue;
  }
  
  public void place(int hx, int hy, int lx, int ly) {
    this.highValueX = hx;
    this.highValueY = hy;
    this.lowValueX = lx;
    this.lowValueY = ly;
    placed = true;
  }

  public String toString() {
    StringBuffer result = new StringBuffer();
    result.append("[");
    result.append(Integer.toString(highValue));
    result.append(Integer.toString(lowValue));
    result.append("]");
    if(!placed){
      result.append("unplaced");
    } else {
      result.append("(");
      result.append(Integer.toString(highValueX +1));
      result.append(",");
      result.append(Integer.toString(highValueY +1));
      result.append(")");
      result.append("(");
      result.append(Integer.toString(lowValueX +1));
      result.append(",");
      result.append(Integer.toString(lowValueY +1));
      result.append(")");
    }
    return result.toString();
  }

  /** turn the domino around 180 degrees clockwise*/
  
  public void invert() {
    int temporaryX = highValueX;
    highValueX = lowValueX;
    lowValueX = temporaryX;
    
    int temporaryY = highValueY;
    highValueY = lowValueY;
    lowValueY = temporaryY;
  }

  public boolean isHorizontal() {
    return highValueY == lowValueY;
  }


  public int compareTo(Domino otherDomino) {
    if(this.highValue < otherDomino.highValue){
      return 1;
    }
    return this.lowValue - otherDomino.lowValue;
  }
  
  
  
}
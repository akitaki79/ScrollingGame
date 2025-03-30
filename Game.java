public class Game {
    
    public static void main(String[] args) {
    test();
  }
  private Grid grid;
  private int userRow;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;

  public Game() {
    grid = new Grid(5, 10);
    userRow = 0;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    updateTitle();
    grid.setImage(new Location(userRow, 0), "user.gif");
  }

  public void play() {
    while (!isGameOver()) {
      grid.pause(100);
      handleKeyPress();
      if (msElapsed % 300 == 0) {
        scrollLeft();
        //oneScroll();
        populateRightEdge();
      }
      updateTitle();
      msElapsed += 100;
    }
  }

  public void handleKeyPress() {
      int key = grid.checkLastKeyPressed();
      Location current = new Location(userRow, 0);
      
      if(key == 38){
          if(userRow == 0){
              userRow = 0;
          }
          else{
              userRow--;
              Location new_loc = new Location(userRow, 0);
              handleCollision(new_loc);
              grid.setImage(new_loc, "user.gif");
              grid.setImage(current, null);
          }
      }
      else if(key == 40){
          if(userRow == 4){
              userRow = 4;
          }
          else{
              userRow++;
              Location new_loc = new Location(userRow, 0);
              handleCollision(new_loc);
              grid.setImage(new_loc, "user.gif");
              grid.setImage(current, null);
          }
      }
  }

  public void populateRightEdge() {
      double odds = Math.random();
      int row_val = (int) (Math.random() * 5);
      if(odds >= 0.7){
          
          Location get_place = new Location(row_val, 9);
          grid.setImage(get_place, "get.gif");
      }
      else if((odds>0.1) && (odds <= 0.7)){
          
          Location get_place = new Location(row_val, 9);
          grid.setImage(get_place, "avoid.gif");
      }
  }
/* wrote this for one row; basically used same code with nested for loop in scrollLeft() method
  public void oneScroll(){
      Location user_loc = new Location(userRow,0);
      for(int i=0; i<10; i++){
          Location prev_pos = new Location(1,i);
          if(i>0){
            Location new_pos = new Location(1, i-1); 
            if ((i-1) < 0) {
                 grid.setImage(prev_pos, null);
            }				
            else if (!prev_pos.equals(new Location(userRow, 1))) {
			    grid.setImage(new_pos, grid.getImage(prev_pos));
            }
		    if (i == 9) {
			    grid.setImage(prev_pos, null);
			}
          }
      }
  }
*/

 public void scrollLeft() {
    Location user_loc = new Location(userRow,0); 
    for(int j=0; j<5; j++){ 
        for(int i=0; i<10; i++){
            Location prev_pos = new Location(j,i); 
            Location new_pos = new Location(j, i-1); 
            if(!prev_pos.equals(user_loc)){ 
                if(i>0){ 
                
                    if ((i-1) < 0) {
                        grid.setImage(prev_pos, null);
                    }				
                    else if (!prev_pos.equals(new Location(userRow, 1))) {
                        grid.setImage(new_pos, grid.getImage(prev_pos));
                    }
                    if (i == 9) {
                        grid.setImage(prev_pos, null);
                    }
                }
            }
            
            if(prev_pos.equals(new Location(userRow, 1))){
               handleCollision(prev_pos);
            }
        }
    }
    
}

  public void handleCollision(Location loc) {
      
          if(grid.getImage(loc) == "get.gif"){
              timesGet++;
          }
          else if(grid.getImage(loc) == "avoid.gif"){
              timesAvoid++;
          }
          grid.setImage(loc, null);
      
  }

  public int getScore() {
    return timesAvoid;
    
  }

  public void updateTitle() {
    grid.setTitle("SCROLLING GAME " + " Final Score: " + timesGet);
  }

  public boolean isGameOver() {
    if(getScore() == 5){
        System.out.println("YOU LOST!");
        System.out.println("FINAL SCORE: " + timesGet);
        return true;
    }
    return false;
  }

  public static void test() {
    Game game = new Game();
    game.play();
  }


  
}
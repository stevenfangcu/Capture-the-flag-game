public class Decoy extends Player {
  //Purpose: This class is a sacrificial lamb, running deep into enemy territory to trick opposing catchers into grabbing them instead of
  //Seekers or Liberators
  private double originalSpeedX = Math.abs(Math.random()*5 - 1);
  private double originalSpeedY = Math.abs(Math.random()*5 - 1);
  //Their original speed is not constant, but rather a range, so that multiple players executing the same code will not have totally uniform movements
  double distanceX;
  double distanceY;
  
  double[] redJukeSpot1 = {750, 25};
  double[] redJukeSpot2 = {725, 225};
  double[] redJukeSpot3 = {737, 450};
  double[] redJukeSpot4 = {700, 300};
  
  double[] blueJukeSpot1 = {50, 25};
  double[] blueJukeSpot2 = {75, 225};
  double[] blueJukeSpot3 = {67, 450};
  double[] blueJukeSpot4 = {100, 300};
  
  double[][] redJukeSpots = {redJukeSpot1, redJukeSpot2, redJukeSpot3, redJukeSpot4};
  double[][] blueJukeSpots = {blueJukeSpot1, blueJukeSpot2, blueJukeSpot3, blueJukeSpot4};
  //JukeSpots are all positions on the grid close to the flag
  //The decoy's goal is to run back and forth between them until it gets caught, wasting the enemy's precious time
  
  double XToRunTo = 0.0;
  double YToRunTo = 0.0;
  
  int Target = -1;
  //Target being equal to -1 triggers the Decoy to set a new target to run to
  int skipTheRest = 0;
  //As usual, this will tip the Decoy off that it should do something besides its regular trickery
  
  @Override
  public double getSpeedX(){ return this.originalSpeedX; }
  
  @Override
  public double getSpeedY(){ return this.originalSpeedY; }
  
  @Override
  protected final void setSpeedX(double speedX, int id){ 
    /* check if the caller knows this entity's id */
    if( id != this.id ){
      throw new SecurityException("Unauthorized change of entity x-direction speed");
    }
    this.originalSpeedX = speedX;
  }
  
  @Override
  protected final void setSpeedY(double speedY, int id){ 
    /* check if the caller knows this entity's id */
    if( id != this.id ){
      throw new SecurityException("Unauthorized change of entity y-direction speed");
    }
    this.originalSpeedY = speedY;
  }
  //My pathfinding algorithm depends on original speed, not current speed, so that's what we retain
  //Also what we alter if we want to permanently change the player's speed
  
  @Override
  public void play(Field field){
    
    if (originalSpeedX > 0) {
      this.speedX = originalSpeedX;
    }
    else {
      this.speedX = originalSpeedX * (-1);
    }
    if (originalSpeedY > 0) {
      this.speedY = originalSpeedY;
    }
    else {
      this.speedY = originalSpeedY * (-1);
    }
    
    if (this.inJail == true) {
      skipTheRest = 1;
      this.speedX = 0;
      this.speedY = 0;
    }
    
    if (this.beenFreed == true) {
      skipTheRest = 1;
      this.speedX = originalSpeedX;
      this.speedY = originalSpeedY;
      
      if (this.team.intern() == "reds"){
        distanceX = (field.getBase2Position()[0] - this.x);
        distanceY = (field.getBase2Position()[1] - this.y);
        if ( Math.hypot( this.getX() - field.getBase2Position()[0], this.getY() - field.getBase2Position()[1] ) <= (field.ARMS_LENGTH)/2 ) {
          this.beenFreed = false;
          skipTheRest = 0;
        }
      }
      
      else{
        distanceX = (field.getBase1Position()[0] - this.x);
        distanceY = (field.getBase1Position()[1] - this.y);
        if ( Math.hypot( this.getX() - field.getBase1Position()[0], this.getY() - field.getBase1Position()[1] ) <= (field.ARMS_LENGTH)/2 ) {
          this.beenFreed = false;
          skipTheRest = 0;
        }
      }
      
      double vector = Math.atan2(distanceY, distanceX);
      this.speedX *= Math.cos(vector);
      this.speedY *= Math.sin(vector);
      
    }
    
    if (skipTheRest == 0) {
      if (this.beenCaught == false) {
        
        if (this.team.intern() == "reds") {
          trickBlues(field);
        }
        else{
          trickReds(field);
        }
      }
      
      else {
        this.speedX = this.originalSpeedX;
        this.speedY = this.originalSpeedY;
        
        if (this.team.intern() == "reds"){
          distanceX = (field.getJail1Position()[0] - this.x);
          distanceY = (field.getJail1Position()[1] - this.y);
          if ( Math.hypot( this.getX() - field.getJail1Position()[0], this.getY() - field.getJail1Position()[1] ) <= (field.ARMS_LENGTH)/2 ) {
            this.inJail = true;
            this.speedX = 0;
            this.speedY = 0;
          }
        }
        
        else{
          distanceX = (field.getJail2Position()[0] - this.x);
          distanceY = (field.getJail2Position()[1] - this.y);
          if ( Math.hypot( this.getX() - field.getJail2Position()[0], this.getY() - field.getJail2Position()[1] ) <= (field.ARMS_LENGTH)/2 ) {
            this.inJail = true;
            this.speedX = 0;
            this.speedY = 0;
          }
        }
        
        double vector = Math.atan2(distanceY, distanceX);
        this.speedX *= Math.cos(vector);
        this.speedY *= Math.sin(vector);
        
      }
    }
    
  }
  
  public void trickBlues(Field field) {
    //Purpose: Tells red Decoys to run into the blue side, heedless of the danger, until they are caught
    if (Target == -1) {
      Target = (int) (Math.random()*3);
      XToRunTo = blueJukeSpots[Target][0];
      YToRunTo = blueJukeSpots[Target][1];
    }
    
    if (Target != -1){
      
      this.speedX = this.originalSpeedX;
      this.speedY = this.originalSpeedY;
      
      distanceX = (XToRunTo - this.x);
      distanceY = (YToRunTo - this.y);
      
      double angle = Math.atan2(distanceY, distanceX);
      this.speedX *= Math.cos(angle);
      this.speedY *= Math.sin(angle);
      
      if ( Math.hypot( this.getX() - XToRunTo, this.getY() - YToRunTo ) <= (field.ARMS_LENGTH) ) {
        Target = -1;
      }
    }
  }
  
  public void trickReds(Field field) {
    //Purpose: Tells blue Decoys to run into the red side, heedless of the danger, until they are caught
    if (Target == -1) {
      Target = (int) (Math.random()*3);
      XToRunTo = redJukeSpots[Target][0];
      YToRunTo = redJukeSpots[Target][1];
    }
    
    if (Target != -1){
      
      this.speedX = this.originalSpeedX;
      this.speedY = this.originalSpeedY;
      
      distanceX = (XToRunTo - this.x);
      distanceY = (YToRunTo - this.y);
      
      double angle = Math.atan2(distanceY, distanceX);
      this.speedX *= Math.cos(angle);
      this.speedY *= Math.sin(angle);
      
      if ( Math.hypot( this.getX() - XToRunTo, this.getY() - YToRunTo ) <= (field.ARMS_LENGTH) ) {
        Target = -1;
      }
    }
  }
  
  @Override
  public void update(Field field){}
  
  public Decoy(Field f, int side, String name, int number, String team,char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = originalSpeedX;
    this.speedY = originalSpeedY;
  }
  
}
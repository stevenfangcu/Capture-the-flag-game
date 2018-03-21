public class Aggressor extends Player {
 //This class is officially in beta: change non-descriptive method names
  private double originalSpeedX = Math.abs(Math.random() * 3.25 - 1) ;
  private double originalSpeedY = Math.abs(Math.random() * 3.25 - 1) ;
  //Their original speed is not constant, but rather a range, so that multiple players executing the same code will not have totally uniform movements
  double distanceX;
  double distanceY;
  int patrolsCalled = 0;
  //When classes that try to catch other players don't have any valid targets, they instead move back and forth between set points
  //patrolsCalled makes sure that they rebound off one point and head to another
  
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
    
    int Hunting = 0;
    //If this is anything other than 0, the player has a target
    //Otherwise, it should patrol between two points
    if(this.getX() > 775 || this.getX() < 15){
      this.speedX = -speedX;
    }
    if(this.getY() > 575 || this.getY() < 15){
      this.speedY = -speedY;
    }
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
    
    if (this.team.intern() == "reds") {
      Hunting = catchBlues(field);
    }
    else {
      Hunting = catchReds(field);
    }
    
    if (Hunting == 0) {
     patrolsCalled += patrol(patrolsCalled);
   }
    
  }
  
  public int catchBlues(Field field) {
    //Purpose: Tells red Aggressors to pick a valid blue target and chase them down
    //Side-Effects: Returns 1, telling the player it shouldn't patrol anymore
    Player Target = null;
    
    for (int i = 0; i < field.getTeam1().size(); i += 1) {
      if (((Player) field.getTeam1().get(i)).getX() > 400 && (((Player) field.getTeam1().get(i)).beenCaught == false) && (((Player) field.getTeam1().get(i)).beenFreed == false)) {
        //Aggressors, as the name implies, are eager to catch
        //The second someone steps over that boundary line, the Aggressor starts to charge them
        Target = (Player) field.getTeam1().get(i);
        break;
      }
    }
    
    if(Target != null) {
      
      this.speedX = this.originalSpeedX;
      this.speedY = this.originalSpeedY;
      
      distanceX = (Target.getX() - this.x);
      distanceY = (Target.getY() - this.y);
    
      double angle = Math.atan2(distanceY, distanceX);
      this.speedX *= Math.cos(angle);
      this.speedY *= Math.sin(angle);
      
      if (field.catchOpponent(this, Target) == true) {
        Target = null;
        return 0;
      }
      
      else {
        return 1;
      }
      
    }

    
    else {
      return 0;
    }
    
        

  }
  
  public int catchReds(Field field) {
    //A mirrored version of catchBlues for blue Aggressors
    Player Target = null;
    
    for (int i = 0; i < field.getTeam2().size(); i += 1) {
      if (((Player) field.getTeam2().get(i)).getX() < 400 && (((Player) field.getTeam2().get(i)).beenCaught == false) && (((Player) field.getTeam2().get(i)).beenFreed == false)) {
        Target = (Player) field.getTeam2().get(i);
        break;
      }
    }
    
    if(Target != null) {
      
      this.speedX = this.originalSpeedX;
      this.speedY = this.originalSpeedY;
      
      distanceX = (Target.getX() - this.x);
      distanceY = (Target.getY() - this.y);
    
      double angle = Math.atan2(distanceY, distanceX);
      this.speedX *= Math.cos(angle);
      this.speedY *= Math.sin(angle);
      
      if (field.catchOpponent(this, Target) == true) {
        Target = null;
        return 0;
      }
      
      else {
        return 1;
      }
      
    }

    
    else {
      return 0;
    }
    
        

  }
  
  public int patrol(int patrolsCalled){
    //Purpose: Make sure that even players who have no target to seek don't appear totally inactive
    //Each player with a patrol method walks up and down different parts of their territory
    //Aggressors are the front lines, so they patrol only a double-digit number of x-coordinates away from the border
    
    this.speedX = this.originalSpeedX;
    this.speedY = this.originalSpeedY;
    
    if (this.team.intern() == "reds") {
      distanceX = (450 - this.x);
    }
    else {
      distanceX = (350 - this.x);
    }
    
    if (patrolsCalled % 2 == 0) {
      distanceY = (500 - this.y);
    }
    else {
      distanceY = (100 - this.y);
    }
    
    double angle = Math.atan2(distanceY, distanceX);
    this.speedX *= Math.cos(angle);
    this.speedY *= Math.sin(angle);
    
    if ( Math.hypot( this.getX() - (distanceX + this.x), this.getY() - (distanceY + this.y) ) <= Field.ARMS_LENGTH ) {
      return 1;
    }
    else{
      return 0;
    }
    
  }
  
  @Override
  public void update(Field field){}
  
  public Aggressor(Field f, int side, String name, int number, String team,char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = originalSpeedX;
    this.speedY = originalSpeedY;
  }
  
}
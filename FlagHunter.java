public class FlagHunter extends Player {
  //This class is officially in beta: needs someone to implement a method that checks if a player has the flag
  //Also need to make catchOpponent actually do something
  private double originalSpeedX = Math.abs(Math.random() * 4.75 - 1);
  private double originalSpeedY = Math.abs(Math.random() * 4.75 - 1);
  //Their original speed is not constant, but rather a range, so that multiple players executing the same code will not have totally uniform movements
  double distanceX;
  double distanceY;
  int patrolsCalled = 0;
  //When classes that try to catch other players don't have any valid targets, they instead move back and forth between set points
  //patrolsCalled makes sure that they rebound off one point and head to another
  int skipTheRest = 0;
  //Any class that can venture into enemy territory has this variable, so that if captured it will skip the rest of its play method
  
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
          Hunting = retrieveRedFlag(field);
        }
        else {
          Hunting = retrieveBlueFlag(field);
        }
        
        if (Hunting == 0) {
          patrolsCalled += patrol(patrolsCalled);
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
  
  public int retrieveRedFlag(Field field) {
    //Purpose: Tells red FlagHunters to chase the blue Seeker2 with the flag
    //Side-Effects: Returns 1, telling the player it shouldn't patrol anymore
    Player Target = null;
    
    for (int i = 0; i < field.getTeam1().size(); i += 1) {
      if (((Player) field.getTeam1().get(i)).hasFlag == true) {
        //Ordinarily this needs to check to make sure it's not catching someone already caught or someone getting a free walk back
        //However, those conditions are mutually exclusive with carrying the flag, so we don't need to include them here
        //FlagHunters are the only players who will pursue enemies beyond their own border
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
  
  public int retrieveBlueFlag(Field field) {
    //Identical to retrieveRedFlag but with the positions reversed
    Player Target = null;
    
    for (int i = 0; i < field.getTeam2().size(); i += 1) {
      if (((Player) field.getTeam2().get(i)).hasFlag == true) {
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
    //FlagHunters are the last line of defence, so they patrol very close to the flag
    
    this.speedX = this.originalSpeedX;
    this.speedY = this.originalSpeedY;
    
    if (this.team.intern() == "reds") {
      distanceX = (735 - this.x);
      if (patrolsCalled % 2 == 0) {
        distanceY = (325 - this.y);
      }
      else {
        distanceY = (500 - this.y);
      }
    }
    else {
      distanceX = (65 - this.x);
      
      if (patrolsCalled % 2 == 0) {
        distanceY = (125 - this.y);
      }
      else {
        distanceY = (300 - this.y);
      }
      
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
  
  public FlagHunter(Field f, int side, String name, int number, String team,char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = originalSpeedX;
    this.speedY = originalSpeedY;
  }
  
}
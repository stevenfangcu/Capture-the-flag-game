public class Liberator extends Player {
  //This class is officially in beta: change non-descriptive method names
  private double originalSpeedX = Math.abs(Math.random() * 4 - 1) ;
  private double originalSpeedY = Math.abs(Math.random() * 4 - 1) ;
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
          Hunting = freeReds(field);
        }
        else {
          Hunting = freeBlues(field);
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
  
  public int freeReds(Field field) {
    //Purpose: Tells red Liberators to run to the jail and get teammates out of there
    //They'll free everyone they can, leaving none behind, even if it means they get caught, because they are real heroes
    //Side-Effects: Returns 1, telling the player it shouldn't patrol anymore
    Player Target = null;
    
    for (int i = 0; i < field.getTeam2().size(); i += 1) {
      if ((((Player) field.getTeam2().get(i)).inJail == true)) {
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
      
      if (field.freeTeammate(this, Target) == true) {
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
  
  public int freeBlues(Field field) {
    //Like freeReds, except for blue Liberators
    Player Target = null;
    
    for (int i = 0; i < field.getTeam1().size(); i += 1) {
      if ((((Player) field.getTeam1().get(i)).inJail == true)) {
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
      
      if (field.freeTeammate(this, Target) == true) {
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
    //Liberators need to run into the back of enemy territory, so they stay near the front
    //Only Aggressors are closer to the border
    
    this.speedX = this.originalSpeedX;
    this.speedY = this.originalSpeedY;
    
    if (this.team.intern() == "reds") {
      distanceX = (500 - this.x);
    }
    else {
      distanceX = (300 - this.x);
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
  
  public Liberator(Field f, int side, String name, int number, String team,char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = originalSpeedX;
    this.speedY = originalSpeedY;
  }
  
}
public class Seeker2 extends Player {
  //This class is officially in beta: needs the winGame method to be working to do what it's supposed to do
  private double originalSpeedX = Math.abs(Math.random() * 5 - 1) ;
  private double originalSpeedY = Math.abs(Math.random() * 5 - 1) ;
  //Their original speed is not constant, but rather a range, so that multiple players executing the same code will not have totally uniform movements
  double distanceX;
  double distanceY;
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
  //I had to alter Entity slightly to be able to override these setters; they're supposed to be final
  
  @Override
  public void play(Field field){
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
      if (hasFlag == false) {
        hasFlag = seekFlag(field);
      }
      else {
        Entity b = field.flag1;    
        if(this.team.intern() == "blues" ){
          b = field.flag2;
        }
        claimFlag(field);
        //The text below sets the flag up so it's ready for the Seeker to take it
        Flag c = (Flag) b; // casting it as a flag object instead of an entity one
        int flagID = c.getID(); // getting the id of the flag
        double flagSpeedX = speedX; // getting the x speed of player
        double flagSpeedY = speedY; // y speed
        c.setSpeedY(flagSpeedY,flagID); // setting the y speed
        c.setSpeedX(flagSpeedX,flagID); // and x speed
        
        if(this.beenCaught == true){
          //For a lot of reasons, Seekers are special
          //They need code that executes only once when they've been caught, to check if they have the flag
          //If they do, the flag teleports back to the base it came from
          this.hasFlag = false; // loses flag
          if(this.getTeam().intern() == "reds"){ // gets team   
            System.out.println(this.getName() + " has dropped the blue flag!");
            c.setX(34+field.minX,flagID);
            c.setY(191+field.minY,flagID);
            c.setSpeedX(0,flagID);
            c.setSpeedY(0,flagID);
          }else{ // other team
            System.out.println(this.getName() + " has dropped the red flag!");
            c.setX(754+field.minX,flagID);
            c.setY(391+field.minY,flagID);
            c.setSpeedX(0,flagID);
            c.setSpeedY(0,flagID);
          }
          
        }
        
      }
      
      if (this.beenCaught == true && ((this.team.intern() == "reds" && this.x < 400) || (this.team.intern() == "blues" && this.x > 400))) {
        //Seekers are the only class that can be caught outside the bounds of enemy territory
        //Now, if that happens, they don't go to jail, they just drop the flag
        //As such, the jail-seeking code MUST only execute when they're on the enemy side
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
      else {
        this.beenCaught = false;
      }
      
      
    }
  }
  
  
  public boolean seekFlag(Field field) {
    //Purpose: tells the seeker to look for the flag
    //Returns true when they have it so they know they don't need to look for it anymore
    if (this.team.intern() == "reds"){
      distanceX = (field.getFlag1Position()[0] - this.x);
      distanceY = (field.getFlag1Position()[1] - this.y);
    }
    
    else{
      distanceX = (field.getFlag2Position()[0] - this.x);
      distanceY = (field.getFlag2Position()[1] - this.y);
    }
    
    double vector = Math.atan2(distanceY, distanceX);
    this.speedX *= Math.cos(vector);
    this.speedY *= Math.sin(vector);
    
    if (this.pickUpFlag(field) == true) {
      return true;
    }
    else {
      return false;
    }
  }
  
  public void claimFlag(Field field) {
    //Purpose: Run the flag back to the base
    //Every time this gets called, Seeker2 checks if it can win yet
    if (this.team.intern() == "reds"){
      distanceX = (field.getBase2Position()[0] - this.x);
      distanceY = (field.getBase2Position()[1] - this.y);
    }
    
    else{
      distanceX = (field.getBase1Position()[0] - this.x);
      distanceY = (field.getBase1Position()[1] - this.y);
    }
    
    double vector = Math.atan2(distanceY, distanceX);
    this.speedX *= Math.cos(vector);
    this.speedY *= Math.sin(vector);
    
    if (winGame(field) == true) {
      this.originalSpeedX = 0;
      this.originalSpeedY = 0;
    }
    
  }
  
  @Override
  public void update(Field field){}
  
  public Seeker2(Field f, int side, String name, int number, String team,char symbol, double x, double y){
    super(f, side, name, number, team, symbol, x, y);
    this.speedX = originalSpeedX;
    this.speedY = originalSpeedY;
  }
  
}
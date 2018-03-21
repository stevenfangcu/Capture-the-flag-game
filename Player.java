public abstract class Player extends Entity{
 
 
  /** this player's team name */
  protected String team;
  
  /** this player's name */
  protected String name;
  
  /** this player's number */
  protected int number;
  
  protected boolean hasFlag = false;
  //Self-evident: sets to true when a flag-seeking player has the flag, determines whether or not they can win the game
  //Also triggers FlagHunter players to attempt to get their flag back
  protected boolean beenCaught = false;
  //Tells a player they've been caught and they need to stop executing their normal play method and head to jail
  //Any player whose job is to catch other players will not target those who have already been caught
  protected boolean inJail = false;
  //Once in jail (i.e., when they are determined to be within half an ARMS_LENGTH from whichever jail they belong in),
  //the player stops moving altogether and can now be freed by their Liberator teammates
  protected boolean beenFreed = false;
  //Once a Liberator tags a person in jail, that person gets to move back to the base at their usual speed
  //They will not go back to their normal play behaviour until they have tagged the base
  
  /** gets this player's team name
    * 
    * @return the team name that this player is on
    */
  public final String getTeam(){ return this.team; }
  public final int getID(){return this.id;}
  
  /** gets this player's name
    * 
    * @return the name of this player
    */
  public final String getName(){ return this.name; }

  /** gets this player's number
    * 
    * @return the number of this player
    */
  public final int getNumber(){ return this.number; }

  
  /** creates a player with specified symbol at specified position 
    * 
    * @param f is the field the player will be playing on
    * @param side is the side of the field the player will play on
    * @param name is this name of the player
    * @param number is this player's number 
    * @param team is this player's team name
    * @param symbol is a character (char) representation of this player
    * @param x is the x-coordinate of this player
    * @param y is the y-coordinate of this player
    */
  public Player(Field f, int side, String name, int number, String team, char symbol, double x, double y){
    super(symbol, x, y);
    this.name = name;
    this.number = number;
    this.team = team;
    f.registerPlayer(this, this.id, side);  // register the player on the field
  }
  
  /** attempt to catch an opponent player
    * 
    * @param opponent a player on the opponent's team that you are trying to catch
    * @param field is the field the game is being played on
    * @return true if this player successfully catches the opponent player, false otherwise
    */
  public final boolean catchOpponent(Player opponent, Field field){
    return field.catchOpponent(this, opponent);
  }
  


  /** Informs this player that they have been caught by another player. 
    * <p>
    * This method should only be called from within the Field class.  
    * 
    * @param opponent is the player that caught this player  
    * @param id should be the id of the this player
    */
  public void beenCaught(Player opponent, int id){
    /* check if the caller knows this entity's id */
    if( this.id != id ){
      throw new SecurityException("Unauthorized attempt to call beenCaught ");
    }
    
  }
    
  /** attempt to free a teammate from jail
    * 
    * @param teammate is another player on this player's team
    * @param field is the field the game is being played on
    * @return true if the <code>teammate</code> is successfully freed from jail, false otherwise 
    */
  public final boolean freeTeammate(Player teammate, Field field){
    return field.freeTeammate(this, teammate);
  }
    
  /** Informs this player that they have been freed by a teammate 
    * <p>
    * This method should only be called from within the Field class.  
    * 
    * @param teammate is the player that caught this player  
    * @param id should be the id of the this player
    */
  public void hasBeenFreed(Player teammate, int id){
    /* check if the caller knows this entity's id */
    if( this.id != id){
      throw new SecurityException("Unauthorized attempt to call hasBeenFreed ");
    }
    
  }
  
  
  /** attempt to pick up the opponent's flag
    * 
    * @param field is the field the game is being played on
    * @return true if this player successfully picked up the opponent's flag, false otherwise 
    */
  public final boolean pickUpFlag(Field field){
    return field.pickUpFlag(this);
  }
  
  
  /** Informs this player that they have picked up the flag
    * <p>
    * This method should only be called from with the Field class.  
    * 
    * @param id should be the id of the this player
    */
  public void hasPickedUpFlag(int id){
    /* check if the caller knows this entity's id */
    if( this.id != id ){
      throw new SecurityException("Unauthorized attempt to call hasPickedUpFlag ");
    }
    
  }
  
  /** Informs this player that they have dropped the flag
    * <p>
    * This method should only be called from within the Field class.  
    * 
    * @param id should be the id of the this player
    */
  public void hasDroppedFlag(int id){
    /* check if the caller knows this entity's id */
    if( this.id != id ){
      throw new SecurityException("Unauthorized attempt to call hasDroppedFlag ");
    }
    
  }
  
  
  /** attempt to win the game
    * 
    * @param field is the field the game is being played on
    * @return true if this player successfully brings the opponent's flag back to this player's base, false otherwise 
    */
  public final boolean winGame(Field field){
    return field.winGame(this);
  }
  
   
  
  
}
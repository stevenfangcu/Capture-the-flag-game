import java.util.*;
import java.io.*;

public class Team {
  private int teamCounter = 0;
  protected int side;
  protected String name;
  protected int number; 
  protected String team;
  protected char symbol;
    public Team(Field field, int numberOfPlayers, int teamCounter, int typeOfplayer){ // makes the different players depending on the input of the user
      String[] blueFirst = {"Steven" , "Alex" , "Wayne" , "David" , "Darrel" , "Ruth" , "Lyle" , "Shannon", 
        "Josephine" , "Greg" , "Isabal" , "Marlon" , "Jason" , "Ida", "Maria", "Daniel" , "Cunningham", "Hudson",
        "Rose", "Mary" , "Merri", "Donald", "Trump"
      };   // 23 names in blue 
      String[] redFirst = {"Tim" , "Manuel", "Michael" , "Wilbur" , "Erick" , "Massey" , "Gilbert" , "Rita",
      "Don" , "Woods", "Tiger" , "Trump" , "Donald" , "Hillary" , "Bernie" , "Tracy" , "Nixon", "Wen" , "Kevin",
      "Melvin" , "Veronica" , "Jermaine" , "Simon"
      }; // 23 names in red
      if(teamCounter == 0){ // setting the parameters for the player
        side = 1;
        number = 12;
        name = "asdasd";
        team = "blues";
        symbol = 'b';
      }else{
        side = 2;
        name = "Bunny El-Amin";
        number = 5;
        team = "reds";
        symbol = 'r';
      }      
      Player q,p; 
      if(teamCounter == 0 && typeOfplayer == 0){ // if statements for which player you want
        for(int i = 0; i < numberOfPlayers; i++){
          
          double randomFirst = Math.random() * 23 - 1;
          int someInt = (int) randomFirst;
          double randomSecond = Math.random() * 23 - 1 ;
          int someInt2 = (int) randomSecond;
          name = blueFirst[someInt] + " " + redFirst[someInt2];
          
          
          q = new Seeker2(field,side,name,number,team,symbol,Math.random()*400+10, Math.random()*500+10); // creating the player of Seeker
        }
      }else if(teamCounter == 0 && typeOfplayer == 1){
        for(int i = 0; i < numberOfPlayers; i++){
          double randomFirst = Math.random() * 23 - 1;
          int someInt = (int) randomFirst;
          double randomSecond = Math.random() * 23 - 1 ;
          int someInt2 = (int) randomSecond;
          name = blueFirst[someInt] + " " + redFirst[someInt2];
          
          q = new Aggressor(field,side,name,number,team,symbol,Math.random()*400+10, Math.random()*500+10); // creating the player of Aggressor 
        }
      }else if(teamCounter == 0 && typeOfplayer == 2){
        for(int i = 0; i < numberOfPlayers; i++){
          double randomFirst = Math.random() * 23 - 1;
          int someInt = (int) randomFirst;
          double randomSecond = Math.random() * 23 - 1 ;
          int someInt2 = (int) randomSecond;
          name = blueFirst[someInt] + " " + redFirst[someInt2];
          
          q = new Defender(field,side,name,number,team,symbol,Math.random()*400+10, Math.random()*500+10); // creating the player of Defender
        }
      }else if(teamCounter == 0 && typeOfplayer == 3){
        for(int i = 0 ; i < numberOfPlayers; i++){
          double randomFirst = Math.random() * 23 - 1;
          int someInt = (int) randomFirst;
          double randomSecond = Math.random() * 23 - 1 ;
          int someInt2 = (int) randomSecond;
          name = blueFirst[someInt] + " " + redFirst[someInt2];
          
           q = new Liberator(field,side,name,number,team,symbol,Math.random()*400+10, Math.random()*500+10); // creating the player of Liberator
        }
      }else if(teamCounter == 0 && typeOfplayer == 4){
        for(int i = 0; i < numberOfPlayers; i++){
          double randomFirst = Math.random() * 23 - 1;
          int someInt = (int) randomFirst;
          double randomSecond = Math.random() * 23 - 1 ;
          int someInt2 = (int) randomSecond;
          name = blueFirst[someInt] + " " + redFirst[someInt2];
          
          q = new FlagHunter(field,side,name,number,team,symbol,Math.random()*400+10, Math.random()*500+10); // creating the player of Flaghunter
        }
      }else if(teamCounter == 0 && typeOfplayer == 5){
        for(int i = 0; i < numberOfPlayers; i++){
          double randomFirst = Math.random() * 23 - 1;
          int someInt = (int) randomFirst;
          double randomSecond = Math.random() * 23 - 1 ;
          int someInt2 = (int) randomSecond;
          name = blueFirst[someInt] + " " + redFirst[someInt2];
          
          q = new Decoy(field,side,name,number,team,symbol,Math.random()*400+10, Math.random()*500+10); // creating the player of Flaghunter
        }
      }
        
        
        
        
      else{        
          if(teamCounter == 1 && typeOfplayer == 0){
            for(int i = 0; i < numberOfPlayers; i++){
              
              double randomFirst = Math.random() * 23 - 1;
              int someInt = (int) randomFirst;
              double randomSecond = Math.random() * 23 - 1 ;
              int someInt2 = (int) randomSecond;
              name = redFirst[someInt2] + " " + blueFirst[someInt];
              
              q = new Seeker2(field,side,name,number,team,symbol,Math.random()*400+410, Math.random()*500+10); // creating the player of seeker
            }
          }else if(teamCounter== 1 && typeOfplayer == 1){
            for(int i = 0; i < numberOfPlayers; i++){
                            
              double randomFirst = Math.random() * 23 - 1;
              int someInt = (int) randomFirst;
              double randomSecond = Math.random() * 23 - 1 ;
              int someInt2 = (int) randomSecond;
              name = redFirst[someInt2] + " " + blueFirst[someInt];
              
            q = new Aggressor(field,side,name,number,team,symbol,Math.random()*400+410, Math.random()*500+10); // creating the player of Aggressor 
            }
          }else if(teamCounter == 1 && typeOfplayer == 2){
            for(int i = 0; i < numberOfPlayers; i++){
                            
              double randomFirst = Math.random() * 23 - 1;
              int someInt = (int) randomFirst;
              double randomSecond = Math.random() * 23 - 1 ;
              int someInt2 = (int) randomSecond;
              name = redFirst[someInt2] + " " + blueFirst[someInt];
              
            q = new Defender(field,side,name,number,team,symbol,Math.random()*400+410, Math.random()*500+10); // creating the player of defender
            }
          }else if(teamCounter == 1 && typeOfplayer == 3){
            for(int i = 0; i < numberOfPlayers; i++){
                            
              double randomFirst = Math.random() * 23 - 1;
              int someInt = (int) randomFirst;
              double randomSecond = Math.random() * 23 - 1 ;
              int someInt2 = (int) randomSecond;
              name = redFirst[someInt2] + " " + blueFirst[someInt];
              
              q = new Liberator(field,side,name,number,team,symbol,Math.random()*400+410, Math.random()*500+10); // creating the player of liberator
            }
          }else if (teamCounter == 1 && typeOfplayer == 4){
            for(int i = 0; i < numberOfPlayers; i++){
                            
              double randomFirst = Math.random() * 23 - 1;
              int someInt = (int) randomFirst;
              double randomSecond = Math.random() * 23 - 1 ;
              int someInt2 = (int) randomSecond;
              name = redFirst[someInt2] + " " + blueFirst[someInt];
              
              q = new FlagHunter(field,side,name,number,team,symbol,Math.random()*400+410, Math.random()*500+10); // creating the player of flaghunter
            }
          }else if(teamCounter == 1 && typeOfplayer == 5){
            for(int i = 0; i < numberOfPlayers; i++){
              double randomFirst = Math.random() * 23 - 1;
              int someInt = (int) randomFirst;
              double randomSecond = Math.random() * 23 - 1 ;
              int someInt2 = (int) randomSecond;
              name = blueFirst[someInt] + " " + redFirst[someInt2];
              
              q = new Decoy(field,side,name,number,team,symbol,Math.random()*400+410, Math.random()*500+10); // creating the player of Flaghunter
            }
          }
          
        }
  }
    /* OLD CONSTRUCTOR WE USED TO MAKE THE NEW ONE
  public Team(Field field, int numberOfPlayers, int teamCounter){ // basic function which creates the amount of players
    int filler = 0; // keeps track of the total amount of types of players
    if(teamCounter == 0){
      side = 1;
      name = "Cat van Kittenish";
      number = 12;
      team = "blues";
      symbol = 'b';
    }else{
      side = 2;
      name = "Bunny El-Amin";
      number = 5;
      team = "reds";
      symbol = 'r';
    }
    
    Player q,p;
    if(teamCounter == 0){
      int playerFiller = 0;
      for(int i = 0; i < numberOfPlayers; i++){
        if(filler == 0){
          q = new Aggressor(field,side,name,number,team,symbol,Math.random()*400+10, Math.random()*500+10); // creating the player of this type
          filler++;
        }else if(filler == 1){
          q = new Defender(field,side,name,number,team,symbol,Math.random()*400+10, Math.random()*500+10); // creating the player of this type
          filler++;
        }else if(filler == 2){
          q = new FlagHunter(field,side,name,number,team,symbol,Math.random()*400+10, Math.random()*500+10); // creating the player of this type
          filler++;
        }else if(filler == 3){
          q = new Liberator(field,side,name,number,team,symbol,Math.random()*400+10, Math.random()*500+10); // creating the player of this type
          filler++;
        }else if (filler == 4){
          q = new Seeker2(field,side,name,number,team,symbol,Math.random()*400+10, Math.random()*500+10); // creating the player of this type 
          filler = 0;
        }
      }
    }
     else{
      for(int i = 0; i < numberOfPlayers; i++){
        if(filler == 0){
          q = new Aggressor(field,side,name,number,team,symbol,Math.random()*400+410, Math.random()*500+10);
          filler++;
        }else if(filler == 1){
          q = new Defender(field,side,name,number,team,symbol,Math.random()*400+410, Math.random()*500+10);
          filler++;
        }else if(filler == 2){
          q = new Seeker2(field,side,name,number,team,symbol,Math.random()*400+410, Math.random()*500+10);
          filler++;
        }else if(filler == 3){
          q = new Liberator(field,side,name,number,team,symbol,Math.random()*400+410, Math.random()*500+10);
          filler++;
        }else if (filler == 4){
          q = new FlagHunter(field,side,name,number,team,symbol,Math.random()*400+410, Math.random()*500+10);
          filler = 0;
        }
      } 
    }
  }
  */
  public Team(String dataText, Field field, String team){ // writing function constructor 
    try{
      BufferedReader in = new BufferedReader(new FileReader(dataText) ); // to read the file
      String firstLine; // making a string to first line of text in the file
      String secondLine; // second string of text in the file 
      int won = 0; // default scores
      int lost = 0;
      
      
      if(in.readLine() != null){ // if the text is empty
      in.skip(12); // skip this many characters 
      firstLine = in.readLine(); // the number which is the only thing in the line is this 
      firstLine = firstLine.trim(); // trim it 
      won = Integer.parseInt(firstLine); // convert the string to an int 
      in.skip(12); // next line skip 12 characters 
      secondLine = in.readLine(); // read the line
      secondLine = secondLine.trim(); // trim it 
      lost = Integer.parseInt(secondLine); // converting the string to an int
      in.close(); // closing the text file 
      }
      
      String personName;
      PrintWriter newIn = new PrintWriter(new File(dataText)); // rewrites the things in the data file 
      ArrayList<Entity> filler = new ArrayList<Entity>(); // filler
      String ourTeam; // which team the players are on
      if(team.intern() == "blues"){ // so the team we are on is blue so get the players on team 2 
        filler = field.getTeam2(); // get the team if they are on blue
        ourTeam = "blues"; // our team is blue
      }else{  // otherwise
        filler = field.getTeam1(); // get the team that are oon red
        ourTeam = "reds"; // our team is red
      }
      
      if(ourTeam == "blues" && field.teamWinner == "reds"  ){ // checking if we are on blue and winner is red therefore we lose
        lost++; // adding one to lost 
      }
      else if(ourTeam == "blues" && field.teamWinner == "blues"){ // if we are blue and the winners are bluer we win
        won++;
      }else if(ourTeam == "reds" && field.teamWinner == "blues"){ // if we are red and the winners are blue we lose
        lost++;
      }else if(ourTeam == "reds" && field.teamWinner == "reds"){ // if we are reds and the winner are reds
        won++;
      }
      
      newIn.println("team : " + team); // printing the team we are on in the text file 
      newIn.println("games won : " +  won); // printing how many games we won
      newIn.println("games lost: " + lost);
      newIn.println("                       ");
      
      for(int i = 0; i < filler.size(); i++){
      Entity enFill = filler.get(i);
      int playerID = field.getID.get(enFill); // players ID 
      Player newPlayer = (Player) enFill;
      newIn.println("player ID and name : " + playerID + " and name is " + newPlayer.getName());
    }
      newIn.close();
    }catch(IOException e){
      // readLine() method might throw this exception
      System.err.println("Error: something bad happened reading " + dataText); 
    }catch(NumberFormatException e){
      System.err.println(e);
    }
  }
}
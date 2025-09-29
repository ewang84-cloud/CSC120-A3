import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.Map;
import java.util.HashMap;

class Conversation implements ConversationRequirements {

  /**
   * Attributes
   */
  private int NumIntended;
  private int NumFinished=0;
  private ArrayList<String> Transcript;
  private Scanner scanner;
  private Random random;
  private String[] responses;
  private Map<String, String> wordMap;


  /**
   * Constructor 
   */
  public Conversation() {
    this.NumIntended = 0;
    this.NumFinished = 0;
    this.Transcript = new ArrayList<>();
    this.scanner = new Scanner (System.in);
    this.random = new Random();
    this.responses = new String[]{
      "Hmmm...",
      "Sounds interesting!",
      "Really?",
      "Uh-uhu",
    };
    this.wordMap = new HashMap<>();
      wordMap.put("I", "you");
      wordMap.put("me", "you");
      wordMap.put("am", "are");
      wordMap.put("you", "I");
      wordMap.put("my", "your");
      wordMap.put("your", "my");
  }


  /**
   * Ask for the number of 
   */
  public void RoundNum(){
    System.out.println("How many rounds?");
    NumIntended = scanner.nextInt();
    scanner.nextLine();
    System.out.println();
  }
  

/**
 * Responding the random responses for message
 * @param inputString
 * @return random responses
 */
  public String RandomRespond (String inputString){
    int TempNum = random.nextInt(responses.length);
    return responses[TempNum];
  }


  /**
   * Turning the targeted sentences into the responding version
   * @param input
   * @return the sentence of response without question mark
   */
  private String convertWords(String input) {
    String[] words = input.split("\\s+");
    StringBuilder result = new StringBuilder();
    
    for (int i = 0; i < words.length; i++) {
      String word = words[i];
      if (wordMap.containsKey(word)) {
        result.append(wordMap.get(word));
      }
      else {
        result.append(word);
      }

      if (i < words.length - 1) {
        result.append(" ");
      }
    }
    
    return result.toString();
  }


  /**
   * Responding the converted response back with a question mark
   * @param inputString
   * @return whole converted response
   */
  public String TargetResponse(String inputString) {
    String converted = convertWords(inputString);
    return converted + "?";
  } 


  /**
   * Starts and runs the conversation with the user
   */
  public void chat() {
    if (NumFinished == 0){
      String Beginning = ("Welcome to ChatBox! What's on your mind?");
      System.out.println(Beginning);
      Transcript.add(Beginning);
    } 

    while (NumFinished < NumIntended){
      String userInput = scanner.nextLine();
      Transcript.add(userInput);         
      String response = respond(userInput);
      Transcript.add(response);
      System.out.println(response);

      NumFinished ++;
      }
    
    if (NumFinished == NumIntended){
      String Ending = ("See ya!");
      System.out.println(Ending);
      Transcript.add(Ending);
      
    }
    }
  

  /**
   * Prints transcript of conversation
   */
  public void printTranscript() {
    if(NumFinished == NumIntended){
      System.out.println ("TRANSCRIPT:");
      for (String message : Transcript)
        System.out.println (message);
    }
  }


  /**
   * Gives appropriate response (mirrored or canned) to user input
   * @param inputString the users last line of input
   * @return mirrored or canned response to user input  
   */
  public String respond(String inputString) {
    boolean shouldMirror = false;
  for (String key : wordMap.keySet()) {
    if (inputString.contains(key)) {
      shouldMirror = true;
      break;
    }
    }
    
  if (shouldMirror) {
    return TargetResponse(inputString);
  }
  else{
    return RandomRespond(inputString);
  }
  }

  public static void main(String[] arguments) {

    Conversation myConversation = new Conversation();
    myConversation.RoundNum();
    myConversation.chat();
    myConversation.printTranscript();

  }
}

package spelling;

import java.util.*;


/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
		
		//final String entry = word;
		word = word.toLowerCase();
		int limit = word.length();
		
	    //TODO: Implement this method.
	    TrieNode currNode = root;
		for(int i = 0; i < limit; i++) {
			char[] charList = word.toCharArray();
			char currentChar = word.charAt(i);

				//if(currNode.getChildren().containsKey(currentChar)) {
				  if(currNode.getChildren().containsKey(charList[i])) {
					
					if(i == limit - 1 && currNode.getChild(charList[i]).endsWord() == false) { //add getchild
						currNode.getChild(charList[i]).setText(word); // add getchild
						currNode.getChild(charList[i]).setEndsWord(true); //add getchild
						size ++;
						return true;
					}else if(i == limit - 1 && currNode.getChild(charList[i]).endsWord() == true ) {
						return false; 
					}else {
					//currNode = currNode.getChild(currentChar);
					currNode = currNode.getChild(charList[i]);}
				//}else if(!currNode.getChildren().containsKey(currentChar)) {
				}else if(!currNode.getChildren().containsKey(charList[i])) {
					
					//currNode.insert(currentChar);
					currNode.insert(charList[i]);
					if(i == limit - 1) {
						currNode.getChild(charList[i]).setText(word);//add getChild 
						currNode.getChild(charList[i]).setEndsWord(true);// add getChild
						size ++;
						return true;
					}else {
					//currNode = currNode.getChild(currentChar);
					currNode = currNode.getChild(charList[i]);
					}
				}
		}
		
	    return false;
	}
	
	

	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
		s = s.toLowerCase();
		int limit = s.length();
	    // TODO: Implement this method
		TrieNode currNode = root;
		
		for(int i = 0; i < limit; i ++) {
			char currChar = s.charAt(i);
			if(currNode.getChildren().containsKey(currChar)) {
				if(i == limit - 1 && currNode.getChild(currChar).getText().equals(s) ) {//add getChild
					 
					return true;
					
				}//else if(i == limit - 1 && currNode.getText()!= s) {
				//	return false;
					
				//}
				else {
				currNode = currNode.getChild(currChar);}
				
			}else if(!currNode.getChildren().containsKey(currChar)) {
				return false;
				
			}
			
			
		}
		
		return false;
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 prefix = prefix.toLowerCase();
    	 List<String> answerList = new ArrayList<String>();
    	 TrieNode stem = new TrieNode();
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 TrieNode currNode = root;
    	 char[] charList = prefix.toCharArray();
    	 int prefixLength = prefix.length();
    	 
    	 for(int i = 0; i<prefixLength; i++) {
    		 if(currNode.getChildren().containsKey(charList[i])) {
    			 if(i == prefixLength - 1) {
    				 stem = currNode.getChild(charList[i]); //Stem is the node represent the prefix
    				 //stem = currNode;
    				 break;
    			 }
    			 currNode = currNode.getChild(charList[i]);
    		 }else {
    			 return answerList;
    		 }
    		 
    	 }
    	 
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 LinkedList<TrieNode> answerQueue = new LinkedList<TrieNode>();
    	 //answerQueue.add(stem);
    	 for(char c: stem.getValidNextCharacters()) {
    			 answerQueue.add(stem.getChild(c));
    	 }
    	 
    	 
    	 while(answerList.size() <numCompletions && answerQueue.size() != 0) {
	    	 TrieNode answerNode = answerQueue.poll();
	    	 if(answerNode.endsWord()) {
	    		 answerList.add(answerNode.getText());
	    	 }
	    	 for(char c:answerNode.getValidNextCharacters()) {
	    		 answerQueue.add(answerNode.getChild(c));
	    		 
	    	 	}
    	 }
    	//while(answerList.size() < numCompletions) {
    		
       		 
       	 
    	 //}
    	 
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 
         return answerList;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	
 	
 	public static void main(String[] args) {
 		
 		AutoCompleteDictionaryTrie emptyDict = new AutoCompleteDictionaryTrie();
 		AutoCompleteDictionaryTrie smallDict = new AutoCompleteDictionaryTrie();
 		
 		smallDict.addWord("Hello");
		smallDict.addWord("HElLo");
		smallDict.addWord("hey");
		smallDict.addWord("head");
		smallDict.addWord("helq");
		smallDict.addWord("helt");
		smallDict.addWord("help");
		smallDict.addWord("hecq");
	    smallDict.addWord("heck");
		smallDict.addWord("he");
		smallDict.addWord("hem");
		
		smallDict.addWord("hot");
		smallDict.addWord("a");
		smallDict.addWord("subsequent");
		
		emptyDict.addWord("hellow");
		emptyDict.addWord("XYZAbC");
 		
 	
		System.out.println(emptyDict.size());
	    System.out.println(emptyDict.isWord("xyzabc"));
		System.out.println(smallDict.isWord("Hell"));
		smallDict.printTree();
		
		List<String> testResult =  smallDict.predictCompletions("he", 1);
		
		//for(String s: testResult) {
			System.out.println(testResult);
		//}
		
 		
 	}
 	
 	 
}
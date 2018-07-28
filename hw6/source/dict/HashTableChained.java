/* HashTableChained.java */

package dict;
import list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/
   protected SList [] hashList;
   protected int collision;
   protected int hashSize;
   protected int entryNumber;



   private static boolean isPrime(int number) {
	   if (number <= 1) {
		   return false;
	   }
	   else if (number == 2 || number ==3) {
		   return true;
	   }
	   else {
		   for ( int i = 2; i*i <= number; i++) {
			   if (number % i == 0) {
				   return false;
			   }
		   }
	   }
	   return true;
   }

   



  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
	  int lower = sizeEstimate;
	  int upper = 2 * sizeEstimate;
	  for (int i = upper; i >= lower; i--) {
		  if (isPrime(i)) {
			  hashSize = i;
			  break;
		  }
	  }
	  hashList = new SList[hashSize];
	  for ( int i = 0; i < hashSize; i++) {
		  hashList[i] = new SList();
	  }
	  collision = 0;
	  entryNumber = 0;
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
	  hashSize = 101;
	  hashList = new SList[hashSize];
	  for (int i = 0; i < hashSize; i++) {
		  hashList[i] = new SList();
	  }
	  collision = 0;
	  entryNumber = 0;
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
	  final int PRIME = 109345121;
	  return (int)(Math.abs((47 * code + 23)) % PRIME) % hashSize; //如果不加（int），则返回的值会截取long的32bits，which may be negative。
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    // Replace the following line with your solution.
    return entryNumber;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    // Replace the following line with your solution.
    return entryNumber == 0;
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
	  Entry newEntry = new Entry();
	  newEntry.key = key;
	  newEntry.value = value;
	  int index = compFunction(key.hashCode());
	  if (hashList[index].length() != 0) {
		  collision++;
	  }

	  hashList[index].insertFront(newEntry);
	  entryNumber++;
	  return newEntry;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    // Replace the following line with your solution.
	int index = compFunction(key.hashCode());
	SList l = hashList[index];
	
	SListNode myNode = (SListNode)l.front();
	while (myNode.isValidNode()) {
		try {
			if (((Entry)myNode.item()).key().equals(key)) {
				return ((Entry)myNode.item());
			} 
			else
				myNode = (SListNode)myNode.next();
		}
		catch (InvalidNodeException e) {
			e.printStackTrace();
		}
	}
    return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */
  public Entry remove(Object key) {
	  SList l = hashList[compFunction(key.hashCode())];
      SListNode myNode = (SListNode)l.front();
	  while (myNode.isValidNode()) {
		  try {
			  if (((Entry) myNode.item()).key().equals(key)) {
				  Entry entry = (Entry) myNode.item();
                  myNode.remove();
                  entryNumber--;
                  return entry;
			  } else {
                  myNode = (SListNode)myNode.next();
              }
          } catch (InvalidNodeException e) {
			  e.printStackTrace();
            }
     }
        return null;
        // Replace the following line with your solution.
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
	  for ( int i = 0; i < hashSize; i++) {
		  hashList[i] = new SList();
	  }
	  entryNumber = 0;
	  collision = 0;
  }




  @Override
  public String toString(){
	  String hashString = "";
	  for(int i=0; i< hashSize; i++){
		  hashString = hashString+"["+hashList[i].length()+"]";
		  if(((i+1)%20)== 0)
			  hashString = hashString +"\n";
	  }
	  return hashString;
  }



  public static double expectedRate(int n, int N) {
        return n - N + N * Math.pow(1 - 1 / (double) N, n);
  }



  public void testOfThis() {
	  System.out.println("The number of the entries is " + entryNumber + ";");
	  System.out.println("The number of the buckets is " + hashSize + ";");
	  System.out.println("The number of the collision is " + collision + ";");
	  System.out.println("The number of the expected collision is " + expectedRate(entryNumber, hashSize) + ";");
	  System.out.println(toString());
  }


}



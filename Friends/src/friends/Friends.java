package friends;

import structures.Queue;
import structures.Stack;

import java.util.*;

public class Friends {

	/**
	 * Finds the shortest chain of people from p1 to p2.
	 * Chain is returned as a sequence of names starting with p1,
	 * and ending with p2. Each pair (n1,n2) of consecutive names in
	 * the returned chain is an edge in the graph.
	 * 
	 * @param g Graph for which shortest chain is to be found.
	 * @param p1 Person with whom the chain originates
	 * @param p2 Person at whom the chain terminates
	 * @return The shortest chain from p1 to p2. Null if there is no
	 *         path from p1 to p2
	 */
/*	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		//copy the vertex number from members and map so it's easier to reference
		int start = g.map.get(p1);
		int end = g.map.get(p2);

		//initialized vistited array
		int length = g.members.length;
		Boolean[] visited = new Boolean[length];


		int [] backLink = new int [length];	

		//initialize the chain first 
		ArrayList<String> introChain = new ArrayList<String>();


		Queue<Integer> queue = new Queue<Integer>();
		//start at the p1 so no need to backtrack
		queue.enqueue(g.map.get(p1));
		visited[g.map.get(p1)] = true;

		//while queue is not empty
		while(!queue.isEmpty()) {
			int node = queue.dequeue();
			for(Friend neighbor = g.members[node].first; neighbor != null; neighbor = neighbor.next) {
				int friendNum = neighbor.fnum;
				// if this hasn't been visited, which is distance -1, added to the queue
				if(!visited[node]) {
					queue.enqueue(friendNum);
					visited[friendNum] = true;
					//put the vertex of nbr into crntent node's position 
					backLink[friendNum] = node;
					//check if the end is being visited, break the traversal from neighbor
					
				}

			}
			// again here we break the whole traversal from the whole thing 
//			if(visited[end]) {
//				break;
//			}
		}
		// at this point, the bfs should be fully traversed.
		//check if p2 is not visited then return null

		// start the traversal of the backLink[] at index of p2, and apply .add to front of the arrayList
		boolean finished = false;
		introChain.add(p2);
		int i = end;
		while(finished = false) {
			//as long as i is not the starting point
			if(i == start) {
				finished = true;
				break;
			}
			// this is like add to front, and move everything to the back of the list
			introChain.add(0, g.members[i].name);
			i = backLink[i];
		}

		return introChain;
*/	
	public static ArrayList<String> shortestChain(Graph g, String p1, String p2) {
		
		/** COMPLETE THIS METHOD **/
		boolean[] visited = new boolean[g.members.length];
		//Insert first element
		int index = g.map.get(p1);
		//make a queue with type Person
		Queue<Person> queue = new Queue<>();
		queue.enqueue(g.members[index]);
		
		
		
		ArrayList<String> PeopleList = new ArrayList<>();
		PeopleList.add(p1);
		
		//initializes the corresponding queue of shortestChain/introChain
		Queue<ArrayList<String>> introChain = new Queue<>();
		introChain.enqueue(PeopleList);
		
		//bfs traversal
		while(!queue.isEmpty()) {
			Person person = queue.dequeue();
			//find the vertex num for crntent person
			int personIndex = g.map.get(person.name);
			visited[personIndex] = true; //mark the person as visited
			
			//Get the path so far from queue
			ArrayList<String> list = introChain.dequeue();
			Friend nbr = g.members[personIndex].first;
			while(nbr != null) {
				if(!visited[nbr.fnum]) {
					//Add the member to the path list to enqueue the new path later					
					ArrayList<String> crnt = new ArrayList<>(list);
					int crntIndex = nbr.fnum;
					String name = g.members[crntIndex].name;
					crnt.add(name);
					
					//Check if person 2 has been found, if found then no need to bfs no more
					if(name.equals(p2)) {
						return crnt;
					}
					
					queue.enqueue(g.members[crntIndex]);
					introChain.enqueue(crnt);
				}

				nbr = nbr.next;
			}
		}
		
		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
		return null;
	}
	/**
	 * Finds all cliques of students in a given school.
	 * 
	 * Returns an array list of array lists - each constituent array list contains
	 * the names of all students in a clique.
	 * 
	 * @param g Graph for which cliques are to be found.
	 * @param school Name of school
	 * @return Array list of clique array lists. Null if there is no student in the
	 *         given school
	 */
	private static void dfs(Graph graph, boolean[] visited, ArrayList<String> cliqueMembers, String school, int index) {
		
		//Method is checking inside the loop if node been visited
		Person person = graph.members[index];
		
		
		//Add /person/student to the list
		if(!visited[index] && person.student && person.school.equals(school)) {
			cliqueMembers.add(person.name);
		}
		visited[graph.map.get(person.name)] = true;

		Friend crnt = graph.members[index].first;
		while(crnt != null) {
			int num = crnt.fnum;
			Person friendPerson = graph.members[num];
			
			if(visited[num] == false && friendPerson.student
					&& friendPerson.school.equals(school)) {
				//System.out.println("Calling on " + friendPerson.name);
				dfs(graph, visited, cliqueMembers, school, num);
			}
			crnt = crnt.next;
		}
		
	}
	public static ArrayList<ArrayList<String>> cliques(Graph g, String school) {

		
		ArrayList<ArrayList<String>> islandList = new ArrayList<>();
		boolean[] visited = new boolean[g.members.length];
		
		for(int i = 0; i < g.members.length; i++) {
			Person crnt = g.members[i];
			//go to the next one  if crnt is not a student
			if(visited[i] || !crnt.student) { 
				continue;
			}
			ArrayList<String> island = new ArrayList<>();
			//System.out.println("Calling from main on " + g.members[i].name);
			dfs(g, visited, island, school, i);
			
			//Discard it if the clique is empty
			if(island != null && 0< island.size() ) {
				islandList.add(island);
			}
		}
		
		return islandList;
		// below is just the previous ones i did
//		// creates the new arraylist of arrayList
//		ArrayList<ArrayList<String>> island = new ArrayList<ArrayList<String>> ();
//
//		//first create visited
//
//		Queue<Integer> queue = new Queue<Integer>();
//
//
//		int length = g.members.length;
//		Boolean[] visited = new Boolean[length];
//		// set the student with different school as visited.
//		for(int i = 0; i < length; i++) {
//			if(!(g.members[i].school.equals(school))) {
//				visited[i] = true;
//				continue;
//			}
//			// this finds the first element of each island/ cliques
//			if(g.members[i].school.equals(school)) {
//				//bfs
//				ArrayList<String> subList= new ArrayList<String>();
//				subList.add(g.members[i].name);
//				//island.add(subList);
//				queue.enqueue(i);
//				visited[i] = true;
//				while(!queue.isEmpty()) {
//					int node = queue.dequeue();			
//					for(Friend neighbor = g.members[node].first; neighbor != null; neighbor = neighbor.next) {
//						int friendNum = neighbor.fnum; 
//						if(!visited[node] && visited[friendNum]) {
//							queue.enqueue(friendNum);
//							visited[friendNum] = true;
//							//put the vertex of nbr into crntent node's position 
//							if(g.members[friendNum].school.equals(school)) {
//								subList.add(g.members[friendNum].name);
//							}
//							//check if the end is being visited, break the traversal from neighbor	
//						}	
//					}					
//				}
//				// after traversal, add the subList to island
//				island.add(subList);
//
//			}
//		}
//
//		return island;
//
//	}
	}
	
	
	/**
	 * Finds and returns all connectors in the graph.
	 * 
	 * @param g Graph for which connectors needs to be found.
	 * @return Names of all connectors. Null if there are no connectors.
	 */
	public static ArrayList<String> connectors(Graph g) {
		HashMap<String, Integer> dfsNums = new HashMap<>();
		HashMap<String, Integer> backNums = new HashMap<>();
		HashSet<String> backedUp = new HashSet<>();
		boolean[] visited = new boolean[g.members.length];
		ArrayList<String> allConnectors = new ArrayList<>();
		
		
		for(int i = 0; i < g.members.length; i++) {
			if(visited[i]) {
				continue;
			}
			//cakk dfs and dfs does update the back, backed, dfsNums and refreshes array int[] since it is a new person
			connectorDFS(g, visited, allConnectors, new int[] {0,0}, i, true, dfsNums, backNums, backedUp);
		}
		//return the array list of connectors
		return allConnectors;
		
	}
	
	private static void connectorDFS(Graph graph, boolean[] visited, ArrayList<String> connectors, int[] nums, int index, boolean startingPoint, HashMap<String, Integer> dfsNums, HashMap<String, Integer> backNums, HashSet<String> backedUp) {
		//Null case is solved by loop condition, does not enter the loop to start.
		//checking loop if a node is visited
		Person person = graph.members[index];	
		int a = graph.map.get(person.name);
		visited[a] = true;
		
		//Update the DFS and Back num
		dfsNums.put(person.name, nums[0]);
		backNums.put(person.name, nums[1]);

		Friend crnt = graph.members[index].first;
		while(crnt != null) {
			int personIndex = crnt.fnum;
			Person friendPerson = graph.members[personIndex];
			
			if(!visited[personIndex]) {
				//increment dfs and backNum for next iteration
				nums[0]++;
				nums[1]++;
				
				connectorDFS(graph, visited, connectors, nums, personIndex,
						false, dfsNums, backNums, backedUp);
				
				// if dfsnum(v) > back(w), then back(v) is set to min(back(v),back(w))
				if(dfsNums.get(person.name) > backNums.get(friendPerson.name)) {
					int minBack = Math.min(backNums.get(person.name), backNums.get(friendPerson.name));
					
					backNums.put(person.name, minBack);
				}
				
				// then this is a connector
				if(dfsNums.get(person.name) <= backNums.get(friendPerson.name)) {
					//if not null and since it's already been visited, makes check on that
					if(!startingPoint || backedUp.contains(person.name)) {
						if(!connectors.contains(person.name))
							connectors.add(person.name);
					}
				}
				
				backedUp.add(person.name);
				
			} else {
				//Neighbor has already been visited
				int minBack = Math.min(backNums.get(person.name), 
						dfsNums.get(friendPerson.name));
				
				backNums.put(person.name, minBack);
			}
			
			crnt = crnt.next;
		}
	}
//		int length = g.members.length;
//		Boolean[] visited = new Boolean[length];
//		int[] dfsNum = new int[length];
//		int num = 1;
//		// update the dfsNum array
//		dfsNum(g, 0, visited, dfsNum, num);
//		/** COMPLETE THIS METHOD **/
//
//		// FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY
//		// CHANGE AS REQUIRED FOR YOUR IMPLEMENTATION
//		return null;
//
//	}
//	private static void dfsNum(Graph g, int v, Boolean[]visited, int dfsNum[], int num) {
//		// starting point
//		visited[v] = true;
//		// num is starting at 1
//		dfsNum[v] = num;
//		for(Friend neighbor = g.members[v].first; neighbor != null; neighbor = neighbor.next) {
//			if(!visited[neighbor.fnum]) {
//				
//				dfsNum(g, neighbor.fnum, visited, dfsNum, num++);
//			}
//		}
//        
//	}
}

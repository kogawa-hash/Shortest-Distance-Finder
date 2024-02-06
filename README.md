# Shortest-Distance-Finder

Tasks:

- Build algorithms for Dijkstra's algorithm that returns the shortest distance from the source to the destination. 

Paint component is the graphing part. We cooperated basic mapping and Dijkstra's algorithms. Initially we used linked list to built adjacency list, but we realized that array list is more efficient for using indexes and searching for elements and adding elements. We graphed each file and calculated the shortest distance correctly from the source to destination.  
This check is performed for each node and all edges leading away from it. Since each edge connects to two nodes, this is done twice per edge, i.e., 2m times. Since we use a set for the check, this is done with constant time; for 2m nodes, the total effort is O(m).
Calculating the total distance: The total distance is calculated at most once per edge because we find a new route to a node at most once per edge. The calculation itself is done with constant effort, so the total effort for this step is also O(m).

Files:

Graph.java represents the main file where we code Dijkstra's and graphing. 

Node.java represents the node class in order to have the intersection.

Edge.java represents the edge class to have the roads.

MinPQ.java represents the pq class.

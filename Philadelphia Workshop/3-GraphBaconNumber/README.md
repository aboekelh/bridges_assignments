Graph Bacon Number
=========

Goals
-----

The purpose of this assignment is to learn to
1. Use the IMDB Actor Movie graph.
2. Compute BFS on that graph.
3. Highlight a shortest path in the graph.


Programming part
----------------

### Task

Highlight the shortest path between two actors in a Movie Actor graph.

### Getting Started

1. Open your scaffolded code.
2. Plug in your credentials.
3. Change the style of nodes Cate_Blanchett and Kevin_Bacon_(I), directly attached nodes and directly attached edges.

### Perform BFS

1. Write a BFS traversal in getBaconNumber that keeps track of parent information.

```algorithm
BFS(G=(V,E), root)
  for all v \in V
    mark[v] = false;
  mark[root] = true;
  queue.push(root);
  while (! queue.empty() )
    v = queue.pop();
    for (u \in neighboor(v))
      if mark[u]
```

### Style the BFS path

1. 

### Help

#### for Java

[SLelement documentation](http://bridgesuncc.github.io/doc/java-api/current/html/classbridges_1_1base_1_1_s_lelement.html)

[Element documentation](http://bridgesuncc.github.io/doc/java-api/current/html/classbridges_1_1base_1_1_element.html)

[ActorMovieIMDB documentation](http://bridgesuncc.github.io/doc/java-api/current/html/classbridges_1_1data__src__dependent_1_1_actor_movie_i_m_d_b.html)

#### for C++

[SLelement documentation](http://bridgesuncc.github.io/doc/cxx-api/current/html/classbridges_1_1_s_lelement.html)

[Element documentation](http://bridgesuncc.github.io/doc/cxx-api/current/html/classbridges_1_1_element.html)

[ActorMovieIMDB documentation *MISSING* ]()

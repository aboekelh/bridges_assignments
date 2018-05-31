SETUP
=====

Goals
-----

The purpose of this assignment is to learn to
1. Access and manipulate remote data through BRIDGES.
2. Manipulate Graph.
3. Display location on a map.

You will be building a visualization that looks like [that](http://bridges-cs.herokuapp.com/assignments/4/bridges_workshop)!

Programming part
----------------

### Task

Grab recent earthquake data and build a graph representing the
locations of the 100 strongest earthquakes.

### Basic

1. Open your scaffolded code.
2. Plug in your credentials.
3. Get the most recent 10000 Earthquake.
4. Only retain the 100 Earthquakes of highest magnitude.

### Place Earthquakes on the map

1. Create a Graph where each earthquake is a vertex.
2. Add no edges for now.
3. Locate Earthquake at their longitude and latitude.
4. Tweak appearance of vertices if you want (e.g., use a different symbols for earthquake in Hawaii or Alaska )
5. Compile, run, and visualize.

### Build a graph based on distances

1. For each pair of earthquake
2. Compute distance using calcDistance
3. If the earthquake a closer than 500km, add an edge.
4. Compile, run, and visualize


### Show just the graph itself

1. Deactivate map overlay (already done)
2. Unpin the vertices by setting their location to infinity
3. Compile, run, and visualize.

### Help

#### for Java

[SLelement documentation](http://bridgesuncc.github.io/doc/java-api/current/html/classbridges_1_1base_1_1_s_lelement.html)

[Element documentation](http://bridgesuncc.github.io/doc/java-api/current/html/classbridges_1_1base_1_1_element.html)

[GraphAdjListSimple documentation](http://bridgesuncc.github.io/doc/java-api/current/html/classbridges_1_1base_1_1_graph_adj_list_simple.html)

[GraphAdjList documentation](http://bridgesuncc.github.io/doc/java-api/current/html/classbridges_1_1base_1_1_graph_adj_list.html)

[ElementVisualizer documentation](http://bridgesuncc.github.io/doc/java-api/current/html/classbridges_1_1base_1_1_element_visualizer.html)

[EarthquakeUSGS documentation](http://bridgesuncc.github.io/doc/java-api/current/html/classbridges_1_1data__src__dependent_1_1_earthquake_u_s_g_s.html)

[Bridges class documentation](http://bridgesuncc.github.io/doc/java-api/current/html/namespacebridges_1_1base.html)

#### for C++

[SLelement documentation](http://bridgesuncc.github.io/doc/cxx-api/current/html/classbridges_1_1_s_lelement.html)

[Element documentation](http://bridgesuncc.github.io/doc/cxx-api/current/html/classbridges_1_1_element.html)

[GraphAdjList documentation](http://bridgesuncc.github.io/doc/cxx-api/current/html/classbridges_1_1_graph_adj_list.html)

[ElementVisualizer documentation](http://bridgesuncc.github.io/doc/cxx-api/current/html/classbridges_1_1_element_visualizer.html)

[EarthquakeUSGS documentation](http://bridgesuncc.github.io/doc/cxx-api/current/html/classbridges_1_1_earthquake_u_s_g_s.html)

[DataSource documentation](http://bridgesuncc.github.io/doc/cxx-api/current/html/namespacebridges_1_1_data_source.html)


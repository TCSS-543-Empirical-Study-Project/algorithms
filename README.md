# Final Project for TCSS543
## Description: 
This project, conducts an empirical study to see which of the network algorithms is better than the others. Our goal in this project is to see what kinds of graphs one algorithm performs better on than the others. 

## The following algorithms are implemented and experimented with: 
1. Ford-Fulkerson (Ch 7.1) 
2. Scaling Ford-Fulkerson (Ch 7.3)
3. Preflow-push algorithm (Ch. 7.4)

## Execute method
- Input the given file
> java tcss543 file
- Input the existing files
> java tcss543

## Structure of code:
### Foundational:
- ResidualVertex.java
- ResidualEdge.java
- ResidualGraph.java

### Three algorithms:
- FordFulkerson.java
- ScalingFordFulkerson.java
- PreflowPush.java

### Testcases:
- ./Bipartite
- ./FixedDegree
- ./Mesh
- ./Random

### Output of testcases:
- output.txt

### Extract data file: 
- getData.csh

## Testcases:
### Test Scenario for FixedDegree graph  -- by Mengting
#### 1. Vertex size test(./FixedDegree/testVertexNumber):
vertex/degree/minCapacity/maxCapacity
- 10v-5o-25min-200max
- 50v-5o-25min-200max
- 100v-5o-25min-200max
- 500v-5o-25min-200max
- 1000v-5o-25min-200max



#### 2. Degree size test(./FixedDegree/testOutEdgeNumber):
vertex/degree/minCapacity/maxCapacity
- 500v-2o-25min-200max
- 500v-4o-25min-200max
- 500v-6o-25min-200max
- 500v-8o-25min-200max
- 500v-10o-25min-200max

#### 3. Capacity Range test(./FixedDegree/testCapacityRange):
vertex/degree/minCapacity/maxCapacity
- 500v-5o-1min-200max
- 500v-5o-50min-200max
- 500v-5o-100min-200max
- 500v-5o-150min-200max
- 500v-5o-199min-200max


### Test Scenario for Random graph  -- by Zelun Jiang
#### 1. Vertex size test(./Random/testVerticesNumber):
vertex/density/minCapacity/maxCapacity
- 10v-20d-25min-200max
- 50v-20d-25min-200max
- 100v-20d-25min-200max
- 150v-20d-25min-200max
- 250v-20d-25min-200max


#### 2. Density test(./Random/testDensity):
vertex/density/minCapacity/maxCapacity
- 100v-10d-25min-200max
- 100v-30d-25min-200max
- 100v-50d-25min-200max
- 100v-70d-25min-200max
- 100v-90d-25min-200max


#### 3. Capacity Range test(./Random/testCapacityRange):
vertex/density/minCapacity/maxCapacity
- 100v-50d-1min-200max
- 100v-50d-50min-200max
- 100v-50d-100min-200max
- 100v-50d-150min-200max
- 100v-50d-199min-200max


### Test Scenario mesh graph  -- by Todd Robbins
#### 1. Vertex size test(./Mesh/testVerticesNumber):
vertex/minCapacity/maxCapacity
- 10v-25min-200max
- 50v-25min-200max
- 100v-25min-200max
- 500v-25min-200max
- 1000v-25min-200max

#### 2. Capacity Range test(./Mesh/testCapacityRange):
vertex/density/minCapacity/maxCapacity
- 500v-1min-200max
- 500v-50min-200max
- 500v-100min-200max
- 500v-150min-200max
- 500v-199min-200max

### Test Scenario Bipartite  graph  -- by Mengting
#### 1. Vertex size test(./Bipartite/testVerticesNumber):
vertex/probability/minCapacity/maxCapacity
- 10v-0.5p-25min-200max
- 50v-0.5p-25min-200max
- 100v-0.5p-25min-200max
- 150v-0.5p-25min-200max
- 250v-0.5p-25min-200max


#### 2. Capacity Range test(./Bipartite/testCapacityRange):
vertex/probability/minCapacity/maxCapacity
- 100v-0.2p-25min-200max
- 100v-0.4p-25min-200max
- 100v-0.6p-25min-200max
- 100v-0.8p-25min-200max
- 100v-1p-25min-200max

#### 3. Capacity Range test(./Bipartite/testDensityNumber):
vertex/probability/minCapacity/maxCapacity
- 100v-0.5p-1min-200max
- 100v-0.5p-50min-200max
- 100v-0.5p-100min-200max
- 100v-0.5p-150min-200max
- 100v-0.5p-199min-200max


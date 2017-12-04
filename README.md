# this project, conducts an empirical study to see which of the network algorithms is better than the others. Our goal in this project is to see what kinds of graphs one algorithm performs better on than the others. The following algorithms are implemented and experimented with: Ford-Fulkerson (Ch 7.1), Scaling Ford-Fulkerson (Ch 7.3), and the preflow-push algorithm (Ch. 7.4).


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
- 1000v-5o-25min-200max
- 1000v-10o-25min-200max
- 1000v-15o-25min-200max
- 1000v-20o-25min-200max
- 1000v-25o-25min-200max


#### 3. Capacity Range test(./FixedDegree/testCapacityRange):
vertex/degree/minCapacity/maxCapacity
- 1000v-5o-1min-200max
- 1000v-5o-50min-200max
- 1000v-5o-100min-200max
- 1000v-5o-150min-200max
- 1000v-5o-199min-200max


### Test Scenario for Random graph  -- by Zelun Jiang
#### 1. Vertex size test(./RandomtestCase/testVerticesNumber):
vertex/density/minCapacity/maxCapacity
- 10v-70d-25min-200max
- 50v-70d-25min-200max
- 100v-70d-25min-200max
- 500v-70d-25min-200max
- 1000v-70d-25min-200max

#### 2. Density test(./RandomtestCase/testDensity):
vertex/density/minCapacity/maxCapacity
- 500v-10d-25min-200max
- 500v-30d-25min-200max
- 500v-50d-25min-200max
- 500v-70d-25min-200max
- 500v-90d-25min-200max


#### 3. Capacity Range test(./RandomtestCase/testCapacityRange):
vertex/density/minCapacity/maxCapacity
- 500v-70d-1min-200max
- 500v-70d-50min-200max
- 500v-70d-100min-200max
- 500v-70d-150min-200max
- 500v-70d-199min-200max

### Test Scenario mesh graph  -- by Todd Robbins
#### 1. Vertex size test(./meshGraphTestCases/testVerticesNumber):
vertex/minCapacity/maxCapacity
- 10v-25min-200max
- 50v-25min-200max
- 100v-25min-200max
- 500v-25min-200max
- 1000v-25min-200max
#### 2. Capacity Range test(./meshGraphTestCases/testCapacityRange):
vertex/density/minCapacity/maxCapacity
- 500v-1min-200max
- 500v-50min-200max
- 500v-100min-200max
- 500v-150min-200max
- 500v-199min-200max


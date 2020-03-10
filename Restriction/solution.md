# Detection and Solution 

This dataset contains different ontology merging tests. In each dataset (d<sub>i</sub>), we include different source ontologies. The below table shows the information for each dataset, including: 
* id; 
* number (n) and name of source ontologies (O<sub>S</sub>); 
* size of source ontologies (in terms of number of classes (C), properties (P), instances (I)); 
* number of corresponding classes, properties, and insatnce of the mapping (M); 
* size of the merged ontology (O<sub>M</sub>).

<table align="center">
<tbody>
<tr align="center">
  <td><b>Case</b></td>
  <td><b>Appearance</b></td>
  <td><b>Examples</b></td>
  <td><b>Conflict type</b></td>
</tr>
  
  <tr align="center">
    <td>A</td>
    <td>value(p<sup>i</sup><sub>maxCard</sub>)  <span>&#8800;</span> value(p<sup>j</sup><sub>maxCard</sub>)</td>
    <td>value(p<sup>i</sup><sub>maxCard</sub>)=(0-4) &nbsp;&nbsp; value(p<sup>j</sup><sub>maxCard</sub>)=(0-5)</td>
    <td>primitive </td>
  </tr>

<tr align="center">
    <td>B</td>
    <td>value(p<sup>i</sup><sub>maxCard</sub>) = value(p<sup>j</sup><sub>maxCard</sub>)</td>
    <td>value(p<sup>i</sup><sub>maxCard</sub>)=(0-4) &nbsp;&nbsp; value(p<sup>j</sup><sub>maxCard</sub>)=(0-4)</td>
    <td>no conflict </td>
  </tr>
  <tr align="center">
    <td>d<sub>3</sub></td>
    <td>3</td>
    <td>cmt | conference | confOf</td>
    <td>- | - | (39,36,0)</td>
    <td>(22,11,0)</td>
    <td>(97,147,0)</td>
  </tr>
  
  <tr align="center">
    <td>d<sub>4</sub></td>
    <td>3</td>
    <td>conference | edas | sigkdd</td>
    <td>- | (104,50,114) | -</td>
    <td>(24,10,0)</td>
    <td>(179,132,114)</td>
  </tr>
  
  <tr align="center">
    <td>d<sub>5</sub></td>
    <td>4</td>
    <td>conference | confOf | edas | ekaw</td>
    <td>- | - | - | - </td>
    <td>(40,13,0)</td>
    <td>(199,167,114)</td>
  </tr>
  
  <tr align="center">
    <td>d<sub>6</sub></td>
    <td>4</td>
    <td>cmt | ekaw | iasted | sigkdd</td>
    <td>- | - | (141,41,4) | -</td>
    <td>(33,5,0)</td>
    <td>(246,156,4)</td>
  </tr>
  
  <tr align="center">
    <td>d<sub>7</sub></td>
    <td>5</td>
    <td>confOf | edas | ekaw | iasted | sigkdd</td>
    <td>- | - | - | - | -</td>
    <td>(53,13,0)</td>
    <td>(305,174,118)</td>
  </tr>
  
  <tr align="center">
    <td>d<sub>8</sub></td>
    <td>7</td>
    <td>cmt | conference | confOf | edas | ekaw | iasted | sigkdd</td>
    <td>- | - | - | - | - | - | - </td>
    <td>(68,27,0)</td>
    <td>(334,274,118)</td>
  </tr>
  
  <tr align="center">
    <td>d<sub>9</sub></td>
    <td>2</td>
    <td>human | mouse</td>
    <td>(3304,2,0) | (2744,3,0)</td>
    <td>(1490,0,0)</td>
    <td>(4526,4,0)</td>
  </tr>
  
  <tr align="center">
    <td>d<sub>10</sub></td>
    <td>2</td>
    <td>FMA_samll | NCI_small</td>
    <td>(3696,24,0) | (6488,63,0)</td>
    <td>(2480,0,0)</td>
    <td>(7285,87,0)</td>
  </tr>
 </table>

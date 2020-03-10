# Dataset statistics

This dataset contains different ontology merging tests. In each dataset (d<sub>i</sub>), we include different source ontologies. The below table shows the information for each dataset, including: 
* id of each dataset (id); 
* name of two source ontologies to be merged (O<sub>S</sub>)
* number of classes in the merged ontology (|C|)
* number of properties in the merged ontology (|C|)
* number of max cardinality restrictions in the merged ontology (|maxCa|)
* number of min cardinality restrictions in the merged ontology (|minCa|)
* number of exact cardinality restrictions in the merged ontology (|exCa|)
* number of allValuesFrom restrictions in the merged ontology (|AVF|)
* number of someValuesFrom restrictions in the merged ontology (|SVF|)
* number of hasValue restrictions in the merged ontology (|HV|)
* number of primitive conflicts (|conf<sub>prim</sub>|)
* number of complex conflicts (|conf<sub>comp</sub>|)

<table align="center">
<tbody>
<tr align="center">
  <td><b>id</b></td>
  <td><b>O<sub>S</sub></b></td>
  <td><b>|C|</b></td>
  <td><b>|P|</b></td>
  <td><b>|maxCa|</b></td>
  <td><b>|minCa|</b></td>
  <td><b>|exCa|</b></td>
  <td><b>|AVF|</b></td>
  <td><b>|SVF|</b></td>
  <td><b>|HV|</b></td>
  <td><b>|conf<sub>prim</sub>|</b></td>
  <td><b>|conf<sub>com</sub>|</b></td>
</tr>
  
  <tr align="center">
    <td>d<sub>1</sub></td>
    <td>cmt, conference</td>
    <td>78</td>
    <td>118</td>
    <td>3</td>
    <td>5</td>
    <td>5</td>
    <td>1</td>
    <td>12</td>
    <td>0</td>
    <td>0</td>
    <td>3</td>
  </tr>

  <tr align="center">
    <td>d<sub>2</sub></td>
    <td>cmt, confOf</td>
    <td>59</td>
    <td>89</td>
    <td>2</td>
    <td>8</td>
    <td>14</td>
    <td>10</td>
    <td>7</td>
    <td>0</td>
    <td>1</td>
    <td>1</td>
  </tr>
  
  <tr align="center">
    <td>d<sub>3</sub></td>
    <td>conference, confOf</td>
    <td>87</td>
    <td>96</td>
    <td>1</td>
    <td>6</td>
    <td>10</td>
    <td>19</td>
    <td>8</td>
    <td>0</td>
    <td>0</td>
    <td>1</td>
  </tr>
 </table>
 
 
# Competency Questions answers on the conflicted O<sub>M</sub> and the repaired O<sub>M'</sub>

This dataset contains answers to 10 CQ questions. <p><a href="http://comerger.uni-jena.de/cqCatalog.jsp">This is the link to the CQs.</a></p>


<table align="center">
<tbody>
<tr align="center">
  <td><b>CQs</b></td>
  <td><b>O<sub>M</sub> d1</b></td>
  <td><b>O<sub>M'</sub> d1</b></td>
  <td><b>O<sub>M</sub> d2</b></td>
  <td><b>O<sub>M'</sub> d2</b></td>
  <td><b>O<sub>M</sub> d3</b></td>
  <td><b>O<sub>M'</sub> d3</b></td>
</tr>
  
  <tr align="center">
    <td>CQ<sub>1</sub></td>
    <td>YES</td>
    <td>YES</td>
    <td>YES</td>
    <td>YES</td>
    <td>YES</td>
    <td>YES</td>
  </tr>
<tr align="center">
    <td>CQ<sub>2</sub></td>
    <td>Person</td>
    <td>Person</td>
    <td>Person</td>
    <td>Person</td>
    <td>Person</td>
    <td>Person</td>
  </tr>
  <tr align="center">
    <td>CQ<sub>3</sub></td>
    <td>exCard 1, minCard 1</td>
    <td>exCard 1</td>
    <td>exCard 1, minCard 1</td>
    <td>exCard 1</td>
    <td>minCard 1</td>
    <td>minCard 1</td>
  </tr>
  <tr align="center">
    <td>CQ<sub>4</sub></td>
    <td>YES</td>
    <td>YES</td>
    <td>YES</td>
    <td>YES</td>
    <td>YES</td>
    <td>YES</td>
  </tr>
  
  <tr align="center">
    <td>CQ<sub>5</sub></td>
    <td>Conference</td>
    <td>Conference</td>
    <td>Conference</td>
    <td>Conference</td>
    <td>Conference</td>
    <td>Conference</td>
  </tr>
  
  <tr align="center">
    <td>CQ<sub>6</sub></td>
    <td>exCard 1, maxCard 2</td>
    <td>exCard 1</td>
    <td>exCard 1</td>
    <td>exCard 1</td>
    <td>maxCard 2</td>
    <td>maxCard 2</td>
  </tr>
  
  <tr align="center">
    <td>CQ<sub>7</sub></td>
    <td>NO</td>
    <td>YES</td>
    <td>NO</td>
    <td>NO</td>
    <td>YES</td>
    <td>YES</td>
  </tr>
  
  <tr align="center">
    <td>CQ<sub>8</sub></td>
    <td>Reviewer, Meta-Reviewer</td>
    <td>Reviewer</td>
    <td>-</td>
    <td>-</td>
    <td>-</td>
    <td>-</td>
  </tr>
  
  <tr align="center">
    <td>CQ<sub>9</sub></td>
    <td>Paper</td>
    <td>Paper</td>
    <td>Contribution</td>
    <td>Contribution</td>
    <td>Contribution</td>
    <td>Contribution</td>
  </tr>
  
  <tr align="center">
    <td>CQ<sub>10</sub></td>
    <td>int</td>
    <td>int</td>
    <td>string,int</td>
    <td>string</td>
    <td>int</td>
    <td>int</td>
  </tr>
 </table>


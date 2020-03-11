# Dataset statistics

For this dataset, we selected three ontologies as an instance from Ontology Alignment Evaluation Initiative (OAEI). 
We decided to choose them because those ontologies are rich with properties and restrictions and suit well for a case study. 
We edited the ontologies by adding some more restrictions and also altered one alignment file to align properties. This was done to to cause more conflicts.

## Characteristics of OWL Restriction Reconciliation Test

This dataset contains different ontology merging tests. In each dataset (d<sub>i</sub>), we include different source ontologies. The below table shows the information for each dataset, including: 
* id; 
* size of classes in merged ontology 
* size of properties in merged ontology
* number of restrictions
* number of primitive conflicts
* number of complex conflicts

<table align="center">
<tbody>
<tr align="center">
  <td><b>id</b></td>
  <td><b>Merged Ontology O<sub>M</sub></b></td>
  <td><b>|C|</b></td>
  <td><b>|P|</b></td>
  <td><b>|maxCard|</b></td>
  <td><b>|minCard|</b></td>
  <td><b>|exCard|</b></td>
  <td><b>|allValuesFrom|</b></td>
  <td><b>|someValuesFrom|</b></td>
  <td><b>|hasValue|</b></td>
  <td><b>|primitive<sub>conflict</sub>|</b></td>
  <td><b>|complex<sub>conflict</sub>|</b></td>
</tr>
  
  <tr align="center">
    <td>d<sub>1</sub></td>
    <td>cmt,conference</td>
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
    <td>cmt,confOf</td>
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
    <td>conference,confOf</td>
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

Queries:
* CQ<sub>1</sub>: Does a Person have an email?
* CQ<sub>2</sub>: Who can have an email?
* CQ<sub>3</sub>: How many emails can a Person have?
* CQ<sub>4</sub>: Does a Conference have a name?
* CQ<sub>5</sub>: Who can have a name?
* CQ<sub>6</sub>: How many names can a Conference have?
* CQ<sub>7</sub>: Has a Review only a Reviewer as author?
* CQ<sub>8</sub>: By whom is a Review written by?
* CQ<sub>9</sub>: What can have a title?
* CQ<sub>10</sub>:Which range does the title have?


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


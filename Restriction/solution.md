# Detection and Solution 


We considers the interaction betweens owl restrctions, as:
* allValuesFrom, someValuesFrom, hasValue, excatCardinality, maxCardinality, minCardinality

The interactions between owl restriction can reveal three different states:
* no conflict
* primitive conflict
* complex conflict

Each conflict on the values or cardinality constraints requires the individual reconciliation method. Thus, we derived a detailed solution of all 21 interaction restriction cases (see cases A-N in the following tables). Case N stands for no conflict. Other cases are explained in detail with given an example for each of them, where p<sub>i</sub> belongs to ontology i (O<sub>i</sub>) and p<sub>j</sub> belongs to ontology j (O<sub>j</sub>).





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
    <td>N</td>
    <td>value(p<sup>i</sup><sub>maxCard</sub>) = value(p<sup>j</sup><sub>maxCard</sub>)</td>
    <td>value(p<sup>i</sup><sub>maxCard</sub>)=(0-4) &nbsp;&nbsp; value(p<sup>j</sup><sub>maxCard</sub>)=(0-4)</td>
    <td>no conflict </td>
  </tr>
  
  <tr align="center">
    <td>B</td>
    <td>value(p<sup>i</sup><sub>minCard</sub>)  <span>&#8800;</span> value(p<sup>j</sup><sub>minCard</sub>)</td>
    <td>value(p<sup>i</sup><sub>minCard</sub>)=(4-n) &nbsp;&nbsp; value(p<sup>j</sup><sub>minCard</sub>)=(5-n)</td>
    <td>primitive </td>
  </tr>
  
  <tr align="center">
    <td>N</td>
    <td>value(p<sup>i</sup><sub>minCard</sub>)  = value(p<sup>j</sup><sub>minCard</sub>)</td>
    <td>value(p<sup>i</sup><sub>minCard</sub>)=(4-n) &nbsp;&nbsp; value(p<sup>j</sup><sub>minCard</sub>)=(4-n)</td>
    <td>no conflict </td>
  </tr>
  
  <tr align="center">
    <td>C</td>
    <td>value(p<sup>i</sup><sub>exCard</sub>)  <span>&#8800;</span> value(p<sup>j</sup><sub>exCard</sub>)</td>
    <td>value(p<sup>i</sup><sub>exCard</sub>)= 4 &nbsp;&nbsp; value(p<sup>j</sup><sub>exCard</sub>)= 5</td>
    <td>primitive </td>
  </tr>
  
  <tr align="center">
    <td>N</td>
    <td>value(p<sup>i</sup><sub>exCard</sub>) = value(p<sup>j</sup><sub>exCard</sub>)</td>
    <td>value(p<sup>i</sup><sub>exCard</sub>)= 4 &nbsp;&nbsp; value(p<sup>j</sup><sub>exCard</sub>)= 4</td>
    <td>primitive </td>
  </tr>
  
  <tr align="center">
    <td>D</td>
    <td>value(p<sup>i</sup><sub>maxCard</sub>) = value(p<sup>j</sup><sub>exCard</sub>)</td>
    <td>value(p<sup>i</sup><sub>maxCard</sub>)=(0-3) &nbsp;&nbsp; value(p<sup>j</sup><sub>exCard</sub>)= 3</td>
    <td>complex </td>
  </tr>
  
  <tr align="center">
    <td>E</td>
    <td>value(p<sup>i</sup><sub>minCard</sub>) = value(p<sup>j</sup><sub>exCard</sub>)</td>
    <td>value(p<sup>i</sup><sub>minCard</sub>)=(3-n) &nbsp;&nbsp; value(p<sup>j</sup><sub>exCard</sub>)= 3</td>
    <td>complex </td>
  </tr>
  
  <tr align="center">
    <td>F</td>
    <td>value(p<sup>i</sup><sub>minCard</sub>) = value(p<sup>j</sup><sub>maxCard</sub>)</td>
    <td>value(p<sup>i</sup><sub>minCard</sub>)=(3-n) &nbsp;&nbsp; value(p<sup>j</sup><sub>maxCard</sub>)=(0-3)</td>
    <td>complex </td>
  </tr>
  
  <tr align="center">
    <td>G</td>
    <td>value(p<sup>i</sup><sub>maxCard</sub>) <span>&#8800;</span> value(p<sup>j</sup><sub>exCard</sub>)</td>
    <td>value(p<sup>i</sup><sub>maxCard</sub>)=(0-3) &nbsp;&nbsp; value(p<sup>j</sup><sub>exCard</sub>)= 2 <b>or</b> value(p<sup>i</sup><sub>maxCard</sub>)=(0-3) &nbsp;&nbsp; value(p<sup>j</sup><sub>exCard</sub>)= 4</td> 
    <td>complex </td>
  </tr>
  
  <tr align="center">
    <td>H</td>
    <td>value(p<sup>i</sup><sub>minCard</sub>) <span>&#8800;</span> value(p<sup>j</sup><sub>exCard</sub>)</td>
    <td>value(p<sup>i</sup><sub>minCard</sub>)=(3-n) &nbsp;&nbsp; value(p<sup>j</sup><sub>exCard</sub>)= 2 <b>or</b> value(p<sup>i</sup><sub>maxCard</sub>)=(3-n) &nbsp;&nbsp; value(p<sup>j</sup><sub>exCard</sub>)= 4</td> 
    <td>complex </td>
  </tr>
  
  <tr align="center">
    <td>I</td>
    <td>value(p<sup>i</sup><sub>minCard</sub>) <span>&#8800;</span> value(p<sup>j</sup><sub>maxCard</sub>)</td>
    <td>value(p<sup>i</sup><sub>minCard</sub>)=(3-n) &nbsp;&nbsp; value(p<sup>j</sup><sub>maxCard</sub>)= (0-4) <b>or</b> value(p<sup>i</sup><sub>maxCard</sub>)=(3-n) &nbsp;&nbsp; value(p<sup>j</sup><sub>exCard</sub>)= (0-2)</td> 
    <td>complex </td>
  </tr>
  
  <tr align="center">
    <td>J</td>
    <td>value(p<sup>i</sup><sub>sVF</sub>) <span>&#8800;</span> value(p<sup>j</sup><sub>sVF</sub>)</td>
    <td>value(p<sup>i</sup><sub>sVF</sub>)=Reviewer &nbsp;&nbsp; value(p<sup>j</sup><sub>sVF</sub>)=Person <b>or</b> value(p<sup>i</sup><sub>sVF</sub>)=Integer &nbsp;&nbsp; value(p<sup>j</sup><sub>sVF</sub>)=String</td> 
    <td>primitive </td>
  </tr>
  
  <tr align="center">
    <td>N</td>
    <td>value(p<sup>i</sup><sub>sVF</sub>) = value(p<sup>j</sup><sub>sVF</sub>)</td>
    <td>value(p<sup>i</sup><sub>sVF</sub>)=Reviewer &nbsp;&nbsp; value(p<sup>j</sup><sub>sVF</sub>)=Reviewer <b>or</b> value(p<sup>i</sup><sub>sVF</sub>)=Integer &nbsp;&nbsp; value(p<sup>j</sup><sub>sVF</sub>)=Integer</td> 
    <td>no conflict </td>
  </tr>
  
  <tr align="center">
    <td>K</td>
    <td>value(p<sup>i</sup><sub>aVF</sub>) <span>&#8800;</span> value(p<sup>j</sup><sub>aVF</sub>)</td>
    <td>value(p<sup>i</sup><sub>aVF</sub>)=Reviewer &nbsp;&nbsp; value(p<sup>j</sup><sub>aVF</sub>)=Person <b>or</b> value(p<sup>i</sup><sub>aVF</sub>)=Integer &nbsp;&nbsp; value(p<sup>j</sup><sub>aVF</sub>)=String</td> 
    <td>primitive </td>
   </tr>
  
  <tr align="center">
    <td>N</td>
    <td>value(p<sup>i</sup><sub>aVF</sub>) = value(p<sup>j</sup><sub>aVF</sub>)</td>
    <td>value(p<sup>i</sup><sub>aVF</sub>)=Reviewer &nbsp;&nbsp; value(p<sup>j</sup><sub>aVF</sub>)=Reviewer <b>or</b> value(p<sup>i</sup><sub>aVF</sub>)=Integer &nbsp;&nbsp; value(p<sup>j</sup><sub>aVF</sub>)=Integer</td> 
    <td>no conflict </td>
  </tr>
  
  <tr align="center">
    <td>L</td>
    <td>value(p<sup>i</sup><sub>aVF</sub>) <span>&#8800;</span> value(p<sup>j</sup><sub>sVF</sub>)</td>
    <td>value(p<sup>i</sup><sub>aVF</sub>)=Reviewer &nbsp;&nbsp; value(p<sup>j</sup><sub>sVF</sub>)=Person <b>or</b> value(p<sup>i</sup><sub>aVF</sub>)=Integer &nbsp;&nbsp; value(p<sup>j</sup><sub>sVF</sub>)=String</td> 
    <td>complex </td>
  </tr>
  
 <tr align="center">
    <td>N</td>
    <td>value(p<sup>i</sup><sub>aVF</sub>) = value(p<sup>j</sup><sub>sVF</sub>)</td>
    <td>value(p<sup>i</sup><sub>aVF</sub>)= Reviewer &nbsp;&nbsp; value(p<sup>j</sup><sub>sVF</sub>)=Reviewer <b>or</b> value(p<sup>i</sup><sub>aVF</sub>)=Integer &nbsp;&nbsp; value(p<sup>j</sup><sub>sVF</sub>)=Integer</td> 
    <td>no conflict </td>
  </tr>
  
   <tr align="center">
    <td>M</td>
    <td>value(p<sup>i</sup><sub>hV</sub>) <span>&#8800;</span> value(p<sup>j</sup><sub>hV</sub>)</td>
    <td>value(p<sup>i</sup><sub>hV</sub>)= Accept.PaperStatus &nbsp;&nbsp; value(p<sup>j</sup><sub>hV</sub>)=Reject.PaperStatus <b>or</b> value(p<sup>i</sup><sub>hV</sub>)=true &nbsp;&nbsp; value(p<sup>j</sup><sub>hV</sub>)=false</td> 
    <td>primitive </td>
  </tr>
  
  <tr align="center">
    <td>N</td>
    <td>value(p<sup>i</sup><sub>hV</sub>) = value(p<sup>j</sup><sub>hV</sub>)</td>
    <td>value(p<sup>i</sup><sub>hV</sub>)= Accept.PaperStatus &nbsp;&nbsp; value(p<sup>j</sup><sub>hV</sub>)=Accept.PaperStatus <b>or</b> value(p<sup>i</sup><sub>hV</sub>)=true &nbsp;&nbsp; value(p<sup>j</sup><sub>hV</sub>)=true</td> 
    <td>no conflict </td>
  </tr>
 </table>
 
 
 
<table align="center">
<tbody>
<tr align="center">
  <td><b>Case</b></td>
  <td><b>Explanation</b></td>
</tr>
<tr align="center">
    <td>A</td>
    <td><b>Detection:</b> based on the Attribute Graph RG there is a violet recursive edge with maxCard - primitive conflict <br> <b>Solution:</b> take the greatest lower bound to cover both - in example it's value(p<sup>j</sup><sub>maxCard</sub>)=(0-5)</td>
</tr>
<tr align="center">
    <td>B</td>
    <td><b>Detection:</b> based on the Attribute Graph RG there is a violet recursive edge with minCard - primitive conflict <br> <b>Solution:</b> take the least upper bound to cover both - in example it's value(p<sup>i</sup><sub>minCard</sub>)=(4-n)</td>
</tr>
<tr align="center">
    <td>C</td>
    <td><b>Detection:</b> based on the Attribute Graph RG there is a violet recursive edge with exCard - primitive conflict <br> <b>Solution:</b> no automatic solution, only through user's preference</td>
</tr>
<tr align="center">
    <td>D</td>
    <td><b>Detection:</b> based on the Attribute Graph RG there is an orange edge between maxCard and exCard - complex conflict <br> <b>Solution:</b> take the exact number to cover both - in example it's value(p<sup>j</sup><sub>exCard</sub>)= 3</td>
</tr>
<tr align="center">
    <td>E</td>
    <td><b>Detection:</b> based on the Attribute Graph RG there is an orange edge between minCard and exCard - complex conflict <br> <b>Solution:</b> take the exact number to cover both - in example it's value(p<sup>j</sup><sub>exCard</sub>)= 3</td>
</tr>
<tr align="center">
    <td>F</td>
    <td><b>Detection:</b> based on the Attribute Graph RG there is an orange edge between minCard and maxCard - complex conflict <br> <b>Solution:</b> take the least upper bound from minCard and greatest lower bound from maxCard to cover both - in example it's 3</td>
</tr>
<tr align="center">
    <td>G</td>
    <td><b>Detection:</b> based on the Attribute Graph RG there is an orange edge between maxCard and exCard - complex conflict <br> <b>Solution:</b> if in range, take the exact number, in example it's value(p<sup>j</sup><sub>exCard</sub>)= 2, if out of range, no solution </td>
</tr>
<tr align="center">
    <td>H</td>
    <td><b>Detection:</b> based on the Attribute Graph RG there is an orange edge between minCard and exCard - complex conflict <br> <b>Solution:</b> if in range, take the exact number, in example it's value(p<sup>j</sup><sub>exCard</sub>)= 4, if out of range, no solution </td>
</tr>
<tr align="center">
    <td>I</td>
    <td><b>Detection:</b> based on the Attribute Graph RG there is an orange edge between minCard and maxCard - complex conflict <br> <b>Solution:</b> like in Case F or no solution if no overlaps </td>
</tr
<tr align="center">
    <td>J</td>
    <td><b>Detection:</b> based on the Attribute Graph RG there is a violet recursive edge with someValuesFrom - primitive conflict <br> <b>Solution:</b> object property: semantic relatedness - if path exists, take superclass; datatype property: subsumption hierarchy - if path exists, take more general type </td>
</tr>
<tr align="center">
    <td>K</td>
    <td><b>Detection:</b> based on the Attribute Graph RG there is a violet recursive edge with allValuesFrom - primitive conflict <br> <b>Solution:</b> object property: semantic relatedness - if path exists, take superclass; datatype property: subsumption hierarchy - if path exists, take more general type </td>
</tr>
<tr align="center">
    <td>L</td>
    <td><b>Detection:</b> based on the Attribute Graph RG there is an orange edge between allValuesFrom and someValuesFrom - complex conflict <br> <b>Solution:</b> object property: semantic relatedness - if path exists, take superclass; datatype property: subsumption hierarchy - if path exists, take more general type </td>
</tr>
<tr align="center">
    <td>M</td>
    <td><b>Detection:</b> based on the Attribute Graph RG there is a violet recursive edge with hasValue - primitive conflict <br> <b>Solution:</b> no automatic solution, only through user's preference </td>
</tr>
<tr align="center">
    <td>N</td>
    <td><b>Detection:</b> based on the Attribute Graph RG there is an edge with label 1 - but there is no conflict <br> <b>Solution:</b> no solution needed, because there is no conflict </td>
</tr>
</table>

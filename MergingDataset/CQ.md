# Competency Questions in the conference domain

We used a set of CQs in the conference domain, a combination of yes/no questions with W-questions.

<table>
    <tr>
    <th align="center">id</th>
    <th align="center">CQ</th>
  </tr>
  <tr>
    <td align="center">CQ1</td>
    <td align="center">Which type of documents can include in the conference document?</td>
  </tr>
  <tr>
    <td align="center">CQ2</td>
    <td align="center">Who (which person) can participate in the conference?</td>
  </tr>
  <tr>
    <td align="center">CQ3</td>
    <td align="center">Can Reviewer be a type of person in the conference domain?</td>
  </tr>
  <tr>
    <td align="center">CQ4</td>
    <td align="center">What is the role of the conference participant at the conference?</td>
  </tr>
  <tr>
    <td align="center">CQ5</td>
    <td align="center">Can a publisher be related to the conference domain?</td>
  </tr>
  <tr>
    <td align="center">CQ6</td>
    <td align="center">Who issues the conference proceeding?</td>
  </tr>
  <tr>
    <td align="center">CQ7</td>
    <td align="center">Who assigns the external reviewer?</td>
  </tr>
  <tr>
    <td align="center">CQ8</td>
    <td align="center">Can conference proceedings have an ISBN?</td>
  </tr>
  <tr>
    <td align="center">CQ9</td>
    <td align="center">Can an author be a conference member?</td>
  </tr>
  <tr>
    <td align="center">CQ10</td>
    <td align="center">Which type of contribution exists in the conference domain?</td>
  </tr>
  <tr>
    <td align="center">CQ11</td>
    <td align="center">Which type of fee exists?</td>
  </tr>
  <tr>
    <td align="center">CQ12</td>
    <td align="center">Which events can exist at the conference?</td>
  </tr>
  <tr>
    <td align="center">CQ13</td>
    <td align="center">Which social events can a conference have?</td>
  </tr>
  <tr>
    <td align="center">CQ14</td>
    <td align="center">Which organization can be at the conference?</td>
  </tr>
  <tr>
    <td align="center">CQ15</td>
    <td align="center">Who assigns the reviewer?</td>
  </tr>
  <tr>
    <td align="center">CQ16</td>
    <td align="center">Can a poster be part of the contribution to the conference?</td>
  </tr>
  <tr>
    <td align="center">CQ17</td>
    <td align="center">Which type of paper can exist in the conference document?</td>
  </tr>
  <tr>
    <td align="center">CQ18</td>
    <td align="center">Can the tutorial be part of the conference?</td>
  </tr>
  <tr>
    <td align="center">CQ19</td>
    <td align="center">Which countries can participate?</td>
  </tr>
  <tr>
    <td align="center">CQ20</td>
    <td align="center">What can be the decision for a submitted paper to the conference</td>
  </tr>
  <tr>
    <td align="center">CQ21</td>
    <td align="center">Who can be a conference member?</td>
  </tr>
  <tr>
    <td align="center">CQ22</td>
    <td align="center">Which type of conference committee exists?</td>
  </tr>
  <tr>
    <td align="center">CQ23</td>
    <td align="center">Can information of the participants be part of the conference document?</td>
  </tr>
  <tr>
    <td align="center">CQ24</td>
    <td align="center">Can a workshop be part of the conference?</td>
  </tr>
  <tr>
    <td align="center">CQ25</td>
    <td align="center">Can a short paper be a contribution at the conference?</td>
  </tr>
  <tr>
    <td align="center">CQ26</td>
    <td align="center">Which type of session exists in the conference?</td>
  </tr>
  <tr>
    <td align="center">CQ27</td>
    <td align="center">Does the conference has a demo session?</td>
  </tr>
  <tr>
    <td align="center">CQ28</td>
    <td align="center">Which audiovisual_equipment conference has?</td>
  </tr>
  <tr>
    <td align="center">CQ29</td>
    <td align="center">Which type of deadline exists in the conference</td>
  </tr>
  <tr>
    <td align="center">CQ30</td>
    <td align="center">Which type of award can be given at the conference?</td>
  </tr>
</table>

We re-write each query for each source and merged ontology as they have different naming for some classes.
Here you see the SPARQL queries on one of the tested ontology.


<table>
    <tr>
    <th align="center">id</th>
    <th align="center">SPARQL</th>
  </tr>
  <tr>
    <td align="center">CQ1</td>
    <td align="center">SELECT ?x  WHERE {?x rdfs:subClassOf  &lt;http://merged#Document&gt;}</td>
  </tr>
  <tr>
    <td align="center">CQ2</td>
    <td align="center">SELECT ?x  WHERE {  ?x rdfs:subClassOf  &lt;http://merged#Person&gt; }</td>
  </tr>
  <tr>
    <td align="center">CQ3</td>
    <td align="center">ASK { &lt;http://merged#Reviewer&gt; rdfs:subClassOf &lt;http://merged#Person&gt; }</td>
  </tr>
  <tr>
    <td align="center">CQ4</td>
    <td align="center">SELECT ?x WHERE  { { SELECT ?x WHERE { &lt;http://conference#Conference_participant&gt; rdfs:subClassOf ?x} } UNION { SELECT ?x WHERE {?x rdfs:subClassOf  &lt;http://conference#Conference_participant&gt; } } }</td>
  </tr>
  <tr>
    <td align="center">CQ5</td>
    <td align="center">ASK { &lt;http://conference#Publisher&gt; rdf:type  owl:Class }</td>
  </tr>
  <tr>
    <td align="center">CQ6</td>
    <td align="center">SELECT  ?x WHERE {   &lt;http://conference#issues&gt; rdfs:domain ?x .  ?p rdfs:range &lt;http://conference#Conference_proceedings&gt;.}</td>
  </tr>
  <tr>
    <td align="center">CQ7</td>
    <td align="center">SELECT  ?x WHERE {   &lt;http://cmt#assignExternalReviewer&gt; rdfs:domain ?x .}</td>
  </tr>
  <tr>
    <td align="center">CQ8</td>
    <td align="center">ASK { &lt;http://conference#has_an_ISBN&gt; rdfs:domain &lt;http://conference#Conference_proceedings&gt; }</td>
  </tr>
  <tr>
    <td align="center">CQ9</td>
    <td align="center">ASK { &lt;http://cmt#ConferenceMember&gt; rdfs:subClassOf &lt;http://merged#author&gt; } </td>
  </tr>
  <tr>
    <td align="center">CQ10</td>
    <td align="center">SELECT ?x  WHERE {  ?x rdfs:subClassOf  &lt;http://conference#Conference_contribution&gt; }</td>
  </tr>
  <tr>
    <td align="center">CQ11</td>
    <td align="center">SELECT ?x  WHERE {  ?x rdfs:subClassOf  &lt;http://conference#Conference_fees&gt;}</td>
  </tr>
  <tr>
    <td align="center">CQ12</td>
    <td align="center">SELECT ?x  WHERE {  ?x rdfs:subClassOf  &lt;http://confOf#Event&gt; }</td>
  </tr>
  <tr>
    <td align="center">CQ13</td>
    <td align="center">SELECT ?x  WHERE {  ?x rdfs:subClassOf  &lt;http://confOf#Social_event&gt; }</td>
  </tr>
  <tr>
    <td align="center">CQ14</td>
    <td align="center">SELECT ?x  WHERE {  ?x rdfs:subClassOf  &lt;http://merged#Organization&gt; }</td>
  </tr>
  <tr>
    <td align="center">CQ15</td>
    <td align="center">SELECT  ?x WHERE {   &lt;http://cmt#assignReviewer&gt; rdfs:domain ?x .}</td>
  </tr>
  <tr>
    <td align="center">CQ16</td>
    <td align="center">ASK { &lt;http://merged#Poster&gt; rdfs:subClassOf  &lt;http://conference#Conference_contribution&gt;}</td>
  </tr>
  <tr>
    <td align="center">CQ17</td>
    <td align="center">SELECT ?x  WHERE {  ?x rdfs:subClassOf  &lt;http://merged#paper&gt; }</td>
  </tr>
  <tr>
    <td align="center">CQ18</td>
    <td align="center">ASK {  &lt;http://merged#Tutorial&gt; rdf:type  owl:Class }</td>
  </tr>
  <tr>
    <td align="center">CQ19</td>
    <td align="center">SELECT ?x WHERE{ ?x  rdf:type &lt;http://merged#Country&gt; }</td>
  </tr>
  <tr>
    <td align="center">CQ20</td>
    <td align="center">SELECT ?x WHERE{ ?x  rdfs:subClassOf &lt;http://cmt#Decision&gt; }</td>
  </tr>
  <tr>
    <td align="center">CQ21</td>
    <td align="center">SELECT ?x WHERE{ ?x  rdfs:subClassOf &lt;http://cmt#ConferenceMember&gt; }</td>
  </tr>
  <tr>
    <td align="center">CQ22</td>
    <td align="center">SELECT ?x WHERE{ ?x  rdfs:subClassOf &lt;http://conference#Committee&gt; }</td>
  </tr>
  <tr>
    <td align="center">CQ23</td>
    <td align="center">ASK { &lt;http://conference#Information_for_participants&gt; rdfs:subClassOf  &lt;http://conference#Conference_document&gt;}</td>
  </tr>
  <tr>
    <td align="center">CQ24</td>
    <td align="center">ASK {  &lt;http://confOf#Workshop&gt; rdf:type  owl:Class }</td>
  </tr>
  <tr>
    <td align="center">CQ25</td>
    <td align="center">ASK { &lt;http://confOf#Short_paper&gt; rdfs:subClassOf  &lt;http://confOf#Contribution&gt;}</td>
  </tr>
  <tr>
    <td align="center">CQ26</td>
    <td align="center">SELECT ?x WHERE{ ?x  rdfs:subClassOf &lt;http://ekaw#Session&gt; }</td>
  </tr>
  <tr>
    <td align="center">CQ27</td>
    <td align="center">ASK {  &lt;http://ekaw#Demo_Session&gt; rdf:type  owl:Class }</td>
  </tr>
  <tr>
    <td align="center">CQ28</td>
    <td align="center">SELECT ?x WHERE{ ?x  rdfs:subClassOf &lt;http://iasted#Audiovisual_equipment&gt; }</td>
  </tr>
  <tr>
    <td align="center">CQ29</td>
    <td align="center">SELECT ?x WHERE{ ?x  rdfs:subClassOf &lt;http://iasted#Deadline&gt; }</td>
  </tr>
  <tr>
    <td align="center">CQ30</td>
    <td align="center">SELECT ?x WHERE{ ?x  rdfs:subClassOf &lt;http://sigkdd#Award&gt; }</td>
  </tr>
</table>

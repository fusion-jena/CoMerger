[![DOI](https://zenodo.org/badge/DOI/10.5281/zenodo.3580593.svg)](https://doi.org/10.5281/zenodo.3580593)

# CoMerger
CoMerger is a Holistic Multiple Ontologies Merging tool, available at http://comerger.uni-jena.de/.

# Publications:
1. Babalou, Samira, and Birgitta König-Ries. "*A Subjective Logic Based Approach to Handling Inconsistencies in Ontology Merging*." OTM Confederated International Conferences" On the Move to Meaningful Internet Systems". Springer, Cham, 2019.
2. Babalou, Samira, and Birgitta König-Ries. "*On using subjective logic to build consistent merged ontologies*." Proceedings of the SEMANTICS, Poster and Demo tracks (2019).
3. Babalou, Samira, and Birgitta König-Ries. "*GMRs: Reconciliation of Generic Merge Requirements in Ontology Integration*." Proceedings of the SEMANTICS, Poster and Demo tracks (2019)
4. Babalou, Samira. "*Holistic Multiple Ontologies Merging*." EKAW (Doctoral Consortium). 2018.
5. Babalou, Samira, and Birgitta König-Ries. "*Holistic Knowledge Integration with {C}o{M}erger: A divide-and-conquer approach*." Submitted to ESWC2020.
6. Babalou, Samira, and Birgitta König-Ries. "*How good is this merged ontology? Towards a customizable quality evaluation*." Submitted to ESWC2020.

# Repository's files:
This repository dedicated to CoMerger packages, inculding source codes and datasets:
* **Merger package**: It includes a holistic merge method based on the given source ontologies and their mapping. 
* **Evaluator package**: aim at evaluating the merged ontology based on the source ontologies.
* **Consistency checker package**: It is based on the subjective logic theory to resolve the inconsistency of the merged ontology with respect to views of source ontologies. 
* **Generic Merge Requirements (GMR)s package**: It consists of a set of requirements and criteria that a merged ontology expects to achieve. Their implementation and the graph-based compatibility checker between them exist.
* **An embedded ontology alignment tool**: We pull the SeeCOnt tool from https://github.com/fusion-jena/OAPT and embedded in our tool, to generate the mapping for the source ontologies before merging them. For SeeCOnt please refer to: <br> Algergawy, Alsayed, Samira Babalou, Mohammad J. Kargar, and S. Hashem Davarpanah. "Seecont: A new seeding-based clustering approach for ontology matching." In East European Conference on Advances in Databases and Information Systems, pp. 245-258. Springer, Cham, 2015.
* **Holistic merge dataset**: It contains the test dataset of publication [5].
* **Merge evaluation dataset**: it contains the test dataset of publication [6].

# Run
* To run the **merge** process:
	1. go to package `fusion.comerger.algorithm.merger.holisticMerge`
	2. set the set the required parameters in the main function of `HolisticMerger.java`
	3. run the main function as java application

* To run the **eveluation** process:
	1. go to package `fusion.comerger.algorithm.merger.holisticMerge.evaluator`
	2. set the parameters in the main funtion of `LocalRun_Eval.java`
	3. run the main function as java application

* To run the **compatibility checker**:
	1. go to package `fusion.comerger.algorithm.merger.holisticMerge.GMR`
	2. set the required parameters in the main function of `LocalRun_CompatibilityChecker.java`
	3. run the main function as java application

* To run the **consistency checker**:
	1. go to package `fusion.comerger.algorithm.merger.holisticMerge.consistency`
	2. set the required parameters in the main function of `LocalRun_Consistency.java`
	3. run the main function as java application

* To run **queries** on the merged and input ontologies
	1. go to package `fusion.comerger.algorithm.merger.query`
	2. set the required parameters in the main function of `LocalRun_Query.java`
	3. run the main function as java application

* To run the merge process for a set of files in a set of folders with **six version of holistic methods** (see [5]):
	1. go to package `fusion.comerger.algorithm.merger.holisticMerge.localTest.batch`
	2. set the required parameters in the main function of `HolisticSixVersion.java`
	3. run the main function as java application
	
* To run **binary ladder** for a set of files in a set of folders
	1. go to package `fusion.comerger.algorithm.merger.holisticMerge.localTest.batch`
	2. set the required parameters in the main function of `BinaryLadder.java`
	3. run the main function as java application
	
* To run **binary balanced** for a set of files in a set of folders
	1. go to package `fusion.comerger.algorithm.merger.holisticMerge.localTest.batch`
	2. set the required parameters in the main function of `BinaryBalance.java`
	3. run the main function as java application
	
* To run the **evaluation metrics** for a set of files in a set of folders:
	1. go to package `fusion.comerger.algorithm.merger.holisticMerge.localTest.batch`
	2. set the required parameters in the main function of `EvalStatisticTest.java`
	3. run the main function as java application

* To run the **web-based application**:
	1. set a Tomcat server on your machine (version 7.0 or higher)
	2. go to `WebContent` folder
	3. run `index.jsp` on Tomcat server
	

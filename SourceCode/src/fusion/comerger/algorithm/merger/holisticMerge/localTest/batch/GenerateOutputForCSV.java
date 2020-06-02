package fusion.comerger.algorithm.merger.holisticMerge.localTest.batch;
/*
 * CoMerger: Holistic Ontology Merging
 * %%
 * Copyright (C) 2019 Heinz Nixdorf Chair for Distributed Information Systems, Friedrich Schiller University Jena
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
* Author: Samira Babalou<br>
* email: samira[dot]babalou[at]uni[dash][dot]jena[dot]de
* Heinz-Nixdorf Chair for Distributed Information Systems<br>
* Institute for Computer Science, Friedrich Schiller University Jena, Germany<br>
* Date: 17/12/2019
*/
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class GenerateOutputForCSV {

	public static void saveResult(String fileName, String testVersion, HashMap<String, String> result)
			throws IOException {
		try (
				// PrintWriter writer = new PrintWriter(new File(fileName))) {
				FileWriter writer = new FileWriter(fileName, true)) {
			StringBuilder sb = new StringBuilder();

			// save the name of file
			sb.append(testVersion);
			sb.append(',');

			// save ont name
			sb.append(result.get("Merged_ontology_name"));
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* *********input ontologies info********* */
			sb.append(result.get("input_ontology_size"));
			sb.append(',');

			sb.append(result.get("corresponding_Class"));
			sb.append(',');

			sb.append(result.get("corresponding_object_properties"));
			sb.append(',');

			sb.append(result.get("corresponding_data_properties"));
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* ********* compactness ********** */
			sb.append(result.get("Tbox_OM"));
			sb.append(',');

			sb.append(result.get("Abox_OM"));
			sb.append(',');

			sb.append(result.get("class_OM"));
			sb.append(',');

			sb.append(result.get("property_OM"));
			sb.append(',');

			sb.append(result.get("instance_OM"));
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			sb.append(result.get("class_coverage"));
			sb.append(',');

			sb.append(result.get("property_coverage"));
			sb.append(',');

			sb.append(result.get("instance_coverage"));
			sb.append(',');

			sb.append(result.get("structure_preservation"));
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* ****** Eval ***** */
			sb.append(result.get("oness"));
			sb.append(',');

			sb.append(result.get("unconnected_class"));
			sb.append(',');

			sb.append(result.get("cycle_class"));
			sb.append(',');

			sb.append(result.get("class_preservation"));
			sb.append(',');

			sb.append(result.get("property_preservation"));
			sb.append(',');

			sb.append(result.get("instance_preservation"));
			sb.append(',');

			sb.append(result.get("value_preservation"));
			sb.append(',');

			sb.append(result.get("cycle_property"));
			sb.append(',');

			sb.append(result.get("inverse_property"));
			sb.append(',');

			sb.append(result.get("unconnected_property"));
			sb.append(',');

			sb.append(result.get("entailment"));
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* ****** Merge Process ***** */
			sb.append(result.get("overlap"));
			sb.append(',');

			sb.append(result.get("Max-Cardinality"));
			sb.append(',');

			sb.append(result.get("k"));
			sb.append(',');

			sb.append(result.get("cluster_class_size"));
			sb.append(',');

			sb.append(result.get("total_axioms"));
			sb.append(',');

			sb.append(result.get("manual_deleted_axioms"));
			sb.append(',');

			sb.append(result.get("Breaking_isA"));
			sb.append(',');

			sb.append(result.get("Breaking_isA_percentage"));
			sb.append(',');

			sb.append(result.get("Breaking_other"));
			sb.append(',');

			sb.append(result.get("Breaking_other_percentage"));
			sb.append(',');

			sb.append(result.get("rewritted_axioms"));
			sb.append(',');

			sb.append(result.get("rewritted_axioms_percentage"));
			sb.append(',');

			sb.append(result.get("refine_action_on_cluster"));
			sb.append(',');

			sb.append(result.get("refine_action_on_merge"));
			sb.append(',');

			sb.append(result.get("cores"));
			sb.append(',');

			sb.append(result.get("num_merge_operation"));
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* ****** time ***** */
			sb.append(result.get("time_reading_input_ontologies"));
			sb.append(',');

			sb.append(result.get("time_reading_alignment"));
			sb.append(',');

			sb.append(result.get("time_collapsed_process"));
			sb.append(',');

			sb.append(result.get("time_determine_k"));
			sb.append(',');

			sb.append(result.get("time_determine_cores"));
			sb.append(',');

			sb.append(result.get("time_clustering_assignment"));
			sb.append(',');

			sb.append(result.get("time_assign_pro_to_cluster"));
			sb.append(',');

			sb.append(result.get("time_whole_clustering"));
			sb.append(',');

			sb.append(result.get("time_cluster_refinement"));
			sb.append(',');

			sb.append(result.get("time_mergedModel_refinement"));
			sb.append(',');

			sb.append(result.get("time_save"));
			sb.append(',');

			sb.append(result.get("time_create_zip"));
			sb.append(',');

			sb.append(result.get("time_total"));
			sb.append(',');

			sb.append(result.get("time_eval"));
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			sb.append(result.get("MU_TotalMerge"));
			sb.append(',');

			sb.append(result.get("MU_Clustering"));
			sb.append(',');

			sb.append(result.get("MU_ClusterRefine_Total"));
			sb.append(',');

			sb.append(result.get("MU_OM_Refine_Total"));
			sb.append(',');

			sb.append(result.get("MU_Cluster_ClassPreservation"));
			sb.append(',');

			sb.append(result.get("MU_OM_ClassPreservation"));
			sb.append(',');

			sb.append(result.get("MU_Cluster_PropertyPreservation"));
			sb.append(',');

			sb.append(result.get("MU_OM_PropertyPreservation"));
			sb.append(',');

			sb.append(result.get("MU_Cluster_InstancePreservation"));
			sb.append(',');

			sb.append(result.get("MU_OM_InstancePreservation"));
			sb.append(',');

			sb.append(result.get("MU_Cluster_StrPreservation"));
			sb.append(',');

			sb.append(result.get("MU_OM_StrPreservation"));
			sb.append(',');

			sb.append(result.get("MU_Cluster_oneness"));
			sb.append(',');

			sb.append(result.get("MU_OM_oneness"));
			sb.append(',');

			sb.append(result.get("MU_Cluster_ClassAcyc"));
			sb.append(',');

			sb.append(result.get("MU_OM_ClassAcyc"));
			sb.append(',');

			sb.append(result.get("MU_Cluster_ClassUncon"));
			sb.append(',');

			sb.append(result.get("MU_OM_ClasssUncon"));
			sb.append(',');

			sb.append('\n');
			writer.write(sb.toString());

			// System.out.println("done!");

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void createOutputHeader(String resultPath) {
		// create csv file
		try (PrintWriter writer = new PrintWriter(new File(resultPath))) {

			StringBuilder sb = new StringBuilder();

			// create header, later do it one time
			// save the name of file
			sb.append("test_version");
			sb.append(',');

			// save ont name
			sb.append("ont_name");
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* *********input ontologies info********* */
			sb.append("input_ontology_size");
			sb.append(',');

			sb.append("corresponding_Class");
			sb.append(',');

			sb.append("corresponding_object_properties");
			sb.append(',');

			sb.append("corresponding_data_properties");
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* ********* compactness ********** */
			sb.append("Tbox_OM");
			sb.append(',');

			sb.append("Abox_OM");
			sb.append(',');

			sb.append("class_OM");
			sb.append(',');

			sb.append("property_OM");
			sb.append(',');

			sb.append("instance_OM");
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			sb.append("class_coverage");
			sb.append(',');

			sb.append("property_coverage");
			sb.append(',');

			sb.append("instance_coverage");
			sb.append(',');

			sb.append("unpreserved_structure");
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* ****** Eval ***** */
			sb.append("oness");
			sb.append(',');

			sb.append("unconnected_class");
			sb.append(',');

			sb.append("cycle_class");
			sb.append(',');

			sb.append("unpreserved_class");
			sb.append(',');

			sb.append("unpreserved_property");
			sb.append(',');

			sb.append("unpreserved_instance");
			sb.append(',');

			sb.append("unpreserved_value");
			sb.append(',');

			sb.append("cycle_property");
			sb.append(',');

			sb.append("inverse_property");
			sb.append(',');

			sb.append("unconnected_property");
			sb.append(',');

			sb.append("entailment");
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* ****** Merge Process ***** */
			sb.append("overlap");
			sb.append(',');

			sb.append("Max-Cardinality");
			sb.append(',');

			sb.append("k");
			sb.append(',');

			sb.append("cluster_class_size");
			sb.append(',');

			sb.append("total_axioms");
			sb.append(',');

			sb.append("manual_deleted_axioms");
			sb.append(',');

			sb.append("Breaking_isA");
			sb.append(',');

			sb.append("Breaking_isA_percentage");
			sb.append(',');

			sb.append("Breaking_other");
			sb.append(',');

			sb.append("Breaking_other_percentage");
			sb.append(',');

			sb.append("rewritted_axioms");
			sb.append(',');

			sb.append("rewritted_axioms_percentage");
			sb.append(',');

			sb.append("refine_action_on_cluster");
			sb.append(',');

			sb.append("refine_action_on_merge");
			sb.append(',');

			sb.append("cores");
			sb.append(',');

			sb.append("num_merge_operation");
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* ****** time ***** */
			sb.append("time_reading_input_ontologies");
			sb.append(',');

			sb.append("time_reading_alignment");
			sb.append(',');

			sb.append("time_collapsed_process");
			sb.append(',');

			sb.append("time_determine_k");
			sb.append(',');

			sb.append("time_determine_cores");
			sb.append(',');

			sb.append("time_clustering_assignment");
			sb.append(',');

			sb.append("time_assign_pro_to_cluster");
			sb.append(',');

			sb.append("time_whole_clustering");
			sb.append(',');

			sb.append("time_cluster_refinement");
			sb.append(',');

			sb.append("time_mergedModel_refinement");
			sb.append(',');

			sb.append("time_save");
			sb.append(',');

			sb.append("time_create_zip");
			sb.append(',');

			sb.append("time_total");
			sb.append(',');

			sb.append("time_eval");
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			sb.append("MU_TotalMerge");
			sb.append(',');

			sb.append("MU_Clustering");
			sb.append(',');

			sb.append("MU_ClusterRefine_Total");
			sb.append(',');

			sb.append("MU_OM_Refine_Total");
			sb.append(',');

			sb.append("MU_Cluster_ClassPreservation");
			sb.append(',');

			sb.append("MU_OM_ClassPreservation");
			sb.append(',');

			sb.append("MU_Cluster_PropertyPreservation");
			sb.append(',');

			sb.append("MU_OM_PropertyPreservation");
			sb.append(',');

			sb.append("MU_Cluster_InstancePreservation");
			sb.append(',');

			sb.append("MU_OM_InstancePreservation");
			sb.append(',');

			sb.append("MU_Cluster_StrPreservation");
			sb.append(',');

			sb.append("MU_OM_StrPreservation");
			sb.append(',');

			sb.append("MU_Cluster_oneness");
			sb.append(',');

			sb.append("MU_OM_oneness");
			sb.append(',');

			sb.append("MU_Cluster_ClassAcyc");
			sb.append(',');

			sb.append("MU_OM_ClassAcyc");
			sb.append(',');

			sb.append("MU_Cluster_ClassUncon");
			sb.append(',');

			sb.append("MU_OM_ClasssUncon");
			sb.append(',');

			sb.append('\n');
			writer.write(sb.toString());

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void inserEmptyLine(String fileName) throws IOException {
		int currentInfo = 72;
		try (FileWriter writer = new FileWriter(fileName, true)) {
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < currentInfo; i++) {
				sb.append(',');
			}

			sb.append('\n');
			writer.write(sb.toString());

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void saveEvalResult(String fileName, String testVersion, HashMap<String, String> result)
			throws IOException {
		try (
				// PrintWriter writer = new PrintWriter(new File(fileName))) {
				FileWriter writer = new FileWriter(fileName, true)) {
			StringBuilder sb = new StringBuilder();

			// save the name of file
			sb.append(testVersion);
			sb.append(',');

			// save ont name
			sb.append(result.get("Merged_ontology_name"));
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* *********input ontologies info********* */
			sb.append(result.get("input_ontology_size"));
			sb.append(',');

			sb.append(result.get("corresponding_Class"));
			sb.append(',');

			sb.append(result.get("corresponding_object_properties"));
			sb.append(',');

			sb.append(result.get("corresponding_data_properties"));
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* ********* compactness ********** */
			sb.append(result.get("Tbox_OM"));
			sb.append(',');

			sb.append(result.get("Abox_OM"));
			sb.append(',');

			sb.append(result.get("class_OM"));
			sb.append(',');

			sb.append(result.get("property_OM"));
			sb.append(',');

			sb.append(result.get("instance_OM"));
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* *** GMR*** */
			// R1: class preservation
			sb.append(result.get("class_preservation"));
			sb.append(',');

			// R2: property preservation
			sb.append(result.get("property_preservation"));
			sb.append(',');

			// R3: instance preservation
			sb.append(result.get("instance_preservation"));
			sb.append(',');

			// R4: correspondence preservation
			sb.append(result.get("correspondence_preservation"));
			sb.append(',');

			// R5: correspondence's property preservation
			sb.append(result.get("correspondenceProperty_preservation"));
			sb.append(',');

			// R6: Value preservation
			sb.append(result.get("value_preservation"));
			sb.append(',');

			// R7: Structure preservation
			sb.append(result.get("structure_preservation"));
			sb.append(',');

			// R8: Class Redundancy
			sb.append(result.get("class_redundancy"));
			sb.append(',');

			// R9: Property Redundancy
			sb.append(result.get("property_redundancy"));
			sb.append(',');

			// R10: Instance Redundancy
			sb.append(result.get("instance_redundancy"));
			sb.append(',');

			// R11: extraneous entity prohibition
			sb.append(result.get("extraneous_prohibition"));
			sb.append(',');

			// R12: Entailment
			sb.append(result.get("entailment"));
			sb.append(',');

			// R13: One type restriction
			sb.append(result.get("oneType_restriction"));
			sb.append(',');

			// R14: Property's value constraint
			sb.append(result.get("value_constraint"));
			sb.append(',');

			// R15: oneness
			sb.append(result.get("oness"));
			sb.append(',');

			// R16: class acyclicity
			sb.append(result.get("cycle_class"));
			sb.append(',');

			// R17; property acyclicity
			sb.append(result.get("cycle_property"));
			sb.append(',');

			// R18: inverse property
			sb.append(result.get("inverse_property"));
			sb.append(',');

			// R19: unconnected classes
			sb.append(result.get("unconnected_class"));
			sb.append(',');

			// R20: unconnected property
			sb.append(result.get("unconnected_property"));
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* *** Annotations *** */
			sb.append(result.get("Ontology_URI"));
			sb.append(',');

			sb.append(result.get("Ontology_Namespace"));
			sb.append(',');

			sb.append(result.get("Ontology_Declaration"));
			sb.append(',');

			sb.append(result.get("Label_Uniqueness"));
			sb.append(',');

			sb.append(result.get("Entity_Type_Declaration"));
			sb.append(',');

			sb.append(result.get("Unify_Naming"));
			sb.append(',');

			/* ****** time ***** */
			sb.append(result.get("time_eval"));
			sb.append(',');

			sb.append('\n');
			writer.write(sb.toString());

			// System.out.println("done!");

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void createEvalOutputHeader(String resultPath) throws IOException {
		try (
				// PrintWriter writer = new PrintWriter(new File(fileName))) {
				FileWriter writer = new FileWriter(resultPath, true)) {
			StringBuilder sb = new StringBuilder();

			// save the name of file
			sb.append("OM_file");
			sb.append(',');

			// save ont name
			sb.append("Merged_ontology_name");
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* *********input ontologies info********* */
			sb.append("input_ontology_size");
			sb.append(',');

			sb.append("corresponding_Class");
			sb.append(',');

			sb.append("corresponding_object_properties");
			sb.append(',');

			sb.append("corresponding_data_properties");
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* ********* compactness ********** */
			sb.append("Tbox_OM");
			sb.append(',');

			sb.append("Abox_OM");
			sb.append(',');

			sb.append("class_OM");
			sb.append(',');

			sb.append("property_OM");
			sb.append(',');

			sb.append("instance_OM");
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* *** GMR*** */
			// R1: class preservation
			sb.append("class_preservation");
			sb.append(',');

			// R2: property preservation
			sb.append("property_preservation");
			sb.append(',');

			// R3: instance preservation
			sb.append("instance_preservation");
			sb.append(',');

			// R4: correspondence preservation
			sb.append("correspondence_preservation");
			sb.append(',');

			// R5: correspondence's property preservation
			sb.append("correspondenceProperty_preservation");
			sb.append(',');

			// R6: Value preservation
			sb.append("value_preservation");
			sb.append(',');

			// R7: Structure preservation
			sb.append("structure_preservation");
			sb.append(',');

			// R8: Class Redundancy
			sb.append("class_redundancy");
			sb.append(',');

			// R9: Property Redundancy
			sb.append("property_redundancy");
			sb.append(',');

			// R10: Instance Redundancy
			sb.append("instance_redundancy");
			sb.append(',');

			// R11: extraneous entity prohibition
			sb.append("extraneous_prohibition");
			sb.append(',');

			// R12: Entailment
			sb.append("entailment");
			sb.append(',');

			// R13: One type restriction
			sb.append("oneType_restriction");
			sb.append(',');

			// R14: Property's value constraint
			sb.append("value_constraint");
			sb.append(',');

			// R15: oneness
			sb.append("oness");
			sb.append(',');

			// R16: class acyclicity
			sb.append("cycle_class");
			sb.append(',');

			// R17; property acyclicity
			sb.append("cycle_property");
			sb.append(',');

			// R18: inverse property
			sb.append("inverse_property");
			sb.append(',');

			// R19: unconnected classes
			sb.append("unconnected_class");
			sb.append(',');

			// R20: unconnected property
			sb.append("unconnected_property");
			sb.append(',');

			/* ***Separate categories*** */
			sb.append(',');

			/* *** Annotations *** */
			sb.append("Ontology_URI");
			sb.append(',');

			sb.append("Ontology_Namespace");
			sb.append(',');

			sb.append("Ontology_Declaration");
			sb.append(',');

			sb.append("Label_Uniqueness");
			sb.append(',');

			sb.append("Entity_Type_Declaration");
			sb.append(',');

			sb.append("Unify_Naming");
			sb.append(',');

			/* ****** time ***** */
			sb.append("time_eval");
			sb.append(',');

			sb.append('\n');
			writer.write(sb.toString());

			// System.out.println("done!");

		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}

}

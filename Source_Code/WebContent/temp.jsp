
<!DOCTYPE html>
<html>
<body>
	<div id="idSecUsabilityProfile">
		<br> <input type="text" value="${SecUsabilityProfile}"
			style="display: none;"> <span id="UsabilityProfile"
			onclick="ShowSection7()" style="cursor: pointer;"> <hh>Usability
			Profile</hh>
		</span><img id="img7" src="/layout/images/down.png" alt="show table"
			onclick="ShowSection7()" style="cursor: pointer;">
		<div id="section7">
			<div class="showcell">
				<span>Correctness of ontology URI</span> <span style="float: right;">8
					cases <img src="/layout/images/cross.jpg">
				</span>
			</div>
			<div class="hidecell">
				<p>
					It checks the correctness of ontology URI [8].<br>
					<span style="color: red;">Unconnected classes are:<br>
						-> http://conference#Important_dates<br> ->
						http://conference#Organization<br> -> http://cmt#Preference<br>
						-> http://cmt#SubjectArea<br> -> http://conference#Topic<br>
						-> http://conference#Conference_proceedings<br> ->
						http://conference#Publisher<br> -> http://cmt#Bid
					</span><br> <br>
				<p>
					<b><u><input type="checkbox" name="repairs"
							value="UnconnClCheck">Repair it!</u></b>
				</p>
				</p>
			</div>
			<div class="showcell">
				<span>Correctness of ontology namespace</span> <span
					style="float: right;"><img src="/layout/images/tick.jpg"></span>
			</div>
			<div class="hidecell">
				<p>
					It checks the correctness of ontology namespace [8].<br>
					<span style="color: green;">There is no unconnected
						property.</span>
				</p>
			</div>
			<div class="showcell">
				<span>Correctness of ontology declaration </span> <span
					style="float: right;">null</span>
			</div>
			<div class="hidecell">
				null <br>
			</div>
			<div class="showcell">
				<span>Correctness of ontology licence</span> <span
					style="float: right;">null</span>
			</div>
			<div class="hidecell">
				null <br>
			</div>
			<div class="showcell">
				<span>Label uniqueness</span> <span style="float: right;">null</span>
			</div>
			<div class="hidecell">
				null <br>
			</div>
			<div class="showcell">
				<span>Unify naming</span> <span style="float: right;">null</span>
			</div>
			<div class="hidecell">
				<p>
				<ul>
					Using same naming criteria in the ontology calculates the ontology
					elements are named following the same convention within the whole
					ontology [8,5]. It checks:
					<li>The same capitalization for concept names (upper case and
						lower case)</li>
					<li>Singular or plural of entities</li>
					<li>Prefix and suffix conventions: do the ontology use a
						different prefix for classes and properties? e.g. author class and
						has_author property.</li>
				</ul>
				null<br>
				</p>
			</div>
			<div class="showcell">
				<span>Entity Type declaration</span> <span style="float: right;">null</span>
			</div>
			<div class="hidecell">
				<p>
					This means checks existence of a declaration of the owl:Ontology
					tag where the ontology metadata should be provided [8].<br>null
				</p>
			</div>
		</div>
	</div>
</body>
</html>
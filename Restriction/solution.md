| Case  | Appearance | Examples          | Conflict type |
| ------------- | ------------- | -------------| ------------- |
| A     | $`value(p_{maxCard}^i)  \neq value(p_{maxCard}^j) `$  | $`value(p_{maxCard}^i)=(0-4) \quad value(p_{maxCard}^j)=(0-5)`$   |  primitive   | 
| N     | $`value(p_{maxCard}^i)  = value(p_{maxCard}^j) `$     | $`value(p_{maxCard}^i)=(0-4) \quad value(p_{maxCard}^j)=(0-4)`$   |  no conflict |
| B     | $`value(p_{minCard}^i)  \neq value(p_{minCard}^j) `$  | $`value(p_{minCard}^i)=(4-n) \quad value(p_{minCard}^j)=(5-n)`$
| N     | $`value(p_{minCard}^i)  = value(p_{minCard}^j) `$     | $`value(p_{minCard}^i)=(4-n) \quad value(p_{minCard}^j)=(4-n)`$
| C     | $`value(p_{exCard}^i)  \neq value(p_{exCard}^j) `$    | $`value(p_{exCard}^i)=4  \quad value(p_{exCard}^j)=5 `$
| N     | $`value(p_{exCard}^i)  = value(p_{exCard}^j) `$       | $`value(p_{exCard}^i)=4 \quad value(p_{exCard}^j)=4 `$
| D     | $`value(p_{maxCard}^i)  = value(p_{exCard}^j) `$      | $`value(p_{maxCard}^i)=(0-3)  \quad value(p_{exCard}^j)=3 `$
| E     | $`value(p_{minCard}^i)  = value(p_{exCard}^j) `$      | $`value(p_{minCard}^i)=(3-n)  \quad value(p_{exCard}^j)=3 `$
| F     | $`value(p_{minCard}^i)  = value(p_{maxCard}^j) `$     | $`value(p_{minCard}^i)=(3-n)  \quad value(p_{maxCard}^j)=(0-3) `$
| G     | $`value(p_{maxCard}^i)  \neq value(p_{exCard}^j) `$   | $`value(p_{maxCard}^i)=(0-3)  \quad value(p_{exCard}^j)=2 `$ **or** $`value(p_{maxCard}^i)=(0-3)  \quad value(p_{exCard}^j)=4`$|
| H     | $`value(p_{minCard}^i)  \neq value(p_{exCard}^j) `$   | $`value(p_{minCard}^i)=(3-n)  \quad value(p_{exCard}^j)=2 `$ **or** $`value(p_{minCard}^i)=(3-n)  \quad value(p_{exCard}^j)=4`$ |
| I     | $`value(p_{minCard}^i)  \neq value(p_{maxCard}^j) `$  | $`value(p_{minCard}^i)=(3-n)  \quad value(p_{maxCard}^j)=(0-4) `$  **or** $`value(p_{minCard}^i)=(3-n)  \quad value(p_{maxCard}^j)=(0-2) `$|
| J     | $`value(p_{someValuesFrom}^i) \neq value(p_{someValuesFrom}^j)`$ | **On object property:** $`value(p_{someValuesFrom}^i)=Reviewer \quad value(p_{someValuesFrom}^j)=Person`$ **or** $`value(p_{someValuesFrom}^i)=Reviewer \quad value(p_{someValuesFrom}^j)=Organizer`$ **On data property:** $`value(p_{someValuesFrom}^i)=Integer value(p_{someValuesFrom}^j)=Float`$ **or** $`value(p_{someValuesFrom}^i)=Integer value(p_{someValuesFrom}^j)=String`$ **or** $`value(p_{someValuesFrom}^i)=Integer value(p_{someValuesFrom}^j)=Boolean`$ 
                                                                            
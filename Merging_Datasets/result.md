# Ontology Merging Test's Info:

This tables shows the statistics of running six version (V<sub>1</sub>-V<sub>6</sub>) of our datasets (d<sub>1</sub>-d<sub>12</sub>). For each test of the created merged ontology (O<sub>M</sub>), we show: 
* test version (id); 
* number of classes of the merged ontology (#C); 
* number of properties of the merged ontology (#P); 
* number of instances of the merged ontology (#I); 
* class coverage (cov<sub>C</sub>); 
* property coverage (cov<sub>P</sub>); 
* instance coverage (cov<sub>I</sub>); 
* number of the unpreserved structure in the merged ontology (#un_str); 
* number of properties with multiple domains and ranges, i.e., oneness (#on); 
* number of unconnected classes (#un<sub>C</sub>); 
* number of cycles on the class hierarchy (#cyc); 
* number of required refinements on the blocks as local refinements (#R<sub>L</sub>); 
* number of required refinements on the final merged ontology as global refinements  (#R<sub>G</sub>).


|         id        | #C | #P | #I | cov<sub>C</sub> | cov<sub>P</sub> | cov<sub>I</sub> | #un_str | #on | #un<sub>C</sub> | #cyc | #R<sub>L</sub> | #R<sub>G</sub> |
|:---------------------------:|:--------:|:-----------:|:-----------:|:--------------:|:-----------------:|:-----------------:|:---------------------:|:-----:|:-----------------:|:-----------:|:------------------------:|:----------------------:|
|  d<sub>1</sub>V<sub>1</sub> |    77    |     120     |      0      |        1       |         1         |         -         |           0           |   0   |         0         |      0      |            14            |            8           |
|  d<sub>1</sub>V<sub>2</sub> |    77    |     120     |      0      |        1       |         1         |         -         |           0           |   0   |         0         |      0      |             -            |           12           |
|  d<sub>1</sub>V<sub>3</sub> |    76    |     120     |      0      |      0.97      |         1         |         -         |           0           |   4   |         6         |      1      |             -            |            0           |
|  d<sub>1</sub>V<sub>4</sub> |    82    |     121     |      0      |        1       |         1         |         -         |           0           |   0   |         0         |      0      |             8            |           12           |
|  d<sub>1</sub>V<sub>5</sub> |    82    |     121     |      0      |        1       |         1         |         -         |           0           |   0   |         0         |      0      |             -            |           13           |
|  d<sub>1</sub>V<sub>6</sub> |    81    |     121     |      0      |      0.98      |         1         |         -         |           0           |   4   |         8         |      0      |             -            |            0           |
|  d<sub>2</sub>V<sub>1</sub> |    112   |      61     |      0      |        1       |         1         |         -         |           0           |   0   |         0         |      0      |            17            |            1           |
|  d<sub>2</sub>V<sub>2</sub> |    112   |      61     |      0      |        1       |         1         |         -         |           0           |   0   |         0         |      0      |             -            |            1           |
|  d<sub>2</sub>V<sub>3</sub> |    112   |      61     |      0      |        1       |         1         |         -         |           0           |   0   |         1         |      0      |             -            |            0           |
|  d<sub>2</sub>V<sub>4</sub> |    115   |      60     |      0      |        1       |         1         |         -         |           0           |   0   |         0         |      0      |            18            |            3           |
|  d<sub>2</sub>V<sub>5</sub> |    115   |      60     |      0      |        1       |         1         |         -         |           0           |   0   |         0         |      0      |             -            |            4           |
|  d<sub>2</sub>V<sub>6</sub> |    115   |      60     |      0      |        1       |         1         |         -         |           0           |   2   |         2         |      0      |             -            |            0           |
|  d<sub>3</sub>V<sub>1</sub> |    98    |     147     |      0      |      0.98      |         1         |         -         |           0           |   0   |         0         |      0      |            21            |           11           |
|  d<sub>3</sub>V<sub>2</sub> |    98    |     147     |      0      |      0.98      |         1         |         -         |           0           |   0   |         0         |      0      |             -            |           16           |
|  d<sub>3</sub>V<sub>3</sub> |    97    |     147     |      0      |      0.97      |         1         |         -         |           0           |   6   |         7         |      1      |             -            |            0           |
|  d<sub>3</sub>V<sub>4</sub> |    109   |     154     |      0      |      0.98      |         1         |         -         |           0           |   0   |         0         |      0      |            14            |           15           |
|  d<sub>3</sub>V<sub>5</sub> |    109   |     154     |      0      |      0.98      |         1         |         -         |           0           |   0   |         0         |      0      |             -            |           17           |
|  d<sub>3</sub>V<sub>6</sub> |    108   |     154     |      0      |      0.97      |         1         |         -         |           0           |   6   |         9         |      0      |             -            |            0           |
|  d<sub>4</sub>V<sub>1</sub> |    202   |     167     |     114     |      0.99      |         1         |         1         |           0           |   0   |         0         |      0      |            30            |           12           |
|  d<sub>4</sub>V<sub>2</sub> |    201   |     167     |     114     |      0.99      |         1         |         1         |           0           |   0   |         0         |      0      |             -            |           18           |
|  d<sub>4</sub>V<sub>3</sub> |    199   |     167     |     114     |      0.98      |         1         |         1         |           0           |   6   |         8         |      1      |             -            |            0           |
|  d<sub>4</sub>V<sub>4</sub> |    226   |     165     |     114     |      0.99      |        0.99       |         1         |           0           |   0   |         0         |      0      |            26            |           18           |
|  d<sub>4</sub>V<sub>5</sub> |    226   |     165     |     114     |      0.99      |        0.99       |         1         |           0           |   0   |         0         |      0      |             -            |           21           |
|  d<sub>4</sub>V<sub>6</sub> |    224   |     165     |     114     |      0.98      |        0.99       |         1         |           0           |   9   |         9         |      0      |             -            |            0           |
|  d<sub>5</sub>V<sub>1</sub> |    248   |     156     |      4      |      0.99      |         1         |         1         |           0           |   0   |         0         |      0      |            61            |            7           |
|  d<sub>5</sub>V<sub>2</sub> |    247   |     156     |      4      |      0.99      |         1         |         1         |           0           |   0   |         0         |      0      |             -            |            9           |
|  d<sub>5</sub>V<sub>3</sub> |    246   |     156     |      4      |      0.98      |         1         |         1         |           0           |   3   |         5         |      0      |             -            |            0           |
|  d<sub>5</sub>V<sub>4</sub> |    253   |     155     |      4      |      0.99      |        1.01       |         1         |           0           |   0   |         0         |      0      |            64            |           13           |
|  d<sub>5</sub>V<sub>5</sub> |    253   |     154     |      4      |      0.99      |         1         |         1         |           0           |   0   |         0         |      0      |             -            |           17           |
|  d<sub>5</sub>V<sub>6</sub> |    251   |     153     |      4      |      0.98      |         1         |         1         |           1           |   9   |         5         |      0      |             -            |            0           |
|  d<sub>6</sub>V<sub>1</sub> |    338   |     274     |     118     |      0.99      |        1.05       |         1         |           0           |   0   |         0         |      0      |            71            |           21           |
|  d<sub>6</sub>V<sub>2</sub> |    336   |     274     |     118     |      0.98      |        1.05       |         1         |           0           |   0   |         0         |      0      |             -            |           29           |
|  d<sub>6</sub>V<sub>3</sub> |    334   |     274     |     118     |      0.98      |        1.05       |         1         |           0           |   14  |         8         |      4      |             -            |            0           |
|  d<sub>6</sub>V<sub>4</sub> |    390   |     283     |     118     |      0.99      |        1.03       |         1         |           0           |   0   |         0         |      0      |            96            |           34           |
|  d<sub>6</sub>V<sub>5</sub> |    390   |     281     |     118     |      0.99      |        1.02       |         1         |           0           |   0   |         0         |      0      |             -            |           41           |
|  d<sub>6</sub>V<sub>6</sub> |    386   |     280     |     118     |      0.98      |        1.01       |         1         |           1           |   24  |         11        |      0      |             -            |            0           |
|  d<sub>7</sub>V<sub>1</sub> |   4526   |      4      |      0      |        1       |         1         |         -         |           0           |   0   |         0         |      0      |             -            |            9           |
|  d<sub>7</sub>V<sub>2</sub> |   4526   |      4      |      0      |        1       |         1         |         -         |           0           |   0   |         0         |      0      |             -            |            9           |
|  d<sub>7</sub>V<sub>3</sub> |   4526   |      4      |      0      |        1       |         1         |         -         |           0           |   0   |         7         |      2      |             -            |            0           |
|  d<sub>7</sub>V<sub>4</sub> |   4873   |      2      |      0      |        1       |         1         |         -         |           0           |   0   |         0         |      0      |             -            |            7           |
|  d<sub>7</sub>V<sub>5</sub> |   4873   |      2      |      0      |        1       |         1         |         -         |           0           |   0   |         0         |      0      |             -            |            7           |
|  d<sub>7</sub>V<sub>6</sub> |   4873   |      2      |      0      |        1       |         1         |         -         |           0           |   0   |         7         |      0      |             -            |            0           |
|  d<sub>8</sub>V<sub>1</sub> |   7290   |      87     |      0      |        1       |         1         |         -         |           0           |   0   |         0         |      0      |             -            |          1949          |
|  d<sub>8</sub>V<sub>2</sub> |   7290   |      87     |      0      |        1       |         1         |         -         |           0           |   0   |         0         |      0      |             -            |          1949          |
|  d<sub>8</sub>V<sub>3</sub> |   7285   |      87     |      0      |        1       |         1         |         -         |           4           |   0   |        1916       |      29     |             -            |            0           |
|  d<sub>8</sub>V<sub>4</sub> |   7721   |      87     |      0      |        1       |         1         |         -         |           0           |   0   |         0         |      0      |             -            |          1925          |
|  d<sub>8</sub>V<sub>5</sub> |   7721   |      87     |      0      |        1       |         1         |         -         |           0           |   0   |         0         |      0      |             -            |          1925          |
|  d<sub>8</sub>V<sub>6</sub> |   7712   |      87     |      0      |        1       |         1         |         -         |           9           |   0   |        1916       |      0      |             -            |            0           |
|  d<sub>9</sub>V<sub>1</sub> |   1145   |     101     |      31     |        1       |        0.72       |         1         |           0           |   0   |         0         |      0      |             8            |           17           |
|  d<sub>9</sub>V<sub>2</sub> |   1145   |     101     |      31     |        1       |        0.72       |         1         |           0           |   0   |         0         |      0      |             -            |           17           |
|  d<sub>9</sub>V<sub>3</sub> |   1144   |     101     |      31     |        1       |        0.72       |         1         |           0           |   14  |         2         |      0      |             -            |            0           |
|  d<sub>9</sub>V<sub>4</sub> |   1146   |     101     |      31     |        1       |        0.72       |         1         |           0           |   0   |         0         |      0      |             0            |           17           |
|  d<sub>9</sub>V<sub>5</sub> |   1146   |     101     |      31     |        1       |        0.72       |         1         |           0           |   0   |         0         |      0      |             -            |           17           |
|  d<sub>9</sub>V<sub>6</sub> |   1145   |     101     |      31     |        1       |        0.72       |         1         |           0           |   14  |         2         |      0      |             -            |            0           |
| d<sub>10</sub>V<sub>1</sub> |   5042   |     2197    |     843     |      0.99      |        0.9        |        0.93       |           0           |   0   |         0         |      0      |            41            |           106          |
| d<sub>10</sub>V<sub>2</sub> |   5042   |     2197    |     843     |      0.99      |        0.9        |        0.93       |           0           |   0   |         0         |      0      |             -            |           116          |
| d<sub>10</sub>V<sub>3</sub> |   5018   |     2197    |     843     |      0.99      |        0.9        |        0.93       |           71          |   21  |         5         |      2      |             -            |            0           |
| d<sub>10</sub>V<sub>4</sub> |   4957   |     2394    |     882     |        1       |        1.01       |        0.98       |           0           |   0   |         0         |      0      |             -            |           143          |
| d<sub>10</sub>V<sub>5</sub> |   4957   |     2394    |     882     |        1       |        1.01       |        0.98       |           0           |   0   |         0         |      0      |             -            |           143          |
| d<sub>10</sub>V<sub>6</sub> |   4928   |     2378    |     882     |      0.99      |         1         |        0.98       |           87          |   22  |         7         |      3      |             -            |            0           |
| d<sub>11</sub>V<sub>1</sub> |   5564   |     2245    |     870     |      0.99      |        0.89       |        0.94       |           0           |   0   |         0         |      0      |            40            |           110          |
| d<sub>11</sub>V<sub>2</sub> |   5564   |     2245    |     870     |      0.99      |        0.89       |        0.94       |           0           |   0   |         0         |      0      |             -            |           120          |
| d<sub>11</sub>V<sub>3</sub> |   5539   |     2245    |     870     |      0.99      |        0.89       |        0.94       |           75          |   21  |         5         |      2      |             -            |            0           |
| d<sub>11</sub>V<sub>4</sub> |   5490   |     2469    |     909     |        1       |        1.01       |        0.98       |           0           |   0   |         0         |      0      |             -            |           143          |
| d<sub>11</sub>V<sub>5</sub> |   5490   |     2469    |     909     |        1       |        1.01       |        0.98       |           0           |   0   |         0         |      0      |             -            |           143          |
| d<sub>11</sub>V<sub>6</sub> |   5461   |     2453    |     909     |      0.99      |         1         |        0.98       |           87          |   22  |         7         |      3      |             -            |            0           |
| d<sub>12</sub>V<sub>1</sub> |   15822  |     3818    |     1262    |      0.98      |        0.95       |        0.96       |           0           |   0   |         0         |      0      |             -            |           524          |
| d<sub>12</sub>V<sub>2</sub> |   15822  |     3818    |     1262    |      0.98      |        0.95       |        0.96       |           0           |   0   |         0         |      0      |             -            |           524          |
| d<sub>12</sub>V<sub>3</sub> |   15729  |     3818    |     1262    |      0.98      |        0.95       |        0.96       |          176          |   44  |        252        |      4      |             -            |            0           |
| d<sub>12</sub>V<sub>4</sub> |   15080  |     3589    |     1262    |      0.99      |        1.03       |        0.96       |           0           |   0   |         0         |      0      |             -            |           977          |
| d<sub>12</sub>V<sub>5</sub> |   15080  |     3589    |     1262    |      0.99      |        1.03       |        0.96       |           0           |   0   |         0         |      0      |             -            |           977          |
| d<sub>12</sub>V<sub>6</sub> |   14944  |     3540    |     1262    |      0.98      |        1.01       |        0.96       |          579          |   85  |        229        |      13     |             -            |            0           |


<table >
  <tr>
    <th class="tg-0lax">id</th>
    <th class="tg-0lax">|C|</th>
    <th class="tg-0lax">|P|</th>
    <th class="tg-0lax">|I|</th>
    <th class="tg-0lax">cov<sub>C</sub></th>
    <th class="tg-0lax">cov<sub>P</sub></th>
    <th class="tg-0lax">cov<sub>I</sub></th>
    <th class="tg-0lax">|un_str|</th>
    <th class="tg-0lax">|on|</th>
    <th class="tg-0lax">|un<sub>C</sub>|</th>
    <th class="tg-0lax">|cyc|</th>
    <th class="tg-0lax">R<sub>L</sub></th>
    <th class="tg-0lax">R<sub>G</sub></th>
  </tr>
  <tr>
    <td class="tg-0lax">d1V1</td>
    <td class="tg-0lax">77</td>
    <td class="tg-0lax">120</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">6</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">17</td>
    <td class="tg-0lax">1</td>
  </tr>
  <tr>
    <td class="tg-0lax">d1V2</td>
    <td class="tg-0lax">77</td>
    <td class="tg-0lax">120</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">6</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">6</td>
  </tr>
  <tr>
    <td class="tg-0lax">d1V3</td>
    <td class="tg-0lax">76</td>
    <td class="tg-0lax">120</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0.97</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">4</td>
    <td class="tg-0lax">6</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d1V4</td>
    <td class="tg-0lax">78</td>
    <td class="tg-0lax">117</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">17</td>
    <td class="tg-0lax">7</td>
  </tr>
  <tr>
    <td class="tg-0lax">d1V5</td>
    <td class="tg-0lax">78</td>
    <td class="tg-0lax">117</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">11</td>
  </tr>
  <tr>
    <td class="tg-0lax">d1V6</td>
    <td class="tg-0lax">76</td>
    <td class="tg-0lax">117</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0.97</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">8</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d2V1</td>
    <td class="tg-0lax">112</td>
    <td class="tg-0lax">61</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">17</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d2V2</td>
    <td class="tg-0lax">112</td>
    <td class="tg-0lax">61</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d2V3</td>
    <td class="tg-0lax">112</td>
    <td class="tg-0lax">61</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d2V4</td>
    <td class="tg-0lax">115</td>
    <td class="tg-0lax">60</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">18</td>
    <td class="tg-0lax">1</td>
  </tr>
  <tr>
    <td class="tg-0lax">d2V5</td>
    <td class="tg-0lax">115</td>
    <td class="tg-0lax">60</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">2</td>
  </tr>
  <tr>
    <td class="tg-0lax">d2V6</td>
    <td class="tg-0lax">115</td>
    <td class="tg-0lax">60</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d3V1</td>
    <td class="tg-0lax">99</td>
    <td class="tg-0lax">147</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">22</td>
    <td class="tg-0lax">5</td>
  </tr>
  <tr>
    <td class="tg-0lax">d3V2</td>
    <td class="tg-0lax">99</td>
    <td class="tg-0lax">147</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">10</td>
  </tr>
  <tr>
    <td class="tg-0lax">d3V3</td>
    <td class="tg-0lax">97</td>
    <td class="tg-0lax">147</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0.97</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">6</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d3V4</td>
    <td class="tg-0lax">101</td>
    <td class="tg-0lax">147</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">8</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">22</td>
    <td class="tg-0lax">9</td>
  </tr>
  <tr>
    <td class="tg-0lax">d3V5</td>
    <td class="tg-0lax">101</td>
    <td class="tg-0lax">147</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">8</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">14</td>
  </tr>
  <tr>
    <td class="tg-0lax">d3V6</td>
    <td class="tg-0lax">99</td>
    <td class="tg-0lax">147</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0.97</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">11</td>
    <td class="tg-0lax">8</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d4V1</td>
    <td class="tg-0lax">202</td>
    <td class="tg-0lax">169</td>
    <td class="tg-0lax">114</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">1.01</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">8</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">24</td>
    <td class="tg-0lax">6</td>
  </tr>
  <tr>
    <td class="tg-0lax">d4V2</td>
    <td class="tg-0lax">202</td>
    <td class="tg-0lax">169</td>
    <td class="tg-0lax">114</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">1.01</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">8</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">11</td>
  </tr>
  <tr>
    <td class="tg-0lax">d4V3</td>
    <td class="tg-0lax">199</td>
    <td class="tg-0lax">167</td>
    <td class="tg-0lax">114</td>
    <td class="tg-0lax">0.98</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">6</td>
    <td class="tg-0lax">8</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d4V4</td>
    <td class="tg-0lax">209</td>
    <td class="tg-0lax">155</td>
    <td class="tg-0lax">114</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">8</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">27</td>
    <td class="tg-0lax">15</td>
  </tr>
  <tr>
    <td class="tg-0lax">d4V5</td>
    <td class="tg-0lax">209</td>
    <td class="tg-0lax">155</td>
    <td class="tg-0lax">114</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">8</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">23</td>
  </tr>
  <tr>
    <td class="tg-0lax">d4V6</td>
    <td class="tg-0lax">207</td>
    <td class="tg-0lax">153</td>
    <td class="tg-0lax">114</td>
    <td class="tg-0lax">0.98</td>
    <td class="tg-0lax">0.98</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">18</td>
    <td class="tg-0lax">8</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d5V1</td>
    <td class="tg-0lax">249</td>
    <td class="tg-0lax">157</td>
    <td class="tg-0lax">4</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">5</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">61</td>
    <td class="tg-0lax">5</td>
  </tr>
  <tr>
    <td class="tg-0lax">d5V2</td>
    <td class="tg-0lax">248</td>
    <td class="tg-0lax">157</td>
    <td class="tg-0lax">4</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">5</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">7</td>
  </tr>
  <tr>
    <td class="tg-0lax">d5V3</td>
    <td class="tg-0lax">246</td>
    <td class="tg-0lax">156</td>
    <td class="tg-0lax">4</td>
    <td class="tg-0lax">0.98</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">3</td>
    <td class="tg-0lax">3</td>
    <td class="tg-0lax">5</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d5V4</td>
    <td class="tg-0lax">247</td>
    <td class="tg-0lax">151</td>
    <td class="tg-0lax">4</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">5</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">66</td>
    <td class="tg-0lax">15</td>
  </tr>
  <tr>
    <td class="tg-0lax">d5V5</td>
    <td class="tg-0lax">247</td>
    <td class="tg-0lax">150</td>
    <td class="tg-0lax">4</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">5</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">21</td>
  </tr>
  <tr>
    <td class="tg-0lax">d5V6</td>
    <td class="tg-0lax">244</td>
    <td class="tg-0lax">148</td>
    <td class="tg-0lax">4</td>
    <td class="tg-0lax">0.98</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">4</td>
    <td class="tg-0lax">14</td>
    <td class="tg-0lax">5</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d6V1</td>
    <td class="tg-0lax">309</td>
    <td class="tg-0lax">175</td>
    <td class="tg-0lax">118</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">5</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">58</td>
    <td class="tg-0lax">7</td>
  </tr>
  <tr>
    <td class="tg-0lax">d6V2</td>
    <td class="tg-0lax">307</td>
    <td class="tg-0lax">175</td>
    <td class="tg-0lax">118</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">5</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">8</td>
  </tr>
  <tr>
    <td class="tg-0lax">d6V3</td>
    <td class="tg-0lax">305</td>
    <td class="tg-0lax">174</td>
    <td class="tg-0lax">118</td>
    <td class="tg-0lax">0.98</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">5</td>
    <td class="tg-0lax">3</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d6V4</td>
    <td class="tg-0lax">324</td>
    <td class="tg-0lax">167</td>
    <td class="tg-0lax">118</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">6</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">79</td>
    <td class="tg-0lax">20</td>
  </tr>
  <tr>
    <td class="tg-0lax">d6V5</td>
    <td class="tg-0lax">323</td>
    <td class="tg-0lax">166</td>
    <td class="tg-0lax">118</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">6</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">29</td>
  </tr>
  <tr>
    <td class="tg-0lax">d6V6</td>
    <td class="tg-0lax">320</td>
    <td class="tg-0lax">163</td>
    <td class="tg-0lax">118</td>
    <td class="tg-0lax">0.98</td>
    <td class="tg-0lax">0.97</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">3</td>
    <td class="tg-0lax">21</td>
    <td class="tg-0lax">6</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d7V1</td>
    <td class="tg-0lax">340</td>
    <td class="tg-0lax">276</td>
    <td class="tg-0lax">118</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">1.05</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">8</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">71</td>
    <td class="tg-0lax">16</td>
  </tr>
  <tr>
    <td class="tg-0lax">d7V2</td>
    <td class="tg-0lax">338</td>
    <td class="tg-0lax">276</td>
    <td class="tg-0lax">118</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">1.05</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">8</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">24</td>
  </tr>
  <tr>
    <td class="tg-0lax">d7V3</td>
    <td class="tg-0lax">334</td>
    <td class="tg-0lax">274</td>
    <td class="tg-0lax">118</td>
    <td class="tg-0lax">0.98</td>
    <td class="tg-0lax">1.05</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">4</td>
    <td class="tg-0lax">14</td>
    <td class="tg-0lax">8</td>
    <td class="tg-0lax">4</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d7V4</td>
    <td class="tg-0lax">367</td>
    <td class="tg-0lax">261</td>
    <td class="tg-0lax">118</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1.03</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">9</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">87</td>
    <td class="tg-0lax">32</td>
  </tr>
  <tr>
    <td class="tg-0lax">d7V5</td>
    <td class="tg-0lax">364</td>
    <td class="tg-0lax">260</td>
    <td class="tg-0lax">118</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">1.02</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">9</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">44</td>
  </tr>
  <tr>
    <td class="tg-0lax">d7V6</td>
    <td class="tg-0lax">359</td>
    <td class="tg-0lax">257</td>
    <td class="tg-0lax">118</td>
    <td class="tg-0lax">0.98</td>
    <td class="tg-0lax">1.01</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">5</td>
    <td class="tg-0lax">33</td>
    <td class="tg-0lax">9</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d8V1</td>
    <td class="tg-0lax">4526</td>
    <td class="tg-0lax">4</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">2</td>
  </tr>
  <tr>
    <td class="tg-0lax">d8V2</td>
    <td class="tg-0lax">4526</td>
    <td class="tg-0lax">4</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">2</td>
  </tr>
  <tr>
    <td class="tg-0lax">d8V3</td>
    <td class="tg-0lax">4526</td>
    <td class="tg-0lax">4</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d8V4</td>
    <td class="tg-0lax">4873</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d8V5</td>
    <td class="tg-0lax">4873</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d8V6</td>
    <td class="tg-0lax">4873</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d9V1</td>
    <td class="tg-0lax">7289</td>
    <td class="tg-0lax">87</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1916</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">33</td>
  </tr>
  <tr>
    <td class="tg-0lax">d9V2</td>
    <td class="tg-0lax">7289</td>
    <td class="tg-0lax">87</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1916</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">33</td>
  </tr>
  <tr>
    <td class="tg-0lax">d9V3</td>
    <td class="tg-0lax">7285</td>
    <td class="tg-0lax">87</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">4</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1916</td>
    <td class="tg-0lax">29</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d9V4</td>
    <td class="tg-0lax">7686</td>
    <td class="tg-0lax">87</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1915</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">11</td>
  </tr>
  <tr>
    <td class="tg-0lax">d9V5</td>
    <td class="tg-0lax">7686</td>
    <td class="tg-0lax">87</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1915</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">11</td>
  </tr>
  <tr>
    <td class="tg-0lax">d9V6</td>
    <td class="tg-0lax">7677</td>
    <td class="tg-0lax">87</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">10</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1915</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d10V1</td>
    <td class="tg-0lax">1146</td>
    <td class="tg-0lax">101</td>
    <td class="tg-0lax">31</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0.72</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">16</td>
  </tr>
  <tr>
    <td class="tg-0lax">d10V2</td>
    <td class="tg-0lax">1146</td>
    <td class="tg-0lax">101</td>
    <td class="tg-0lax">31</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0.72</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">16</td>
  </tr>
  <tr>
    <td class="tg-0lax">d10V3</td>
    <td class="tg-0lax">1144</td>
    <td class="tg-0lax">101</td>
    <td class="tg-0lax">31</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0.72</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">14</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d10V4</td>
    <td class="tg-0lax">1149</td>
    <td class="tg-0lax">101</td>
    <td class="tg-0lax">31</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0.72</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">15</td>
  </tr>
  <tr>
    <td class="tg-0lax">d10V5</td>
    <td class="tg-0lax">1149</td>
    <td class="tg-0lax">101</td>
    <td class="tg-0lax">31</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0.72</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">15</td>
  </tr>
  <tr>
    <td class="tg-0lax">d10V6</td>
    <td class="tg-0lax">1148</td>
    <td class="tg-0lax">101</td>
    <td class="tg-0lax">31</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0.72</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">14</td>
    <td class="tg-0lax">2</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d11V1</td>
    <td class="tg-0lax">5031</td>
    <td class="tg-0lax">2197</td>
    <td class="tg-0lax">843</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">0.9</td>
    <td class="tg-0lax">0.93</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">3</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">41</td>
    <td class="tg-0lax">109</td>
  </tr>
  <tr>
    <td class="tg-0lax">d11V2</td>
    <td class="tg-0lax">5031</td>
    <td class="tg-0lax">2197</td>
    <td class="tg-0lax">843</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">0.9</td>
    <td class="tg-0lax">0.93</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">3</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">122</td>
  </tr>
  <tr>
    <td class="tg-0lax">d11V3</td>
    <td class="tg-0lax">5003</td>
    <td class="tg-0lax">2197</td>
    <td class="tg-0lax">843</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">0.9</td>
    <td class="tg-0lax">0.93</td>
    <td class="tg-0lax">81</td>
    <td class="tg-0lax">21</td>
    <td class="tg-0lax">4</td>
    <td class="tg-0lax">3</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d11V4</td>
    <td class="tg-0lax">4756</td>
    <td class="tg-0lax">2394</td>
    <td class="tg-0lax">882</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1.03</td>
    <td class="tg-0lax">0.98</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">169</td>
  </tr>
  <tr>
    <td class="tg-0lax">d11V5</td>
    <td class="tg-0lax">4756</td>
    <td class="tg-0lax">2394</td>
    <td class="tg-0lax">882</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1.03</td>
    <td class="tg-0lax">0.98</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">169</td>
  </tr>
  <tr>
    <td class="tg-0lax">d11V6</td>
    <td class="tg-0lax">4717</td>
    <td class="tg-0lax">2357</td>
    <td class="tg-0lax">882</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">1.01</td>
    <td class="tg-0lax">0.98</td>
    <td class="tg-0lax">118</td>
    <td class="tg-0lax">24</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">3</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d12V1</td>
    <td class="tg-0lax">5553</td>
    <td class="tg-0lax">2245</td>
    <td class="tg-0lax">870</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">0.89</td>
    <td class="tg-0lax">0.94</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">3</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">41</td>
    <td class="tg-0lax">113</td>
  </tr>
  <tr>
    <td class="tg-0lax">d12V2</td>
    <td class="tg-0lax">5553</td>
    <td class="tg-0lax">2245</td>
    <td class="tg-0lax">870</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">0.89</td>
    <td class="tg-0lax">0.94</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">3</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">126</td>
  </tr>
  <tr>
    <td class="tg-0lax">d12V3</td>
    <td class="tg-0lax">5524</td>
    <td class="tg-0lax">2245</td>
    <td class="tg-0lax">870</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">0.89</td>
    <td class="tg-0lax">0.94</td>
    <td class="tg-0lax">85</td>
    <td class="tg-0lax">21</td>
    <td class="tg-0lax">4</td>
    <td class="tg-0lax">3</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
  <tr>
    <td class="tg-0lax">d12V4</td>
    <td class="tg-0lax">5289</td>
    <td class="tg-0lax">2469</td>
    <td class="tg-0lax">909</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1.03</td>
    <td class="tg-0lax">0.98</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">169</td>
  </tr>
  <tr>
    <td class="tg-0lax">d12V5</td>
    <td class="tg-0lax">5289</td>
    <td class="tg-0lax">2469</td>
    <td class="tg-0lax">909</td>
    <td class="tg-0lax">1</td>
    <td class="tg-0lax">1.03</td>
    <td class="tg-0lax">0.98</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">0</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">169</td>
  </tr>
  <tr>
    <td class="tg-0lax">d12V6</td>
    <td class="tg-0lax">5250</td>
    <td class="tg-0lax">2432</td>
    <td class="tg-0lax">909</td>
    <td class="tg-0lax">0.99</td>
    <td class="tg-0lax">1.01</td>
    <td class="tg-0lax">0.98</td>
    <td class="tg-0lax">118</td>
    <td class="tg-0lax">24</td>
    <td class="tg-0lax">7</td>
    <td class="tg-0lax">3</td>
    <td class="tg-0lax">-</td>
    <td class="tg-0lax">0</td>
  </tr>
</table>

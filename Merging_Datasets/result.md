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
    <td class="tg-baqh">d<sub>1</sub>V<sub>1</sub></td>
    <td class="tg-baqh">77</td>
    <td class="tg-baqh">120</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">14</td>
    <td class="tg-baqh">8</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>1</sub>V<sub>2</sub></td>
    <td class="tg-baqh">77</td>
    <td class="tg-baqh">120</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">12</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>1</sub>V<sub>3</sub></td>
    <td class="tg-baqh">76</td>
    <td class="tg-baqh">120</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0.97</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">4</td>
    <td class="tg-baqh">6</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>1</sub>V<sub>4</sub></td>
    <td class="tg-baqh">82</td>
    <td class="tg-baqh">121</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">8</td>
    <td class="tg-baqh">12</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>1</sub>V<sub>5</sub></td>
    <td class="tg-baqh">82</td>
    <td class="tg-baqh">121</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">13</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>1</sub>V<sub>6</sub></td>
    <td class="tg-baqh">81</td>
    <td class="tg-baqh">121</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">4</td>
    <td class="tg-baqh">8</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>2</sub>V<sub>1</sub></td>
    <td class="tg-baqh">112</td>
    <td class="tg-baqh">61</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">17</td>
    <td class="tg-baqh">1</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>2</sub>V<sub>2</sub></td>
    <td class="tg-baqh">112</td>
    <td class="tg-baqh">61</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">1</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>2</sub>V<sub>3</sub></td>
    <td class="tg-baqh">112</td>
    <td class="tg-baqh">61</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>2</sub>V<sub>4</sub></td>
    <td class="tg-baqh">115</td>
    <td class="tg-baqh">60</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">18</td>
    <td class="tg-baqh">3</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>2</sub>V<sub>5</sub></td>
    <td class="tg-baqh">115</td>
    <td class="tg-baqh">60</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">4</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>2</sub>V<sub>6</sub></td>
    <td class="tg-baqh">115</td>
    <td class="tg-baqh">60</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">2</td>
    <td class="tg-baqh">2</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>3</sub>V<sub>1</sub></td>
    <td class="tg-baqh">98</td>
    <td class="tg-baqh">147</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">21</td>
    <td class="tg-baqh">11</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>3</sub>V<sub>2</sub></td>
    <td class="tg-baqh">98</td>
    <td class="tg-baqh">147</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">16</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>3</sub>V<sub>3</sub></td>
    <td class="tg-baqh">97</td>
    <td class="tg-baqh">147</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0.97</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">6</td>
    <td class="tg-baqh">7</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>3</sub>V<sub>4</sub></td>
    <td class="tg-baqh">109</td>
    <td class="tg-baqh">154</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">14</td>
    <td class="tg-baqh">15</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>3</sub>V<sub>5</sub></td>
    <td class="tg-baqh">109</td>
    <td class="tg-baqh">154</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">17</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>3</sub>V<sub>6</sub></td>
    <td class="tg-baqh">108</td>
    <td class="tg-baqh">154</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0.97</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">6</td>
    <td class="tg-baqh">9</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>4</sub>V<sub>1</sub></td>
    <td class="tg-baqh">202</td>
    <td class="tg-baqh">167</td>
    <td class="tg-baqh">114</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">30</td>
    <td class="tg-baqh">12</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>4</sub>V<sub>2</sub></td>
    <td class="tg-baqh">201</td>
    <td class="tg-baqh">167</td>
    <td class="tg-baqh">114</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">18</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>4</sub>V<sub>3</sub></td>
    <td class="tg-baqh">199</td>
    <td class="tg-baqh">167</td>
    <td class="tg-baqh">114</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">6</td>
    <td class="tg-baqh">8</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>4</sub>V<sub>4</sub></td>
    <td class="tg-baqh">226</td>
    <td class="tg-baqh">165</td>
    <td class="tg-baqh">114</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">26</td>
    <td class="tg-baqh">18</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>4</sub>V<sub>5</sub></td>
    <td class="tg-baqh">226</td>
    <td class="tg-baqh">165</td>
    <td class="tg-baqh">114</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">21</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>4</sub>V<sub>6</sub></td>
    <td class="tg-baqh">224</td>
    <td class="tg-baqh">165</td>
    <td class="tg-baqh">114</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">9</td>
    <td class="tg-baqh">9</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>5</sub>V<sub>1</sub></td>
    <td class="tg-baqh">248</td>
    <td class="tg-baqh">156</td>
    <td class="tg-baqh">4</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">61</td>
    <td class="tg-baqh">7</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>5</sub>V<sub>2</sub></td>
    <td class="tg-baqh">247</td>
    <td class="tg-baqh">156</td>
    <td class="tg-baqh">4</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">9</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>5</sub>V<sub>3</sub></td>
    <td class="tg-baqh">246</td>
    <td class="tg-baqh">156</td>
    <td class="tg-baqh">4</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">3</td>
    <td class="tg-baqh">5</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>5</sub>V<sub>4</sub></td>
    <td class="tg-baqh">253</td>
    <td class="tg-baqh">155</td>
    <td class="tg-baqh">4</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">1.01</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">64</td>
    <td class="tg-baqh">13</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>5</sub>V<sub>5</sub></td>
    <td class="tg-baqh">253</td>
    <td class="tg-baqh">154</td>
    <td class="tg-baqh">4</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">17</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>5</sub>V<sub>6</sub></td>
    <td class="tg-baqh">251</td>
    <td class="tg-baqh">153</td>
    <td class="tg-baqh">4</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">9</td>
    <td class="tg-baqh">5</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>6</sub>V<sub>1</sub></td>
    <td class="tg-baqh">338</td>
    <td class="tg-baqh">274</td>
    <td class="tg-baqh">118</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">1.05</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">71</td>
    <td class="tg-baqh">21</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>6</sub>V<sub>2</sub></td>
    <td class="tg-baqh">336</td>
    <td class="tg-baqh">274</td>
    <td class="tg-baqh">118</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">1.05</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">29</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>6</sub>V<sub>3</sub></td>
    <td class="tg-baqh">334</td>
    <td class="tg-baqh">274</td>
    <td class="tg-baqh">118</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">1.05</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">14</td>
    <td class="tg-baqh">8</td>
    <td class="tg-baqh">4</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>6</sub>V<sub>4</sub></td>
    <td class="tg-baqh">390</td>
    <td class="tg-baqh">283</td>
    <td class="tg-baqh">118</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">1.03</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">96</td>
    <td class="tg-baqh">34</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>6</sub>V<sub>5</sub></td>
    <td class="tg-baqh">390</td>
    <td class="tg-baqh">281</td>
    <td class="tg-baqh">118</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">1.02</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">41</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>6</sub>V<sub>6</sub></td>
    <td class="tg-baqh">386</td>
    <td class="tg-baqh">280</td>
    <td class="tg-baqh">118</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">1.01</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">24</td>
    <td class="tg-baqh">11</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>7</sub>V<sub>1</sub></td>
    <td class="tg-baqh">4526</td>
    <td class="tg-baqh">4</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">9</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>7</sub>V<sub>2</sub></td>
    <td class="tg-baqh">4526</td>
    <td class="tg-baqh">4</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">9</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>7</sub>V<sub>3</sub></td>
    <td class="tg-baqh">4526</td>
    <td class="tg-baqh">4</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">7</td>
    <td class="tg-baqh">2</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>7</sub>V<sub>4</sub></td>
    <td class="tg-baqh">4873</td>
    <td class="tg-baqh">2</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">7</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>7</sub>V<sub>5</sub></td>
    <td class="tg-baqh">4873</td>
    <td class="tg-baqh">2</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">7</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>7</sub>V<sub>6</sub></td>
    <td class="tg-baqh">4873</td>
    <td class="tg-baqh">2</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">7</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>8</sub>V<sub>1</sub></td>
    <td class="tg-baqh">7290</td>
    <td class="tg-baqh">87</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">1949</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>8</sub>V<sub>2</sub></td>
    <td class="tg-baqh">7290</td>
    <td class="tg-baqh">87</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">1949</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>8</sub>V<sub>3</sub></td>
    <td class="tg-baqh">7285</td>
    <td class="tg-baqh">87</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">4</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1916</td>
    <td class="tg-baqh">29</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>8</sub>V<sub>4</sub></td>
    <td class="tg-baqh">7721</td>
    <td class="tg-baqh">87</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">1925</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>8</sub>V<sub>5</sub></td>
    <td class="tg-baqh">7721</td>
    <td class="tg-baqh">87</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">1925</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>8</sub>V<sub>6</sub></td>
    <td class="tg-baqh">7712</td>
    <td class="tg-baqh">87</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">9</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">1916</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>9</sub>V<sub>1</sub></td>
    <td class="tg-baqh">1145</td>
    <td class="tg-baqh">101</td>
    <td class="tg-baqh">31</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0.72</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">8</td>
    <td class="tg-baqh">17</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>9</sub>V<sub>2</td>
    <td class="tg-baqh">1145</td>
    <td class="tg-baqh">101</td>
    <td class="tg-baqh">31</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0.72</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">17</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>9</sub>V<sub>3</sub></td>
    <td class="tg-baqh">1144</td>
    <td class="tg-baqh">101</td>
    <td class="tg-baqh">31</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0.72</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">14</td>
    <td class="tg-baqh">2</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>9</sub>V<sub>4</sub></td>
    <td class="tg-baqh">1146</td>
    <td class="tg-baqh">101</td>
    <td class="tg-baqh">31</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0.72</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">17</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>9</sub>V<sub>5</sub></td>
    <td class="tg-baqh">1146</td>
    <td class="tg-baqh">101</td>
    <td class="tg-baqh">31</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0.72</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">17</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>9</sub>V<sub>6</sub></td>
    <td class="tg-baqh">1145</td>
    <td class="tg-baqh">101</td>
    <td class="tg-baqh">31</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0.72</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">14</td>
    <td class="tg-baqh">2</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>10</sub>V<sub>1</sub></td>
    <td class="tg-baqh">5042</td>
    <td class="tg-baqh">2197</td>
    <td class="tg-baqh">843</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">0.9</td>
    <td class="tg-baqh">0.93</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">41</td>
    <td class="tg-baqh">106</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>10</sub>V<sub>2</sub></td>
    <td class="tg-baqh">5042</td>
    <td class="tg-baqh">2197</td>
    <td class="tg-baqh">843</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">0.9</td>
    <td class="tg-baqh">0.93</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">116</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>10</sub>V<sub>3</sub></td>
    <td class="tg-baqh">5018</td>
    <td class="tg-baqh">2197</td>
    <td class="tg-baqh">843</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">0.9</td>
    <td class="tg-baqh">0.93</td>
    <td class="tg-baqh">71</td>
    <td class="tg-baqh">21</td>
    <td class="tg-baqh">5</td>
    <td class="tg-baqh">2</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>10</sub>V<sub>4</sub></td>
    <td class="tg-baqh">4957</td>
    <td class="tg-baqh">2394</td>
    <td class="tg-baqh">882</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1.01</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">143</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>10</sub>V<sub>5</td>
    <td class="tg-baqh">4957</td>
    <td class="tg-baqh">2394</td>
    <td class="tg-baqh">882</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1.01</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">143</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>10</sub>V<sub>6</sub></td>
    <td class="tg-baqh">4928</td>
    <td class="tg-baqh">2378</td>
    <td class="tg-baqh">882</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">87</td>
    <td class="tg-baqh">22</td>
    <td class="tg-baqh">7</td>
    <td class="tg-baqh">3</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>11</sub>V<sub>1</sub></td>
    <td class="tg-baqh">5564</td>
    <td class="tg-baqh">2245</td>
    <td class="tg-baqh">870</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">0.89</td>
    <td class="tg-baqh">0.94</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">40</td>
    <td class="tg-baqh">110</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>11</sub>V<sub>2</sub></td>
    <td class="tg-baqh">5564</td>
    <td class="tg-baqh">2245</td>
    <td class="tg-baqh">870</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">0.89</td>
    <td class="tg-baqh">0.94</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">120</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>11</sub>V<sub>3</sub></td>
    <td class="tg-baqh">5539</td>
    <td class="tg-baqh">2245</td>
    <td class="tg-baqh">870</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">0.89</td>
    <td class="tg-baqh">0.94</td>
    <td class="tg-baqh">75</td>
    <td class="tg-baqh">21</td>
    <td class="tg-baqh">5</td>
    <td class="tg-baqh">2</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>11</sub>V<sub>4</sub></td>
    <td class="tg-baqh">5490</td>
    <td class="tg-baqh">2469</td>
    <td class="tg-baqh">909</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1.01</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">143</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>11</sub>V<sub>5</sub></td>
    <td class="tg-baqh">5490</td>
    <td class="tg-baqh">2469</td>
    <td class="tg-baqh">909</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">1.01</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">143</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>11</sub>V<sub>6</sub></td>
    <td class="tg-baqh">5461</td>
    <td class="tg-baqh">2453</td>
    <td class="tg-baqh">909</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">1</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">87</td>
    <td class="tg-baqh">22</td>
    <td class="tg-baqh">7</td>
    <td class="tg-baqh">3</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>12</sub>V<sub>1</sub></td>
    <td class="tg-baqh">15822</td>
    <td class="tg-baqh">3818</td>
    <td class="tg-baqh">1262</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">0.95</td>
    <td class="tg-baqh">0.96</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">524</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>12</sub>V<sub>2</sub></td>
    <td class="tg-baqh">15822</td>
    <td class="tg-baqh">3818</td>
    <td class="tg-baqh">1262</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">0.95</td>
    <td class="tg-baqh">0.96</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">524</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>12</sub>V<sub>3</sub></td>
    <td class="tg-baqh">15729</td>
    <td class="tg-baqh">3818</td>
    <td class="tg-baqh">1262</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">0.95</td>
    <td class="tg-baqh">0.96</td>
    <td class="tg-baqh">176</td>
    <td class="tg-baqh">44</td>
    <td class="tg-baqh">252</td>
    <td class="tg-baqh">4</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>12</sub>V<sub>4</sub></td>
    <td class="tg-baqh">15080</td>
    <td class="tg-baqh">3589</td>
    <td class="tg-baqh">1262</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">1.03</td>
    <td class="tg-baqh">0.96</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">977</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>12</sub>V<sub>5</sub></td>
    <td class="tg-baqh">15080</td>
    <td class="tg-baqh">3589</td>
    <td class="tg-baqh">1262</td>
    <td class="tg-baqh">0.99</td>
    <td class="tg-baqh">1.03</td>
    <td class="tg-baqh">0.96</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">0</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">977</td>
  </tr>
  <tr>
    <td class="tg-baqh">d<sub>12</sub>V<sub>6</sub></td>
    <td class="tg-baqh">14944</td>
    <td class="tg-baqh">3540</td>
    <td class="tg-baqh">1262</td>
    <td class="tg-baqh">0.98</td>
    <td class="tg-baqh">1.01</td>
    <td class="tg-baqh">0.96</td>
    <td class="tg-baqh">579</td>
    <td class="tg-baqh">85</td>
    <td class="tg-baqh">229</td>
    <td class="tg-baqh">13</td>
    <td class="tg-baqh">-</td>
    <td class="tg-baqh">0</td>
  </tr>
</table>

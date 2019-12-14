
package fusion.comerger.general.output;

public class Evaluator
{
    public ResultData compare(Alignment as1, Alignment as2)
    {
        if (as1 == null || as2 == null) {
            return null;
        }
        int found = as1.size();
        int exist = as2.size();
        int correct = 0;

        if (found == 0 || exist == 0) {
            return null;
        }
        
        ResultData result = new ResultData();
        Alignment errorAlignment = new Alignment();
        Alignment correctAlignment = new Alignment();
        Alignment lostAlignment = new Alignment();
        
        for (int i = 0; i < found; i++) {
            Mapping map1 = as1.getMapping(i);
            boolean flag = true;
            for (int j = 0; j < exist; j++) {
                Mapping map2 = as2.getMapping(j);
                if (map1.equals(map2)) {
                    correct++;
                    correctAlignment.addMapping(map1);
                    flag = false;
                    break;
                }
            }
            if (flag == true) {
                errorAlignment.addMapping(map1);
            }
        }
        for (int i = 0; i < exist; i++) {
            Mapping map1 = as2.getMapping(i);
            boolean flag = true;
            for (int j = 0; j < found; j++) {
                Mapping map2 = as1.getMapping(j);
                if (map1.equals(map2)) {
                    flag = false;
                    break;
                }
            }
            if (flag == true) {
                lostAlignment.addMapping(map1);
            }
        }
        System.out.println("Found: " + found + 
        		", Exist: " + exist + ", Correct: " + correct);

        double prec = (double) correct / found;
        double rec = (double) correct / exist;
        System.out.println("Precision: " + prec + ", Recall: " + rec);
        // F-measure
        double fm = (1 + Parameters.alpha) * (prec * rec) / (Parameters.alpha * prec + rec);
        System.out.println("F-Measure: " + fm);

        result.setFound(found);
        result.setExist(exist);
        result.setCorrect(correct);
        
        result.setPrecision(prec);
        result.setRecall(rec);
        result.setFMeasure(fm);
        
        result.setCorrectAlignment(correctAlignment);
        result.setErrorAlignment(errorAlignment);
        result.setLostAlignment(lostAlignment);
        
        return result;
    }
}


package fusion.comerger.algorithm.matcher;

import fusion.comerger.general.output.Alignment;

public interface AbstractMatcher
{
    public void match();

    public Alignment getAlignment();

    public Alignment getClassAlignment();

    public Alignment getPropertyAlignment();

    public Alignment getInstanceAlignment();
}

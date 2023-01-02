import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class PostTester implements FsmModel {
    private SUT systemUnderTest = new SUT();

    private PostCall state = PostCall.Start;
    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean b) {
        if(b){
            systemUnderTest = new SUT();
        }
        state = PostCall.Start;
    }

    public boolean PostCallSuccessGuard(){return getState().equals(PostCall.Start);}
    public @Action void PostCallSuccess() {
        assertEquals(true,SUT.PostApiCall(true));
    }

    public boolean PostCallFailedGuard(){return getState().equals(PostCall.Start);}
    public @Action void PostCallFailed(){
        state = PostCall.Bad;
        assertEquals(false,SUT.PostApiCall(false));
    }

    @Test
    public void PostTesterRunner() {
        final GreedyTester tester = new GreedyTester(new PostTester());
        tester.setRandom(new Random());
        tester.buildGraph();
        tester.addListener(new StopOnFailureListener());
        tester.addListener("verbose");
        tester.addCoverageMetric(new TransitionPairCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        tester.generate(5);
        tester.printCoverage();
    }
}

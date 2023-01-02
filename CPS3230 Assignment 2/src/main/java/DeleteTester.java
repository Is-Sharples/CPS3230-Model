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

public class DeleteTester implements FsmModel {
    private SUT systemUnderTest = new SUT();

    private DeleteCall state = DeleteCall.Start;
    @Override
    public Object getState() {
        return state;
    }

    @Override
    public void reset(boolean b) {
        if(b){
            systemUnderTest = new SUT();
        }
        state = DeleteCall.Start;
    }

    public boolean DeleteCallSuccessGuard(){return getState().equals(DeleteCall.Start);}
    public @Action void DeleteCallSuccess() {
        assertEquals(200,SUT.DeleteApiCall("00de4d33-5d10-4151-ad8f-39dca960ddce"));
    }

    public boolean DeleteCallFailedGuard(){return getState().equals(DeleteCall.Start);}
    public @Action void DeleteCallFailed(){
        state = DeleteCall.Bad;
        assertEquals(400,SUT.DeleteApiCall("Bad User ID"));
    }

    @Test
    public void DeleteTesterRunner() {
        final GreedyTester tester = new GreedyTester(new DeleteTester());
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
